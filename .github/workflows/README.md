Workflows:

| `YML File` | `Runs on push?` | `Runs on demand?` | `Description` |
| :------------ | :--- | :--- | :--- |
|<img width=90/>|<img width=60/>|<img width=600/>|
| auto_openjdk | yes | no | Run against OpenJDK JVM on ubuntu-latest, windows-latest, macos-latest |
| galt | no | yes | Same as os_latest_jacobin but in "galt" mode |
| jacobin_all_oses | yes | yes | Jacotest against Jacobin on all O/Ses |
| narrow_jacobin | no | yes | Run selected test cases against Jacobin (manual edit) |
| narrow_openjdk | no | yes | Run selected test cases against the OpenJDK JVM (manual edit) |
