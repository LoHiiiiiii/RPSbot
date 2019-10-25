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
public class FollowupFrequencyPlayer implements RPSPlayer {

    private final Random random;
    private final MyHashMap<Move, MyHashMap<Move, Integer>> frequencies;
    private Move previous = Move.ROCK;
    private Boolean firstMove = true;
    
    /**
     * @param random Randomnumber generator to be used in case of ties.
     */
    public FollowupFrequencyPlayer(Random random){
        this.random = random;
        frequencies = new MyHashMap<>();
        for(Move m : Move.values()){
            MyHashMap<Move, Integer> map = new MyHashMap<>();
            map.put(Move.ROCK, 0);
            map.put(Move.PAPER, 0);
            map.put(Move.SCISSORS, 0);
            frequencies.put(m, map);
        }
    }
    
    /**
     * @return Move that beats the move the opponent uses most frequently after their previous move.
     */
    @Override
    public Move getMove() {
        int hiscore = Integer.MIN_VALUE;
        int currentScore;
        MyList<Move> possibleMoves = new MyList();
        Move[] moves = new Move[frequencies.size()];
        moves = frequencies.keys(moves);
        for(Move m : moves){
            currentScore = frequencies.get(previous).get(m);
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
     * Adds 1 to the score of the move that would've won the opponents move in the table for the opponent's previous move.
     * @param myMove
     * @param opponentMove
     */
    @Override
    public void recordResult(Move myMove, Move opponentMove) {
        if (!firstMove){
            MyHashMap<Move, Integer> map = frequencies.get(previous);
            map.replace(RPSLogic.rotateMove(opponentMove, 1), map.get(RPSLogic.rotateMove(opponentMove, 1)) + 1);
        }
        firstMove = false;
        previous = opponentMove;
    }

    @Override
    public void reset(){
        Move[] moves = new Move[frequencies.size()];
        moves = frequencies.keys(moves);
        for(Move m : moves){
            for(Move j : moves){
                frequencies.get(m).replace(j, 0);
            }
        }
    }
    
    public RPSPlayer clone() {
        return new FollowupFrequencyPlayer(random);
    }
}
