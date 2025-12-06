package com.PinkCats.worldprotect.Database;

import java.io.IOException;
import java.sql.*;

import static com.PinkCats.worldprotect.Database.Operator.SqlMapQuery.*;

public class SqlInit {

    // 数据库 URL
    private static final String DB_URL = "192.168.147.111:3309"; // 替换为你的数据库名称
    // 数据库凭据
    private static final String USER = "root"; // 替换为你的用户名
    private static final String PASSWORD = "36900367459b"; // 替换为你的密码
    private static final String DATABASE_NAME = "WorldProtect";

    public static void DataBaseInit() {
        System.out.println("DataBaseInit");
        SafeSql(SqlInit::EnsureTableExists);
        SafeSql(SqlInit::UpdateMapSelf);
    }

    private static void UpdateMapSelf(Statement statement) throws SQLException {
        UpdateMapItem(statement);
        UpdateMapBlock(statement);
        UpdateMapWorld(statement);
        UpdateMapBehaviour(statement);
        UpdateMapPlayer(statement);
    }


    static void SafeSql(SqlInterface.SQLOperation operation) {


        try (Connection connection = DriverManager.getConnection("jdbc:mysql://"+DB_URL+"/", USER, PASSWORD); Statement statement = connection.createStatement()) {
            try {
                EnsureDataBaseExists(statement);
                operation.execute(statement);
            } catch (SQLException e) {
                System.err.println("发生 SQL 数据库初始化操作异常! "+e.getMessage());
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Communications link failure"))
                System.out.println("[SQL Link Error] Please Check Port/IP.  Core Message: "+e.getMessage());
            else if(e.getMessage().contains("Access denied for user"))
                System.out.println("[SQL Varify Error] Please Check Username/Password: "+e.getMessage());
            else
                System.out.println("[SQL Other Error]: "+e.getMessage());
        } catch (IOException e) {
            System.out.println("[IOE Error]: "+e.getMessage());
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
                    "count SMALLINT NOT NULL," +
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