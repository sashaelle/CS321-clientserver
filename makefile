JFLAGS = -g
JC = javac


client: client.class
client.class:
	java blackjackClient.java localhost 1706


server: server.class
server.class:
	java blackjackServer.java 1706
