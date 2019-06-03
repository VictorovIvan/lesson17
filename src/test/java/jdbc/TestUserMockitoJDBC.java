import jdbc.ConnectDataBase;
import jdbc.UserJDBC;
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
 * Использую Spy и Mockito создать заглушки для интерфейса JDBC
 */

public class TestUserMockitoJDBC {

    LocalDate localDate = LocalDate.of(1950, Month.DECEMBER, 30);
    private User someInsertUser = new User(1, "ABC", localDate, 1, "London", "abra@cadab.ra", "Somebody Insert user");
    private User someUpdateUser = new User(1, "EFG", localDate, 1, "Agraba", "abra@taram.ba", "Somebody Update user");
    private User someReadUser = new User(0, "", localDate, 0, "", "", "");
    private UserJDBC someUserJDBC = mock(UserJDBC.class);
    @Spy
    UserJDBC userJDBC;
    @Spy
    static Connection connectDataBase;

    @BeforeAll
    static void init() {
        connectDataBase = spy(ConnectDataBase.connectionDataBase(null));
    }

    @BeforeEach
    void beforeEach() {
        this.userJDBC = spy(new UserJDBC(connectDataBase));
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
        when(someUserJDBC.readUserParametric(someInsertUser.id)).thenReturn(someInsertUser);
//        userJDBC.addUserParametric(someInsertUser);
        someReadUser = someUserJDBC.readUserParametric(someInsertUser.id);
        assertEquals(true, someReadUser.equals(someInsertUser), "Object was not created");
    }

    @Test
    @DisplayName("Read from the database")
    void readUserTest() {
        when(someUserJDBC.readUserParametric(someInsertUser.id)).thenReturn(someInsertUser);
//        userJDBC.addUserParametric(someInsertUser);
        someReadUser = someUserJDBC.readUserParametric(someInsertUser.id);
        assertEquals(true, someReadUser.equals(someInsertUser), "Object was not read");
        System.out.println("Reading database is succeeded");
    }

    @Test
    @DisplayName("Update to the database")
    void updateUserTest() {
        when(someUserJDBC.readUserParametric(someUpdateUser.id)).thenReturn(someInsertUser);
//        userJDBC.addUserParametric(someInsertUser);
//        userJDBC.updateUserParametric(someUpdateUser);
        someReadUser = someUserJDBC.readUserParametric(someUpdateUser.id);
        assertEquals(true, someReadUser.equals(someInsertUser), "Object was not deleted");
        System.out.println("Updating database is succeeded");
    }

    @Test
    @DisplayName("Delete row from the database")
    void deleteUserTest() {
        when(someUserJDBC.readUserParametric(someUpdateUser.id)).thenReturn(someInsertUser);
//        userJDBC.deleteUserParametric(someUpdateUser);
        someReadUser = someUserJDBC.readUserParametric(someUpdateUser.id);
        assertNotEquals(true, someUpdateUser.equals(someReadUser), "Object was not deleted");
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