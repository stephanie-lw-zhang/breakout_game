# High-level design goals of your project
- Make the code readable.
- Make the code flexible (easy to add new features), especially through abstraction. 
# How to add new features to your project
- To add a new powerup, create a subclass that extends the superclass named PowerUp. The subclass must implement the abstract method applyPowerUp, which is the method that applies the power up, such as making the paddle wider. Note here that for this method, no need to write an if statement for “if the powerup intersects with the paddle” because that is written in another method (stepPowerUp) that calls applyPowerUp. However, you have to include this.disappears(root) to make the powerUp disappear after intersecting with the paddle. Any additional powerup subclass must also implement the constructor by passing in an image ideally from the resources folder.

- To add new levels, first create a new block configuration file in the resources folder. The configuration file consists of a text file. Each block or lack thereof is represented by two numbers separated by a period, and the information for each block or lack thereof is separated by a comma. The first number before the period represents the type of block (1 is a one-hit block, 2 is a two-hit block, etc) and the second numbers is the type of power up. Each line of the text file corresponds to a different height in the game. After the configuration file is set up, the private method raiseLevel() must be edited to add a new else if test. This else if test will check if the previous level has been completed and reads in a new block configuration with the new file that was just created.

- To create a new block that requires more hits to be destroyed, change the parameters hits and imageView when initializing the block. Also add another if test in the Block.readBlockConfiguration method to recognize this new number of hits. To add this Block type to the Gameplay scene, change a configuration file to include the new number of hits in one of the positions before the period (ie “4.X” if the new number of hits is 4).
# Major design choices and trade-offs 
- The PowerUp class is an abstract class with subclasses for each specific powerup.
    * Pros
        - Keeps the code more organized and readable
        - Makes it easier to add new powerups with different features
        - Reduces need for unnecessary if/else statements
    * Cons
        - Because the powerups each deal with a different object in the game (paddle, bouncer, and points specifically), the parameters for applyPowerUp is very long and not every parameter is used in each call.
- The Block class is not an abstract class.
    * Pros
        - There wasn’t enough difference in functionality between the different blocks to warrant a new subclass for blocks that need different hits. The number of hits needed is already an parameter accounted for in the constructor, and so the only difference would be purely aesthetic (which image to use). 
    * Cons
        - If we wanted to implement new blocks that differ in ways other than just how many hits it takes to destroy one, then abstraction and subclasses would keep the code organized, readable, and focused on the key features of each type of block. In summary, a single non-abstract block class makes our program less flexible. 
- Not creating a larger GameObjects abstract class for each object within the class (such as blocks, powerups, bouncer, paddle). 
    * Pros
        - Not necessary because there weren’t that many overlapping methods between objects. Even for methods that are somewhat similar, such as whether two objects intersect, that can already be written in one line.
    * Cons
        - Creating a superclass would have somewhat reduced duplicate code, specifically for creating objects. 
        - This could have reduced the number of parameters passed to methods like applyPowerUp, where not all parameters are used. Instead of separate paddle, bouncer, and block parameters, those can just be grouped under one GameObject parameter. However, this would also mean generalizing each specific method like widenPaddle or createAdditionalBouncer to all objects.
- Not creating a Tester abstract class for specific levels
    * Pros
        - It kept all the testing materials in one place. The if statements were very sequential and seemed to make sense in the context of the testing design. For example, IF it was Level 1 AND a comma was pressed, test the corner. IF it was Level 3 AND a comma was pressed, test that the bouncer bounces off the paddle. 
    * Cons
        - Some may argue that the multiple if and if else statements made the class hard to read
        - It included nine boolean values that had to be kept track of as the methods were called especially in step, which could cause a lot of confusion
# Any assumptions or decisions made to simplify or resolve ambiguities in your the project's functionality
- We assumed the format of the file read into the method that determines the block configuration, named “readBlockConfiguration” in the Block class. The format are lines of numbers separated by commas. Each number has a single digit before and after the decimal point, and the digit can only be a number between 0 and 3 (inclusive). The integer before the decimal determines how many hits it takes for the block to get destroyed, and the integer after the decimal determines which powerup the block releases if destroyed (with 0 meaning no powerup).

- We also limited the game to 3 levels, with hardcoded block configurations for each level. 

- The High score feature did not carry over between runs of the program. However, it did carry over between multiple restarts of the same run.


