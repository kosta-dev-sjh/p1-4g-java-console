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
		
		String sql="select * from shop";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			
			while(rs.next()) {
				ItemDTO itemDTO = new ItemDTO
						(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
				rs.getInt(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), rs.getString(10));
				
				list.add(itemDTO);
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
	public void buyShopItem(int heroId, int itemId) throws GameException {
		

	}

	@Override
	public void sellShopItem(int heroId, int itemId) throws GameException {
		

	}

}
