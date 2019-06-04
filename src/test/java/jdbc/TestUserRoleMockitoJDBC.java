import jdbc.ConnectDataBase;
import jdbc.UserRoleJDBC;
import object.UserRole;
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
public class TestUserRoleMockitoJDBC {
    UserRole someInsertUserRole = new UserRole(1, 0, 0);
    UserRole someUpdateUserRole = new UserRole(1, 5, 5);
    UserRole someReadUserRole = new UserRole(0, 0, 0);
    private UserRoleJDBC someUserRoleJDBC = mock(UserRoleJDBC.class);
    @Spy
    UserRoleJDBC userRoleJDBC;
    @Spy
    static Connection connectDataBase;

    @BeforeAll
    static void init() {
        connectDataBase = ConnectDataBase.connectionDataBase(null);
    }

    @BeforeEach
    void beforeEach() {
        this.userRoleJDBC = new UserRoleJDBC(connectDataBase);
        this.someInsertUserRole.id = 1;
    }

    @Test
    @DisplayName("Connect to database")
    void connectToDataBase() {
        assertNotEquals(null, connectDataBase, "Connecting is did't. Please check user/password.");
        System.out.println("Connecting is succeeded");
    }

    @Test
    @DisplayName("Create in the database")
    void createUserTest() {
        when(someUserRoleJDBC.readUserRoleParametric(someInsertUserRole.id)).thenReturn(someInsertUserRole);
        someUserRoleJDBC.addUserRoleParametric(someInsertUserRole);
        someReadUserRole = someUserRoleJDBC.readUserRoleParametric(someInsertUserRole.id);
        assertEquals(someInsertUserRole, someReadUserRole, "Object was not created");
        someUserRoleJDBC.deleteUserRoleParametric(someInsertUserRole);
    }

    @Test
    @DisplayName("Read from the database")
    void readUserTest() {
        when(someUserRoleJDBC.readUserRoleParametric(someInsertUserRole.id)).thenReturn(someInsertUserRole);
        someUserRoleJDBC.addUserRoleParametric(someInsertUserRole);
        someReadUserRole = someUserRoleJDBC.readUserRoleParametric(someInsertUserRole.id);
        assertEquals(someInsertUserRole, someReadUserRole, "Object was not read");
        System.out.println("Reading database is succeeded");
        someUserRoleJDBC.deleteUserRoleParametric(someInsertUserRole);
    }

    @Test
    @DisplayName("Update to the database")
    void updateUserTest() {
        when(someUserRoleJDBC.readUserRoleParametric(someUpdateUserRole.id)).thenReturn(someUpdateUserRole);
        someUserRoleJDBC.addUserRoleParametric(someInsertUserRole);
        someUserRoleJDBC.updateUserParametric(someUpdateUserRole);
        someReadUserRole = someUserRoleJDBC.readUserRoleParametric(someUpdateUserRole.id);
        assertEquals(someUpdateUserRole, someReadUserRole, "Object was not deleted");
        System.out.println("Updating database is succeeded");
        someUserRoleJDBC.deleteUserRoleParametric(someUpdateUserRole);
    }

    @Test
    @DisplayName("Delete row from the database")
    void deleteUserRoleTest() {
        when(someUserRoleJDBC.readUserRoleParametric(someUpdateUserRole.id)).thenReturn(null);
        someUserRoleJDBC.addUserRoleParametric(someUpdateUserRole);
        someUserRoleJDBC.deleteUserRoleParametric(someUpdateUserRole);
        someReadUserRole = userRoleJDBC.readUserRoleParametric(someUpdateUserRole.id);
        assertNotEquals(someUpdateUserRole, someReadUserRole, "Object was not deleted");
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
