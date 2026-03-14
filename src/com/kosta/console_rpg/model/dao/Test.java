package com.kosta.console_rpg.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kosta.console_rpg.util.DBManager;



public class Test {

    public static void SelectTest(String param) {

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "select * from item where item_name = ?";

        try {

            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);

            ps.setString(1, param);

            rs = ps.executeQuery();

            if (rs.next()) {

                int id = rs.getInt("item_id");
                String name = rs.getString("item_name");
                String type = rs.getString("item_type");
                String grade = rs.getString("item_grade");
                int atk = rs.getInt("item_atk_bonus");
                int buyPrice = rs.getInt("item_price_buy");

                System.out.println("아이템ID : " + id + " | " + "아이템명 : " + name + " | " + "아이템 타입 : " + type + 
                		" | " + "아이템 등급 : " + grade + " | " + "공격력 : " + atk + " | " + "구매가격 : " + buyPrice);

            } else {
                System.out.println("아이템 없음");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(con, ps, rs);
        }
    }
    
    public static void InsertTest(int in_id, int h_id, int it_id, int i_q, boolean eq) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql = "insert into inventory(inventory_id, fk_hero_id, fk_item_id, inventory_quantity, inventory_is_equipped) values(?,?,?,?,?)";

        try {

            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);

            ps.setInt(1, in_id);
            ps.setInt(2, h_id);
            ps.setInt(3, it_id);
            ps.setInt(4, i_q);
            ps.setBoolean(5, eq);
            
            int re = ps.executeUpdate();
            
            System.out.println("인벤토리에 item_id = " + re + "을 담았습니다.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(con, ps);
        }
    }

    public static void main(String[] args) {
    	SelectTest("나무 검");
    	InsertTest(1,1,1,1,true);

    }
}
