# PenguinFishProbability
A simulation for a thought experiment. 

Let's say I take a grid of 100 x 100 squares, and randomly assign each square in the grid to be water or ice. Which of the following do you think is the most likely?

(a) There is a path of water such that a fish can swim from the first column to the last column through the grid, only going on water.
(b) There is a path of ice such that a penguin can walk from the first column to the last column through the grid, only going on ice.
(c) Both the fish and the penguin can get across.
(d) Neither the fish nor the penguin can get across.

To clarify, diagonal moves aren't allowed. The penguin can't walk diagonally or "jump" across water, and the fish can't swim diagonally or jump over the ice.

## To compile and run

To compile: `javac src/MultiThreadExperiment/*.java`

To run: `java -cp src MultiThreadExperiment.Main`
