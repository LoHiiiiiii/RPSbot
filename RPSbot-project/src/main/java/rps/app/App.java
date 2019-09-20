package rps.app;

import rps.game.RPSPlayer;
import rps.game.RPSLogic;
import rps.game.HumanPlayer;
import rps.game.SingleMovePlayer;
import rps.game.Move;
import java.util.HashMap;

/**
 *
 * @author vertt
 */
public class App {
    
    private IO io;
    
    public void init(IO io){
        this.io = io;
    }
    
    /**
     * Two players and the game logic is instantiated. In the loop both players are asked for movest,
     * then both of the chosen moves are printed and evaluated. Then the score is printed and the loop continues if neither player won.
     */
    public void run(){
        RPSPlayer playerOne = new HumanPlayer(io);
        RPSPlayer playerTwo = new SingleMovePlayer(Move.ROCK);
        RPSLogic logic = new RPSLogic();
        
        Move playerOneMove;
        Move playerTwoMove;
        
        logic.startGame(5);
        io.print("Play to 5!");
        
        while(true){
            playerOneMove = playerOne.getMove();
            playerTwoMove = playerTwo.getMove();
            io.print("Player one chose " + playerOneMove.name() + "! Player two chose " + playerTwoMove.name() + "!");
            logic.evaluateMoves(playerOneMove, playerTwoMove);
            playerOne.recordResult(playerOneMove, playerTwoMove);
            playerTwo.recordResult(playerTwoMove, playerOneMove);
            io.print(logic.getScore());
            if (logic.playerOneHasWon()){
                io.print("Player one wins!");
                break;
            } else if (logic.playerTwoHasWon()){
                io.print("Player two wins!");
                break;
            }
        }
    }
    
}
