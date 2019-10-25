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
import rps.game.FollowupFrequencyPlayer;
import rps.game.FrequencyCountPlayer;
import rps.game.HistoryPatternPlayer;
import rps.game.Move;
import rps.game.RPSPlayer;
import rps.game.RotatingPlayer;
import rps.game.StrategyPlayer;

public abstract class PlayerConstructor {
    
    /**
     * Wrapper for creating a strategy Player with 2 selectorLayers, first one with only one selector, second one with 8 selectors. 
     * Primarys strategies are 3 rotating players, each with different rotation value, a frequency count player, histroystring players for opponents move and
     * one for both players' moves, one antirotating player and one followupfrequency player.
     * @return the strategyPlayer that was constructed
     */
    public static StrategyPlayer getStrategyPlayer(){
        Random rand = new Random();
        
        BaseSelector[] topLayer = new BaseSelector[]{new DecayResetSelector(0.6, 0.1, 0.6, true, false, rand)};
        BaseSelector[] bottomLayer = new BaseSelector[]{new DecayResetSelector(0.6, 0.1, 0.6, false, false, rand), 
                                                        new DecayResetSelector(0.6, 0.1, 0.6, false, true, rand),
                                                        new DecayResetSelector(0.6, 0.1, 0.6, true, false, rand),
                                                        new DecayResetSelector(0.6, 0.1, 0.6, true, true, rand),
                                                        new DecayResetSelector(0, 0, 0, false, false, rand), //naive
                                                        new DecayResetSelector(0, 0, 0, false, true, rand), //naive, with tie as loss
                                                        new DecayResetSelector(0, 1, 1, true, false, rand), //loss always resets
                                                        new DecayResetSelector(0, 1, 1, true, true, rand)}; //loss always resets, tie is loss
        
        BaseSelector[][] selectors = new BaseSelector[2][];
        selectors[0] = topLayer;
        selectors[1] = bottomLayer;
        
        RPSPlayer[] players = new RPSPlayer[8];
        
        players[0] = new RotatingPlayer(Move.ROCK, 0);
        players[1] = new RotatingPlayer(Move.PAPER, 1);
        players[2] = new RotatingPlayer(Move.SCISSORS, 2);
        players[3] = new FrequencyCountPlayer(rand);
        players[4] = new AntirotatingPlayer(Move.ROCK);
        players[5] = new HistoryPatternPlayer(Move.SCISSORS, false);
        players[6] = new HistoryPatternPlayer(Move.PAPER, true);
        players[7] = new FollowupFrequencyPlayer(rand);
        
        return new StrategyPlayer(selectors, rand, players);
    }
}
