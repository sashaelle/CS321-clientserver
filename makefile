JFLAGS = -g
JC = javac


client: client.class
	java blackjackClient localhost 1706
client.class:
	javac blackjackClient.java


server: server.class
	java blackjackServer 1706
server.class:
	javac blackjackServer.java

library: server.class
	javac libblackjack.java

clean:
	rm -f *.class