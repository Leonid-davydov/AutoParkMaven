package by.incubator.application.infrastrucrure.orm;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}
