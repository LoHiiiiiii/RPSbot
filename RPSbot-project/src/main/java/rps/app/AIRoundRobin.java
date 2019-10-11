/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.app;

import java.util.Random;
import java.util.Arrays;
import rps.game.AntirotatingPlayer;
import rps.game.BaseSelector;
import rps.game.DecayResetSelector;
import rps.game.FrequencyCountPlayer;
import rps.game.Move;
import rps.game.MyHashMap;
import rps.game.MyList;
import rps.game.RPSLogic;
import rps.game.RPSPlayer;
import rps.game.RotatingPlayer;
import rps.game.StrategyPlayer;

/**
 *
 * @author vertt
 */
public class AIRoundRobin {
    
    public static void start(IO io, int wins){
        //roundRobin(io, wins, 6, 6, 6, 0, 1, 0, 1, 0, 1); //First iteration, 100 wins
        //roundRobin(io, wins, 11, 4, 4, 0.7, 0.9, 0, 1, 0, 1); //Second iteration, 100 wins
        //roundRobin(io, wins, 10, 1, 20, 0.6, 0.85, 0, 0, 0, 1); //Third iteration, 100 wins
        roundRobin(io, wins, 16, 1, 9, 0.7, 0.85, 0, 0, 0.1, 0.9); //Fourth iteration, 500 wins
    }
    
    public static void roundRobin(IO io, int wins, int decayStep, int resetStep, int chosenResetStep, double decayMin, double decayMax, double resetMin, double resetMax, double chosenResetMin, double chosenResetMax){
        int index = 0;
        Random rand = new Random();
        StrategyPlayer[] ais = new StrategyPlayer[decayStep * resetStep * chosenResetStep * 2];
        Integer[] scores = new Integer[ais.length];
        
        RPSPlayer[] players = new RPSPlayer[5];
        
        players[0] = new RotatingPlayer(Move.ROCK, 0);
        players[1] = new RotatingPlayer(Move.PAPER, 1);
        players[2] = new RotatingPlayer(Move.SCISSORS, 2);
        players[3] = new FrequencyCountPlayer(rand);
        players[4] = new AntirotatingPlayer(Move.ROCK);
        
        double decay, reset, chosenReset;
        
        for (int i = 0; i < decayStep; ++i){
            for (int j = 0; j < resetStep; ++j){
                for (int k = 0; k < chosenResetStep; ++k){
                    decay = (decayStep <= 1) ? decayMin :decayMin + (i/((double) decayStep-1)) * (decayMax-decayMin);
                    reset = (resetStep <= 1) ? resetMin : resetMin + (j/((double) resetStep-1)) * (resetMax-resetMin);
                    chosenReset = (chosenResetStep <= 1) ? chosenResetMin : chosenResetMin + (k/((double) chosenResetStep-1)) * (chosenResetMax-chosenResetMin);
                    rand = new Random();
                    ais[index] = new StrategyPlayer(new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(decay,reset, chosenReset, true, rand)}}, rand, players);
                    scores[index] = 0;
                    index++;
                    rand = new Random();
                    ais[index] = new StrategyPlayer(new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(decay,reset, chosenReset, false, rand)}}, rand, players);
                    scores[index] = 0;
                    index++;
                    io.print("Initializing: " + index + "/" + ais.length);
                }
            }
        }
        
        RPSLogic logic = new RPSLogic();
        
        Move p1, p2;
        
        for (int i = 0; i < ais.length -1; ++i){
            for (int j = i; j < ais.length; ++j){
                logic.startGame(wins);
                int rounds = 0;
                while(!logic.playerOneHasWon() && !logic.playerTwoHasWon()){
                    p1 = ais[i].getMove();
                    p2 = ais[j].getMove();
                    logic.evaluateMoves(p1, p2);
                    ais[i].recordResult(p1, p2);
                    ais[j].recordResult(p2, p1);
                    rounds++;
                    if (rounds % (wins * 10) == 0)
                        io.print(logic.getScore());
                    if (rounds >= wins * 1000)
                        break;
                }
                if (logic.playerOneHasWon())
                    scores[i]++;
                else
                    scores[j]++;
                io.print("Playing:"+ ((i)*ais.length + (j)) + "/" +(ais.length*ais.length-1));
            }
        }
        MyHashMap<Integer, MyList<Integer>> scoreIndices = new MyHashMap(ais.length);
        
        for (int i = 0; i < scores.length; ++i){
            scoreIndices.put(scores[i], new MyList());
            scoreIndices.get(scores[i]).add(i);
        }
        Arrays.sort(scores);
        
        for(int i = scores.length -1; i > scores.length -11; --i){
            for (int j = 0; j < scoreIndices.get(scores[i]).count(); ++j)
                io.print(ais[scoreIndices.get(scores[i]).get(j)].printHighestSelector() + " - " + (scores[i]/((double)ais.length-1) * 100) + "%");
        }
        
        for(int i = 0; i < 10; ++i){
            for (int j = 0; j < scoreIndices.get(scores[i]).count(); ++j)
                io.print(ais[scoreIndices.get(scores[i]).get(j)].printHighestSelector() + " - " + (scores[i]/((double)ais.length-1) * 100) + "%");
        }
    }
}
