/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.game;

/**
 *
 * @author vertt
 */
public class RPSLogic {
    
    int playerOneScore;
    int playerTwoScore;
    
    int scoreToWin = 5;
    
    public void startGame(int scoreToWin){
        this.scoreToWin = scoreToWin;
        startGame();
    }
    
    public void startGame(){
        playerOneScore = 0;
        playerTwoScore = 0;
    }
    
    /**
     *Here the RPS game logic happens. If one player forfeits, the other gets maximum score and the evaluation ends.
     * If no-one forfeits, the appropriate player has their score incremented one by the Rock beats Scissors beats Paper beats Rock logic.
     * @param playerOneMove The move player one has chosen.
     * @param playerTwoMove The move player one has chosen.
     */
    public void evaluateMoves(Move playerOneMove, Move playerTwoMove){
        int change = getScoreChange(playerOneMove, playerTwoMove);
        playerOneScore += Math.max(0, change);
        playerTwoScore -= Math.min(0, change);
    }
    
    public int getScoreChange(Move playerOneMove, Move playerTwoMove){
        if (playerOneMove != playerTwoMove){
            if ((playerOneMove == Move.ROCK && playerTwoMove == Move.SCISSORS) || 
                (playerOneMove == Move.PAPER && playerTwoMove == Move.ROCK) || 
                (playerOneMove == Move.SCISSORS && playerTwoMove == Move.PAPER))
                return 1;
            else
                return -1;
        }
        return 0;
    }
    
    public Boolean playerOneHasWon(){
        return (playerOneScore >= scoreToWin);
    }
    
    public Boolean playerTwoHasWon(){
        return (playerTwoScore >= scoreToWin);
    }
    
    public String getScore(){
        return playerOneScore + " - " + playerTwoScore;
    }
    
    public int getPlayerOneScore(){
        return playerOneScore;
    }
    
    public int getPlayerTwoScore(){
        return playerTwoScore;
    }
    
    public static Move rotateMove(Move move, int rotation){
        rotation = rotation % 3;
        switch(rotation){
            case 1:
                switch(move){
                    case ROCK: return Move.PAPER;
                    case PAPER: return Move.SCISSORS;
                    default: return Move.ROCK;
                }
            case 2:
                switch(move){
                    case ROCK: return Move.SCISSORS;
                    case PAPER: return Move.ROCK;
                    default: return Move.PAPER;
                }
            default: return move;
        }
    }
    
    public static int getRotation(Move moveOne, Move moveTwo){
        if (moveOne == moveTwo)
            return 0;
        else {
            switch(moveOne){
                case ROCK:
                    switch(moveTwo){
                        case PAPER: return 1;
                        default: return 2;
                    }
                case PAPER:
                    switch(moveTwo){
                        case SCISSORS: return 1;
                        default: return 2;
                    }
                default:
                    switch(moveTwo){
                        case ROCK: return 1;
                        default: return 2;
                    }
            }
        }
    }
}
