### Test Case Information
| TEST CASE ID | SCP-56 |
| :--- | :--- |
| Owner of Test | Thomas Kwashnak |
| Test Name | Test Game Speed |
| Date of Last Revision | 11/16/2021 |
| Test Objective | Ensure that the game runs consistently through multiple distributions |
| Test Notes| Due to the nature of this test case, multiple runs of the test must be conducted on various machines of different make and models. Measurements will be taken using a recording software, and should not be expected to be perfect|
|Software Required|Screen recording software: Needs to be able to record the screen such that the tester can go back and accurately measure the time between events|


### Procedure

|Step | Action | Expected Result | Measurement Field |
|:---:| :---        |    :----  | :----:|
|1|Run the game|The game successfully opens||
|2|Begin recording|A screen recorder is started that records the game||
|3|Click *Play Game*|The tutorial level is successfully opened||
|4|Walk to the left-most edge of the level|The cat is at the left most bound of the tutorial level||
|5|Hold `D/Right Arrow` until the cat is blocked from moving by the tree|The cat moves from the edge of the map into the tree|**Measurement 1:** The time it takes starting when the cat begins moving to when the cat finishes moving|
|6|Move the cat out from under the tree|The cat has a clear sky above it such that the cat is able to jump||
|7|Hold `Space/W/Up Arrow` until the cat reaches the ground again|The cat jumps and lands back on the ground|**Measurement 2:** The time it takes starting when the cat begins jumping to when the cat hits the ground again|
|8|Hold `e` until the player shoots a reasonable amount of fireballs|The cat shoots multiple fireballs|**Measurement 3:** The time between two consecutive fireballs spawning (multiple attempts can be averaged if needed)|\
|9|Complete the tutorial level|When the tutorial level is completed, the `level complete` is shown, and after a few seconds the player is loaded into the next level|**Measurement 4:** The time that the `level complete` screen is shown

### Test Execution
Instances of tests are listed in table below (template provided). Measurements listed in instructions above are listed below for test completer to evaluate.

**Overall Test Completion** indicates whether all steps were completed and no errors / inability to follow instructions occurred.


|Date of Test|Tester Name|Computer Configuration | Measurement 1|Measurement 2|Measurement 3|Measurement 4| Overall Test Completion|
|:---:|:---|:---|:---:|:---:|:---:|:---:|:---:|
|[DATE]|[TESTER]|[CONFIG]|[MEASURE 1]|[MEASURE 2]|[MEASURE 3]|[MEASURE 4]|[PASS/FAIL]|

[comment]: <> (Add test rows to end here ^^)

### Completion Criteria
 - There are no outliers on all measurements

### Test Completion
- **Tester**: [TEST REVIEWER NAME]
- **Date of Test**: [TEST COMPLETION DATE]
- **Test Result**: [TEST RESULT]