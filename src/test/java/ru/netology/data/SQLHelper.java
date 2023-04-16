package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static QueryRunner query = new QueryRunner();

    @SneakyThrows
    private static Connection connection() {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    public static DataHelper.VerificationCode getVerificationCode() {
        var requestSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1";
        try {
            var code = query.query(connection(), requestSQL, new ScalarHandler<String>());
            return new DataHelper.VerificationCode(code);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @SneakyThrows
    public static void cleanData() {
        query.execute(connection(), "DELETE FROM auth_codes");
        query.execute(connection(), "DELETE FROM card_transactions");
        query.execute(connection(), "DELETE FROM cards");
        query.execute(connection(), "DELETE FROM users");
    }

    @SneakyThrows
    public static String userStatus(String login) {
        connection();
        String data = "SELECT status FROM users WHERE login = ?;";
        return query.query(connection(), data, new ScalarHandler<>(), login);
    }
}