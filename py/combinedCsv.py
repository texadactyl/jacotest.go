import sys
import csv
from collections import defaultdict

# results[testcase][os] = status
results = defaultdict(dict)
oses = set()

# Read from stdin
for line in sys.stdin:
    line = line.strip()
    if not line:
        continue
    os_name, test_case, status = line.split(None, 2)
    oses.add(os_name)
    results[test_case][os_name] = status

# Sort OS names
oses = sorted(oses)

# Ensure stdout uses UTF-8 (Python 3.7+)
if hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding="utf-8")

writer = csv.writer(sys.stdout, lineterminator="\n")

# Header
writer.writerow(["Test case"] + oses)

# Rows
for test_case in sorted(results.keys()):
    row = [test_case] + [results[test_case].get(os_name, "") for os_name in oses]
    writer.writerow(row)
