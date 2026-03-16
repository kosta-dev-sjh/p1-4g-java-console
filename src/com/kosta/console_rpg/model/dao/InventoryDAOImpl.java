package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.InventoryDTO;
import com.kosta.console_rpg.util.DBManager;

public class InventoryDAOImpl implements InventoryDAO {

	@Override
	public List<InventoryDTO> selectInventoryByHeroId(int heroId) throws GameException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<InventoryDTO> list = new ArrayList<>();
		
		String sql="select * from inventory where fk_hero_id = ?";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, heroId);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				InventoryDTO inventoryDTO = new InventoryDTO
						(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(5));
				
				list.add(inventoryDTO);
			}
			
			
		}catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("히어로의 인벤토리 정보를 가져오지 못했습니다.");
			
		}finally {
			DBManager.close(con, ps, rs);
		}			
				
		
		return list;
	}

	@Override
	public void equipItem(int heroId, int itemId) throws GameException {
		Connection con=null;
		PreparedStatement ps=null;

		String sql ="update inventory set inventory_is_equipped = 1 where fk_hero_id = ? and fk_item_id = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, heroId);
			ps.setInt(2, itemId);
			
			int result = ps.executeUpdate();
			
			if(result == 0) {
	            throw new GameException("장비 장착 실패");
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con, ps);
		}
		
	}

	@Override
	public void unequipItem(int heroId, int itemId) throws GameException {
		Connection con=null;
		PreparedStatement ps=null;

		String sql ="update inventory set inventory_is_equipped = 0 where fk_hero_id = ? and fk_item_id = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, heroId);
			ps.setInt(2, itemId);
			
			int result = ps.executeUpdate();
			
			if(result == 0) {
	            throw new GameException("장비 해제 실패");
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con, ps);
		}
	}

	@Override   
	public void updateUsedPotion(int heroId, int inventoryId) throws GameException {
		Connection con=null;
		PreparedStatement ps=null;

		String sql ="update inventory set inventory_quantity = ? where fk_hero_id = ? and inventory_id = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, heroId);
			ps.setInt(2, inventoryId);
			
			int result = ps.executeUpdate();
			
			if(result == 0) {
	            throw new GameException("포션 수량 업데이트 실패");
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con, ps);
		}
		
	}

	@Override
	public void updateQuantity(int heroId, int itemId, int quantity) throws GameException {
		Connection con=null;
		PreparedStatement ps=null;

		String sql ="update inventory set inventory_quantity = ? where fk_hero_id = ? and fk_item_id = ?";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, quantity);
			ps.setInt(2, heroId);
			ps.setInt(3, itemId);
			
			int result = ps.executeUpdate();
			
			if(result == 0) {
	            throw new GameException("인벤토리 수량 업데이트 실패");
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con, ps);
		}
		
	}

	@Override
	public void insertItem(int heroId, int itemId) throws GameException {
		Connection con=null;
		PreparedStatement ps=null;

		String sql ="insert into inventory(fk_hero_id, fk_item_id) values (?, ?)";
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			ps.setInt(1, heroId);
			ps.setInt(2, itemId);
			
			int result = ps.executeUpdate();
			
			if(result == 0) {
	            throw new GameException("인벤토리에 아이템 등록 실패");
	        }
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");
			
		} finally {
			DBManager.close(con, ps);
		}
	}
}
