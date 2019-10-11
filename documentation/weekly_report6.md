# Weekly Report 6

This week's focus was on getting the project to run fully on my own algorithms. I realized that the RPSplayer was a mutable key, so I decided to ditch the HashMap implemention in the selectors, which wasn't really an issue since the key-pair structure was more important there.

After I finished the data structures, I wanted the project to finally be playable, so I started pitting different variations of the AIs against eachother. The testing will use only one selector layer, but the final product will use the winner of these tests as the topmost layer, and itself and some other selectors as a secondary layer.