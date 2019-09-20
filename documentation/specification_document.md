# Specification Document

The software itself will be a simple text based UI, where you can exit the game or input the number of rounds you need to win in play rock-paper-scissors against to be considered the winner of the game. Then the player can input their next move or forfeit. After that the opponent reveals their move. The game will continue suchly until one player forfeits or reaches the specified amount of wins. After that you return to the first part.

For the AI, I will be mostly using the article by Daniel Lawrence as a base (https://daniel.lawrence.lu/programming/rps/). It specifies many primary strategies and metastrategies you can use in the game. He also specifies different methods for choosing the metastrategy. 

#### Primary strategies
Fixed move - Always pick the same thing\
Frequency counting - Pick the move that would have won the most if used\
Rotation - Constantly picking a move by rotating previous move by 0, 1 or 2 (0 means picking the same move, 1 means picking the move that would have beat the move and 2 means picking the move that would have lost to the move)\
Anti-rotation - Assume the opponent is rotating, calculate by how much and then rotate their move by one more than they would\
History string - Record all the moves made, then once you spot a pattern pick the next move in that pattern\

#### Metastrategies
P0a - Just play the strategy\
P0b - Calculate what is the best move using your strategy from the opponent's position, then rotate that by one\
P1a - Rotate P0a by two, thus beating P0b\
P1b - Rotate P0b by two, thus beating P1a\
P2a - Rotate P1a by two, thus beating P1b\
P2b - Rotate P1b by two, thus beating P2b. Note that this loses to P0a\

#### Metastrategy selectors
Naive scoring - Picking a succesful move nets you one point, picking a failing move costs you one. Pick the strategy with the best score\
Decaying score - Same as naive, but after each round multiple the score by a number between 0 and 1, thus forgetting about previous moves gradually\
Drop switch - Naive scoring, except losing even once sets you to 0 (or lower)\
Random drop switch - Same as above, except the drop isn't certain\

These can be modified by chasing the score outcome on a draw, or having special case for the loss or draw of the selected metastrategy. On top of this, we can add an additional layer by using selectors which select selectors instead of metastrategy!

#### Additional algorithms and data structures
After perusing through the different strategies and selectors, I think the only data structure I need is a HashMap which is for keeping score. Sorting might be useful, but as the number of strategies per layer probably won't exceed tens, it is unnecessary.
