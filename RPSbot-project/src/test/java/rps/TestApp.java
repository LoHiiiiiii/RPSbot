package rps;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import rps.app.App;
import rps.app.IO;
import rps.game.Move;
import rps.game.RotatingPlayer;

public class TestApp {
    
    class DummyIO implements IO{
        
        @Override
        public void print(String s){
            
        }
        
        @Override
        public String readLine(String line){
            return line;
        }
    }

    @Test
    public void testApp(){
        App app = new App();
        app.init(new DummyIO());
        assertEquals(true, app.run(new RotatingPlayer(Move.ROCK, 0), new RotatingPlayer(Move.SCISSORS, 0)));
        assertEquals(false, app.run(new RotatingPlayer(Move.SCISSORS, 0), new RotatingPlayer(Move.ROCK, 0)));
    }
}