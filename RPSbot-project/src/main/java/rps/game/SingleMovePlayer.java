package rps.game;

/**
 *
 * @author vertt
 */
public class SingleMovePlayer implements RPSPlayer {
    
    Move myMove;
    
    /**
     *Constructor which in the move the player uses is specified
     * @param myMove
     */
    public SingleMovePlayer(Move myMove){
        this.myMove = myMove;
    }
    
    @Override
    public Move getMove(){
        return myMove;
    }
    
    @Override
    public void recordResult(Move myMove, Move opponentMove){
        // Doesn't do anything because tactic isn't affected by what the result waas
    }
    
}
