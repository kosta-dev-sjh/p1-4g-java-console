package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.InventoryDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.util.DBManager;

public class ShopDAOImpl implements ShopDAO {

	@Override
	public List<ItemDTO> selectShopItems() throws GameException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ItemDTO> list = new ArrayList<>();
		
		String sql="select i.* from shop s join item i on s.fk_item_id = i.item_id;";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ItemDTO itemDTO = new ItemDTO(
		                rs.getInt("item_id"),
		                rs.getString("item_name"),
		                rs.getString("item_type"),
		                rs.getInt("item_price_buy"),
		                rs.getInt("item_price_sell"),
		                rs.getInt("item_effect_hp"),
		                rs.getInt("item_effect_mp"),
		                rs.getInt("item_atk_bonus"),
		                rs.getInt("item_def_bonus"),
		                rs.getString("item_grade")
		            );
				
				list.add(itemDTO);
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("상점 정보를 가져오지 못했습니다.");
			
		}finally {
			DBManager.close(con, ps, rs);
		}			
				
		
		return list;
	}
	
	public ItemDTO selectItemById(Connection con, int itemId) throws GameException {

	    PreparedStatement ps = null;
	    ResultSet rs = null;

	    ItemDTO itemDTO = null;

	    String sql = "select * from item where item_id = ?";

	    try {
	        con = DBManager.getConnection();
	        ps = con.prepareStatement(sql);

	        ps.setInt(1, itemId);

	        rs = ps.executeQuery();

	        if (rs.next()) {

	            itemDTO = new ItemDTO(
	                    rs.getInt("item_id"),
	                    rs.getString("item_name"),
	                    rs.getString("item_type"),
	                    rs.getInt("item_price_buy"),
	                    rs.getInt("item_price_sell"),
	                    rs.getInt("item_effect_hp"),
	                    rs.getInt("item_effect_mp"),
	                    rs.getInt("item_atk_bonus"),
	                    rs.getInt("item_def_bonus"),
	                    rs.getString("item_grade")
	            );
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new GameException("아이템 정보를 가져오지 못했습니다.");

	    } finally {
	        DBManager.close(null, ps, rs);
	    }

	    return itemDTO;
	}

	
	public void buyShopItem(Connection con, int heroId, int itemId, int quantity) throws GameException {

	    PreparedStatement ps = null;

	    try {
	        String updateSql = """
	                update inventory
	                set inventory_quantity = inventory_quantity + ?
	                where fk_hero_id = ?
	                and fk_item_id = ?
	                """;

	        ps = con.prepareStatement(updateSql);
	        ps.setInt(1, quantity);
	        ps.setInt(2, heroId);
	        ps.setInt(3, itemId);

	        int result = ps.executeUpdate();
	        
	        if (result == 0) {

	            String insertSql = """
	                    insert into inventory(fk_hero_id, fk_item_id, inventory_quantity)
	                    values (?, ?, ?)
	                    """;

	            ps = con.prepareStatement(insertSql);
	            ps.setInt(1, heroId);
	            ps.setInt(2, itemId);
	            ps.setInt(3, quantity);

	            ps.executeUpdate();
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new GameException("아이템 구매 DB 오류");

	    } finally {
	        DBManager.close(ps);
	    }
	}
	
	@Override
	public void buyShopItem(int heroId, int itemId, int quantity) throws GameException {
		Connection con=null;
		
		try {
			con = DBManager.getConnection();
			buyShopItem(con, heroId, itemId, quantity);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con);
		}
		
	}
	
	
	@Override
	public void sellShopItem(Connection con, int heroId, int itemId) throws GameException {
		PreparedStatement ps=null;
		
		String updateSql =
		        "update inventory set inventory_quantity = inventory_quantity - 1 "
		      + "where fk_hero_id = ? and fk_item_id = ? and inventory_quantity > 0";
		
		try {
			ps = con.prepareStatement(updateSql);
			
			ps.setInt(1, heroId);
			ps.setInt(2, itemId);
			
			int result = ps.executeUpdate();
			
			String deleteSql = "delete from inventory where fk_hero_id = ? and fk_item_id = ? and inventory_quantity = 0";
			
			ps = con.prepareStatement(deleteSql);
		    ps.setInt(1, heroId);
		    ps.setInt(2, itemId);

		    ps.executeUpdate();
		    
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(ps);
		}
	}


	@Override
	public void sellShopItem(int heroId, int itemId) throws GameException {
		Connection con=null;
		
		try {
			con = DBManager.getConnection();
			sellShopItem(con, heroId, itemId);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con);
		}
	}

}
