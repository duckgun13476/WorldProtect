package com.PinkCats.worldprotect.Database;

import com.PinkCats.worldprotect.Database.GUI.mes;

import java.io.IOException;
import java.sql.*;

import static com.PinkCats.worldprotect.Config.*;
import static com.PinkCats.worldprotect.Database.Operator.SqlMapQuery.*;

public class SqlInit {

    private static boolean IsDatabaseAvailable = false;

    private static int FailedCount = 0;
    private static boolean CanUseDatabase = true;

    public static void DataBaseInit() {
        mes.info("DataBaseInit...");
        SafeSql(SqlInit::EnsureTableExists);
        if (IsDatabaseAvailable) {
            SafeSql(SqlInit::UpdateMapSelf);
            mes.info("DataBaseInit Complete！");
        } else {
            mes.error("DataBaseInit Failed！(WorldProtect will disable) Please Check Message");
            CanUseDatabase = false;
        }

    }

    private static void UpdateMapSelf(Statement s) throws SQLException {
        UpdateMapItem(s);
        UpdateMapBlock(s);
        UpdateMapWorld(s);
        UpdateMapBehaviour(s);
        UpdateMapPlayer(s);
        UpdateMapShortNbt(s);
    }


    static void SafeSql(SqlInterface.SQLOperation operation) {
        if (!CanUseDatabase) {
            FailedCount++;
            mes.error("Database Not Available, Record Failed. Please Check Database Failed time ["+FailedCount+"]");
            return;
        }

        try (Connection connection = DriverManager.getConnection("jdbc:mysql://"+db_url+"/", user, password); Statement statement = connection.createStatement()) {
            try {
                EnsureDataBaseExists(statement);
                operation.execute(statement);
                IsDatabaseAvailable = true;
            } catch (SQLException e) {
                mes.error("发生 SQL 数据库初始化操作异常! "+e.getMessage());
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Communications link failure"))
                mes.info("[SQL Link Error] Please Check Port/IP: [" + db_url + "] Driver Message: " + e.getMessage());
            else if(e.getMessage().contains("Access denied for user"))
                mes.info("[SQL Varify Error] Please Check Username/Password: [" + user+"|"+password + "] Driver Message: " + e.getMessage());
            else
                mes.info("[SQL Other Error]: "+e.getMessage());
        } catch (IOException e) {
            mes.info("[IOE Error]: "+e.getMessage());
        }

    }


    // 检查数据库是否存在
    private static void EnsureDataBaseExists(Statement statement) throws SQLException {
        String checkDatabaseQuery = "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + DATABASE_NAME + "'";
        ResultSet resultSet = statement.executeQuery(checkDatabaseQuery);
        if (!resultSet.next()){
            String createDatabaseQuery = "CREATE DATABASE " + DATABASE_NAME;
            statement.executeUpdate(createDatabaseQuery);
        }
        statement.executeUpdate("USE " + DATABASE_NAME);
    }

    public static void EnsureTableExists(Statement statement) throws SQLException {

        String tableName ;
        String checkTableQuery;
        ResultSet tableResultSet;
        //Map_Behaviour
        tableName = "Map_Behaviour";
        checkTableQuery = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = '" + DATABASE_NAME + "' AND TABLE_NAME = '" + tableName + "'";
        tableResultSet = statement.executeQuery(checkTableQuery);
        if (!tableResultSet.next()) {
            String createTableQuery = "CREATE TABLE " + tableName + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "map_behaviour VARCHAR(100) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            statement.executeUpdate(createTableQuery);
        }
        tableResultSet.close();


        //Map_Operator
        tableName = "Map_Operator";
        checkTableQuery = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = '" + DATABASE_NAME + "' AND TABLE_NAME = '" + tableName + "'";
        tableResultSet = statement.executeQuery(checkTableQuery);
        if (!tableResultSet.next()) {
            String createTableQuery = "CREATE TABLE " + tableName + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "operator VARCHAR(100) NOT NULL," +
                    "uuid VARCHAR(100) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            statement.executeUpdate(createTableQuery);
        }
        tableResultSet.close();

        //Map_World
        tableName = "Map_World";
        checkTableQuery = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = '" + DATABASE_NAME + "' AND TABLE_NAME = '" + tableName + "'";
        tableResultSet = statement.executeQuery(checkTableQuery);
        if (!tableResultSet.next()) {
            String createTableQuery = "CREATE TABLE " + tableName + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "world VARCHAR(100) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            statement.executeUpdate(createTableQuery);
        }
        tableResultSet.close();

        //Map_Item
        tableName = "Map_Item";
        checkTableQuery = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = '" + DATABASE_NAME + "' AND TABLE_NAME = '" + tableName + "'";
        tableResultSet = statement.executeQuery(checkTableQuery);
        if (!tableResultSet.next()) {
            String createTableQuery = "CREATE TABLE " + tableName + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "item_id VARCHAR(100) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            statement.executeUpdate(createTableQuery);
        }
        tableResultSet.close();

        //Record_Item
        tableName = "Record_Item";
        checkTableQuery = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = '" + DATABASE_NAME + "' AND TABLE_NAME = '" + tableName + "'";
        tableResultSet = statement.executeQuery(checkTableQuery);
        if (!tableResultSet.next()) {
            String createTableQuery = "CREATE TABLE " + tableName + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "time INT NOT NULL," +
                    "operator SMALLINT NOT NULL," +
                    "world TINYINT NOT NULL," +
                    "x INT NOT NULL," +
                    "y INT NOT NULL," +
                    "z INT NOT NULL," +
                    "itemdata INT DEFAULT NULL," +
                    "count INT NOT NULL," +
                    "Behaviour SMALLINT NOT NULL," +
                    "rollback TINYINT NOT NULL" +

                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            statement.executeUpdate(createTableQuery);
        }
        tableResultSet.close();



        //Map_Block
        tableName = "Map_Block";
        checkTableQuery = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = '" + DATABASE_NAME + "' AND TABLE_NAME = '" + tableName + "'";
        tableResultSet = statement.executeQuery(checkTableQuery);
        if (!tableResultSet.next()) {
            String createTableQuery = "CREATE TABLE " + tableName + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "block_id VARCHAR(100) NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            statement.executeUpdate(createTableQuery);
        }
        tableResultSet.close();

        //Record_Block
        tableName = "Record_Block";
        checkTableQuery = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = '" + DATABASE_NAME + "' AND TABLE_NAME = '" + tableName + "'";
        tableResultSet = statement.executeQuery(checkTableQuery);
        if (!tableResultSet.next()) {
            String createTableQuery = "CREATE TABLE " + tableName + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "time INT NOT NULL," +
                    "operator SMALLINT NOT NULL," +
                    "world TINYINT NOT NULL," +
                    "x INT NOT NULL," +
                    "y INT NOT NULL," +
                    "z INT NOT NULL," +
                    "blockdata INT DEFAULT NULL," +
                    "Behaviour SMALLINT NOT NULL," +
                    "rollback TINYINT NOT NULL" +

                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            statement.executeUpdate(createTableQuery);
        }
        tableResultSet.close();


        //Map_Nbt
        tableName = "Map_Nbt";
        NbtList(statement, tableName);

        tableName = "Map_BigNbt";
        NbtList(statement, tableName);


    }

    private static void NbtList(Statement statement, String tableName) throws SQLException {
        String checkTableQuery;
        ResultSet tableResultSet;
        checkTableQuery = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " +
                "WHERE TABLE_SCHEMA = '" + DATABASE_NAME + "' AND TABLE_NAME = '" + tableName + "'";
        tableResultSet = statement.executeQuery(checkTableQuery);
        if (!tableResultSet.next()) {
            String createTableQuery = "CREATE TABLE " + tableName + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "nbt BLOB NOT NULL" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
            statement.executeUpdate(createTableQuery);
        }
        tableResultSet.close();
    }


}