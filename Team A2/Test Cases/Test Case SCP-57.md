### Test Case Information

| TEST CASE ID | SCP-57 |
| :--- | :--- |
| Owner of Test | Thomas Kwashnak |
| Test Name | Test Player Functionality |
| Date of Last Revision | 11/16/2021 |
| Test Objective | Ensure that all current functionalities of the player is implemented in new Player class and still functional |

Functionality of `Player.java` tested:

- Movement, and movement animations
- Jumping, including gravity and hitting floor
- Animation while jumping
- Collision with enemies
- Shooting, including animations while standing still and moving
- Collision with map boundaries and map walls
- Ability to complete all levels with physics

### Procedure

|Step | Action | Expected Result | Pass/Fail     |
|:---:| :---        |    :----  | :---: |
|0a| **Evaluated on Each Step:** | Player Animation changes as expected (*If a step does not pass this result, fail that step and indicate "Failed Step 0a"*) |`N/A`|
|1| Run the game| The game successfully opens |Pass|
|2| Click "*Play*" |The tutorial level is entered, game does not crash. 3 hearts are displayed on the top of the screen.|Pass|
|3| Hold `A/Left Arrow` |The player moves to the left. The player does not fall off the map and is instead stopped at the edge|Pass|
|4| Hold `D/Right Arrow`|The player moves to the right. The player is stopped once they hit the tree|Pass|
|5| Hold both `A/Left Arrow` and `D/Right Arrow`|The player stands still|Pass|
|6| Tap `Space/W/Up Arrow` | The player short-jumps. The player then falls back down after the button is let go and lands on the ground|Pass|
|7|Hold `Space/W/Up Arrow` | The player high-jumps. The player falls back down after a certain height and and falls back down to the ground|Pass|
|8|Hold `Shift` and repeat steps 3-5|The player moves in the same ways as stated above, but faster|Pass|
|9|Hold `ctrl/S/Down Arrow`|The player crouches|Pass|
|10|Tap `A/Left Arrow`, then hit `Space/W/Up Arrow` quickly followed by `D/Right Arrow`|The player faces left, then switches to facing right while in the air|Pass|
|11|Tap `D/Right Arrow`, then hit `Space/W/Up Arrow` quickly followed by `A/Left Arrow`|The player faces right, then switches to facing left while in the air|Pass|
|12|Tap `A/Left Arrow`, then tap `e` (*Attack Button*)|The player faces to the left, and attacks, sending a projectile to the left|Pass|
|13|Hold `A/Left Arrow`, then tap `e` |The player moves to the left, and attacks while moving|Pass|
|14|Tap `Space/W/Up Arrow`, then tap `e`  while in the air|The player jumps while facing left, and then shoots a projectile while in the air|Pass|
|15|Tap `D/Right Arrow`, then tap `e` |The player faces to the right, and attacks, sending a projectile to the right|Pass|
|16|Hold `D/Right Arrow`, then tap `e` |The player moves to the right, and attacks while moving|Pass|
|17|Tap `Space/W/Up Arrow`, then tap `e` while in the air|The player jumps while facing right, and then shoots a projectile while in the air|Pass|
|18|Hold `e`|The player shoots once, then cannot shoot for a specified duration of time|Pass|
|19|Navigate and run into enemy|The player's health is set to 0 and the player dies. Death animation is played and player falls down to the ground|Pass|
|20|Restart the game|The level is loaded again, and the player's health is back to full|Pass|
|21|Navigate and run into a projectile from an enemy|The player's health is now 2 (reduced by 1)|Pass|
|22|Navigate and attempt to run off the right-most edge of the map (*without hitting the gold level-complete box*)|The player is stopped and cannot run off of the map|Pass|
|23|Hit the complete-level gold box|The level is completed and the player walks off to the right of the screen|Pass|
|24|Hit Escape, go back to main menu|The main menu is displayed|Pass|
|25|Hit "Play Game"|The first level is loaded|Pass|
|26|Play all levels of the game|Each level is completable|Pass|

### Test Completion

- **Tester**: Nicholas Tourony
- **Date of Test**: 11/30/2021
- **Test Result**: Passed