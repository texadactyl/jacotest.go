'''

Parse a Jacotest summary report and produce a CSV table
where each row is formmated as follows:

    ERROR CATEGORY, TEST CASE, TRIGGERING MESSAGE

'''

import argparse
import csv
import re
import sys
from pathlib import Path


# Show help and then exit to the O/S
def show_help():
    '''
    Show help and eit to the O/S with an error status code.
    '''
    arg0 = Path(sys.argv[0]).name
    print(f"\nUsage:  {arg0}  [-h]  {{Input summary report}}  {{output CSV file}}\n")
    print("where\n")
    print("\nExit codes:")
    print("\t0\tNormal completion.")
    print("\t1\tSomething went wrong during execution or help requested.\n")
    sys.exit(1)


header_re = re.compile(r"^===== (.+?) =====$")


def write_failures_csv(input_path: str, output_path: str):
    '''
    Write all the CSV lines from the input file.
    '''
    rows = []
    current_category = ""

    # ----- With the open input file, build the intermediate dict rows.
    with open(input_path, "r", encoding="utf-8") as infile:

        # ----- For every input line.
        for raw_line in infile:
            line = raw_line.strip()

            # Detect category section
            m = header_re.match(line)
            if m:
                current_category = m.group(1)
                continue

            # End of section
            if line.startswith("--- Total"):
                current_category = ""
                continue

            # Skip irrelevant lines
            if not current_category or not line:
                continue

            # Parse: "name: reason"
            if ":" not in line:
                continue

            test_name, reason = line.split(":", 1)
            rows.append(
                (
                    current_category.strip(),
                    test_name.strip(),
                    reason.strip(),
                )
            )

    # ----- Sort the rows dict.
    rows.sort(key=lambda r: (r[0], r[1]))

    # ----- Write the output CSV file from dict rows.
    with open(output_path, "w", newline="", encoding="utf-8") as outfile:
        writer = csv.writer(outfile)

        # ----- Heading row.
        writer.writerow(
            ["ERROR CATEGORY", "TEST CASE", "TRIGGERING MESSAGE"]
        )

        # ----- Detail rows.
        for category, test, reason in rows:
            writer.writerow([category, test, reason])


def parse_args():
    '''
    Parse the command-line arguments.
    '''

    parser = argparse.ArgumentParser(add_help=False)
    parser.add_argument("-h", action="store_true")
    parser.add_argument("-in", dest="input", default="reports/latest.txt")
    parser.add_argument("-out", dest="output", default="reports/errSummary.csv")

    args = parser.parse_args()

    # Replicate original Go semantics
    if args.h:
        show_help()

    if args.input is None or args.output is None:
        print(
            f"*** ERROR: Wrong number of command line arguments "
            f"({len(sys.argv) - 1}), should be exactly 2"
        )
        show_help()

    return args.input, args.output

def mainFunc():
    '''
    Simple main function.
    '''

    if not Path("VERSION.txt").exists():
        print("*** ERROR: Wrong directory. Position to the top of the Jacotest tree.")
        show_help()

    input_path, output_path = parse_args()
    write_failures_csv(input_path, output_path)


if __name__ == "__main__":
    mainFunc()
