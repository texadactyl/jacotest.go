'''
Support Jacotest Github Actions

Stdin: OS-name, test-case-name, passed-or-failed

Stdout: CSV that looks like this:
    Test case,macos-latest,ubuntu-24.04-arm,ubuntu-latest,windows-11-arm,windows-latest
    HELPERS,passed,passed,passed,passed,passed
    JACOBIN-0161-0229-classes,failed,failed,failed,failed,failed
    JACOBIN-0161-instantiate-class,passed,passed,passed,passed,passed
    JACOBIN-0211-pbcrypto,failed,failed,failed,failed,failed
    JACOBIN-0217-multidim-2d,passed,passed,passed,passed,passed
    JACOBIN-0217-multidim-3d,passed,passed,passed,passed,passed
    JACOBIN-0227-string-array,passed,passed,passed,passed,passed
    ***JACOBIN-0231-stats,failed,passed,passed,passed,passed
    JACOBIN-0234-0240-0241-array-length,passed,passed,passed,passed,passed
    JACOBIN-0236-bitwise,passed,passed,passed,passed,passed
    Passed-Failed,7-3,8-2,8-2,8-2,8-2

Stderr: Count of discrepancies and a list of those test cases
'''

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

# Keep track of discrepancies
discrepancies = []

# Header
writer.writerow(["Test case"] + oses)

# Rows
for test_case in sorted(results.keys()):
    per_os = results[test_case]
    statuses = [per_os.get(os_name, "") for os_name in oses]
    agree = len(set(statuses)) == 1

    display_name = test_case if agree else f"***{test_case}"
    if not agree:
        discrepancies.append(test_case)

    row = [display_name] + statuses
    writer.writerow(row)

# ---- Summary counts ----
summary = ["Passed-Failed"]

for os_name in oses:
    passed = failed = 0
    for test_case, per_os in results.items():
        val = per_os.get(os_name, "").lower()
        if val == "passed":
            passed += 1
        elif val == "failed":
            failed += 1
    summary.append(f"{passed}-{failed}")

writer.writerow(summary)

# ---- Write discrepancies to stderr ----
winPct = float(passed) * 100.0 / (float(passed + failed))
print(f"Number of discrepancies: {len(discrepancies)}", file=sys.stderr)
print(f"Passed-Failed = {passed}-{failed} ({winPct:.1f}%)", file=sys.stderr)
if discrepancies:
    print("Discrepant test cases:", file=sys.stderr)
    for t in discrepancies:
        print(f" - {t}", file=sys.stderr)
