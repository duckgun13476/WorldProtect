package com.PinkCats.worldprotect.Database.Operator;
import java.nio.ByteBuffer;
import com.PinkCats.worldprotect.Database.Item.RecordPlayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class SqlMapQuery {

    //ShortNbt Map Handler
    public static Map<ByteBuffer, Integer> ShortNbtMapID = new HashMap<>();
    public static int FetchMapShortNbtId(Statement s, byte[] item_id) throws SQLException {

        ByteBuffer key = ByteBuffer.wrap(item_id);
        Integer result = ShortNbtMapID.get(key);

        if (result == null) {
            InsertMapShortNbt(s, item_id);
            UpdateMapShortNbt(s);
            return FetchMapShortNbtId(s,item_id);
        } else
            return result;
    }

    public static void UpdateMapShortNbt(Statement statement) throws  SQLException {
        ShortNbtMapID.clear();
        String querySql = "SELECT id, nbt FROM Map_Nbt"; // 替换为你的表名和字段名
        ResultSet rs = statement.executeQuery(querySql);
        while (rs.next()) {
            int id = rs.getInt("id");
            byte[] nbtBlob = rs.getBytes("nbt");
            ByteBuffer nbtBuffer = ByteBuffer.wrap(nbtBlob);
            ShortNbtMapID.put(nbtBuffer, id);
        }
        rs.close();
    }

    public static void InsertMapShortNbt(Statement statement, byte[] nbtBlob) throws SQLException {
        if (nbtBlob == null || nbtBlob.length == 0) {
            System.err.println("插入失败：nbt 不能为空");
            return;
        }
        PreparedStatement stmt;
        String insertSql = "INSERT INTO Map_Nbt (nbt) VALUES (?)";
        stmt = statement.getConnection().prepareStatement(insertSql);
        stmt.setBytes(1, nbtBlob);
        stmt.executeUpdate();
    }













    //Item Map Handler
    public static Map<String, Integer> ItemMapID = new HashMap<>();
    public static int FetchMapItemId(Statement s, String item_id) throws SQLException {
        Object result = ItemMapID.get(item_id);
        if (result == null) {
            InsertMapItem(s, item_id);
            UpdateMapItem(s);
            return FetchMapItemId(s,item_id);
        } else
            return (int) result;
    }

    public static void UpdateMapItem(Statement statement) throws  SQLException {
        ItemMapID.clear();
        String querySql = "SELECT id, item_id FROM Map_Item"; // 替换为你的表名和字段名
        ResultSet rs = statement.executeQuery(querySql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String item = rs.getString("item_id");
            ItemMapID.put(item, id);
        }
        rs.close();
    }

    public static void InsertMapItem(Statement statement, String itemId) throws SQLException {
        if (itemId == null || itemId.trim().isEmpty()) {
            System.err.println("插入失败：item_id 不能为空");
            return;
        }
        PreparedStatement stmt;
        String insertSql = "INSERT INTO Map_Item (item_id) VALUES (?)";
        stmt = statement.getConnection().prepareStatement(insertSql);
        stmt.setString(1, itemId.trim());
        stmt.executeUpdate();
    }


    //Block Map Handler
    public static Map<String, Integer> BlockMapID = new HashMap<>();
    public static int FetchMapBlockId(Statement s, String blockId) throws SQLException {
        Object result = BlockMapID.get(blockId);
        if (result == null) {
            InsertMapBlock(s, blockId);
            UpdateMapBlock(s);
            return FetchMapBlockId(s,blockId);
        } else
            return (int) result;
    }

    public static void UpdateMapBlock(Statement statement) throws  SQLException {
        BlockMapID.clear();
        String querySql = "SELECT id, block_id FROM Map_Block"; // 替换为你的表名和字段名
        ResultSet rs = statement.executeQuery(querySql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String item = rs.getString("block_id");
            BlockMapID.put(item, id);
        }
        rs.close();
    }

    public static void InsertMapBlock(Statement statement, String blockId) throws SQLException {
        if (blockId == null || blockId.trim().isEmpty()) {
            System.err.println("插入失败：item_id 不能为空");
            return;
        }
        PreparedStatement stmt;
        String insertSql = "INSERT INTO Map_Block (block_id) VALUES (?)";
        stmt = statement.getConnection().prepareStatement(insertSql);
        stmt.setString(1, blockId.trim());
        stmt.executeUpdate();
    }


    //World Map Handler
    public static Map<String, Integer> WorldMapID = new HashMap<>();
    public static int FetchMapWorldId(Statement s, String WorldId) throws SQLException {
        Object result = WorldMapID.get(WorldId);
        if (result == null) {
            InsertMapWorld(s, WorldId);
            UpdateMapWorld(s);
            return FetchMapWorldId(s,WorldId);
        } else
            return (int) result;
    }

    public static void UpdateMapWorld(Statement statement) throws  SQLException {
        WorldMapID.clear();
        String querySql = "SELECT id, world FROM Map_World"; // 替换为你的表名和字段名
        ResultSet rs = statement.executeQuery(querySql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String item = rs.getString("world");
            WorldMapID.put(item, id);
        }
        rs.close();
    }

    public static void InsertMapWorld(Statement statement, String WorldId) throws SQLException {
        if (WorldId == null || WorldId.trim().isEmpty()) {
            System.err.println("插入失败：world 不能为空");
            return;
        }
        PreparedStatement stmt;
        String insertSql = "INSERT INTO Map_World (world) VALUES (?)";
        stmt = statement.getConnection().prepareStatement(insertSql);
        stmt.setString(1, WorldId.trim());
        stmt.executeUpdate();
    }



    //Behaviour Map Handler
    public static Map<String, Integer> BehaviourMapID = new HashMap<>();
    public static int FetchMapBehaviourId(Statement s, String BehaviourId) throws SQLException {
        Object result = BehaviourMapID.get(BehaviourId);
        if (result == null) {
            InsertMapBehaviour(s, BehaviourId);
            UpdateMapBehaviour(s);
            return FetchMapBehaviourId(s,BehaviourId);
        } else
            return (int) result;
    }

    public static void UpdateMapBehaviour(Statement statement) throws  SQLException {
        BehaviourMapID.clear();
        String querySql = "SELECT id, map_behaviour FROM Map_Behaviour"; // 替换为你的表名和字段名
        ResultSet rs = statement.executeQuery(querySql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String item = rs.getString("map_behaviour");
            BehaviourMapID.put(item, id);
        }
        rs.close();
    }

    public static void InsertMapBehaviour(Statement statement, String BehaviourId) throws SQLException {
        if (BehaviourId == null || BehaviourId.trim().isEmpty()) {
            System.err.println("插入失败：map_behaviour 不能为空");
            return;
        }
        PreparedStatement stmt;
        String insertSql = "INSERT INTO Map_Behaviour (map_behaviour) VALUES (?)";
        stmt = statement.getConnection().prepareStatement(insertSql);
        stmt.setString(1, BehaviourId.trim());
        stmt.executeUpdate();
    }


    //complex

    //Player Map Handler
    public static Map<String, Integer> PlayerMapID = new HashMap<>();
    public static int FetchMapOperatorId(Statement s, String PlayerId,String UUID) throws SQLException {
        RecordPlayer Player = new RecordPlayer(PlayerId,UUID);
        Object result = PlayerMapID.get(UUID);
        if (result == null) {
            InsertMapPlayer(s, Player);
            UpdateMapPlayer(s);
            return FetchMapOperatorId(s,  PlayerId, UUID);
        } else
            return (int) result;
    }

    public static void UpdateMapPlayer(Statement statement) throws  SQLException {
        PlayerMapID.clear();
        String querySql = "SELECT id, uuid,operator FROM Map_Operator"; // 替换为你的表名和字段名
        ResultSet rs = statement.executeQuery(querySql);
        while (rs.next()) {
            int id = rs.getInt("id");
            String uuid = rs.getString("uuid");
            PlayerMapID.put(uuid, id);
        }
        rs.close();
    }

    public static void InsertMapPlayer(Statement statement, RecordPlayer Player) throws SQLException {
        String PlayerId = Player.getUUID();
        String PlayerName = Player.getOperator();

        if (PlayerId == null || PlayerName == null || PlayerId.trim().isEmpty()) {
            System.err.println("插入失败：map_player 不能为空");
            return;
        }
        PreparedStatement stmt;
        String insertSql = "INSERT INTO Map_Operator (uuid, operator) VALUES (?, ?)";
        stmt = statement.getConnection().prepareStatement(insertSql);

        // 2. 绑定参数：第一个?对应uuid，第二个?对应玩家名称
        stmt.setString(1, PlayerId.trim());   // 绑定UUID参数
        stmt.setString(2, PlayerName.trim()); // 绑定玩家名称参数

        stmt.executeUpdate();
    }



}
