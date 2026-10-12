"""
JACOBIN-985
Scan a Jacotest stdout/stderr log file and emit stdout consisting of:
    test-case      Subset of the line beginning with "INVOKE"
"""
def process_text_file(filepath):
    """
    This is the function that does the work.

    Parameters
    ----------
    filepath : string
        This is the file that holds the Jacotest combined stdout and stderr.

    Returns
    -------
    None
    """
    ex_token = "Executing"
    jdk_phrase = "JDK library function was requested"
    invoke = "INVOKE"

    tcname = None  # Will store the test case name found after 'ex'

    try:
        with open(filepath, "r", encoding="utf-8") as file:
            # Main loop scanning line by line (handles EOF naturally)
            for line in file:
                line_stripped = line.strip()
                if not line_stripped:
                    continue

                tokens = line_stripped.split()

                # 1. Scan for 'ex'
                found_ex = False
                for ix, token in enumerate(tokens):
                    if token == ex_token or ex_token in token:
                        found_ex = True
                        # tcname = the next token after ex
                        if ix + 1 < len(tokens):
                            tcname = tokens[ix + 1]
                        break

                if found_ex:
                    # Save tcname and continue main loop
                    continue

                # 2. Scan for 'jdk'
                if jdk_phrase in line_stripped:
                    # Extract text beginning with "INVOKE" if present,
                    # otherwise fallback to entire line
                    if "INVOKE" in line_stripped:
                        invoke_idx = line_stripped.index(invoke)
                        invoke_subset = line_stripped[invoke_idx:]
                        print(f"{tcname}\t{invoke_subset}")
                    else:
                        print(f"{tcname}\t{line_stripped}")
                    continue

    except FileNotFoundError:
        print(f"Error: The file '{filepath}' could not be found.")
    except IOError as ex:
        print(f"An unexpected I/O error occurred in file '{filepath}': {ex}")


process_text_file("log.txt")
