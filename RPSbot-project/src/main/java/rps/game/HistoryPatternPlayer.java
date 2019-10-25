/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.game;

import java.util.Objects;

/**
 *
 * @author vertt
 */
public class HistoryPatternPlayer implements RPSPlayer {

    private final Move defaultMove;
    private final Boolean recordMyMove;
    private String history;
    
    /**
     * @param defaultMove move used if no pattern is found
     * @param recordMyMove whether to consider only opponent's move or what both players used.
     */
    public HistoryPatternPlayer(Move defaultMove, Boolean recordMyMove){
        this.defaultMove = defaultMove;
        this.recordMyMove = recordMyMove;
    }
    
    /**
     * Finds the move the opponent used next if it would be part of the longest pattern they have played.
     * @return the move found in the pattern or the defaultMove if no pattern found
     */
    @Override
    public Move getMove() {
        Move m = stringToMove(getPredictedMove(history));
        if (m != null){
            return RPSLogic.rotateMove(m, 1);
        }
        return defaultMove;
    }

    @Override
    public void recordResult(Move myMove, Move opponentMove) {
        if (recordMyMove)
            history += moveToString(myMove);
        history += moveToString(opponentMove);
    }

    private String moveToString(Move move){
        switch(move){
            case ROCK: return "r";
            case PAPER: return "p";
            case SCISSORS: return "s";
        }
        return "";
    }
    
    private Move stringToMove(String string){
        switch(string){
            case "r": return Move.ROCK;
            case "p": return Move.PAPER;
            case "s": return Move.SCISSORS;
        }
        return null;
    }
    
    /**
     * Finds the longest nonrepeating substring that includes the final char of the string and returns the char that comes after that. 
     * If recordMyMove is true, considers sets of 2 chars.
     * @return the move found in the pattern or the defaultMove if no pattern found
     */
    private String getPredictedMove(String string){
        if (string == null || string.length() == 0)
            return "";
        
        int stepSize = (recordMyMove) ? 2 : 1;
        int length = string.length();
        
        int longestLength = 0;
        int longestIndex = 0;
        MyList<Pair<Integer,Integer>> matchStartIndices = new MyList<>();
        String firstPartToMatch = string.substring(length-stepSize, length);
        
        String s1, s2;
        Pair<Integer, Integer> current;
        
        for (int i = stepSize*2; i <= length; i += stepSize){
            s1 = string.substring(length-i, length-i+stepSize);
            for (int j = 0; j < matchStartIndices.count(); ++j){
                current = matchStartIndices.get(j);
                if (Objects.equals(current.key, current.value))
                    continue;
                s2 = string.substring(length-current.key-current.value+stepSize, length-current.key-current.value+stepSize + 1);
                if (s2.equals(s1))
                    current.value += stepSize;
                if (current.value > longestLength){
                    longestIndex = current.key;
                    longestLength = current.value;
                }
            }
            
            if (s1.equals(firstPartToMatch)){
                matchStartIndices.add(new Pair(i, stepSize));
                if (longestLength == 0){
                    longestIndex = i;
                    longestLength = stepSize;
                }
            }
            
        }
        
        if (longestLength > 0){
            return string.substring(length-longestIndex+stepSize, length-longestIndex+stepSize+1);
        }
        
        return "";
    }
    
    @Override
    public RPSPlayer clone() {
        return new HistoryPatternPlayer(defaultMove, recordMyMove);
    }

    @Override
    public void reset() {
        history = "";
    }
    
}
