package jdbc;

import object.UserRole;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * Class UserRoleJDBC
 */
public class UserRoleJDBC {
    private Connection connectDataBase;

    /**
     * Constructor RoleDJDBC
     *
     * @param connectDataBase Creates a Statement object for sending SQL statements to the database.
     */
    public UserRoleJDBC(Connection connectDataBase) {
        this.connectDataBase = connectDataBase;
    }

    /**
     * Insert object UserRole to database USER_ROLE with parametric query
     *
     * @param userRole Object of the UserRole
     */
    public void addUserRoleParametric(UserRole userRole) {
        try (PreparedStatement prepStatement = connectDataBase.prepareStatement("INSERT INTO \"public\".\"USER_ROLE\" VALUES (?, ?, ?)")) {
            prepStatement.setInt(1, userRole.id);
            prepStatement.setInt(2, userRole.user_id);
            prepStatement.setInt(3, userRole.role_id);
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handler SQL from userRole object UserRole
     *
     * @param userRoleHandler Object of the UserRole
     * @return Modifed Prepared Statement
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    private PreparedStatement HandlerUserRole(UserRole userRoleHandler) throws SQLException {
        PreparedStatement preparedStatementHandler = connectDataBase.prepareStatement("INSERT INTO \"public\".\"USER_ROLE\" VALUES (?, ?, ?)");
        preparedStatementHandler.setInt(1, userRoleHandler.id);
        preparedStatementHandler.setInt(2, userRoleHandler.user_id);
        preparedStatementHandler.setInt(3, userRoleHandler.role_id);
        return preparedStatementHandler;
    }

    /**
     * Read object UserRole from database USER_ROLE
     *
     * @return Query from userRole
     */
    public UserRole readUserRoleParametric(Integer queryToRead) {
        PreparedStatement prepStatement = null;
        UserRole queryUserRole = null;
        try {
            prepStatement = connectDataBase.prepareStatement("SELECT * FROM \"public\".\"USER_ROLE\" WHERE (id = ?)");
            prepStatement.setInt(1, queryToRead);
            try (ResultSet resultSet = prepStatement.executeQuery()) {
                if (resultSet.next()) {
                    queryUserRole = new UserRole(resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getInt("role_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queryUserRole;
    }

    /**
     * Update object UserRole from database USER with parametric query
     *
     * @param userRole Object of the UserRole
     */
    public void updateUserParametric(UserRole userRole) {
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connectDataBase.prepareStatement("UPDATE \"public\".\"USER_ROLE\" SET id = ?, user_id = ?, role_id = ? WHERE id = ?");
            prepStatement.setInt(1, userRole.id);
            prepStatement.setInt(2, userRole.user_id);
            prepStatement.setInt(3, userRole.role_id);
            prepStatement.setInt(4, userRole.id);
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete object UserRole from database USER_ROLE
     *
     * @param userRole Object User_Role
     */
    public void deleteUserRoleParametric(UserRole userRole) {
        PreparedStatement prepStatement;
        
        try {
            prepStatement = connectDataBase.prepareStatement("DELETE FROM \"public\".\"USER_ROLE\" WHERE id = ?");
            prepStatement.setInt(1, userRole.id);
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}