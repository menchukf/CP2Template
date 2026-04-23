package com.example;


public class Game{
	//map is 800*400
	private Player players[] = new Player[2];
	public Game(KeyHandler k) {
		players[0] = new Player(250, 0, k, 1);
		players[1] = new Player(550, 0, null, 2);
	}
	public Player player1() {
		return players[0];
	}
	public Player player2() {
		return players[1];
	}
	public void update() {
		for(Player player : players) {
			player.update();
		}
	}
}


