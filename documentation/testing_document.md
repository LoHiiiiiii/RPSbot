# Testing document

## The Plan

Most of the tests during this project will be simple JUnit tests that make sure that the classes work as intended. Performance testing isn't as important, as the goal is simply to beat human players, and a higher winrate will be a priority over performance to a high degree. The most interesting testing will be the test that decides the values for the selectors.

I will pit 18 522 single selectorlayer AIs against eachother. Each double parameter will receive a a value between 0.0 and 1.0 with a step of 0.05. The bots will compete each other in a round robin style tournament, and each single game will consist of a first to 100 match. The winner of this tournament shall be used as the top-most layer for the final AI. The winner and some top contenders and prechosen AI's (such as naive scoring) shall be the second layer, after which the primarystrategies through the metafilter will be available. The size of the step and amount of wins needed to win a game haven't been set in stone as the games need to finish in a somewhat reasonable time, although I am willing to let the program run for multiple hours.

## Actual testing

I quickly found out that my a single game takes too long for me to use the values I had planned to use.
Instead I took lighter iterations, first of which had only less than 500 bots. Then I looked for trends in the values of the top 10 highest performing AI's and planned the next test according to the results in order to narrow down something competitive.

After running this system for a while, it became clear that AI vs AI rock-paper-scissors quickly degenerated into playing randomly. I had limited resources to try against human, but from the 5 different people I played, none could be the final bot in first to twenty and only one could get ahead of the both once before it had gained an advantage.