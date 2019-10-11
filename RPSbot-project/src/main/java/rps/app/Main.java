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
        AIRoundRobin.start(io, 500);
        //application.init(io);
        //application.run(new HumanPlayer(io), StrategyPlayerConstructor.getStrategyPlayer(), 7);
    }
}