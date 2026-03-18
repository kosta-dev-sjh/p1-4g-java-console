package com.kosta.console_rpg.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;

import com.kosta.console_rpg.exception.GameException;

/**
 * 문자열 SHA-256 해시 처리 유틸
 * - 입력 문자열을 byte 배열로 변환 후 해시 계산
 * - byte 배열을 16진수 문자열로 변환하여 반환
 *
 * 작성자      : 송정현
 * 생성일      : 2026.03.14
 * 최종 수정자 :
 * 최종 수정일 :
 */
public class EncryptUtil {
	private EncryptUtil() {}
	/**
	 * 입력된 문자열을 SHA-256 방식으로 해시 처리해 반환하는 메소드
	 *
	 * @param value 해시 처리할 문자열
	 * @return SHA-256 해시 문자열
	 * @throws GameException 해시 처리 중 오류가 발생한 경우
	 */
	public static String sha256(String value) throws GameException {
		try {
			// SHA-256 알고리즘 인스턴스 생성
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			
			byte[] hash = md.digest(value.getBytes(StandardCharsets.UTF_8));
			
			// 해시 결과를 16진수 문자열로 변환
			StringBuilder sb = new StringBuilder();
			for (byte b : hash) {
			    sb.append(String.format("%02x", b)); // (10진수 -> 16진수)
			}
			
			return sb.toString();
			
		} catch(NoSuchAlgorithmException e) {
			throw new GameException("일시적인 오류가 발생했습니다. 다시 시도해주세요.");
		}
	}
}
