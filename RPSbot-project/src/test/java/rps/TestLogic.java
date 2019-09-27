/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import rps.game.Move;
import rps.game.RPSLogic;

/**
 *
 * @author vertt
 */
public class TestLogic {
    
    @Test
    public void testGame(){
        RPSLogic logic = new RPSLogic();
        logic.startGame(2);
        logic.evaluateMoves(Move.PAPER, Move.ROCK);
        assertEquals(1, logic.getPlayerOneScore());
        assertEquals(0, logic.getPlayerTwoScore());
        logic.evaluateMoves(Move.SCISSORS, Move.ROCK);
        assertEquals(1, logic.getPlayerOneScore());
        assertEquals(1, logic.getPlayerTwoScore());
        logic.evaluateMoves(Move.PAPER, Move.PAPER);
        assertEquals(1, logic.getPlayerOneScore());
        assertEquals(1, logic.getPlayerTwoScore());
        logic.evaluateMoves(Move.SCISSORS, Move.PAPER);
        assertEquals(2, logic.getPlayerOneScore());
        assertEquals(1, logic.getPlayerTwoScore());
        assertEquals(true, logic.playerOneHasWon());
        assertEquals(false, logic.playerTwoHasWon());
        assertEquals("2 - 1", logic.getScore());
    }
    
    @Test
    public void testRotating(){
        assertEquals(RPSLogic.rotateMove(Move.ROCK, 0), Move.ROCK);
        assertEquals(RPSLogic.rotateMove(Move.ROCK, 1), Move.PAPER);
        assertEquals(RPSLogic.rotateMove(Move.ROCK, 4), Move.PAPER);
        assertEquals(RPSLogic.rotateMove(Move.ROCK, 2), Move.SCISSORS);
        assertEquals(RPSLogic.rotateMove(Move.PAPER, 1), Move.SCISSORS);
        assertEquals(RPSLogic.rotateMove(Move.PAPER, 2), Move.ROCK);
        assertEquals(RPSLogic.rotateMove(Move.SCISSORS, 1), Move.ROCK);
        assertEquals(RPSLogic.rotateMove(Move.SCISSORS, 2), Move.PAPER);
    }
    
    @Test
    public void testGettingRotation(){
        assertEquals(RPSLogic.getRotation(Move.ROCK, Move.ROCK), 0);
        assertEquals(RPSLogic.getRotation(Move.ROCK, Move.PAPER), 1);
        assertEquals(RPSLogic.getRotation(Move.ROCK, Move.SCISSORS), 2);
        assertEquals(RPSLogic.getRotation(Move.PAPER, Move.SCISSORS), 1);
        assertEquals(RPSLogic.getRotation(Move.PAPER, Move.ROCK), 2);
        assertEquals(RPSLogic.getRotation(Move.SCISSORS, Move.ROCK), 1);
        assertEquals(RPSLogic.getRotation(Move.SCISSORS, Move.PAPER), 2);
    }
}
