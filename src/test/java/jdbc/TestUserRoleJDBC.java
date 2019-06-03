import jdbc.ConnectDataBase;
import jdbc.UserRoleJDBC;
import object.UserRole;
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
public class TestUserRoleJDBC {
    UserRole someInsertUserRole = new UserRole(1, 0, 0);
    UserRole someUpdateUserRole = new UserRole(1, 5, 5);
    UserRole someReadUserRole = new UserRole(0, 0, 0);

    UserRoleJDBC userRoleJDBC;
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
        userRoleJDBC.addUserRoleParametric(someInsertUserRole);
        someReadUserRole = userRoleJDBC.readUserRoleParametric(someInsertUserRole.id);
        assertEquals(true, someReadUserRole.equals(someInsertUserRole), "Object was not created");
        System.out.println("Insert to database is succeeded");
    }

    @Test
    @DisplayName("Read from the database")
    void readUserTest() {
        userRoleJDBC.addUserRoleParametric(someInsertUserRole);
        someReadUserRole = userRoleJDBC.readUserRoleParametric(someInsertUserRole.id);
        assertEquals(true, someReadUserRole.equals(someInsertUserRole), "Object was not read");
        System.out.println("Reading database is succeeded");
    }

    @Test
    @DisplayName("Update to the database")
    void updateUserTest() {
        userRoleJDBC.addUserRoleParametric(someInsertUserRole);
        userRoleJDBC.updateUserParametric(someUpdateUserRole);
        someReadUserRole = userRoleJDBC.readUserRoleParametric(someUpdateUserRole.id);
        assertEquals(true, someReadUserRole.equals(someUpdateUserRole), "Object was not deleted");
        System.out.println("Updating database is succeeded");
    }

    @Test
    @DisplayName("Delete row from the database")
    void deleteUserRoleTest() {
        userRoleJDBC.deleteUserRoleParametric(someUpdateUserRole);
        someReadUserRole = userRoleJDBC.readUserRoleParametric(someUpdateUserRole.id);
        assertNotEquals(true, someUpdateUserRole.equals(someReadUserRole), "Object was not deleted");
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
