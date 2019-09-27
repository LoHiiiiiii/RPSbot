package rps.game;

/**
 *
 * @author vertt
 */
public class RotatingPlayer implements RPSPlayer {
    
    private Move previousMove;
    int rotationAmount;
    
    /**
     *Constructor which in the starting move is specified and by how much the previous move is rotated by.
     * @param startingMove
     * @param rotationAmount
     */
    public RotatingPlayer(Move startingMove, int rotationAmount){
        this.previousMove = startingMove;
        this.rotationAmount = rotationAmount;
    }
    
    @Override
    public Move getMove(){
        return RPSLogic.rotateMove(previousMove, rotationAmount);
    }
    
    @Override
    public void recordResult(Move myMove, Move opponentMove){
        previousMove = myMove;
    }
    
    @Override
    public RPSPlayer clone(){
        return new RotatingPlayer(previousMove, rotationAmount);
    }
}
