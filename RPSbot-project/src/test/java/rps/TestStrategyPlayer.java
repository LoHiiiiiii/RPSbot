package rps;

import java.util.Random;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import rps.game.Move;
import rps.game.StrategyPlayer;

/**
 *
 * @author vertt
 */
public class TestStrategyPlayer {
    
    @Test
    public void testEmptyClone(){
        Random rand = new Random(0);
        StrategyPlayer sp = new StrategyPlayer(null, rand);
        assertEquals(Move.PAPER, sp.getMove());
        rand.setSeed(0);
        assertEquals(Move.PAPER, sp.clone().getMove());
    }
}
