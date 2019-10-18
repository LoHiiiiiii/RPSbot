/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.game;

import java.util.Random;

/**
 *
 * @author vertt
 */
public class FrequencyCountPlayer implements RPSPlayer {

    private final Random random;
    private final MyHashMap<Move, Integer> frequencies;
    
    /**
     *
     * @param random Randomnumber generator to be used in case of ties.
     */
    public FrequencyCountPlayer(Random random){
        this.random = random;
        frequencies = new MyHashMap<>();
        frequencies.put(Move.ROCK, 0);
        frequencies.put(Move.PAPER, 0);
        frequencies.put(Move.SCISSORS, 0);
    }
    
    @Override
    public Move getMove() {
        int hiscore = Integer.MIN_VALUE;
        int currentScore;
        MyList<Move> possibleMoves = new MyList();
        Move[] moves = new Move[frequencies.size()];
        moves = frequencies.keys(moves);
        for(Move m : moves){
            currentScore = frequencies.get(m);
            if (currentScore > hiscore){
                possibleMoves.clear();
                hiscore = currentScore;
                possibleMoves.add(m);
            } else if (currentScore == hiscore)
                possibleMoves.add(m);
        }
        return (Move) possibleMoves.get(0);
    }

    /**
     * Adds 1 to the score of the move that would've won the opponents move
     * @param myMove
     * @param opponentMove
     */
    @Override
    public void recordResult(Move myMove, Move opponentMove) {
        frequencies.replace(RPSLogic.rotateMove(opponentMove, 1), frequencies.get(RPSLogic.rotateMove(opponentMove, 1)) + 1);
    }

    @Override
    public void reset(){
        Move[] moves = new Move[frequencies.size()];
        moves = frequencies.keys(moves);
        for(Move m : moves){
            frequencies.replace(m, 0);
        }
    }
    
    public RPSPlayer clone() {
        return new FrequencyCountPlayer(random);
    }
}
