Workflows:

| `YML File` | `Runs on push?` | `Runs on demand?` | `Description` |
| :------------ | :--- | :--- | :--- |
|<img width=90/>|<img width=60/>|<img width=600/>|
| amd64_jacobin | yes | yes | Run against Jacobin on 3 AMD/Intel O/Ses |
| arm64_jacobin | yes | yes | Run against Jacobin on Ubuntu and Windows on ARM64 |
| auto_openjdk | yes | no | Run against OpenJDK JVM on 3 AMD/Intel O/Ses |
| galt | no | yes | Same as amd64_jacobin but in "galt" mode |
| narrow_testing | no | yes | Same as amd64_jacobin but with selected test cases (manual edit) |

