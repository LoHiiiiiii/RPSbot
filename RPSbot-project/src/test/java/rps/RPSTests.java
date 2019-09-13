/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import rps.game.Move;
import rps.game.SingleMovePlayer;
import rps.game.RPSLogic;
import rps.game.HumanPlayer;
import rps.app.IO;

/**
 *
 * @author vertt
 */
public class RPSTests {
    
    class DummyIO implements IO{
        
        String myString;
        public int rounds = 0;
        
        public void setString(String myString){
           this.myString = myString;
           rounds = 0;
        }
        
        @Override
        public void print(String s){
            
        }
        
        @Override
        public String readLine(String line){
            String s = myString;
            myString = "R";
            rounds++;
            return s;
        }
    }
    
    @Test
    public void testSingleMovePlayer(){
     SingleMovePlayer smp = new SingleMovePlayer(Move.ROCK);
     assertEquals(Move.ROCK, smp.getMove());
    }
    
    @Test
    public void testRPSLogic(){
     RPSLogic logic = new RPSLogic();
     logic.startGame(2);
     logic.evaluateMoves(Move.PAPER, Move.ROCK);
     assertEquals(1, logic.getPlayerOneScore());
     assertEquals(0, logic.getPlayerTwoScore());
     logic.evaluateMoves(Move.SCISSORS, Move.ROCK);
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
    public void testHumanPlayer(){
        DummyIO io = new DummyIO();
        HumanPlayer hp = new HumanPlayer(io);
        io.setString("R");
        assertEquals(Move.ROCK, hp.getMove());
        io.setString("P");
        assertEquals(Move.PAPER, hp.getMove());
        io.setString("S");
        assertEquals(Move.SCISSORS, hp.getMove());
        io.setString("F");
        assertEquals(Move.FORFEIT, hp.getMove());
        io.setString("X");
        assertEquals(Move.ROCK, hp.getMove());
        assertEquals(2, io.rounds);
    }
}
