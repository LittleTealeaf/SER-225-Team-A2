### Test Case Information

| TEST CASE ID | SCP-49 |
| :--- | :--- |
| Owner of Test | Nicholas Tourony |
| Test Name | Difficulty Test |
| Date of Last Revision | 11/30/2021 |
| Test Objective | Ensure that the difficulty settings change the amount of health the cat has and changes the movement speed of the enemies and their projectiles. |

### Procedure

|Step | Action | Expected Result | Pass/Fail     |
|:---:| :---        |    :----  | :---: |
|1| Run the game| The game successfully opens |Pass|
|2| Select the difficulty menu. | The difficulty menu opens. | Pass |
|3| Select normal difficulty | The game lets you select normal difficulty and automatically puts you back to the main menu. | Pass|
|4| Start the game. | The cat is loaded into the first level with 3 health and the enemies and projectiles move at their slowest speed. | Pass|
|5| Take projectile damage until the character dies | The cat can take 3 projectile hits before dying. | Pass|
|6| Respawn and run into an enemy. | The cat instantly dies. | Pass |
|7| Select hard difficulty and start the game | The cat is loaded into the first level with 2 health and the enemies move faster. | Pass |
|8| Take projectile damage until the character dies | The cat can take 2 projectile hits before dying. | Pass|
|9| Respawn and run into an enemy. | The cat instantly dies. | Pass |
|10| Select hardcore difficulty and start the game.  | The cat is loaded into the first level with 1 health and the enemies move even faster. | Pass |
|11| Take damage. | The cat dies. | Pass |
|12| Press escape. | The player is returned to the main menu. | Pass |
|13| Load into level 6, die to any enemy, and press space. | The player is restarted at the tutorial level. | Pass |
|14| Restart the game and beat each level at every difficulty to ensure the levels are still possible. (Use the level select to make hardcore mode easier) | Each level is beat on every difficulty | Passed |

### Test Completion

- **Tester**: Thomas Kwashnak
- **Date of Test**: 11/30/2021
- **Test Result**: Passed