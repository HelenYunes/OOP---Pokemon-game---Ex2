# Welcome to the Helen's & Ilan's Pokemon game project!

This project is about implementing a directed (positive) weighted graph theory algorithms. In the second part of the project we implemented these algorithms on a game we called “Catch the Pokemons”.

![three_pokemons](https://cdn.vox-cdn.com/thumbor/DTp9raihs-H_5AvJYmaGg7sHz-k=/0x0:2257x1320/920x613/filters:focal(949x480:1309x840):format(webp)/cdn.vox-cdn.com/uploads/chorus_image/image/63738986/pokemon.0.0.png)

## What is this Pokemon game?
This game is a Pokemon catching game. The game contains 24 unique levels [0,23] with a different scenario in each level. The player receives information through the server's API in JSON format about the the Directed Weighted Graph, where it's nodes and edges are located. The user also receives on which edge the Pokemons are currently on. 
Each level the player has different number of agents which the player can control and use to catch the Pokemons. The Pokemons are caught only if the agent passes near them on the edge that they're located on. The goal is to catch as many Pokemons as possible in the given game time and get the maximum amount of points. Each Pokemon has a different value, different Pokemons have different points they reward when caught. Points are deducted as the number of moves the player chooses to make grows.

## How to control the agents?
The agents are controlled by java code which is executed when the game starts and should run automatically without any further user input. The player has to come up with the best algorithm for the task of getting maximum points in each scenario.

This is our grade table:

![grade_table](https://i.imgur.com/eTxWAeF.jpg)

## How to install and run the game?

* First download the Ex2.jar file in the repository home page or [here will be the link to the download](https://imgur.com/pudcf0A).
* Download the 'data' folder and put it in the same directory as Ex2.jar.
* Run Ex2.jar.
* (optional) run the Ex2.jar file from the terminal followed by id number and level, i.e ./Ex2.jar 123456789 15.

## How to play?
After the player launched the game:
* Enter the player's ID number.
* Choose the scenario number.
* Click on start the game.
* Wait until the time runs out and see the score.

![game_menu](https://i.ibb.co/mTZ8hTv/Whats-App-Image-2020-12-19-at-12-47-17.jpg)
