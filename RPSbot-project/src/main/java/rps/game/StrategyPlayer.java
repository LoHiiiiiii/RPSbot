/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rps.game;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author vertt
 */
public class StrategyPlayer implements RPSPlayer {
    
    private enum Meta {
        P0a,
        P0b,
        P1a,
        P1b,
        P2a,
        P2b
    }

    private class MetaFilter implements RPSPlayer {
    
        private final Meta myMeta;
        private final RPSPlayer myPlayer;
    
        public MetaFilter(Meta myMeta, RPSPlayer myPlayer){
            this.myMeta = myMeta;
            this.myPlayer = myPlayer;
        }
        /**
        * Rotates the move according to the specified meta. B type metas get an additional rotation because they would pick what the opponent should pick, so it needs to be rotated to beat that.
        * @return The move the player would choose rotated appropriately by the filter's meta
        */
        @Override
        public Move getMove(){
            Move myMove = myPlayer.getMove();
            switch(myMeta){
                case P1a: case P0b: return RPSLogic.rotateMove(myMove, 1);
                case P2a: case P1b: return RPSLogic.rotateMove(myMove, 2);
                default: return myMove;
            }
        }
        
        /**
        * Records the result, but mirrored for type b metas, so they instead think what the opponent should play.
        * @param myMove The move the player chose. Flipped with opponentMove for b type metas
        * @param opponentMove The move the opponent chose. Flipped with myMove for b type metas
        */
        @Override
        public void recordResult(Move myMove, Move opponentMove){
            switch(myMeta){
                case P0a: case P1a: case P2a: myPlayer.recordResult(myMove, opponentMove);
                default: myPlayer.recordResult(opponentMove, myMove);
            }
        }
        
        @Override
        public void reset(){
            myPlayer.reset();
        }
        
        @Override
        public RPSPlayer clone(){
            return new MetaFilter(myMeta, myPlayer.clone());
        }
    }
    
    BaseSelector[][] selectorLayers;
    RPSPlayer[] playerParams; //None of these are referred in the actual sets, to simplify cloning and allowing same list to initiate multiple selectors
    
    RPSPlayer[] strategies;
    Random random;
    
    /**
     * 
     * @param selectorLayers An array of array of selectors. Each selector in the first array is given the next layer as strategies to select from. The final layer is given the actual strategies.
     * @param random a random seed for the random player
     * @param players an arbitary amount of RPS strategies that the selector will choose from. Works with selectors, but intended to use primary strategies.
     */
    public StrategyPlayer(BaseSelector[][] selectorLayers, Random random, RPSPlayer... players ){
        this.selectorLayers = selectorLayers;
        this.random = random;
        playerParams = players;
        Meta[] metaValues = Meta.values();
        
        strategies = new RPSPlayer[players.length * metaValues.length +1];
        //Before loop so priotized in ties
        int index = 0;
        strategies[index] = new RandomPlayer(random);
        
        for (RPSPlayer p : players){
            if (p == null)
                continue;
            for (Meta m : metaValues){
                index++;
                strategies[index] = new MetaFilter(m, p.clone());
            }
        }
        
        if (index < strategies.length -1)
            strategies = Arrays.copyOf(strategies, index);
        
        Boolean lastLayer;
        if (selectorLayers != null){
            for (int i = 0; i < selectorLayers.length; ++i){
                lastLayer = (i == selectorLayers.length -1);
                for (int j = 0; j < selectorLayers[i].length; ++j){
                    selectorLayers[i][j].setPlayers((lastLayer) ? strategies : selectorLayers[i+1]);
                }
            }
        }
    }
    
    /**
     * Returns a move by a random topmost selector. If no selectors are given, returns a move from a random strategy, which has atleast the RandomPlayer.
     * @return The move that the StrategyPlayer thinks will win
     */
    @Override
    public Move getMove(){
        if (selectorLayers == null || selectorLayers.length == 0 || selectorLayers[0].length == 0){
            return strategies[random.nextInt(strategies.length)].getMove();
        } else {
            return selectorLayers[0][random.nextInt(selectorLayers[0].length)].getMove();
        }
    }
    
    /**
     * Records results for each strategy and selector.
     * @param myMove what this StragyPlayer played
     * @param opponentMove what the opponent played
     */
    @Override
    public void recordResult(Move myMove, Move opponentMove){
        for (int i = 0; i < selectorLayers.length; ++i){
            for (int j = 0; j < selectorLayers[i].length; ++j){
               selectorLayers[i][j].recordResult(myMove, opponentMove);
            }
        }
        for (int i = 0; i < strategies.length; ++i){
            strategies[i].recordResult(myMove, opponentMove);
        }
    }
    
    @Override
    public RPSPlayer clone(){
        BaseSelector[][] clonedLayers = null;
        if (selectorLayers != null){
            clonedLayers = new BaseSelector[selectorLayers.length][];
            for (int i = 0; i < clonedLayers.length; ++i){
                clonedLayers[i] = new BaseSelector[selectorLayers[i].length];
                for (int j = 0; j < clonedLayers[i].length; ++i){
                    clonedLayers[i][j] = (BaseSelector)selectorLayers[i][j].clone();
                }
            }
        }
        return new StrategyPlayer(clonedLayers, random, playerParams);
    }
    
    @Override
    public void reset(){
        for(BaseSelector[] array : selectorLayers){
            for (BaseSelector bs : array){
                bs.reset();
            }
        }
        
        for (int i = 0; i < strategies.length; ++i){
            strategies[i].reset();
        }
    }
    
    public String printHighestSelector(){
        return selectorLayers[0][0].toString();
    }
    
}
