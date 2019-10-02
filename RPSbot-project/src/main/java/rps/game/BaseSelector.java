package rps.game;

/**
 *
 * @author vertt
 */
public abstract class BaseSelector implements SelectionMethod, RPSPlayer {
    
    protected MyHashMap<RPSPlayer, Double> scores = new MyHashMap<>();
    protected MyHashMap<RPSPlayer, Move> lastChosenMoves = new MyHashMap<>();
    protected RPSPlayer lastChosenPlayer;

    /**
     *
     * @return Move by the strategy with the highest score.
     */
    @Override
    public Move getMove() {
        double hiscore = -Double.MAX_VALUE;
        Move move;
        Move chosenMove = Move.PAPER; // Has to be initiated for the case that all scores are Integer.MIN_VALUE
        for(RPSPlayer p : scores.keys(new RPSPlayer[scores.size()])){
            move = p.getMove();
            lastChosenMoves.replace(p, move);
            if (scores.get(p) > hiscore){
                hiscore = scores.get(p);
                chosenMove = move;
                lastChosenPlayer = p;
            }
        }
        return chosenMove;
    }
    
    @Override
    public abstract RPSPlayer clone();
    
    @Override
    public void setPlayers(RPSPlayer[] players) {
        scores.clear();
        lastChosenMoves.clear();
        for(RPSPlayer p : players){
            scores.put(p, 0.0);
            lastChosenMoves.put(p, Move.ROCK);
        }
    }
    
    public void setScoresAndMoves(MyHashMap<RPSPlayer, Double> scores, MyHashMap<RPSPlayer, Move> lastChosenMoves){
        this.scores = scores;
        this.lastChosenMoves = lastChosenMoves;
        
    }
}
