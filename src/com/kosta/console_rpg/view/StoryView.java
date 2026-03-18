package com.kosta.console_rpg.view;

import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.test.ConsoleEffectTest;
/**
 *  각 스테이지에 맞는 스토리 출력을 위한 view
 *
 * 작성자     : 김재민
 * 생성일     : 2026.03.18
 * 최종 수정자 :
 * 최종 수정일 :
 */
public class StoryView {
    public static void epilogue() throws Exception {
        //hero이름 세션에서 가져옴
        String heroName = LoginSession.getInstance().getCurrentHero().getHeroName();
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        String pre = "프롤로그\n\n";
        //한번에 출력이 아닌, 속도조절 (작성할 string, 각 문장간의 딜레이 int)
        ConsoleEffectTest.slowPrint(pre,50);
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("평화롭던 왕국 아르테리아에 어둠의 기운이 스며들기 시작했다.\n"));
        sb.append(String.format("하늘은 붉게 물들었고, 현자들은 오래된 예언서에서 단 하나의 이름을 발견했다.\n"));
        sb.append(String.format("평범한 마을 청년 %s — 그는 어느 아침 문 앞에 놓인 낡은 검을 집어 들었다.\n", heroName));
        sb.append(String.format("손에 쥐는 순간 기묘한 온기가 심장까지 퍼져나갔고, 그는 아무것도 묻지 않은 채 첫 걸음을 내디뎠다.....\n\n"));
        String pro = sb.toString();
        ConsoleEffectTest.slowPrint(pro,30);

    }

    public static void stage1Start() throws Exception {
        String heroName = LoginSession.getInstance().getCurrentHero().getHeroName();
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        String title = "Stage 1 - 녹색 초원\n\n";
        ConsoleEffectTest.slowPrint(title,30);
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("왕국 동쪽 끝 초록 들판은 이름과 달리 더 이상 평화롭지 않았다.\n"));
        sb.append(String.format("반투명한 슬라임 무리가 들판을 뒤덮으며 꽃들을 녹여버리고 있었고, 마을 사람들은 두려움에 떨며 집 안에 숨어 있었다. \n"));
        sb.append(String.format("%s — 초원 어귀에 서서 천천히 검을 뽑아 들었다. %s — 초원 어귀에 서서 천천히 검을 뽑아 들었다.\n", heroName));
        sb.append(String.format("눈앞에 펼쳐진 들판 가득 번들거리는 슬라임들이 그를 향해 일제히 몸을 굴려오기 시작했다......\n\n"));
        String story = sb.toString();
        ConsoleEffectTest.slowPrint(story,30);

    }
    public static void stage1End() throws Exception {
        String heroName = LoginSession.getInstance().getCurrentHero().getHeroName();
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        String title = "Stage 1 - 후일담\n\n";
        ConsoleEffectTest.slowPrint(title,50);
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("슬라임의 몸체는 잘라도 다시 합쳐졌다\n"));
        sb.append(String.format("%s — 숨을 고르고 적을 관찰했다. \n",heroName));
        sb.append(String.format("몸의 중심부에서 어둡게 빛나는 핵을 발견한 순간, 검에 집중력을 실어 그 한 점을 정확히 꿰뚫었다."));
        sb.append(String.format("섬광이 터지며 슬라임들이 흔적도 없이 사라졌다.\n"));
        sb.append(String.format("초원에 바람이 불었다.\n"));
        sb.append(String.format("처음으로 자신의 힘을 제어한 그 순간, 소년의 눈빛이 전사의 눈빛으로 바뀌었다.\n"));
        String story = sb.toString();
        ConsoleEffectTest.slowPrint(story,30);

    }
    public static void stage2Start() throws Exception {
        String heroName = LoginSession.getInstance().getCurrentHero().getHeroName();
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        String title = "Stage 2 - 고블린 동굴\n\n";
        ConsoleEffectTest.slowPrint(title,30);
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("초원을 뒤로하고 북쪽 산맥으로 발걸음을 옮긴 %s — 어둠 속에서 타오르는 횃불 무리를 발견했다.\n", heroName));
        sb.append(String.format("고블린동굴 — 산의 내장처럼 깊이 파인 그 굴은 역겨운 냄새와 괴성으로 가득 차 있었다.\n"));
        sb.append(String.format("%s — 검손잡이를 단단히 쥐며 어둠 속으로 한 걸음씩 나아갔다.\n", heroName));

        String story = sb.toString();
        ConsoleEffectTest.slowPrint(story,30);

    }

    public static void stage2End() throws Exception {
        String heroName = LoginSession.getInstance().getCurrentHero().getHeroName();
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        String title = "Stage 2 - 후일담\n\n";
        ConsoleEffectTest.slowPrint(title,30);
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("동굴 입구를 지나는 순간 고블린이 나타났다.\n"));
        sb.append(String.format("%s — 패닉하지 않았다. \n", heroName));
        sb.append(String.format("침착하게 검을 이용하여 괴성을 지르며 달려드는 고블린을 섬멸하였다.\n"));
        sb.append(String.format("그 이후 고블린동굴에서는 더 이상 괴성이 들리지 않았다. \n"));
        sb.append(String.format("어둠 속에 조용한 숨소리만 남을 뿐이었다.....\n"));
        String story = sb.toString();
        ConsoleEffectTest.slowPrint(story,30);

    }

    public static void stage3Start() throws Exception {
        String heroName = LoginSession.getInstance().getCurrentHero().getHeroName();
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        String title = "Stage 3 - 드래곤 레어\n\n";
        ConsoleEffectTest.slowPrint(title,30);
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("세계의 끝, 불과 용암이 흐르는 검은 산 정상.\n", heroName));
        sb.append(String.format("%s — 드래곤레어 앞에 섰을 때, 외투는 너덜너덜했고 검에는 수십 번의 전투 흔적이 새겨져 있었다. \n", heroName));
        sb.append(String.format("굴 안에서 뜨거운 바람이 터져나왔다.\n"));
        sb.append(String.format("비늘 하나하나가 흑요석처럼 빛나는 드래곤 이그나르가 날개를 펼치자 하늘이 가려졌다.\n"));
        sb.append(String.format("그러나 %s — 뒷걸음치지 않았다. \n"));
        sb.append(String.format("오히려 한 걸음 더 나아갔다.\n"));

        String story = sb.toString();
        ConsoleEffectTest.slowPrint(story,30);

    }

    public static void stage3End() throws Exception {
        String heroName = LoginSession.getInstance().getCurrentHero().getHeroName();
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        String title = "Stage 3 - 드래곤 레어\n\n";
        ConsoleEffectTest.slowPrint(title,30);
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("수십 번의 공방이 오갔다.\n"));
        sb.append(String.format("어깨에 상처가 나고 피가 흘렀지만 %s — 쓰러질 때마다 다시 일어섰다.\n", heroName));
        sb.append(String.format("그것이 그의 진정한 힘이었다. \n"));
        sb.append(String.format("마침내 드래곤의 가슴 비늘 사이, 심장이 뛰는 단 한 곳을 찾아낸 순간\n"));
        sb.append(String.format("마지막 힘을 모아 도약한 검이 빛을 내뿜으며 이그나르를 꿰뚫었다.\n"));
        sb.append(String.format("드래곤레어 전체가 흔들리며 무너졌다. \n"));
        sb.append(String.format("%s — 검을 회수하고 조용히 걸어나왔다.\n", heroName));

        String story = sb.toString();
        ConsoleEffectTest.slowPrint(story,30);

    }

    public static void end() throws Exception {
        String heroName = LoginSession.getInstance().getCurrentHero().getHeroName();
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
        String title = "Ending\n\n";
        ConsoleEffectTest.slowPrint(title,30);
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("아르테리아에 종이 울렸다.\n"));
        sb.append(String.format("왕국의 모든 이들이 거리로 쏟아져 나와 하늘을 올려다보았고,"));
        sb.append(String.format("드래곤레어가 있던 검은 산에서 뻗어나온 빛이 초원으로, 동굴로, 마을 골목골목까지 퍼져나갔다.\n", heroName));
        sb.append(String.format("%s — 왕궁이 아닌 마을로 돌아갔다. \n"));
        sb.append(String.format("왕관도, 훈장도 원하지 않았다. \n"));
        sb.append(String.format("그저 낡은 검을 문 앞에 세워두고 내일의 아침을 기다렸다.\n"));
        sb.append(String.format("그의 이름은 세월이 흘러 전설이 되었다.\n"));
        sb.append(String.format("아이들이 그 이름을 부르며 뛰놀고, 음유시인이 노래하며, 현자들이 책에 기록했다.\n"));
        sb.append(String.format("\n.\n.\n.\n.\n"));
        sb.append(String.format("슬라임의 들판에서 지혜를 배웠고, 고블린의 어둠 속에서 용기를 증명했으며, 드래곤의 불길 앞에서 전설이 되었다.\n" +
                "— 아르테리아 왕국 연대기 中\n"));

        String story = sb.toString();
        ConsoleEffectTest.slowPrint(story,30);

    }

}
