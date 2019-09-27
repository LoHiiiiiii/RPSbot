package rps.game;
import java.util.Random;

/**
 *
 * @author vertt
 */
public class RandomPlayer implements RPSPlayer {
    
    private Random random;
    
    /**
     *Constructor which in the random number generator is given
     * @param random
     */
    public RandomPlayer(Random random){
        this.random = random;
    }
    
    /**
     * Returns a random move.
     * @return 
     */
    @Override
    public Move getMove(){
        int randomInt = random.nextInt(3);
        switch(randomInt){
            case 0: return Move.ROCK;
            case 1: return Move.PAPER;
            default: return Move.SCISSORS;
        }
    }
    
    @Override
    public void recordResult(Move myMove, Move opponentMove){
        // Doesn't do anything because tactic isn't affected by what the result waas
    }
 
    @Override
    public RPSPlayer clone(){
        return new RandomPlayer(random);
    }
}
