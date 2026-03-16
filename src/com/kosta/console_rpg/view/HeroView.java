package com.kosta.console_rpg.view;

import java.util.Scanner;

import com.kosta.console_rpg.controller.HeroController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;

public class HeroView {

	public static void createHeroView() {
		System.out.println();
	}
	
	private static Scanner sc = new Scanner(System.in);


	
	public static void showHeroInfo() {

        try {
            System.out.print("유저 아이디 입력 : ");
            int userId = sc.nextInt();

            HeroDTO heroDTO = HeroController.showHeroInfo(userId);

            System.out.println("===== 히어로 정보 =====");
            System.out.println(heroDTO);

        } catch (GameException e) {
            System.out.println(e.getMessage());
        }
    }
	
	public static void skillMenu() {
		
	}
}
