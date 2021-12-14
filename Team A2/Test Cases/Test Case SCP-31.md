### Test Case Information

| TEST CASE ID | SCP-31 |
| :--- | :--- |
| Owner of Test | Jacob Conrad |
| Test Name | Implement Health System Test |
| Date of Last Revision | 10/27/2021 |
| Test Objective | Verify that the user has a health system that is visible and allows them to take damage multiple times before dying |

### Procedure

|Step | Action | Expected Result | Pass/Fail     |
|:---:| :---        |    :----  | :---: |
|1|Run the game|The main menu successfully displays|Pass|
|2|Press space on "Play Game"|The level select menu should be open|Pass|
|3|Press space on the tutorial level|The tutorial level should load, and there should be three red hearts at the top of the screen representing the users health|Pass|
|4|Receive three projectiles from an enemy|The user should lose a heart (heart turns grey) each time and dies on the third one|Pass|
|5|Restart the level by pressing space|Level and hearts should be reset|Pass|
|6|Touch an enemy|All hearts should be lost|Pass|
|7|Press escape to go to the main menu|Hearts should no longer be visible|Pass|
|8|Restart the tutorial level|Health should be visible again and be at three hearts (not the previous zero hearts from losing and going to the menu)|Pass

### Test Completion

- **Tester**: Thomas Kwashnak
- **Date of Test**: 10/27/2021
- **Test Result**: Pass