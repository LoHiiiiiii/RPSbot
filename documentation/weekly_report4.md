# Weekly Report 4

This week was another that wasn't as productive as I had hoped. I mulled some time over the implementation of the selectors. I eventually settled on them not updating the results of the primary strategies so the same strategy could be scored by different selector without problems. This also decoupled the StrategyPlayer from selection.

I also noticed that lists would probably be nice to have, so I implemented my own.

The biggest failure of this week is a continuation of the previous one's. My unit testing is still poor. I did manage to clean it up by a little, but code coverage is still not where it should be (although now I finally remembered what to use to check code coverage for Java, which helped out a ton). I didn't have time to actually setup a working version of the StrategyPlayer, which is the biggest thing not tested at the moment. I expect all of this to improve next week.