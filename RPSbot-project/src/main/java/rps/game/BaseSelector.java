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
        double hiscore = Double.MIN_VALUE;
        Move move;
        Move chosenMove = Move.ROCK; // Has to be initiated for the case that all scores are Integer.MIN_VALUE
        for(RPSPlayer p : (RPSPlayer[])scores.keys()){
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
    public RPSPlayer clone() {
        return null;
    }
    
    @Override
    public void setPlayers(RPSPlayer[] players) {
        scores.clear();
        for(RPSPlayer p : players)
            scores.put(p, 0.0);
    }
}
