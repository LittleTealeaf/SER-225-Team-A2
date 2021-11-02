### Test Case Information
| TEST CASE ID | SCP-44 |
| :--- | :--- |
| Owner of Test | Thomas Kwashnak |
| Test Name | Test Map Bounds |
| Date of Last Revision | 11/2/21 |
| Test Objective | Ensure that the player can not jump off the edges no matter the dimensions of the map |

### Procedure

|Step | Action | Expected Result | Pass/Fail     |
|:---:| :---        |    :----  | :---: |
|1|Run the game|The game successfully loads and the player is directed to the main menu||
|2|Click on "Play Game"|The tutorial level is loaded and the game starts||
|3|Walk to the left|The player walks for a tiny bit, then hits an invisible "barrier" preventing them from leaving the screen||
|4|Beat the tutorial level, but do not touch the gold box|The player is at the end of the map, but hasn't touched the gold box||
|5|Attempt to walk off the edge on the right of the map|The player hits an invisible barrier preventing them from leaving the screen||
|6|Hit escape, back to main menu|The player is navigated back to the main menu||
|7|Click on "Level Select"|The level select screen displays||
|8|Click on "Level 6"|Level 6 is loaded and begins||
|9|Walk to the left|The player walks for a tiny bit, then hits an invisible "barrier" preventing them from leaving the screen||
|10|Beat the level, but do not touch the gold box|The player is at the end of the map, but hasn't touched the gold box||
|11|Attempt to walk off the edge on the right of the map|The player hits an invisible barrier preventing them from leaving the screen||

### Test Completion
- **Tester**: [TESTER NAME]
- **Date of Test**: DATE OF TEST
- **Test Result**: TEST RESULT