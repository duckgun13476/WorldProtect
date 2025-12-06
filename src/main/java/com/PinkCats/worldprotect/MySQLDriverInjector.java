package com.PinkCats.worldprotect;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Vector;

// 核心工具类：仅负责注入驱动，无其他逻辑
public class MySQLDriverInjector {
    private static boolean injected = false;

    public static void inject() {
        if (injected) return;

        try {
            // 关键修改：类名改为重定位后的路径
            Class<?> driverClass = Class.forName(
                    "com.PinkCats.worldprotect.shaded.com.mysql.cj.jdbc.Driver", // 对应relocate的路径
                    true,
                    MySQLDriverInjector.class.getClassLoader()
            );

            Driver mysqlDriver = (Driver) driverClass.getDeclaredConstructor().newInstance();
            DriverManager.registerDriver(mysqlDriver);

            // 兜底修改loadedDrivers
            java.lang.reflect.Field loadedDriversField = DriverManager.class.getDeclaredField("loadedDrivers");
            loadedDriversField.setAccessible(true);
            Vector<Driver> loadedDrivers = (Vector<Driver>) loadedDriversField.get(null);
            loadedDrivers.add(mysqlDriver);

            injected = true;
            System.out.println("[驱动注入] 成功打破类加载器隔离！");
        } catch (Exception e) {
            System.err.println("[驱动注入] 失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}