package com.PinkCats.worldprotect.Database;

import com.PinkCats.worldprotect.Database.Item.RecordItem;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static com.PinkCats.worldprotect.Database.Item.RecordItem.validateRecordItem;

public class SqlEntry {



    // 先定义一个实体类来封装单条Record_Item数据（方便批量传递）
    

    /**
     * Record_Item 表批量插入方法（优化性能，减少数据库交互）
     * @param statement 数据库Statement对象
     * @param recordItemList 要插入的批量数据列表
     * @param batchSize 每次提交的批次大小（建议500-1000，平衡性能和内存）
     * @throws SQLException 数据库异常（交由调用方捕获）
     */
    public static void batchInsertRecordItem(Statement statement, List<RecordItem> recordItemList, int batchSize) throws SQLException {
        // 1. 基础校验
        if (recordItemList == null || recordItemList.isEmpty()) {
            System.err.println("批量插入失败：待插入数据列表为空");
            return;
        }
        if (batchSize <= 0) {
            batchSize = 500; // 默认批次大小
            System.out.println("批次大小非法，使用默认值：" + batchSize);
        }

        // 2. 构建批量插入SQL（与单条插入一致）
        String insertSql = "INSERT INTO Record_Item (" +
                "time, operator, world, x, y, z, itemdata,count,Behaviour, rollback" +
                ") VALUES (?, ?, ?, ?, ?, ?, ?,?, ?, ?)";

        PreparedStatement pstmt = null;
        try {
            // 关闭自动提交，手动控制事务（提升批量插入性能）
            statement.getConnection().setAutoCommit(false);
            pstmt = statement.getConnection().prepareStatement(insertSql);

            // 3. 遍历数据，添加到批次
            int count = 0; // 计数，达到批次大小则提交
            for (RecordItem item : recordItemList) {
                // 单条数据合法性校验（过滤无效数据，不影响整批插入）
                if (!validateRecordItem(item)) {
                    System.err.println("跳过无效数据：time=" + item.getTime() + ", x=" + item.getX() + ", y=" + item.getY() + ", z=" + item.getZ());
                    continue;
                }

                // 设置参数（与单条插入逻辑一致）
                pstmt.setInt(1, item.getTime());
                pstmt.setShort(2, item.getOperator());
                pstmt.setShort(3, item.getWorld());
                pstmt.setInt(4, item.getX());
                pstmt.setInt(5, item.getY());
                pstmt.setInt(6, item.getZ());
                pstmt.setInt(7, item.getItemdata());
                pstmt.setShort(8, item.getBehaviour());
                pstmt.setShort(9, item.getBehaviour());
                pstmt.setShort(10, item.getRollback());

                // 添加到批次
                pstmt.addBatch();
                count++;

                // 达到批次大小，执行批次插入
                if (count % batchSize == 0) {
                    pstmt.executeBatch(); // 执行当前批次
                    statement.getConnection().commit(); // 提交事务
                    pstmt.clearBatch(); // 清空批次
                    System.out.println("已提交批次：" + count + " 条数据");
                }
            }

            // 4. 处理剩余数据（不足一个批次的部分）
            if (count % batchSize != 0) {
                pstmt.executeBatch();
                statement.getConnection().commit();
                System.out.println("提交剩余数据，总计插入：" + count + " 条数据");
            }

            // 恢复自动提交
            statement.getConnection().setAutoCommit(true);
            System.out.println("批量插入完成，共处理有效数据：" + count + " 条");

        } catch (SQLException e) {
            // 批量插入失败，回滚事务
            statement.getConnection().rollback();
            System.err.println("批量插入失败，已回滚事务：" + e.getMessage());
            throw e; // 抛出异常，让调用方感知
        } finally {
            // 5. 资源清理
            if (pstmt != null) {
                pstmt.close();
            }
            // 确保恢复自动提交（避免影响后续操作）
            if (statement.getConnection() != null && !statement.getConnection().getAutoCommit()) {
                statement.getConnection().setAutoCommit(true);
            }
        }
    }




}
