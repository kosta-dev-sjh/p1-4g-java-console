package com.kosta.console_rpg.util;

import java.util.HashMap;
import java.util.Random;

public class RandomUtil {
	
	private static final Random random = new Random();
	
	private RandomUtil() {}
	
	//start이상 end미만값의 int 값 반환
	public static int randomArrange(int start, int end) {	  
		return random.nextInt(start,end);	 
	} 
	
	//1~6의 int형 정수 반환
	public static int diceRoll() {
		return randomArrange(1,7);
	}

	
	//{4=⚃}의 map으로 반환
	public static HashMap<Integer,String> inhencedDiceRoll(){
		int result= diceRoll();
		HashMap<Integer,String> dice = new HashMap<>(); 
		switch(result) {
			case 1: dice.put(1, "⚀");
			break;
			case 2: dice.put(2, "⚁");
			break;
			case 3: dice.put(3, "⚂");
			break;
			case 4: dice.put(4, "⚃");
			break;
			case 5: dice.put(5, "⚄");
			break;
			case 6: dice.put(6, "⚅");
			break;
			default: System.out.println("error");
			break;
			
				
		}			
		return dice;
		 
	 }
}
