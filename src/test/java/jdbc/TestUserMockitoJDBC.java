import jdbc.DBConnector;
import jdbc.UserDAO;
import object.User;
import org.junit.jupiter.api.*;
import org.mockito.Spy;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import static org.mockito.Mockito.*;

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

class TestUserMockitoJDBC {

    private LocalDate localDate = LocalDate.of(1950, Month.DECEMBER, 30);
    private User someInsertUser = new User(1, "ABC", localDate, 1, "London", "abra@cadab.ra", "Somebody Insert user");
    private User someUpdateUser = new User(1, "EFG", localDate, 1, "Agraba", "abra@taram.ba", "Somebody Update user");
    private User someReadUser = new User(0, "", localDate, 0, "", "", "");
    private UserDAO someUserDAO = mock(UserDAO.class);
    @Spy
    UserDAO userDAO;
    @Spy
    static Connection connectDataBase;

    @BeforeAll
    static void init() {
        connectDataBase = spy(DBConnector.getConnection());
    }

    @BeforeEach
    void beforeEach() {
        this.userDAO = new UserDAO(connectDataBase);
        this.someInsertUser.id = 1;
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
        when(someUserDAO.readUser(someInsertUser.id)).thenReturn(someInsertUser);
        someUserDAO.addUser(someInsertUser);
        someReadUser = someUserDAO.readUser(someInsertUser.id);
        assertEquals(someInsertUser, someReadUser, "Object was not created");
        someUserDAO.deleteUser(someInsertUser);
    }

    @Test
    @DisplayName("Read from the database")
    void readUserTest() {
        when(someUserDAO.readUser(someInsertUser.id)).thenReturn(someInsertUser);
        someUserDAO.addUser(someInsertUser);
        someReadUser = someUserDAO.readUser(someInsertUser.id);
        assertEquals(someInsertUser, someReadUser, "Object was not read");
        System.out.println("Reading database is succeeded");
        someUserDAO.deleteUser(someInsertUser);
    }

    @Test
    @DisplayName("Update to the database")
    void updateUserTest() {
        when(someUserDAO.readUser(someUpdateUser.id)).thenReturn(someUpdateUser);
        someUserDAO.addUser(someInsertUser);
        someUserDAO.updateUser(someUpdateUser);
        someReadUser = someUserDAO.readUser(someUpdateUser.id);
        assertEquals(someUpdateUser, someReadUser, "Object was not deleted");
        System.out.println("Updating database is succeeded");
        someUserDAO.deleteUser(someUpdateUser);
    }

    @Test
    @DisplayName("Delete row from the database")
    void deleteUserTest() {
        when(someUserDAO.readUser(someUpdateUser.id)).thenReturn(null);
        someUserDAO.addUser(someUpdateUser);
        userDAO.deleteUser(someUpdateUser);
        someReadUser = someUserDAO.readUser(someUpdateUser.id);
        assertNotEquals(someUpdateUser, someReadUser, "Object was not deleted");
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
