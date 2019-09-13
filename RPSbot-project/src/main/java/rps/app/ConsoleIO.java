package rps.app;

import java.util.Scanner;

/**
 *
 * @author vertt
 */

public class ConsoleIO implements IO{
    
    private Scanner input;
    
    public ConsoleIO() {
        this.input = new Scanner(System.in);
    }
    
    @Override
    public void print(String line){
        System.out.println(line);
    }
    
    @Override
    public String readLine(String prompt){
        print(prompt);
        return input.nextLine();
    }
}
