package jdbc;

import object.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Class UserJDBC
 */
public class UserJDBC {
    private Connection connectDataBase;

    /**
     * Constructor UserJDBC
     *
     * @param connectDataBase Creates a Statement object for sending SQL statements to the database.
     */
    public UserJDBC(Connection connectDataBase) {
        this.connectDataBase = connectDataBase;
    }


    /**
     * Insert object User to database USER with parametric query
     *
     * @param user Object of the User
     */
    public void addUserParametric(User user) {
        PreparedStatement prepStatement;

        try {
            prepStatement = connectDataBase.prepareStatement("INSERT INTO \"public\".\"USER\" VALUES (?, ?, ?, ?, ?, ?, ?)");
            prepStatement = HandlerUser(user, prepStatement);
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handler SQL from user object
     *
     * @param userHandler Object of the User
     * @return Modified Prepared Statement
     */
    private PreparedStatement HandlerUser(User userHandler, PreparedStatement preparedStatementHandler) {
        try {
            preparedStatementHandler.setInt(1, userHandler.id);
            preparedStatementHandler.setString(2, userHandler.name);
            preparedStatementHandler.setObject(3, userHandler.birthday);
            preparedStatementHandler.setInt(4, userHandler.login_id);
            preparedStatementHandler.setString(5, userHandler.city);
            preparedStatementHandler.setString(6, userHandler.email);
            preparedStatementHandler.setString(7, userHandler.description);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatementHandler;
    }

    /**
     * Get object User from database USER
     *
     * @return Query from user
     */
    public User readUserParametric(Integer idUser) {
        PreparedStatement prepStatement = null;
        User queryUser = null;
        try {
            prepStatement = connectDataBase.prepareStatement("SELECT * FROM \"public\".\"USER\" WHERE (id = ?)");

            prepStatement.setInt(1, idUser);

            ResultSet resultSet = prepStatement.executeQuery();
            if (resultSet.next()) {
                queryUser = new User(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getObject("birthday", LocalDate.class), resultSet.getInt("login_id"),
                        resultSet.getString("city"), resultSet.getString("email"),
                        resultSet.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryUser;
    }

    /**
     * Update object User from database USER with parametric query
     *
     * @param user Object of the User
     */
    public void updateUserParametric(User user) {
        PreparedStatement prepStatement;
        try {
            prepStatement = connectDataBase.prepareStatement("UPDATE \"public\".\"USER\" SET id = ?, name = ?, birthday = ?, login_id = ?,city = ?, email = ?, description = ? WHERE id = ?");
            prepStatement.setInt(1, user.id);
            prepStatement.setString(2, user.name);
            prepStatement.setObject(3, user.birthday);
            prepStatement.setInt(4, user.login_id);
            prepStatement.setString(5, user.city);
            prepStatement.setString(6, user.email);
            prepStatement.setString(7, user.description);
            prepStatement.setInt(8, user.id);
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete object User from database USER
     *
     * @param user Object User
     */
    public void deleteUserParametric(User user) {

        User userRead = null;
        User queryUser = null;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connectDataBase.prepareStatement("DELETE FROM \"public\".\"USER\" WHERE id = ?");
            prepStatement.setInt(1, user.id);
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
