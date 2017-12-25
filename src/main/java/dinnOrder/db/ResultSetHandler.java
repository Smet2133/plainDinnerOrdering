package dinnOrder.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetHandler<T> {
    T func(ResultSet resultSet) throws SQLException;
}
