package com.kosta.console_rpg.view;

import com.kosta.console_rpg.controller.SkillController;
import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.HeroDTO;
import com.kosta.console_rpg.model.dto.HeroSkillDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.ConsoleUtils;
import com.kosta.console_rpg.util.InputUtil;

import java.util.List;

public class SkillUpgradeView {

	private static final int MP_CONSUMPTION = 5;
	private static SkillController skillController = new SkillController();

	public static void start() {

		if (LoginSession.getInstance().getCurrentHero() == null) {
			System.out.println("로그인이 필요합니다.");
			return;
		}

		while (true) {
			System.out.println(new SkillUpgradeView());
			System.out.print("선택 ▶ ");

			try {
				int choice = InputUtil.inputInt();

				if (choice == 0) {
					System.out.println("뒤로가기");
					return;
				}

				List<HeroSkillDTO> skillList = skillController.selectHeroSkills();

				if (choice < 1 || choice > skillList.size()) {
					System.out.println("잘못된 입력입니다.");
					continue;
				}

				HeroSkillDTO selected = skillList.get(choice - 1);
				int skillId = selected.getSkill().getSkillId();

				// Y/N 확인
				System.out.println("\n▶ 스킬을 강화하시겠습니까?");
				System.out.print("[Y] YES  [N] NO ▶ ");

				String confirm = InputUtil.inputString().trim().toUpperCase();

				if (!confirm.equals("Y")) {
					System.out.println("취소되었습니다.");
					continue;
				}

				// 강화 실행
				skillController.upgradeHeroSkill(skillId);

				// 최신 데이터 다시 조회
				List<HeroSkillDTO> updatedList = skillController.selectHeroSkills();
				HeroSkillDTO updatedSkill = updatedList.get(choice - 1);

				int newLevel = updatedSkill.getSkillLevel();
				int currentGem = LoginSession.getInstance().getCurrentHero().getHeroGem();

				// 결과 출력
				System.out.println("\n현재 " + updatedSkill.getSkill().getSkillName() + " 레벨은 " + newLevel + "입니다.");
				System.out.println();
				System.out.println("현재 보유 Gem : " + currentGem);

			} catch (GameException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	@Override
	public String toString() {

		HeroDTO hero = LoginSession.getInstance().getCurrentHero();
		List<HeroSkillDTO> skillList = skillController.selectHeroSkills();

		StringBuilder sb = new StringBuilder();

        ConsoleUtils.printTitleBar("HERO STATUS");
		sb.append(String.format(" 히어로 : %s\n", hero.getHeroName()));
		sb.append(String.format(" 현재 Level : %d\n", hero.getHeroLevel()));
		sb.append(String.format(" 보유 Gem : %d\n\n", hero.getHeroGem()));

		sb.append("──────────────── SKILL UPGRADE ─────────────────\n\n");

		for (int i = 0; i < 3; i++) {
			HeroSkillDTO skill = skillList.get(i);
			int upgradeCost = skillList.get(i).getSkill().getSkillUpgradeCost() * skill.getSkillLevel();
			int requiredLevel = skill.getSkill().getSkillRequiredHeroLevel();

			sb.append(String.format(" [%d] %s\n", i + 1, skillList.get(i).getSkill().getSkillName()));
			sb.append(String.format("    ▸ 레벨 : %d\n", skillList.get(i).getSkillLevel()));
			sb.append(String.format("    ▸ 데미지 : %s\n", skillList.get(i).getCalculatedDamage()));
			sb.append(String.format("    ▸ MP 소모 : %d\n", skill.getCalculatedMpCost()));

			if (skillList.get(i).getSkillLevel() == 2) {
				requiredLevel += 1;

				sb.append(String.format("    ▸ 요구 레벨 : %d\n", requiredLevel));
				sb.append("    --------------\n");
				sb.append(String.format("    ▸ 스킬 강화 비용: %d Gem\n", upgradeCost));
				sb.append("\n\n");
			} else if (skill.getSkillLevel() == 3) {
				sb.append(String.format("    ▸ SKILL MASTERED\n"));
				sb.append("\n\n");
			} else {
				sb.append(String.format("    ▸ 요구 레벨 : %d\n", requiredLevel));
				sb.append("    --------------\n");
				sb.append(String.format("    ▸ 스킬 강화 비용: %d Gem\n", upgradeCost));
				sb.append("\n\n");
			}
		}

		sb.append("────────────────── MENU ──────────────────\n\n");
		sb.append(" [번호 입력] 스킬 강화\n");
		sb.append(" [0] 뒤로가기\n\n");

		return sb.toString();
	}
}