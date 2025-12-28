Workflows:

| `YML File` | `Runs on push?` | `Runs on demand?` | `Description` |
| :------------ | :--- | :--- | :--- |
|<img width=90/>|<img width=60/>|<img width=600/>|
| auto_hotspot | yes | yes | Run against Hotspot JVM on macos-latest, ubuntu-latest, windows-latest |
| auto_jacobin | yes | yes | Jacotest against Jacobin on all O/Ses |
| narrow_jacobin | no | yes | Run selected test cases against Jacobin (manual edit) |
| narrow_hotspot | no | yes | Run selected test cases against the Hotspot JVM (manual edit) |
| galt | no | yes | Same as jacobin_all_oses but in "galt" mode |

Note that "all O/Ses": macos-latest, ubuntu-24.04-arm, ubuntu-latest, windows-11-arm, windows-latest.
