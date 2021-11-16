### Test Case Information
| TEST CASE ID | SCP-57 |
| :--- | :--- |
| Owner of Test | Thomas Kwashnak |
| Test Name | Test Player Functionality |
| Date of Last Revision | 11/16/2021 |
| Test Objective | Ensure that all current functionalities of the player is implemented in new Player class and still functional |

### Procedure

|Step | Action | Expected Result | Pass/Fail     |
|:---:| :---        |    :----  | :---: |
|n| Run the game| The game successfully opens ||
|n| Click "*Play*" |The tutorial level is entered, game does not crash||
|n| Hold `A/Left Arrow` |The player moves to the left. The player does not fall off the map and is instead stopped at the edge||
|n| Hold `D/Right Arrow`|The player moves to the right. The player is stopped once they hit the tree||
|n| Hold both `A/Left Arrow` and `D/Right Arrow`|The player stands still||
|n| Hit `Space/W/Up Arrow` | The player jumps||
|n|Hold `Shift` and repeat steps 3-5|The player moves in the same ways as stated above, but faster||
|n|Hold `ctrl`|The player crouches||
|n|Hit `A/Left Arrow`, then hit `Space/W/Up Arrow` quickly followed by `D/Right Arrow`|The player faces left, then switches to facing right while in the air||
|n|Hit `D/Right Arrow`, then hit `Space/W/Up Arrow` quickly followed by `A/Left Arrow`|The player faces right, then switches to facing left while in the air||



### Test Completion
- **Tester**: [TESTER NAME]
- **Date of Test**: DATE OF TEST
- **Test Result**: TEST RESULT