package jdbc;

import object.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class RoleJDBC
 */
public class RoleJDBC {
    private Connection connectDataBase;

    /**
     * Constructor RoleJDBC
     *
     * @param connectDataBase Creates a Statement object for sending SQL statements to the database.
     */
    public RoleJDBC(Connection connectDataBase) {
        this.connectDataBase = connectDataBase;
    }

    /**
     * Insert object Role to database ROLE with parametric query
     *
     * @param role Object role
     */
    public void addRoleParametric(Role role) {
        if (role.name.equals("Administration")) {
            try {
                PreparedStatement prepStatement = connectDataBase.prepareStatement("INSERT INTO \"public\".\"ROLE\" VALUES (?, 'Administration', ?)");
                handlerPrepareStatement(prepStatement, role);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (role.name.equals("Clients")) {
                PreparedStatement prepStatement = null;
                try {
                    prepStatement = connectDataBase.prepareStatement("INSERT INTO \"public\".\"ROLE\" VALUES (?, 'Clients', ?)");
                    handlerPrepareStatement(prepStatement, role);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (role.name.equals("Billing")) {
                PreparedStatement prepStatement = null;
                try {
                    prepStatement = connectDataBase.prepareStatement("INSERT INTO \"public\".\"ROLE\" VALUES (?, 'Billing', ?)");
                    handlerPrepareStatement(prepStatement, role);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Handler for parametric id and description
     *
     * @param prepHandlerStatement Executes the SQL query in this PreparedStatement. object and returns the ResultSet object generated by the query.
     * @param roleHandler          Object role
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    private void handlerPrepareStatement(PreparedStatement prepHandlerStatement, Role roleHandler) {
        try {
            prepHandlerStatement.setInt(1, roleHandler.id);
            prepHandlerStatement.setString(2, roleHandler.description);
            prepHandlerStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read object Role from database ROLE
     *
     * @return
     */
    public Role readRoleParametric(Integer queryToRead) {
        Role roleRead = null;
        Role queryRole = null;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connectDataBase.prepareStatement("SELECT * FROM \"public\".\"ROLE\" WHERE (id = ?)");
            prepStatement.setInt(1, queryToRead);
            try (ResultSet resultSet = prepStatement.executeQuery();) {
                if (resultSet.next()) {
                    queryRole = new Role(resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("description"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return queryRole;
    }


    /**
     * Update object Role from database ROLE
     *
     * @param role Object role
     */
    public void updateRoleParametric(Role role) {
        PreparedStatement prepStatement;
        if (role.name.equals("Administration")) {
            try {
                prepStatement = connectDataBase.prepareStatement("UPDATE \"public\".\"ROLE\" SET id = ?,name = 'Administration', description = ? WHERE id = ?");
                prepStatement.setInt(1, role.id);
                prepStatement.setString(2, role.description);
                prepStatement.setInt(3, role.id);
                prepStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            if (role.name.equals("Clients")) {
                try {
                    prepStatement = connectDataBase.prepareStatement("UPDATE \"public\".\"ROLE\" SET id = ?,name = 'Clients', description = ? WHERE id = ?");
                    prepStatement.setInt(1, role.id);
                    prepStatement.setString(2, role.description);
                    prepStatement.setInt(3, role.id);
                    prepStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (role.name.equals("Billing")) {
                try {
                    prepStatement = connectDataBase.prepareStatement("UPDATE \"public\".\"ROLE\" SET id = ?, name = 'Billing', description = ? WHERE id = ?");
                    prepStatement.setInt(1, role.id);
                    prepStatement.setString(2, role.description);
                    prepStatement.setInt(3, role.id);
                    prepStatement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Delete object Role from database ROLE
     *
     * @param role Object role
     */
    public void deleteRoleParametric(Role role) {

        Role roleRead = null;
        Role queryRole = null;
        PreparedStatement prepStatement = null;
        try {
            prepStatement = connectDataBase.prepareStatement("DELETE FROM \"public\".\"ROLE\" WHERE id = ?");
            prepStatement.setInt(1, role.id);
            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}