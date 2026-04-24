# Blackjack Client-Server Project

Blackjack is a common card game. The game uses a standard deck of 52 cards and is also known as "21".

## Executing
### Server
Compile: javac BlackjackServer.java
Run: java BlackjackServer 1706 

### Client
Compile: javac BlackjackClient.java
Run: java BlackjackClient 127.0.0.1 1706

### Library
The library is accessed by both the Client and the Server through code integration. 

## Game Rules

The goal of Blackjack is to approach the card combination value of 21. The player must get as close to that value without going over. Going over would result in a "Bust" and the player would lose the game.

Cards numbered 2-10 hold the value of the card as shown. All face cards (Jack, Queen, King) are also valued at 10. An Ace is valued at 11 unless the player's other cards sum to be more than 21, then the Ace is valued at 1.

Blackjack is played between players and a single dealer. Each player is dealt 2 cards at the start of the game. The dealer is also dealt 2 cards, but the dealer reveals only one of their cards to the player.

## Game Flow

1. Client connects to server
2. Cards are dealt
3. Player chooses Hit or Hold
4. Dealer plays according to rules
5. Scores are compared
6. Result is displayed (Win, Lose, Draw)

## Game Start

When the client connects to the server, the client will print "Welcome to Blackjack" and then present to the player their starting hand as well as one of the dealer's cards. In the game of Blackjack, the player cannot see the dealer's full hand value. This ambiguity will impact the player's decision to either hit or hold.

## Player Actions

### Hit
At the start of the game, if the player holds a score of less than 21, they have the option to "Hit". This means they are asking for another card from the dealer. The system will then provide another card to the player and update the score. 

### Hold
If the player is satisfied with the score of their current cards, the player has the option to "Hold". The system will then finalize the player's turn and move on to the dealer's hand.

## Dealer Behavior
### Score: < 17
If the dealer has a score of under 17, the game of Blackjack requires the dealer to "Hit" (obtain more cards) until they hold a score that is 17 or greater. The system will provide cards and output the card values to the client each time the dealer hits. 
### Score: >= 17
The dealer's hand is complete and the system will then compare the scores of the dealer and the player.

## Winning the Game
### Blackjack
On the initial deal of cards, if the player obtains an Ace and a 10-value card (10, Jack, Queen, King), the player has Blackjack. If the dealer does not have Blackjack on the initial deal, the player wins. 

### Dealer vs. Player
If the player holds a score that is closer to 21 than the dealer, the player wins.

### Dealer Bust
During the consecutive dealer "Hits", if the dealer surpasses 21, then the player wins. 

## Losing the Game
### Dealer Blackjack
If the dealer obtains a natural 21 and the player does not, the player loses instantly. 
### Bust
If the player hits and receives a card that increases their total score to above 21, the player "Busts" and loses. 

## Draw
### Blackjack
If both the player and the dealer receive a natural 21 (Blackjack), the game is a draw. The system will then prompt the user to either start a new game or exit the program.
### Equal Scores 
If the player and the dealer obtain a score of the same value, the game ends in a draw. The system will prompt the user for a new game or to exit the program. 