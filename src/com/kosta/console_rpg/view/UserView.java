package com.kosta.console_rpg.view;

/*
 * 게임 가입신청 뷰
 *
 * 작성자     : 이진주
 * 생성일     : 2026.03.16
 * 최종 수정자 : 
 * 최종 수정일 : 
 */
public class UserView {
	public static void main(String[] args) {
		UserView view = new UserView();
        view.start();
	}
	public void start() {
		System.out.println(this);
	}

	public String toString(/* String id, String pw, String name, String idMessage */) {

		StringBuilder sb = new StringBuilder();

	    String idMessage = "";
	    String id = "1";       // 문자열로 변경
	    String pw = "major1234";
	    String name = "히어로짱";

	    int FIELD_WIDTH = 23;

	    sb.append("__________________┌ SIGN UP ┐__________________\n\n");

	    sb.append(String.format(" ▸ ID   [%-" + FIELD_WIDTH + "s]\n", id));

	    if (idMessage != null && !idMessage.isEmpty()) {
	        sb.append(String.format("**%s\n", idMessage));
	    }

	    sb.append(String.format(" ▸ PW   [%-" + FIELD_WIDTH + "s]\n", pw));
	    sb.append(String.format(" ▸ NAME [%-" + (FIELD_WIDTH-2) + "s]\n\n", name));

	    sb.append("_________________________________________________\n\n");

	    sb.append("[E] ENTER\n");
	    sb.append("[C] CANCEL\n");

	    if (idMessage != null && !idMessage.isEmpty()) {
	        sb.append("[R] RETRY\n");
	    }

	    return sb.toString();
    }
	
	
}
