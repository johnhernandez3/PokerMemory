/*
 * ICOM 4015 Advanced Programming - Prof. Bienvenido Vélez
 * Project 1: Poker Memory Game
 * Students: Orlando Torres (802-14-8096) & Pedro Rivera (802-14-6446)
 * April 8, 2016*/

import java.io.FileNotFoundException;
import java.io.IOException;


public class GameManager {

	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		// make an instance of the main game class
		MemoryGame instance = new MemoryGame();
		instance.newGame("easy");
	}
}



