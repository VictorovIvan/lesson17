import jdbc.ConnectDataBase;
import jdbc.RoleJDBC;
import object.Role;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


/*
 * Задание1
 * Взять за основу ДЗ_13. Написать тест на CRUD операции
 * Инициализацию соединение с БД произвести один раз перед началом тестов, после завершения всех тестов, закрыть соединие с БД
 * Подготовку данных для CRUD операций производить в методе Init использую @Before
 *
 * Задание 2
 * Использую Spy и Mockito создать заглушки для интерфейса JDBC
 */
public class TestRoleJDBC {
    private Role someInsertRole = new Role(1, "Administration", "Some description about Administration");
    private Role someUpdateRole = new Role(1, "Billing", "Some update description about Administration");
    private Role someReadRole = new Role(0, "", "");

    RoleJDBC roleJDBC;
    static Connection connectDataBase;

    @BeforeAll
    static void init() {
        connectDataBase = ConnectDataBase.connectionDataBase(null);
    }

    @BeforeEach
    void beforeEach() {
        this.roleJDBC = new RoleJDBC(connectDataBase);
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
        roleJDBC.addRoleParametric(someInsertRole);
        someReadRole = roleJDBC.readRoleParametric(someInsertRole.id);
        assertEquals(someInsertRole, someReadRole, "Object was not created");
        System.out.println("Insert to database is succeeded");
        roleJDBC.deleteRoleParametric(someInsertRole);
    }

    @Test
    @DisplayName("Read from the database")
    void readRoleTest() {
        roleJDBC.addRoleParametric(someInsertRole);
        someReadRole = roleJDBC.readRoleParametric(someInsertRole.id);
        assertEquals(someInsertRole, someReadRole, "Object was not read");
        System.out.println("Reading database is succeeded");
        roleJDBC.deleteRoleParametric(someInsertRole);
    }

    @Test
    @DisplayName("Update to the database")
    void updateRoleTest() {
        roleJDBC.addRoleParametric(someInsertRole);
        roleJDBC.updateRoleParametric(someUpdateRole);
        someReadRole = roleJDBC.readRoleParametric(someUpdateRole.id);
        assertEquals(someUpdateRole, someReadRole, "Object was not deleted");
        System.out.println("Updating database is succeeded");
        roleJDBC.deleteRoleParametric(someUpdateRole);
    }

    @Test
    @DisplayName("Delete row from the database")
    void deleteRoleTest() {
        roleJDBC.addRoleParametric(someInsertRole);
        roleJDBC.deleteRoleParametric(someInsertRole);
        someReadRole = roleJDBC.readRoleParametric(someInsertRole.id);
        assertNotEquals(someInsertRole, someReadRole, "Object was not deleted");
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
