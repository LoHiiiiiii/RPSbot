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
public class AIVersus {
    
    public static void start(IO io){
       
    }
    
    public static void roundRobin(IO io, int wins, int rounds, int decayStep, int resetStep, int chosenResetStep, double decayMin, double decayMax, double resetMin, double resetMax, double chosenResetMin, double chosenResetMax){
        int index = 0;
        Random rand = new Random();
        StrategyPlayer[] ais = new StrategyPlayer[decayStep * resetStep * chosenResetStep * 4];
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
                    ais[index] = new StrategyPlayer(new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(decay, reset, chosenReset, true, true, rand)}}, rand, players);
                    scores[index] = 0;
                    index++;
                    ais[index] = new StrategyPlayer(new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(decay, reset, chosenReset, true, false, rand)}}, rand, players);
                    scores[index] = 0;
                    index++;
                    ais[index] = new StrategyPlayer(new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(decay, reset, chosenReset, false, true, rand)}}, rand, players);
                    scores[index] = 0;
                    index++;
                    ais[index] = new StrategyPlayer(new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(decay, reset, chosenReset, false, false, rand)}}, rand, players);
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
                for (int k  = 0; k < rounds; ++k){
                    logic.startGame(wins);
                    int plays = 0;
                    while(!logic.playerOneHasWon() && !logic.playerTwoHasWon()){
                        p1 = ais[i].getMove();
                        p2 = ais[j].getMove();
                        logic.evaluateMoves(p1, p2);
                        ais[i].recordResult(p1, p2);
                        ais[j].recordResult(p2, p1);
                        plays++;
                        if (plays % (wins * 10) == 0)
                            io.print(logic.getScore());
                        if (plays >= wins * 100)
                            break;
                    }
                    if (logic.playerOneHasWon())
                        scores[i]++;
                    else
                        scores[j]++;
                    ais[i].reset();
                    ais[j].reset();
                    io.print("Playing:"+ ((i)*ais.length + (j)) + "/" +(ais.length*ais.length-1));
                }
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
                io.print(ais[scoreIndices.get(scores[i]).get(j)].printHighestSelector() + " - " + (scores[i]/((double)(ais.length-1) * rounds) * 100) + "%");
        }
        
        for(int i = 0; i < 10; ++i){
            for (int j = 0; j < scoreIndices.get(scores[i]).count(); ++j)
                io.print(ais[scoreIndices.get(scores[i]).get(j)].printHighestSelector() + " - " + (scores[i]/((double)(ais.length-1) * rounds) * 100) + "%");
        }
    }
    
    public static void presetFoes(IO io, int wins, int rounds, int decayStep, int resetStep, int chosenResetStep, double decayMin, double decayMax, double resetMin, double resetMax, double chosenResetMin, double chosenResetMax){
        int index = 0;
        Random rand = new Random();
        StrategyPlayer[] ais = new StrategyPlayer[decayStep * resetStep * chosenResetStep * 4];
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
                    decay = (decayStep <= 1) ? decayMin : decayMin + (i/((double) decayStep-1)) * (decayMax-decayMin);
                    reset = (resetStep <= 1) ? resetMin : resetMin + (j/((double) resetStep-1)) * (resetMax-resetMin);
                    chosenReset = (chosenResetStep <= 1) ? chosenResetMin : chosenResetMin + (k/((double) chosenResetStep-1)) * (chosenResetMax-chosenResetMin);
                    ais[index] = new StrategyPlayer(new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(decay,reset, chosenReset, true, true, rand)}}, rand, players);
                    scores[index] = 0;
                    index++;
                    ais[index] = new StrategyPlayer(new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(decay,reset, chosenReset, true, false, rand)}}, rand, players);
                    scores[index] = 0;
                    index++;
                    ais[index] = new StrategyPlayer(new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(decay,reset, chosenReset, false, true, rand)}}, rand, players);
                    scores[index] = 0;
                    index++;
                    ais[index] = new StrategyPlayer(new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(decay,reset, chosenReset, false, false, rand)}}, rand, players);
                    scores[index] = 0;
                    index++;
                    io.print("Initializing: " + index + "/" + ais.length);
                }
            }
        }
        
        RPSLogic logic = new RPSLogic();
        
        Move p1, p2;
        
        RPSPlayer[] foes = PlayerConstructor.getFoes();
        
        for (int i = 0; i < ais.length; ++i){
            for (int j = 0; j < foes.length; ++j){
                for (int k  = 0; k < rounds; ++k){
                    logic.startGame(wins);
                    int plays = 0;
                    while(!logic.playerOneHasWon() && !logic.playerTwoHasWon()){
                        p1 = ais[i].getMove();
                        p2 = foes[j].getMove();
                        logic.evaluateMoves(p1, p2);
                        ais[i].recordResult(p1, p2);
                        foes[j].recordResult(p2, p1);
                        plays++;
                        if (plays % (wins * 10) == 0)
                            io.print(logic.getScore());
                        if (plays >= wins * 1000)
                            break;
                    }
                    if (logic.playerOneHasWon())
                        scores[i]++;
                    ais[i].reset();
                    foes[j].reset();
                    io.print("Playing:"+ ((i)*foes.length + (j)) + "/" +(ais.length*foes.length));
                }
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
                io.print(ais[scoreIndices.get(scores[i]).get(j)].printHighestSelector() + " - " + (scores[i]/((double)(foes.length) * rounds) * 100) + "%");
        }
        
        for(int i = 0; i < 10; ++i){
            for (int j = 0; j < scoreIndices.get(scores[i]).count(); ++j)
                io.print(ais[scoreIndices.get(scores[i]).get(j)].printHighestSelector() + " - " + (scores[i]/((double)(foes.length) * rounds) * 100) + "%");
        }
    }
}
