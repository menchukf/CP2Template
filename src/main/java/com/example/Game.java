package com.example;

import java.io.Serializable;

public class Game implements Serializable{
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
		//check for collision and register damage accordingly
		if (!players[0].attackingHitbox.vanished && players[0].attackingHitbox.checkCollision(players[1].hitbox) && !players[0].hasHit) {
			players[1].hp -= 10;
			players[0].hasHit = true;
			players[0].flow += 500;
		}
		if (!players[1].attackingHitbox.vanished && players[1].attackingHitbox.checkCollision(players[0].hitbox) && !players[1].hasHit) {
			players[0].hp -= 10;
			players[1].hasHit = true;
			players[1].flow += 500;
		}
	}
}


