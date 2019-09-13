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
        if (playerOneMove == Move.FORFEIT){
            playerTwoScore = scoreToWin;
        } else if (playerTwoMove == Move.FORFEIT){
            playerOneScore = scoreToWin;
        } else if (playerOneMove != playerTwoMove){
            if ((playerOneMove == Move.ROCK && playerTwoMove == Move.SCISSORS) || 
                (playerOneMove == Move.PAPER && playerTwoMove == Move.ROCK) || 
                (playerOneMove == Move.SCISSORS && playerTwoMove == Move.PAPER))
                playerOneScore++;
            else
                playerTwoScore++;
        }
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
}
