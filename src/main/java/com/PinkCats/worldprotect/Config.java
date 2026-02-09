package com.PinkCats.worldprotect;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Worldprotect.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.ConfigValue<String> DB_URL =
            BUILDER.comment("MySql Address : IP:port")
                    .define("DataBase_Url", "2.2.2.2:2");
    public static final ForgeConfigSpec.ConfigValue<String> USER =
            BUILDER.comment("MySql UserName")
                    .define("DataBase_User", "root");

    public static final ForgeConfigSpec.ConfigValue<String> PASSWORD =
            BUILDER.comment("MySql PassWord")
                    .define("DataBase_PassWord", "");

    public static final ForgeConfigSpec.ConfigValue<String> DATABASE_NAME =
            BUILDER.comment("DataBase Name. You can change it if you wanna")
                    .define("DataBase_Name", "WorldProtect");


    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static String db_url;
    public static String user;
    public static String password;
    public static String database_name;


    @SubscribeEvent
    static void onLoad(final ModConfigEvent.Loading event) {
        Init();
    }
    @SubscribeEvent
    static void onReload(final ModConfigEvent.Reloading event) {
        Init();
    }

    private static void Init() {
        db_url = DB_URL.get();
        user = USER.get();
        password = PASSWORD.get();
        database_name = DATABASE_NAME.get();
    }
}
