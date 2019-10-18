package rps.app;

import rps.game.HumanPlayer;



/**
 *
 * @author vertt
 */

public class Main {
    
    public static void main(String[] args) throws Exception {  
        App application = new App();
        ConsoleIO io = new ConsoleIO();
        //AIVersus.start(io);
        application.init(io);
        application.run(new HumanPlayer(io), PlayerConstructor.getStrategyPlayer(), 20);
    }
}