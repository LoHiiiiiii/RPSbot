package rps.game;

/**
 *
 * @author vertt
 */
public class AntirotatingPlayer implements RPSPlayer {
    
    private Move previousMove;
    int rotationAmount = 0;
    
    /**
     *Constructor which in the starting move is specified and by how much the previous move is rotated by.
     * @param startingMove
     */
    public AntirotatingPlayer(Move startingMove){
        this.previousMove = startingMove;
    }
    
    @Override
    public Move getMove(){
        return RPSLogic.rotateMove(previousMove, rotationAmount);
    }
    
    @Override
    public void recordResult(Move myMove, Move opponentMove){
        rotationAmount = RPSLogic.getRotation(previousMove, opponentMove) + 1;
        previousMove = opponentMove;
    }
    
    @Override
    public RPSPlayer clone(){
        return new AntirotatingPlayer(previousMove);
    }
}
