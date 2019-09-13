package rps.app;

/**
 *
 * @author vertt
 */

public class Main {
    
    public static void main(String[] args) throws Exception {  
        App application = new App();
        application.init(new ConsoleIO());
        application.run();
    }
}