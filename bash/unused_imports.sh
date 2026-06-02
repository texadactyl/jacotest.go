#!/usr/bin/env bash
# Walks a Java source tree and identifies every unused import.
# Specifying option -Z, also removes them in place.

SOURCE_DIR="./tests/"

set -e

DELETE=false
[[ "${1:-}" == "-Z" ]] && DELETE=true

while IFS= read -r file; do
  body=$(sed '/^[[:space:]]*import[[:space:]]/d; s|//.*||' "$file")

  while IFS= read -r lineno; do
    line=$(sed -n "${lineno}p" "$file")
    simple=$(echo "$line" | sed 's/.*\.\([A-Za-z_$][A-Za-z0-9_$]*\);.*/\1/')

    [[ "$line" == *\** ]] && continue

    if ! echo "$body" | grep -qP "(?<![A-Za-z0-9_\$])${simple}(?![A-Za-z0-9_\$])"; then
      echo "Unused import in $file (line $lineno): $line"
      $DELETE && sed -i "${lineno}d" "$file"
    fi
  done < <(grep -n '^[[:space:]]*import[[:space:]]' "$file" | cut -d: -f1 | sort -rn)
done < <(find "$SOURCE_DIR" -type f -name "*.java")
