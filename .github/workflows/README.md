Workflows:

| `YML File` | `Runs on push?` | `Runs on demand?` | `Description` |
| :------------ | :--- | :--- | :--- |
|<img width=90/>|<img width=60/>|<img width=600/>|
| auto_hotspot | yes | yes | Run against Hotspot JVM on macos-latest, ubuntu-latest, windows-latest |
| auto_jacobin | yes | yes | Jacotest against Jacobin on all O/Ses |
| one_test_case_jacobin | no | yes | Run one selected test case against Jacobin (manual edit) |
| one_test_case_hotspot | no | yes | Run one selected test case against the Hotspot JVM (manual edit) |

Note that "all O/Ses" includes macos-26-intel, macos-latest, ubuntu-24.04-arm, ubuntu-latest, windows-11-arm, and windows-latest.
