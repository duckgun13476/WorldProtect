package com.PinkCats.worldprotect.Database;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

class SqlInterface {
    @FunctionalInterface
    interface SQLOperation {
        void execute(Statement statement) throws SQLException, IOException;
    }
}
