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
import rps.game.Move;
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
        assertEquals(Move.ROCK, arp.getMove());
        assertEquals(Move.ROCK, clone.getMove());
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
    }
}
