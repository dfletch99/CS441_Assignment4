# CS441_Assignment4
Block stacking... thing.
Make a tower of blocks as high as you can
Your score is kept on a leaderboard, there are 5 default scores/names to start, so the player can compete with something.

# The Screens
## The main menu
First screen to load upon startup. Shows the first block of the tower sliding back and forth, awaiting placement by the player.
Cute text boxes giving the very short description/instructions for the game: "Stack the blocks! It's harder than you think"
There are two buttons for the player to press. One starts the game and brings the player to the Main Game screen. The other takes the 
player to the leaderboard screen
## The leader board
The leaderboard is seven pages of 100 names and scores. The highest scores are on the top of the lower pages, the lowest scores are on the 
bottom of the lower pages. I could have easily made the leaderboard dynamic by using an arraylist instead of an array, I am just very, 
very pressed for time.
The player can press the page forward/backward buttons accordingly, and there is also a "back" button in the top left of the screen that 
takes the player to the main menu
## The main game
The game starts with a single black block sliding back and forth across the screen. The player can tap the screen to stop that block from 
moving. Upon tapping, another block will appear directly above the first one in a random location left-to-right, and will itself begin 
sliding.
Each time the player taps the screen, the block that is moving will stop, and any portion of the block that is not directly above the 
block below it "falls off," and that block (as well as the next block to appear) will be thinner. For example. If the left half of the 
block is not directly above the block below it, but the right half is, the left half will be deleted and the width of the block will be 
halved. The next block to appear will also have that same width
Each time the player places 10 blocks, the speed at which the blocks slide increases. This makes it increasingly difficult to maintain 
precision in placing the blocks exactly in line with the ones below them. I find it incredibly difficult to achieve a score above 40.
The game is over when the player fails to place the block above any portion of the block below it, i.e. the last block is completely 
suspended in mid-air. The final score is however many blocks are actually on the stack minus one (the starting block does not count 
towards the player's score, meaning the player can theoretically achieve a score of 0 by completely missing the second block). The player's score is visible for the entirity of the game, and is always accurate.
## Game over animation
Once the player fails to place a block on the stack, the camera slowly pans down to present the final stack to the player one last time. 
Upon reaching the bottom, the game stalls for a second or two and then displays the game over screen.
## Game over
The game over screen displays the final score the player earned along with two buttons: a try again button and a leaderboard button. The 
try again button immediately resets the game and brings the player to the main game screen. The leaderboard button obviously brings the 
player to the leaderboard (with the newly earned score added).

# Struggles
The largest struggle by far for this project was attempting to (and ultimately abandoning) implement a tablelayout for the leaderboard 
screen. For some reason, switching activities was not working with libgdx, and so I eventually had to settle for a more programmatic 
approach.
