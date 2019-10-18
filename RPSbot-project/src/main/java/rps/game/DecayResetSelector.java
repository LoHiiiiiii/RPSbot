package rps.game;
import java.util.Random;

public class DecayResetSelector extends BaseSelector {
    
    private final double decayRate;
    private final double resetChance;
    private final double chosenResetChance;
    private final Boolean tieIsLoss;
    private final Boolean resetUpwards;
    private final Random random;
    
    private final int resetValue = 0;
    
    /**
     *
     * @param decayRate Between 0 and 1.
     * @param resetChance Between 0 and 1.
     * @param chosenResetChance Between 0 and 1.
     * @param tieIsLoss tie is considered the same as loss scoringwise
     * @param resetUpwards Whether to reset the losers score if it is below the resetValue.
     * @param random
     */
    public DecayResetSelector(double decayRate, double resetChance, double chosenResetChance, Boolean tieIsLoss, Boolean resetUpwards, Random random){
        this.decayRate = Math.min(1, Math.max(0,decayRate));
        this.resetChance = Math.min(1, Math.max(0, resetChance));
        this.chosenResetChance = Math.min(1, Math.max(0, chosenResetChance));
        this.tieIsLoss = tieIsLoss;
        this.resetUpwards = resetUpwards;
        this.random = random;
    }
    
    /**
     * Each winning player gets one added to the score. Each losing player has a chance of resetting their scores to the resetValue. 
     * For losers that didn't get reset, instead get their score lowered by one. Then each player gets their score multiplied by 1 - the decayRate.
     * @param myMove Isn't used.
     * @param opponentMove What the opponent played.
     */
    @Override
    public void recordResult(Move myMove, Move opponentMove) {
        double score;
        double randomValue;
        Move winningMove = RPSLogic.rotateMove(opponentMove, 1);
        Move losingMove = RPSLogic.rotateMove(opponentMove, 2);
        RPSPlayer p;
        for(int i = 0; i < scores.count(); ++i){
            score = scores.get(i).value;
            if (lastChosenMoves.get(i).value == losingMove || (tieIsLoss && lastChosenMoves.get(i).value != winningMove)){
                randomValue = random.nextDouble();
                p = lastChosenMoves.get(i).key;
                if ((resetUpwards || score > resetValue) && ((p == lastChosenPlayer && randomValue < chosenResetChance) || (p != lastChosenPlayer && randomValue < resetChance)))
                    score = resetValue;
                else 
                    score--;
            } else if (lastChosenMoves.get(i).value == winningMove)
                score++;
            score *= 1.0-decayRate;
            scores.get(i).value = score;
        }
    }

    @Override
    public RPSPlayer clone() {
        DecayResetSelector dsr = new DecayResetSelector(decayRate, resetChance, chosenResetChance, resetUpwards, tieIsLoss, random);
        MyList<Pair<RPSPlayer, Double>> newScores = new MyList<>();
        MyList<Pair<RPSPlayer, Move>> newLastMoves = new MyList<>();
        for (int i = 0; i < scores.count(); ++i){
            newScores.add(new Pair(scores.get(i).key, scores.get(i).value));
            newLastMoves.add(new Pair(lastChosenMoves.get(i).key, lastChosenMoves.get(i).value));
        }
        dsr.setScoresAndMoves(newScores, newLastMoves);
        return dsr;
    }
    
    public MyList<Pair<RPSPlayer, Double>> getScores(){
        return scores;
    }
    
    @Override
    public void reset(){
        super.reset();
    }
    
    @Override
    public String toString(){
        return "D: " + decayRate + ", R: " + resetChance + ", CR: " + chosenResetChance + ", RUp: " + resetUpwards + ", TieLoss: " + tieIsLoss;
    }
    
}
