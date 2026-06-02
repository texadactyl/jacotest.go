#!/usr/bin/env bash
# Walks a Java source tree and prints every unused import.

SOURCE_TREE_ROOT="./tests"

set -e

while IFS= read -r file; do
  # Strip import block + comments to get the "body" for reference checking
  body=$(sed '/^[[:space:]]*import[[:space:]]/d; s|//.*||' "$file")

  while IFS= read -r line; do
    # Extract the simple class name from the import (last dot-separated token)
    simple=$(echo "$line" | sed 's/.*\.\([A-Za-z_$][A-Za-z0-9_$]*\);.*/\1/')

    # Skip wildcards — can't verify without a compiler
    [[ "$line" == *\** ]] && continue

    # If the simple name doesn't appear in the body, the import is unused
    if ! echo "$body" | grep -qP "(?<![A-Za-z0-9_\$])${simple}(?![A-Za-z0-9_\$])"; then
      echo "$file: $line"
    fi
  done < <(grep -n '^[[:space:]]*import[[:space:]]' "$file" \
           | sed 's/^\([0-9]*\):[[:space:]]*/line \1: /')
done < <(find "$SOURCE_TREE_ROOT" -type f -name "*.java")
