package rps.app;

import rps.game.HumanPlayer;
import rps.game.Move;
import rps.game.RotatingPlayer;



/**
 *
 * @author vertt
 */

public class Main {
    
    public static void main(String[] args) throws Exception {  
        App application = new App();
        ConsoleIO io = new ConsoleIO();
        application.init(io);
        application.run(new HumanPlayer(io), new RotatingPlayer(Move.ROCK, 0));
    }
}