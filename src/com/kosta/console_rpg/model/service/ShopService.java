package com.kosta.console_rpg.model.service;

import java.sql.Connection;
import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dao.HeroDAO;
import com.kosta.console_rpg.model.dao.HeroDAOImpl;
import com.kosta.console_rpg.model.dao.ShopDAO;
import com.kosta.console_rpg.model.dao.ShopDAOImpl;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.session.LoginSession;
import com.kosta.console_rpg.util.DBManager;

public class ShopService {
	
	private ShopDAO shopDAO = new ShopDAOImpl();
	private HeroDAO heroDAO = new HeroDAOImpl();

	public List<ItemDTO> showShop() throws GameException {
		
		List<ItemDTO> list = shopDAO.selectShopItems();
		
		return list;
		
	}
	
	public void buyItem(int itemId) throws GameException {
		
		int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();
		
		Connection con = null;

	    try {
	        con = DBManager.getConnection();
	        con.setAutoCommit(false); // 트랜잭션

	        // 아이템 가격 조회
	        ItemDTO item = shopDAO.selectItemById(con, itemId);

	        if (item == null) {
	            throw new GameException("존재하지 않는 아이템입니다.");
	        }

	        int price = item.getItemPriceBuy();

	        //️ 현재 Gem 조회
	        int gem = LoginSession.getInstance().getCurrentHero().getHeroGem();

	        // Gem 부족 체크
	        if (gem < price) {
	            throw new GameException("Gem이 부족합니다.");
	        }
	        
	        // 인벤토리 추가
	        shopDAO.buyShopItem(con, heroId, itemId);

	        // Gem 감소
	        heroDAO.updateHeroGem(con, heroId, gem - price);
	        LoginSession.getInstance().getCurrentHero().setHeroGem(gem - price);

	        con.commit(); //성공 시 commit

	    } catch (Exception e) {

	        try {
	            con.rollback(); //실패 시 rollback
	        } catch (Exception ex) {}

	        throw new GameException("아이템 구매 실패");
	    } finally {
	        DBManager.close(con, null);
	    }
		
	}
	
	public void sellItem(int itemId) throws GameException {
		
		int heroId = LoginSession.getInstance().getCurrentHero().getHeroId();
		
		Connection con = null;

	    try {
	        con = DBManager.getConnection();
	        con.setAutoCommit(false); // 트랜잭션

	        // 아이템 가격 조회
	        ItemDTO item = shopDAO.selectItemById(con, itemId);

	        if (item == null) {
	            throw new GameException("존재하지 않는 아이템입니다.");
	        }

	        int price = item.getItemPirceSell();

	        //️ 현재 Gem 조회
	        int gem = LoginSession.getInstance().getCurrentHero().getHeroGem();
	        
	        // 인벤토리 삭제
	        shopDAO.sellShopItem(con, heroId, itemId);

	        // Gem 증가
	        heroDAO.updateHeroGem(con, heroId, gem + price);
	        LoginSession.getInstance().getCurrentHero().setHeroGem(gem + price);

	        con.commit(); //성공 시 commit

	    } catch (Exception e) {

	        try {
	            con.rollback(); //실패 시 rollback
	        } catch (Exception ex) {}

	        throw new GameException("아이템 판매 실패");
	    } finally {
	        DBManager.close(con, null);
	    }
		
	}
	
	
}
