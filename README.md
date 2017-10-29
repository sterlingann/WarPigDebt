# DebtWars

## OverView
DebtWars is an autonomous "Pacman" style game played by two robo-piggy banks that take turns racing along a 7x7 game board (Android GridView) of credit cards (Nodes) in search of a randomly placed "get out of debt" token. 

## How to Play
1.  At the start of each round, the coordinates of a magical "get out of debt" destination are randomly assigned.  
2. The hungry robo pigs will take turns in half second intervals evaluating all of the remaining credit cards to determine the best path to travel so that they can get you out of debt!
3. An "escape from debt" square is indicated on the board.  Credit cards which have not yet been visited are grey. The robo pigs leave behind a trail of eaten aka "cut-up" cards, which correspond to each piggybank by their respective color.  
4. The game ends automatically when a robo-pig reaches its goal destination.  Hit the "play" button to load a new users "credit report" and start another round.  Hit the "reset" button to clear the scoreboard.
5. When a robo-pig wins it earns a point.  A tally of the #of users that each pig has rescued from debt is visible on the bottom portion of the screen.

## How it Works
The robo pigs have a sophisticated pallete.  They wont eat a credit card that the opponent pig has already consumed.  The pigs want to eliminate your debt in a manner that is as as short and fast as possible.  The robo-pigs determine the most apetizing path by using the a* algorithm.   

## Features
The game is designed in Android Studio.  The target sdk is 25.  Please contact me at sterlingann@gmail.com if you encounter any issues bulding or compiling.

## Sources
The most difficult part of developing this was coming up with the a logic that adhered to the rules.  Fortunately there were many articles on google that described various types of shortest path algorithms that could be used in this situation.
* http://www.geeksforgeeks.org/a-search-algorithm/
* https://en.wikipedia.org/wiki/A*_search_algorithm
* https://developer.android.com/guide/topics/ui/layout/gridview.html
* https://www.javatpoint.com/java-priorityqueue