"""
Given a class suffix and an input data file, generate the
relevant AddStatic statement for each input line triplet.

Input file anatomy:
-------------------

Line 1 - a single string consisting of the class suffix E.g. Integer
Lines 2, 3, 4 - entry #1
Lines 5, 6, 7 - entry #2
etc.

Each line-triplet is creatted into the text input file when pasting from the Java API on the web.
See https://docs.oracle.com/en/java/javase/17/docs/api/constant-values.html
E.g.
public static final byte                format: byte
CURRENCY_SYMBOL                         name: CURRENCY_SYMBOL
0x1a                                    value: 0x1a
"""

import sys


def show_help(my_name):
    """
    Show command-line help and exit to the O/S.
    """
    print(f"\nUsage:   python3   {my_name}   in-path   out-path\n")
    sys.exit(1)


def translator(arg_type, arg_value):
    """
    Given a Java API definition of type and value, translate them into
    forms that are usable for adding statics definitions.
    """
    if arg_type == "byte":
        return "Byte", f"int64({arg_value})"
    if arg_type == "char":
        hexstr = arg_value.removeprefix("'\\u").removesuffix("'")
        if hexstr != arg_value:
            wint = int(hexstr, 16)
            return "Char", f"rune({wint})"
        return "Char", f"rune({arg_value})"
    if arg_type == "double":
        return "Double", f"float64({arg_value})"
    if arg_type == "float":
        float_value = arg_value
        if float_value[-1] == "f":
            float_value = float_value[0:-1]
        return "Float", f"float64({float_value})"
    if arg_type == "int":
        return "Int", f"int64({arg_value})"
    if arg_type == "long":
        long_value = arg_value
        if long_value[-1] == "l":
            long_value = long_value[0:-1]
        return "Long", f"int64({long_value})"
    if arg_type == "short":
        return "Short", f"int64({arg_value})"
    if arg_type == "boolean":
        return "Bool", f"bool({arg_value})"
    print(f"*************** INVALID TYPE: {arg_type}, value: {arg_value}")
    sys.exit(1)

if __name__ == "__main__":

    print("Begin")
    in_counter = 0
    out_counter = 0

    # Process command-line arguments
    # There must be exactly 2 command line parameters.
    nargs = len(sys.argv)
    if nargs != 3:
        show_help(sys.argv[0])
    path_in = sys.argv[1]   # input file
    path_out = sys.argv[2]  # output file


    with open(path_in, encoding="utf-8") as fin:

        class_suffix = fin.readline().strip()
        class_string = "java/lang/" + class_suffix

        with open(path_out, mode="w", encoding="utf-8") as fout:

            lines = fin.readlines()
            columns = []
            ix = 0
            OUT_FORMAT = '_ = AddStatic("{}.{}\", Static{{Type: types.{}, Value: {}}})\n'

            while ix < len(lines):

                line = lines[ix].strip()
                if line == "":
                   break
                columns = line.split(' ')
                in_type = columns[3]

                ix += 1
                line = lines[ix].strip()
                columns = line.split(' ')
                in_name = columns[0]

                ix += 1
                line = lines[ix].strip()
                columns = line.split(' ')
                in_value = columns[0]

                out_type, out_value = translator(in_type, in_value)

                wstr = OUT_FORMAT.format(class_string, in_name, out_type, out_value)
                fout.write(wstr)

                ix += 1
                in_counter += 3
                out_counter += 1

    print(f"Processed {in_counter} input lines and wrote {out_counter} output lines")
    print("End")
