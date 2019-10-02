/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps;

import java.util.Random;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import rps.game.RotatingPlayer;
import rps.game.DecayResetSelector;
import rps.game.Move;
import rps.game.RPSPlayer;

/**
 *
 * @author vertt
 */
public class TestDecayResetSelector {
    
    @Test
    public void test(){
        Random rand = new Random(0);
        DecayResetSelector drs = new DecayResetSelector(0.1, 1, 1, false, rand); 
        RotatingPlayer rock = new RotatingPlayer(Move.ROCK, 0);
        RotatingPlayer scissors = new RotatingPlayer(Move.SCISSORS,0);
        drs.setPlayers(new RPSPlayer[]{rock, scissors});
        drs.getMove();
        drs.recordResult(Move.ROCK, Move.PAPER);
        assertEquals(drs.getScores().get(scissors), 0.9, 0.01);
        assertEquals(drs.getScores().get(rock), -0.9, 0.01);
        assertEquals(Move.SCISSORS, drs.getMove());
        assertEquals(drs.getMove(), drs.clone().getMove());
        drs.recordResult(Move.SCISSORS, Move.ROCK);
        assertEquals(drs.getScores().get(scissors), 0, 0.01);
        assertEquals(drs.getScores().get(rock), -0.81, 0.01);
        assertEquals(Move.SCISSORS, drs.getMove());
    }
}
