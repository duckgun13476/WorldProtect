package com.PinkCats.worldprotect.Database.GUI;

import com.PinkCats.worldprotect.Worldprotect;

import static com.PinkCats.worldprotect.Database.GUI.SingleLog.WP_MES;

public class mes {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m"; // 黄色
    public static final String CYAN = "\u001B[36m";   // 青色
    public static final String MAGENTA = "\u001B[35m"; // 品红
    public static final String WHITE = "\u001B[37m";   // 白色
    public static final String BLACK = "\u001B[30m";   // 黑色

    public static final String LOGO = "[WP]";

    public static void blue(Object message) {
        String messageString = String.valueOf(message);
        Worldprotect.LOGGER.info(CYAN+LOGO+BLUE + "{}" + RESET, messageString);
        WP_MES.log("[INFO] "+messageString);
    }
    public static void warn(Object message) {
        String messageString = String.valueOf(message);
        Worldprotect.LOGGER.warn(CYAN+LOGO+YELLOW + "{}" + RESET, messageString);
        WP_MES.log("[WARN] "+messageString);
        WP_MES.log("[WARN] "+messageString);
    }
    public static void error(Object message) {
        String messageString = String.valueOf(message);
        Worldprotect.LOGGER.error(CYAN+LOGO+RED + "{}" + RESET, messageString);
        WP_MES.log("[ERROR] "+messageString);
        WP_MES.log("[ERROR] "+messageString);
    }
    public static void info(Object message) {
        String messageString = String.valueOf(message);
        Worldprotect.LOGGER.info(CYAN+LOGO+GREEN + "{}" + RESET, messageString);
        WP_MES.log("[INFO] "+messageString);
    }
    public static void purple(Object message) {
        String messageString = String.valueOf(message);
        Worldprotect.LOGGER.info(CYAN+LOGO+MAGENTA + "{}" + RESET, messageString);
        WP_MES.log("[INFO] "+messageString);
    }

    private static final StackWalker WALKER =
            StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static void debug(Object message) {
        String messageString = String.valueOf(message);

        String caller = WALKER.walk(frames -> {
            boolean seenThisDebug = false;

            for (StackWalker.StackFrame f : (Iterable<StackWalker.StackFrame>) frames::iterator) {
                // 先找到“本类的 debug()”
                if (f.getClassName().equals(mes.class.getName())
                        && f.getMethodName().equals("debug")) {
                    seenThisDebug = true;
                    continue;
                }

                // debug() 之后的第一个 frame 才是真正的调用者
                if (seenThisDebug) {
                    return shortenClassPath(f.getClassName());
                }
            }
            return "unknown";
        });

        Worldprotect.LOGGER.info(CYAN + "[{}][Debug]" + MAGENTA + "{}" + RESET, caller, messageString);
        WP_MES.log("[" + caller + "][DevelopDebug] " + messageString);
    }

    private static String shortenClassPath(String fullClassName) {
        // 例：
        // fullClassName = co.Pink_Cats.worldprotect.DebugGroup.Main
        // 期望输出 = D.G.Main

        String[] parts = fullClassName.split("\\.");
        if (parts.length <= 3) {
            return fullClassName; // 极端情况兜底
        }

        StringBuilder sb = new StringBuilder();

        // 从第 4 段开始（跳过 co.Pi.wo）
        for (int i = 3; i < parts.length; i++) {
            String part = parts[i];
            if (part.isEmpty()) continue;

            if (i == parts.length - 1) {
                // 最后一段：类名，完整保留
                sb.append(part);
            } else {
                // 中间包名：只取首字母
                sb.append(part.charAt(0)).append('.');
            }
        }

        return sb.toString();
    }


}
