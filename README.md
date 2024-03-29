Game
====


This project implements the game of Breakout.

Names: Matthew Rose & Stephanie Zhang

### Timeline

Start Date: 1/26/19

Finish Date: 2/11/19

Hours Spent: 30

### Resources Used
* Lab Frogger
* Lab bounce
* Office Hours (thank you Natalie and Inchan!)

### Running the Program

Main class:
Gameplay.java

Run the main method in Gameplay.java to start. The arrow keys must be pressed to start the game from the splash screen. If the player loses three lives and the lose screen appears, then the player can press the arrow keys again to restart the game. If the player removes all blocks from Levels 1, 2, and 3 and reaches the win screen, they can again use to arrow keys to restart the game. The high score keeps track of the highest score from the current run of the program (between resets after wins/losses).

Data files needed: (all in resources)
* ball.gif
* brick1.gif
* brick2.gif
* brick3.gif
* breakout_background.png
* breakout_win.png
* breakout_lose.png
* bouncerPowerUpTest.txt
* paddlePowerUpTest.txt
* pointsPowerUpTest.txt
* Config1.txt
* Config2.txt
* Config3.txt
* Extraballpower.gif
* Lift.txt
* Paddle.gif
* paddleBounce.txt
* wallBounce.txt
* sideBlock.txt
* Sizepower.gif
* pointspower.gif


Key/Mouse inputs:

* LEFT/RIGHT: move the paddle on the bottom of the screen to left/right.
* ",": checks that the ball bounces off of the corner correctly on Level 1; checks if the additional bouncer power up works and actually adds bouncer on Level 2; checks that the ball bounces off of the paddle on Level 3.
* ".": checks that a block breaks after being struck by a block on Level 1; checks if the points power up works and actually increases the points on Level 2; checks if the ball bounces off of the right wall of screen on Level 3.
* "/": checks that the number of lives decreases by one if the ball is transported below the bottom of the screen on Level 1; checks if the wider paddle power up works and actually makes the paddle wider on Level 2; checks if the ball correctly bounces off of the side of a block on Level 3.

Cheat keys:

* "R": reset all balls to the center of the screen.
* "L": add an extra life.
* "SPACE": remove a block from the screen.

Known Bugs:

*  In order for the corner test to work properly on Level 1 (“,”), there cannot be a block in the top left corner of Level 1 

Extra credit:
* Blocks that are multiple hit blocks change colors to signify how many hits left are needed. For example, a 3-hit block that is hit once looks like a 2-hit block afterwards.
* The power up for wider paddle makes sure the paddle stays within the bounds of the screen even if the paddle is on the edge of the screen when it receives the powerup. It moves the paddle inwards by the extra amount so the whole paddle stays within the screen.
* After additional bouncers are generated by the powerup, if any of the bouncers fall, it doesn’t respawn and the number of lives remains unchanged until the last bouncer falls (it doesn’t matter if it was the original bouncer or not). Therefore, the additional bouncers function as actual “additional” bouncers instead of like the original one that can respawn many times. When there’s only one bouncer left it behaves as it normally does.
* If you hit two blocks at the same time, you can clear both of those blocks regardless of how many hits should clear it. Furthermore, the ball doesn’t bounce off the block normally, rather it can just travel through directly. 


### Notes
* The high score is initially set to zero at the start of the game (because this is the first time it is being played) and is updated after the player wins or loses and restarts the game.
* Each level is more difficult in 2 ways. The ball gets faster and the blocks start lower with each level.
* Level configuration is a text file with numbers separated by commas. The integer before the decimal is the number of hits it takes to break the block, and the integer after the decimal corresponds to the power up. If the number is 0, that means there is an empty space there.


### Impressions

*  It’s not easy making a game! Much appreciation to programmers in the game industry.

