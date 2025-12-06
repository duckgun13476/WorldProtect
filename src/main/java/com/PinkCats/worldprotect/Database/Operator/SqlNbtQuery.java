package com.PinkCats.worldprotect.Database.Operator;

import net.minecraft.world.item.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlNbtQuery {





    public static int InsertMapNbtItem(Statement statement, byte[] nbtBlob) throws SQLException {
        if (nbtBlob == null ) {
            System.err.println("插入失败：data 不能为空");
            return 0;
        }
        PreparedStatement stmt;
        String insertSql = "INSERT INTO Map_Nbt (nbt) VALUES (?)";
        stmt = statement.getConnection().prepareStatement(insertSql);
        stmt.setBytes(1, nbtBlob);
        stmt.executeUpdate();
        try (ResultSet rs = stmt.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1); // 返回新增记录的自增ID
            } else {
                throw new SQLException("插入BLOB成功，但未获取到自增ID");
            }
        }
    }



    public static byte[] itemStackToBlob(ItemStack itemStack) {
        // 此处替换为你实际的ItemStack序列化逻辑（比如NBT序列化）
        // 示例：用ByteArrayOutputStream序列化NBT
        try (java.io.ByteArrayOutputStream bos = new java.io.ByteArrayOutputStream();
             java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(bos)) {
            // 假设ItemStack可序列化（实际MC需用NBTTagCompound序列化）
            oos.writeObject(itemStack.save(new net.minecraft.nbt.CompoundTag()));
            return bos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("ItemStack序列化为BLOB失败", e);
        }
    }
}
