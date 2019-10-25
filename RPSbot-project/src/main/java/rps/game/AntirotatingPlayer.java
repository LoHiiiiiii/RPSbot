package rps.game;

/**
 *
 * @author vertt
 */
public class AntirotatingPlayer implements RPSPlayer {
    
    private Move previousMove;
    int rotationAmount = 0;
    
    private final Move startingMove;
    
    /**
     *Constructor which in the starting move is specified and by how much the previous move is rotated by.
     * @param startingMove
     */
    public AntirotatingPlayer(Move startingMove){
        this.startingMove = startingMove;
        this.previousMove = startingMove;
    }
    
    /**
     * @return the opponent's previous move rotated by 1 + the calculated rotationamount.
     */
    @Override
    public Move getMove(){
        return RPSLogic.rotateMove(previousMove, rotationAmount);
    }
    
    /**
     * Checks how much the opponent has rotated their previous move, adds one to that and stores the new move as the previous move.
     * @param myMove What this player played (irrelevant for movecalculations)
     * @param opponentMove What the opponent played
     */
    @Override
    public void recordResult(Move myMove, Move opponentMove){
        rotationAmount = RPSLogic.getRotation(previousMove, opponentMove) + 1;
        previousMove = opponentMove;
    }
    
    @Override
    public RPSPlayer clone(){
        return new AntirotatingPlayer(previousMove);
    }
    
    @Override
    public void reset(){
        rotationAmount = 0;
        previousMove = startingMove;
    }
}
