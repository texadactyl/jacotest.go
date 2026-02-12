'''
Script: goVersion.py

Stdin format: go version go1.26.0 windows/amd64

Stdout (sorted by O/S):
    ubuntu-latest    go1.26.0
    windows-latest   go1.26.0
    etc.
'''

import sys
from collections import defaultdict

# results[testcase][os] = status
results = defaultdict(dict)
oses = set()

# Read from stdin
for line in sys.stdin:
    line = line.strip()
    if not line:
        continue
    os_name, _, _, go_version, _ = line.split(None, 5)
    oses.add(os_name)
    results[os_name] = go_version

# Sort OS names
oses = sorted(oses)

# Ensure stdout uses UTF-8 (Python 3.7+)
if hasattr(sys.stdout, "reconfigure"):
    sys.stdout.reconfigure(encoding="utf-8")

# Rows
pad_char = "."
length = 20
for oh_es in sorted(results.keys()):
    go_version = results[oh_es]
    print(f"{oh_es:{pad_char}<{length}}{go_version}")
