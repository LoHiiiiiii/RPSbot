/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.app;

import java.util.Random;
import rps.game.AntirotatingPlayer;
import rps.game.BaseSelector;
import rps.game.DecayResetSelector;
import rps.game.FrequencyCountPlayer;
import rps.game.Move;
import rps.game.RPSPlayer;
import rps.game.RotatingPlayer;
import rps.game.StrategyPlayer;

/**
 *
 * @author vertt
 */
public class StrategyPlayerConstructor {
    
    public static StrategyPlayer getStrategyPlayer(){
        Random rand = new Random();
        
        BaseSelector[][] selectors = new BaseSelector[][]{new BaseSelector[]{new DecayResetSelector(0.84, 0, 0.3, false, rand)}};
        
        RPSPlayer[] players = new RPSPlayer[5];
        
        players[0] = new RotatingPlayer(Move.ROCK, 0);
        players[1] = new RotatingPlayer(Move.PAPER, 1);
        players[2] = new RotatingPlayer(Move.SCISSORS, 2);
        players[3] = new FrequencyCountPlayer(rand);
        players[4] = new AntirotatingPlayer(Move.ROCK);
        
        return new StrategyPlayer(selectors, rand, players);
    }
}
