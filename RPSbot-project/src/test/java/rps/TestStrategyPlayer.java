package rps;

import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import rps.app.PlayerConstructor;
import rps.game.Move;
import rps.game.RPSPlayer;
import rps.game.StrategyPlayer;

/**
 *
 * @author vertt
 */
public class TestStrategyPlayer {
    
    @Test
    public void testStrategyPlayer(){
        Random rand = new Random(0);
        StrategyPlayer sp = new StrategyPlayer(null, rand);
        assertEquals(Move.PAPER, sp.getMove());
        rand.setSeed(0);
        assertEquals(Move.PAPER, sp.clone().getMove());
        sp = PlayerConstructor.getStrategyPlayer();
        RPSPlayer[] strategies = sp.getStrategies();
        assertEquals(49, strategies.length);
        rand.setSeed(0);
        sp.getMove();
        Move m = strategies[7].getMove();
        rand.setSeed(0);
        sp.reset();
        assertEquals(m, strategies[7].getMove());
        assertEquals(m, strategies[7].clone().getMove());
        sp.reset();
        assertEquals(m, ((StrategyPlayer)sp.clone()).getStrategies()[7].getMove());
        sp.recordResult(m, m);
        assertTrue(m != strategies[7].getMove());
    }
}
