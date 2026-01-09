# Jacobin Path Test Suite

This repository contains a set of test programs for **Jacobin** that validate **Windows and POSIX path semantics**. The tests are designed to check HotSpot-faithful behavior, including edge cases for absolute paths, drive-relative paths, UNC paths, and path normalization.

---

## Test Files

| File | Description | OS Coverage | Notes |
|------|------------|-------------|-------|
| `file-path-windows-only.java` | Tests Windows-specific paths, including drive letters, drive-relative paths (`C:`), drive-rooted paths without a drive (`\foo`), UNC paths, and mixed separators. | Windows only | Exits immediately on non-Windows OS. Fully HotSpot-faithful semantics. |
| `file-path-posix.java` | Tests POSIX-style paths, including absolute paths (`/a/b/c`), relative paths (`foo/bar`), current/parent directory (`.` and `..`), normalization, and mixed concatenation. | POSIX (Linux/macOS) and Windows | Works on any OS; uses POSIX-style paths. |

---

## Windows Path Semantics Tested

* **Absolute drive paths:** `C:\foo`  
* **Drive-relative paths:** `C:`, `C:foo`  
* **Drive-rooted but drive-unspecified:** `\foo`  
* **UNC paths:** `\\server\share\dir\file.txt`  
* **Relative paths:** `foo`  
* **Mixed separator normalization:** `C:/a/b\c`  

### HotSpot-faithful expectations

| Path | isAbsolute() | getRoot() |
|------|--------------|-----------|
| `C:\foo` | true | `C:\` |
| `C:` | false | `C:` |
| `C:foo` | false | `C:` |
| `\foo` | false | `<null>` |
| `\\server\share\dir\file.txt` | true | `\\server\share\` |

> Note: `\foo` is **not absolute** and its root is `<null>` on HotSpot. It is considered **relative to the current drive**.

---

## POSIX Path Semantics Tested

* **Absolute paths:** `/a/b/c`  
* **Relative paths:** `foo/bar`  
* **Current/parent directory:** `.` and `..`  
* **Normalization:** `/x/./y/../z → /x/z`  
* **Concatenation of absolute + relative:** `/a/b` + `c/d`  

### Example expectations

| Path | isAbsolute() | getRoot() | Normalized |
|------|--------------|-----------|------------|
| `/a/b/c` | true | `/` | `/a/b/c` |
| `foo/bar` | false | `<null>` | `foo/bar` |
| `.` | false | `<null>` | `.` |
| `..` | false | `<null>` | `..` |
| `/x/./y/../z` | true | `/` | `/x/z` |
| `/a/b` + `c/d` | true | `/` | `/a/b/c/d` |

---

## Test Cases

file-path-windows-only
file-path-posix

