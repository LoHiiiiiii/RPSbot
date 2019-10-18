package rps.game;

/**
 *
 * @author vertt
 */
public abstract class BaseSelector implements SelectionMethod, RPSPlayer {
    
    protected MyList<Pair<RPSPlayer, Double>> scores = new MyList<>();
    protected MyList<Pair<RPSPlayer, Move>> lastChosenMoves = new MyList<>();
    protected RPSPlayer lastChosenPlayer;

    /**
     *
     * @return Move by the strategy with the highest score.
     */
    @Override
    public Move getMove() {
        double hiscore = -Double.MAX_VALUE;
        Move move;
        Move chosenMove = Move.PAPER; // Has to be initiated for the case that all scores are -Integer.MAX_VALUE or no players
        for(int i = 0; i < scores.count(); ++i){
            move = scores.get(i).key.getMove();
            lastChosenMoves.get(i).value = move;
            if (scores.get(i).value > hiscore){
                hiscore = scores.get(i).value;
                chosenMove = move;
                lastChosenPlayer = scores.get(i).key;
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
            scores.add(new Pair(p, 0.0));
            lastChosenMoves.add(new Pair(p, Move.ROCK));
        }
    }
    
    public void setScoresAndMoves(MyList<Pair<RPSPlayer, Double>> scores, MyList<Pair<RPSPlayer, Move>> lastChosenMoves){
        this.scores = scores;
        this.lastChosenMoves = lastChosenMoves;
        
    }
    
    @Override
    public void reset(){
        for (int i = 0; i < scores.count(); ++i){
            scores.get(i).value = 0.0;
            lastChosenMoves.get(i).value = Move.ROCK;
        }
    }
}
