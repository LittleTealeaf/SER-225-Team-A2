### Test Case Information
| TEST CASE ID | SCP-52 |
| :--- | :--- |
| Owner of Test | Thomas Kwashnak |
| Test Name | Test Speed-Running Enhancements |
| Date of Last Revision | 11/23/2021 |
| Test Objective |  Testing Additions and Functionality Changes of Speed-Running Enhancements |

#### Tested Enhancements
 - Stopwatch
   - Top-Right of screen when playing
   - Entering from main menu resets timer
   - Stores time took to complete each level, displays current level's time, as well as total time for all previous times
   - Some way to display the user's final result (breakdown of levels) after beating final boss
   - Deaths are in some way detrimental to a user's level time (either make death animation slower, or make death screen longer)
   - Pausing the game pauses the time
 - Level Completed Screens display quicker (shorter time)

### Procedure

|Step | Action | Expected Result | Pass/Fail     |
|:---:| :---        |    :----  | :---: |
|1|Run the game|The game loads up. There is no timer on the main menu.|Pass|
|2|Click Play|The tutorial level is loaded. On the top right is one timer that times the amount of elapsed time since the player started.|Pass|
|3|Navigate and die in the tutorial level|The dead screen has a count-down displayed.|Not implemented|
|4|While the death screen's countdown is counting down, try to hit space to respawn|The player is not able to respawn until the countdown timer is over|Not implemented|
|5|Navigate through the level and die once again. Once the death screen is loaded, hit escape to go back to the main menu|The main menu is loaded and the timer no longer displays|Pass|
|6|Hit "Play" again to go back into the main menu|The tutorial level is loaded, and the timer is reset back to 0|Pass|
|7|Complete the tutorial level|The level completed screen is shown, but only for a brief second before the next level is loaded. On the top right is now two timers, one that displays the total time, and another that displays the elapsed time for the current level|Pass|
|8|Hit `P/Escape` to pause the game|While the game is paused, timers are also paused|Pass|
|9|Exit back to main menu, Select "Difficulty", and select "Hardcore"|The player is brought back to the main menu, and the hardcore difficulty is selected|Pass|
|10|Select "Level Select", then select "Level 3"|Level 3 is loaded, the timer starts running|Pass|
|11|Die, and then respawn|The timer is reset back to 0|Pass|

### Test Completion
- **Tester**: Nicholas Tourony
- **Date of Test**: 11/30/2021
- **Test Result**: Pass