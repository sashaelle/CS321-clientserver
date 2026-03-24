# CS321-clientserver


Client
-Accepts cards from server

-Asks user to hit or hold

-If hit
    -Accepts card from server
    -Checks for bust (hand over 21)
    
-If hold
    -Sends hand to client
    -Recieves winner from client and displays to user


Server

-Sends card(s) to client

-Calculates winner (closer to 21 but not over)

-Returns winner to client with point totals


Library

-Calculates the value of a hand
