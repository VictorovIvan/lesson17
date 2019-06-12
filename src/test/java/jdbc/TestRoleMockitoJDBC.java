import jdbc.DBConnector;
import jdbc.RoleDAO;
import object.role.Name;
import object.role.Role;
import org.junit.jupiter.api.*;
import org.mockito.Spy;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * Задание1
 * Взять за основу ДЗ_13. Написать тест на CRUD операции
 * Инициализацию соединение с БД произвести один раз перед началом тестов, после завершения всех тестов, закрыть соединие с БД
 * Подготовку данных для CRUD операций производить в методе Init использую @Before
 *
 * Задание 2
 * Использую Spy и Mockito создать заглушки для интерфейса JDBC
 */

class TestRoleMockitoJDBC {
    private Role someInsertRole = new Role(1, "Administration", "Some description about Administration");
    private RoleDAO someRoleDAO = mock(RoleDAO.class);
    private Role someUpdateRole = new Role(1, "Billing", "Some update description about Administration");
    private Role someReadRole = new Role(0, "", "");
    @Spy
    RoleDAO RoleDAO;
    @Spy
    static Connection connectDataBase;

    @BeforeAll
    static void init() {
        connectDataBase = DBConnector.getConnection();
    }

    @BeforeEach
    void beforeEach() {
        this.RoleDAO = new RoleDAO(connectDataBase);
        this.someInsertRole.id = 1;
    }

    @Test
    @DisplayName("Connect to database")
    void connectToDataBase() {
        assertNotEquals(null, connectDataBase, "Connecting is did't. Please check user/password.");
        System.out.println("Connecting is succeeded");
    }

    @Test
    @DisplayName("Create in the database")
    void createRoleTest() {
        when(someRoleDAO.readRole(someInsertRole.id)).thenReturn(someInsertRole);
        someRoleDAO.addRoleParametric(someInsertRole, Name.Administration);
        someReadRole = someRoleDAO.readRole(someInsertRole.id);
        assertEquals(someInsertRole, someReadRole, "Object was not created");
        someRoleDAO.deleteRole(someInsertRole);
    }

    @Test
    @DisplayName("Read from the database")
    void readRoleTest() {
        when(someRoleDAO.readRole(someInsertRole.id)).thenReturn(someInsertRole);
        someRoleDAO.addRoleParametric(someInsertRole, Name.Administration);
        someReadRole = someRoleDAO.readRole(someInsertRole.id);
        assertEquals(someInsertRole, someReadRole, "Object was not read");
        System.out.println("Reading database is succeeded");
        someRoleDAO.deleteRole(someInsertRole);
    }

    @Test
    @DisplayName("Update to the database")
    void updateRoleTest() {
        when(someRoleDAO.readRole(someUpdateRole.id)).thenReturn(someUpdateRole);
        someRoleDAO.addRoleParametric(someInsertRole, Name.Administration);
        someRoleDAO.updateRoleParametric(someUpdateRole, Name.Billing);
        someReadRole = someRoleDAO.readRole(someUpdateRole.id);
        assertEquals(someUpdateRole, someReadRole, "Object was not deleted");
        System.out.println("Updating database is succeeded");
        someRoleDAO.deleteRole(someUpdateRole);
    }

    @Test
    @DisplayName("Delete row from the database")
    void deleteRoleTest() {
        when(someRoleDAO.readRole(someUpdateRole.id)).thenReturn(null);
        someRoleDAO.addRoleParametric(someUpdateRole, Name.Administration);
        someRoleDAO.deleteRole(someUpdateRole);
        someReadRole = someRoleDAO.readRole(someUpdateRole.id);
        assertNotEquals(someUpdateRole, someReadRole, "Object was not deleted");
        System.out.println("Deleting row in database is succeeded");
    }

    @AfterAll
    static void closeConnection() {
        try {
            connectDataBase.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection was disconnected");
    }
}
