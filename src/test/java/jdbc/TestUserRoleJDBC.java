import jdbc.DBConnector;
import jdbc.UserRoleDAO;
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
 * Использую Spy и Mockito создать заглушки для интерфейса DAO
 */
class TestUserRoleJDBC {
    private UserRole someInsertUserRole = new UserRole(1, 0, 0);
    private UserRole someUpdateUserRole = new UserRole(1, 5, 5);
    private UserRole someReadUserRole = new UserRole(0, 0, 0);

    private UserRoleDAO userRoleDAO;
    private static Connection connectDataBase;

    @BeforeAll
    static void init() {
        connectDataBase = DBConnector.getConnection();
    }

    @BeforeEach
    void beforeEach() {
        this.userRoleDAO = new UserRoleDAO(connectDataBase);
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
        userRoleDAO.addUserRole(someInsertUserRole);
        someReadUserRole = userRoleDAO.readUserRole(someInsertUserRole.id);
        assertEquals(someInsertUserRole, someReadUserRole, "Object was not created");
        System.out.println("Insert to database is succeeded");
        userRoleDAO.deleteUserRole(someInsertUserRole);
    }

    @Test
    @DisplayName("Read from the database")
    void readUserTest() {
        userRoleDAO.addUserRole(someInsertUserRole);
        someReadUserRole = userRoleDAO.readUserRole(someInsertUserRole.id);
        assertEquals(someInsertUserRole, someReadUserRole, "Object was not read");
        System.out.println("Reading database is succeeded");
        userRoleDAO.deleteUserRole(someInsertUserRole);
    }

    @Test
    @DisplayName("Update to the database")
    void updateUserTest() {
        userRoleDAO.addUserRole(someInsertUserRole);
        userRoleDAO.updateUser(someUpdateUserRole);
        someReadUserRole = userRoleDAO.readUserRole(someUpdateUserRole.id);
        assertEquals(someUpdateUserRole, someReadUserRole, "Object was not deleted");
        System.out.println("Updating database is succeeded");
        userRoleDAO.deleteUserRole(someUpdateUserRole);
    }

    @Test
    @DisplayName("Delete row from the database")
    void deleteUserRoleTest() {
        userRoleDAO.addUserRole(someInsertUserRole);
        userRoleDAO.deleteUserRole(someInsertUserRole);
        someReadUserRole = userRoleDAO.readUserRole(someInsertUserRole.id);
        assertNotEquals(someInsertUserRole, someReadUserRole, "Object was not deleted");
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
