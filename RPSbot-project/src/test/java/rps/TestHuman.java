/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import rps.app.IO;
import rps.game.HumanPlayer;
import rps.game.Move;
import rps.game.RPSPlayer;

/**
 *
 * @author vertt
 */
public class TestHuman {
    
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
    public void testIO(){
        DummyIO io = new DummyIO();
        HumanPlayer hp = new HumanPlayer(io);
        io.setString("R");
        assertEquals(Move.ROCK, hp.getMove());
        io.setString("P");
        assertEquals(Move.PAPER, hp.getMove());
        io.setString("S");
        assertEquals(Move.SCISSORS, hp.getMove());
        io.setString("X");
        assertEquals(Move.ROCK, hp.getMove());
        assertEquals(2, io.rounds);
        hp.reset();
        hp.recordResult(Move.ROCK, Move.PAPER); //Purely for code coverage
    }
    
    @Test
    public void testClone(){
        DummyIO io = new DummyIO();
        HumanPlayer hp = new HumanPlayer(io);
        RPSPlayer clone = hp.clone();
        io.setString("R");
        assertFalse(clone == null);
        assertEquals(hp.getMove(), clone.getMove());
    }
    
    @Test(expected=NullPointerException.class)
    public void testNullIO(){
        HumanPlayer hp = new HumanPlayer(null);
        
    }
}
