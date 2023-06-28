package itss.group14.timekeeper.dbservices.dbconection;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractSQLConnection {
    protected Connection connection;

    public abstract void connect() throws SQLException;

    public abstract void disconnect() throws SQLException;

    public abstract Connection getConnection();
}
