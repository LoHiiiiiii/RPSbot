/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.game;
import rps.app.IO;

/**
 *
 * @author vertt
 */
public class HumanPlayer implements RPSPlayer {
    
    private IO io;
    
    public HumanPlayer (IO io) {
        if (io == null)
            throw new NullPointerException("Cannot construct a HumanPlayer with a null IO");
        this.io = io;
    }

    /**
     * Asks the player for a move, and asks again if can't parse the input into one.
     * @return One of the four allowable moves.
     */
    @Override
    public Move getMove() {
        String s = io.readLine("Choose your next move! (Rock, Paper, Scissors or Forfeit)");
        char c = 0; //Default value
        if (s.length() > 0)
            c = s.charAt(0);
        switch (c){
            case 'R': case 'r': case 'K': case 'k': return Move.ROCK;
            case 'P': case 'p': return Move.PAPER;
            case 'S': case 's': return Move.SCISSORS;
            default: return getMove();
        }
    }

    @Override
    public void recordResult(Move myMove, Move opponentMove) {
        //Doesn't record anything since humans have to strategize themselves
    }
    
    @Override
    public RPSPlayer clone(){
        return new HumanPlayer(io);
    }
    
}
