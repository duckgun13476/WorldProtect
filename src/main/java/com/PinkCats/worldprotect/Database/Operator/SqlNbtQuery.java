package com.PinkCats.worldprotect.Database.Operator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.PinkCats.worldprotect.Database.Operator.SqlMapQuery.FetchMapShortNbtId;

public class SqlNbtQuery {





    public static int InsertMapNbtItem(Statement statement, byte[] nbtBlob) throws SQLException {
        if (nbtBlob == null ) {
            System.err.println("插入失败：data 不能为空");
            return 0;
        }

        if(nbtBlob.length < 1024)
            return FetchMapShortNbtId(statement,nbtBlob);

        PreparedStatement stmt;

        String insertSql = "INSERT INTO Map_BigNbt (nbt) VALUES (?)";
        stmt = statement.getConnection().prepareStatement(insertSql,Statement.RETURN_GENERATED_KEYS);
        stmt.setBytes(1, nbtBlob);
        stmt.executeUpdate();
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1)+1000000000; // 返回新增记录的自增ID
            } else {
                throw new SQLException("插入BLOB成功，但未获取到自增ID");
            }
        }
    }



}
