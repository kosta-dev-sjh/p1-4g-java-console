package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kosta.console_rpg.exception.GameException;
import com.kosta.console_rpg.model.dto.BattlePotionDTO;
import com.kosta.console_rpg.model.dto.InventoryDTO;
import com.kosta.console_rpg.model.dto.ItemDTO;
import com.kosta.console_rpg.util.DBManager;

public class InventoryDAOImpl implements InventoryDAO {

	@Override
	public List<InventoryDTO> selectInventoryByHeroId(int heroId) throws GameException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<InventoryDTO> list = new ArrayList<>();

		String sql = "select "
				+ " inv.inventory_id, "
				+ " inv.fk_hero_id, "
				+ " inv.fk_item_id, "
				+ " inv.inventory_quantity, "
				+ " inv.inventory_is_equipped, "
				+ " i.item_id, "
				+ " i.item_name, "
				+ " i.item_type, "
				+ " i.item_price_buy, "
				+ " i.item_price_sell, "
				+ " i.item_effect_hp, "
				+ " i.item_effect_mp, "
				+ " i.item_atk_bonus, "
				+ " i.item_def_bonus, "
				+ " i.item_grade "
				+ "from inventory inv "
				+ "join item i "
				+ "on inv.fk_item_id = i.item_id "
				+ "where inv.fk_hero_id = ?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, heroId);
			rs = ps.executeQuery();

			while (rs.next()) {

				ItemDTO item = new ItemDTO(
						rs.getInt("item_id"),
						rs.getString("item_name"),
						rs.getString("item_type"),
						rs.getInt("item_price_buy"),
						rs.getInt("item_price_sell"),
						rs.getInt("item_effect_hp"),
						rs.getInt("item_effect_mp"),
						rs.getInt("item_atk_bonus"),
						rs.getInt("item_def_bonus"),
						rs.getString("item_grade"));

				InventoryDTO inventory = new InventoryDTO(
						rs.getInt("inventory_id"),
						rs.getInt("fk_hero_id"),
						rs.getInt("fk_item_id"),
						rs.getInt("inventory_quantity"),
						rs.getInt("inventory_is_equipped"));

				inventory.setItem(item); // InventoryDTO 안에 ItemDTO 포함시키기

				list.add(inventory);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("인벤토리 조회 실패");
		} finally {
			DBManager.close(con, ps, rs);
		}

		return list;
	}

	@Override
	public InventoryDTO selectInventoryById(int inventoryId) throws GameException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		InventoryDTO inventory = null;

		String sql = "select "
				+ " inv.inventory_id, "
				+ " inv.fk_hero_id, "
				+ " inv.fk_item_id, "
				+ " inv.inventory_quantity, "
				+ " inv.inventory_is_equipped, "
				+ " i.item_id, "
				+ " i.item_name, "
				+ " i.item_type, "
				+ " i.item_price_buy, "
				+ " i.item_price_sell, "
				+ " i.item_effect_hp, "
				+ " i.item_effect_mp, "
				+ " i.item_atk_bonus, "
				+ " i.item_def_bonus, "
				+ " i.item_grade "
				+ "from inventory inv "
				+ "join item i "
				+ "on inv.fk_item_id = i.item_id "
				+ "where inv.inventory_id = ?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, inventoryId);

			rs = ps.executeQuery();

			if (rs.next()) {

				ItemDTO item = new ItemDTO(
						rs.getInt("item_id"),
						rs.getString("item_name"),
						rs.getString("item_type"),
						rs.getInt("item_price_buy"),
						rs.getInt("item_price_sell"),
						rs.getInt("item_effect_hp"),
						rs.getInt("item_effect_mp"),
						rs.getInt("item_atk_bonus"),
						rs.getInt("item_def_bonus"),
						rs.getString("item_grade"));

				inventory = new InventoryDTO(
						rs.getInt("inventory_id"),
						rs.getInt("fk_hero_id"),
						rs.getInt("fk_item_id"),
						rs.getInt("inventory_quantity"),
						rs.getInt("inventory_is_equipped"));

				inventory.setItem(item);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("인벤토리 단건 조회 실패");
		} finally {
			DBManager.close(con, ps, rs);
		}

		return inventory;
	}

	@Override
	public boolean equipStatus(int heroId, int itemId) throws GameException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		boolean status = false;

		String sql = "select inventory_is_equipped from inventory where fk_hero_id = ? and fk_item_id = ?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, heroId);
			ps.setInt(2, itemId);

			rs = ps.executeQuery();

			if (rs.next()) {
				status = rs.getBoolean("inventory_is_equipped");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("아이템 장착 상태 조회 실패");
		} finally {
			DBManager.close(con, ps, rs);
		}
		return status;
	}

	@Override
	public void equipItem(int heroId, int itemId) throws GameException {
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "update inventory set inventory_is_equipped = 1 where fk_hero_id = ? and fk_item_id = ?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, heroId);
			ps.setInt(2, itemId);

			int result = ps.executeUpdate();

			if (result == 0) {
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
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "update inventory set inventory_is_equipped = 0 where fk_hero_id = ? and fk_item_id = ?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, heroId);
			ps.setInt(2, itemId);

			int result = ps.executeUpdate();

			if (result == 0) {
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
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "update inventory set inventory_quantity = inventory_quantity - 1 where fk_hero_id = ? and inventory_id = ? and inventory_quantity > 0";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, heroId);
			ps.setInt(2, inventoryId);

			int result = ps.executeUpdate();

			if (result == 0) {
				throw new GameException("포션이 없거나 사용 실패");
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
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "update inventory set inventory_quantity = ? where fk_hero_id = ? and fk_item_id = ?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, quantity);
			ps.setInt(2, heroId);
			ps.setInt(3, itemId);

			int result = ps.executeUpdate();

			if (result == 0) {
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
		Connection con = null;
		PreparedStatement ps = null;

		String sql = "insert into inventory(fk_hero_id, fk_item_id) values (?, ?)";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, heroId);
			ps.setInt(2, itemId);

			int result = ps.executeUpdate();

			if (result == 0) {
				throw new GameException("인벤토리에 아이템 등록 실패");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("DB 오류");

		} finally {
			DBManager.close(con, ps);
		}
	}

	@Override
	public List<BattlePotionDTO> selectBattlePotionList(int heroId) throws GameException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<BattlePotionDTO> potionList = null;

		String sql = """
				select 
					i.inventory_id,
					t.item_id,
					t.item_name,
					t.item_effect_hp,
					t.item_effect_mp,
					t.item_atk_bonus,
					t.item_def_bonus,
					i.inventory_quantity
				from inventory i
				join item t on i.fk_item_id = t.item_id
				where i.fk_hero_id = ?
				and t.item_type = 'POTION'
				""";

		try {
			potionList = new ArrayList<>();
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, heroId);
			rs = ps.executeQuery();

			while (rs.next()) {
				BattlePotionDTO battlePotion = new BattlePotionDTO(
						rs.getInt("inventory_id"),
						rs.getInt("item_id"),
						rs.getString("item_name"),
						rs.getInt("item_effect_hp"),
						rs.getInt("item_effect_mp"),
						rs.getInt("item_atk_bonus"),
						rs.getInt("item_def_bonus"),
						rs.getInt("inventory_quantity"));

				potionList.add(battlePotion);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new GameException("포션 아이템 조회 실패");
		} finally {
			DBManager.close(con, ps, rs);
		}

		return potionList;
	}

}
