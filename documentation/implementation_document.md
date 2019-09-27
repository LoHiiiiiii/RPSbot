# Implementation document

The final structure of the AI is an arbitrary number of layers, which arrays inside an array, of Selector classes, which evaluate each other's performance when going down by a layer. The final layer gets the PrimaryStrategy classes to analyze, and their permutations through the MetaFilter class which is an subclass of the StrategyPlayer class.