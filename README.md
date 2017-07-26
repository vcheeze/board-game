The objective of this assignment is to add an additional game to the framework.

## Complica

Complica is similar to Connect Four in that players attempt achieve four chips in a row vertically, horizontally, or diagonally. There are two major differences in the game:

1. Complica is played on seven-row, four-column board; and
2. players are allowed to add chips to columns that are seemingly full.

The second difference has major implications for what end-of-game means in Complica. In cases where the column is full, that column behaves like a queue: the first chip in, now at the bottom of the column, is pushed out; all other chips are lowered; and the new chip is placed at the top of the column.

This rule change means that a board with chips placed in every position does not necessarily imply that the game is over. Further, the first instance of four chips in-a-row by a particular player does not necessarily imply that that player is the winner. The winner is the player with the most (four-)in-a-row's after a chip has been placed. If two players have the same number of consecutive chips (zero included) the game continues.

## Older components

Adding a new model will truly test the MVC design decisions you made. If your previous implementation was solid, you should not have to alter any view code -- or at least very little view code -- to drop in the new game. Both games should easily work with the console or the graphical view. There may be alterations pertaining to how you instantiate the concrete game, but those should be localised and minimal: if your view knows how to deal with a `Game`, it should not care with which game it is dealing.

## Run

To run the game, the game and view type should be provided via the command line; your code in `test.sys.GameTest` should deal with these arguments.

You can supply these arguments through Ant as follows:
```
ant run -Dgame=complica -Dview=console
```
Ant will then take care of passing `complica` and `console` to `test.sys.GameTest` in the correct order. Possible values for `game` and `view` are connectfour and graphical, respectively. If such values are passed, the requested model and view should be run.

## Expectations

### Usability (30 points)

Usability and programmatic aspects of the previous assignment should be maintained. That means there should be a single graphical view and a single console view that are capable of displaying and handling interaction of a specified model.

### Correctness (40 points)

As was the case in the first edition of Board Games, there is a suite of tests we have developed that we will use for grading. However, you are strongly encouraged to build your own unit tests during development. Again, those tests should reside in `src/test/unit/foo`, where `foo` is your NetID. Tests for Complica should be in their own Java file, and can live alongside tests for Connect Four. The number of points you receive for this aspect will be based on the fraction of tests passed.

Questions about testing semantics, and thus ambiguities to this specification, should be resolved using GitHub issues (mark the "assignee" as @jsw7 or @kqm1).

### Design (10 points)

There are now four different "modes" your suite must support: two games and two views. To properly manage this complexity, you should use the factory and template design patterns. The factory design pattern comes in various flavors -- the "simple" version is good enough, but the "abstract" version is also acceptable.

Your code should clearly document where these patterns have been applied.

## Working with Git

You should sync your current fork with this one, prior to beginning. Before doing so, it is a good idea to "tag" your current version:
```bash
$> git tag -a v2 -m "Homework 2 final"
$> git push origin v2
```
Tagging allows you to "bookmark" a milestone in your projects development. Once the tag is complete, you can go about syncing your fork; instructions for doing so can be found [here](https://help.github.com/articles/syncing-a-fork/). If you happen to sync before you tag, all is not lost: you can [retroactively tag](http://stackoverflow.com/a/4404197) if you know the commit you would like to tag. Syncing should update your README and add the new class structure to your existing work.

## Workflow

It is expected that you will use GitHub religiously. You should fork this project, then `branch`/`commit`/`push` freely.

See the assignment listing in NYU Classes for the due date. We will grade the code that is on your master branch at the exact deadline.
