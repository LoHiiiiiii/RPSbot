/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps;

import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import rps.game.AntirotatingPlayer;
import rps.game.FrequencyCountPlayer;
import rps.game.HistoryPatternPlayer;
import rps.game.Move;
import rps.game.RandomPlayer;
import rps.game.RotatingPlayer;

/**
 *
 * @author vertt
 */
public class TestPrimaryStrategies {
    
    @Test
    public void testRotatingPlayer(){ 
        //Tests that Rock is rotated by one, then once the record is resulted it is scissors. 
        //Also checks that clone would use the same move, and keeps using that even if the original has recorded a move.
        RotatingPlayer rp = new RotatingPlayer(Move.ROCK, 1);
        RotatingPlayer clone = (RotatingPlayer) rp.clone();
        assertEquals(clone.getMove(), rp.getMove());
        assertEquals(Move.PAPER, rp.getMove());
        rp.recordResult(rp.getMove(), Move.ROCK);
        assertEquals(Move.SCISSORS, rp.getMove());
        assertEquals(Move.PAPER, clone.getMove());
        rp.reset();
        assertEquals(Move.PAPER, rp.getMove());
    }
    
    @Test
    public void testAntirotatingPlayer(){
        //Tests that first move is rock for the antirotater, then sees that the opponent rotated by one, so its next move should be that rotated by two.
        //Also checks that clone would use the same move, and keeps using that even if the original has recorded a move.
        AntirotatingPlayer arp = new AntirotatingPlayer(Move.ROCK);
        AntirotatingPlayer clone = (AntirotatingPlayer) arp.clone();
        assertEquals(clone.getMove(), arp.getMove());
        assertEquals(Move.ROCK, arp.getMove());
        arp.recordResult(Move.ROCK, Move.PAPER);
        arp.recordResult(Move.ROCK, Move.SCISSORS);
        assertEquals(Move.PAPER, arp.getMove());
        assertEquals(Move.ROCK, clone.getMove());
        arp.reset();
        assertEquals(Move.ROCK, arp.getMove());
    }
    
    @Test
    public void testFrequencyCount(){
        //Tests that first move is rock for the antirotater, then sees that the opponent rotated by one, so its next move should be that rotated by two.
        //Also checks that clone would use the same move, and keeps using that even if the original has recorded a move.
        Random random = new Random();
        FrequencyCountPlayer fcp = new FrequencyCountPlayer(random);
        FrequencyCountPlayer clone = (FrequencyCountPlayer) fcp.clone();
        fcp.recordResult(Move.ROCK, Move.PAPER);
        clone.recordResult(Move.ROCK, Move.PAPER);
        assertEquals(clone.getMove(), fcp.getMove());
        assertEquals(Move.SCISSORS, fcp.getMove());
        fcp.recordResult(Move.ROCK, Move.SCISSORS);
        fcp.recordResult(Move.ROCK, Move.SCISSORS);
        assertEquals(Move.ROCK, fcp.getMove());
        assertEquals(Move.SCISSORS, clone.getMove());
        fcp.recordResult(Move.ROCK, Move.PAPER);
        Move m = fcp.getMove();
        assertTrue(m == Move.SCISSORS || m == Move.ROCK);
        assertEquals(Move.SCISSORS, clone.getMove());
        fcp.reset();
        fcp.recordResult(Move.ROCK, Move.PAPER);
        assertEquals(Move.SCISSORS, fcp.getMove());
    }
    
    @Test
    public void testRandomPlayer(){
        int seed = 2; //Seed where first three moves are different
        Random random = new Random(seed);
        RandomPlayer rp = new RandomPlayer(random);
        Move moveOne = rp.getMove();
        Move moveTwo = rp.getMove();
        Move moveThree = rp.getMove();
        Boolean allMoves = moveOne != moveTwo && moveTwo != moveThree && moveThree != moveOne;
        assertTrue(allMoves);
        random.setSeed(seed);
        rp.recordResult(moveOne, moveTwo);
        assertEquals(moveOne, rp.getMove());
        assertEquals(moveTwo, rp.clone().getMove());
        rp.reset();
    }
    
    @Test
    public void testHistoryPatternPlayer(){
        HistoryPatternPlayer hpp = new HistoryPatternPlayer(Move.ROCK, false);
        hpp.recordResult(Move.ROCK, Move.ROCK);
        hpp.recordResult(Move.ROCK, Move.PAPER);
        hpp.recordResult(Move.ROCK, Move.SCISSORS);
        hpp.recordResult(Move.ROCK, Move.ROCK);
        assertEquals(Move.SCISSORS, hpp.getMove());
        hpp.reset();
        assertEquals(Move.ROCK, hpp.getMove());
        hpp.recordResult(Move.ROCK, Move.ROCK);
        hpp.recordResult(Move.ROCK, Move.PAPER);
        hpp.recordResult(Move.ROCK, Move.SCISSORS);
        hpp.recordResult(Move.ROCK, Move.ROCK);
        hpp.recordResult(Move.ROCK, Move.ROCK);
        hpp.recordResult(Move.ROCK, Move.PAPER);
        hpp.recordResult(Move.ROCK, Move.ROCK);
        hpp.recordResult(Move.ROCK, Move.ROCK);
        hpp.recordResult(Move.ROCK, Move.PAPER);
        assertEquals(Move.PAPER, hpp.getMove());
    }
}
