package rps.game;
import java.util.Random;

public class DecayResetSelector extends BaseSelector {
    
    private final double decayRate;
    private final double resetChance;
    private final double chosenResetChance;
    private final Boolean resetUpwards;
    private final Random random;
    
    private final int resetValue = 0;
    
    /**
     *
     * @param decayRate Between 0 and 1.
     * @param resetChance Between 0 and 1.
     * @param chosenResetChance Between 0 and 1.
     * @param resetUpwards Whether to reset the losers score if it is below the resetValue.
     * @param random
     */
    public DecayResetSelector(double decayRate, double resetChance, double chosenResetChance, Boolean resetUpwards, Random random){
        this.decayRate = decayRate;
        this.resetChance = resetChance;
        this.chosenResetChance = chosenResetChance;
        this.resetUpwards = resetUpwards;
        this.random = random;
    }
    
    /**
     * Each winning player gets one added to the score. Each losing player has a chance of resetting their scores to the resetValue. 
     * For losers that didn't get resetted, instead get their score lowered by one. Then each player gets their score multiplied by the decayRate.
     * @param myMove Isn't used.
     * @param opponentMove What the opponent played.
     */
    @Override
    public void recordResult(Move myMove, Move opponentMove) {
        double score;
        double randomValue;
        Move winningMove = RPSLogic.rotateMove(opponentMove, 1);
        Move losingMove = RPSLogic.rotateMove(opponentMove, 2);
        for(RPSPlayer p : (RPSPlayer[])scores.keys()){
            score = scores.get(p);
            if (lastChosenMoves.get(p) == losingMove){
                randomValue = random.nextDouble();
                if ((resetUpwards || score > resetValue) && ((p == lastChosenPlayer && randomValue > chosenResetChance) || (p != lastChosenPlayer && randomValue > resetChance)))
                    score = resetValue;
                else 
                    score--;
            } else if (lastChosenMoves.get(p) == winningMove)
                score++;
            score *= decayRate;
            scores.replace(p, score);
        }
    }

    @Override
    public RPSPlayer clone() {
        return new DecayResetSelector(decayRate, resetChance, chosenResetChance, resetUpwards, random);
    }
    
}
