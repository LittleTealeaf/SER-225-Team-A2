### Test Case Information

| TEST CASE ID | SCP-41 |
| :--- | :--- |
| Owner of Test | Thomas Kwashnak |
| Test Name | Game Completion Bug Fix |
| Date of Last Revision | 10/2/2021 |
| Test Objective | Ensure that the game is completable, and returns the player to the main menu after beating the last level |

### Procedure

|Step | Action | Expected Result | Pass/Fail     |
|:---:| :---        |    :----  | :---: |
|1|Run the game|The game successfully opens|P|
|2|Click on Level Select|The Level Select screen successfully opens|P|
|3|Click on the last level|The last level successfully opens|P|
|4|Beat the last level|The last level is beatable, the "Level complete" dialogue displays, and after a few seconds the user is taken to the menu screen|P|
|5|Click on play game|The first level is loaded|P|
|6|Beat the first level|The first level is beatable, the "level complete" dialogue displays, and after a few seconds the user is taken to the next level|P|

### Test Completion

- **Tester**: Nicholas Tourony
- **Date of Test**: 11/7/2021
- **Test Result**: Passed