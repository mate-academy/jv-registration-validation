package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final int OK_YEARS_OLD = 18;
    public static final int NOT_OK_YEARS_OLD = 17;
    public static final int NOT_OK_MINUS_YEARS_OLD = -17;
    public static final int NOT_OK_ZERO_YEARS_OLD = 0;
    public static final String OK_PASSWORD = "password";
    public static final String OK_LOGIN = "login12345";

    private static RegistrationService registration = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_NullLogin_NotOk() {
        User user1 = new User();
        user1.setPassword(OK_PASSWORD);
        user1.setAge(OK_YEARS_OLD);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "Login cant be null");
    }

    @Test
    void register_NullPassword_NotOk() {
        User user1 = new User();
        user1.setLogin(OK_LOGIN);
        user1.setAge(OK_YEARS_OLD);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "Password cant be null");
    }

    @Test
    void register_BelowOrZeroAge_NotOk() {
        User user1 = new User();
        user1.setLogin("login123");
        user1.setPassword(OK_PASSWORD);
        user1.setAge(NOT_OK_MINUS_YEARS_OLD);

        User user2 = new User();
        user2.setLogin("newlogin123");
        user2.setPassword(OK_PASSWORD);
        user2.setAge(NOT_OK_ZERO_YEARS_OLD);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "Age cant be below 0");
        assertThrows(RegisterException.class, () -> registration.register(user2),
                "Age cant be 0");
    }

    @Test
    void register_DuplicateLogin_NotOk() {
        User user1 = new User();
        user1.setLogin("login123");
        user1.setPassword(OK_PASSWORD);
        user1.setAge(OK_YEARS_OLD);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(user1);

        User user2 = new User();
        user2.setLogin("login123");
        user2.setPassword(OK_PASSWORD);
        user2.setAge(OK_YEARS_OLD);
        assertThrows(RegisterException.class, () -> registration.register(user2),
                "In storage we cant have 2 equals logins");
    }

    @Test
    void register_TooShortLogin_NotOk() {
        User user1 = new User();
        user1.setLogin("12345");
        user1.setPassword(OK_PASSWORD);
        user1.setAge(OK_YEARS_OLD);

        User user2 = new User();
        user2.setLogin("");
        user2.setPassword(OK_PASSWORD);
        user2.setAge(OK_YEARS_OLD);

        User user3 = new User();
        user3.setLogin("123");
        user3.setPassword(OK_PASSWORD);
        user3.setAge(OK_YEARS_OLD);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "minimum length for login is 6");

        assertThrows(RegisterException.class, () -> registration.register(user2),
                "minimum length for login is 6");

        assertThrows(RegisterException.class, () -> registration.register(user3),
                "minimum length for login is 6");
    }

    @Test
    void register_TooShortPassword_NotOk() {
        User user1 = new User();
        user1.setLogin("login123");
        user1.setPassword("12345");
        user1.setAge(OK_YEARS_OLD);

        User user2 = new User();
        user2.setLogin("login1233");
        user2.setPassword("");
        user2.setAge(OK_YEARS_OLD);

        User user3 = new User();
        user3.setLogin("login12345");
        user3.setPassword("123");
        user3.setAge(OK_YEARS_OLD);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "minimum length for password is 6");
        assertThrows(RegisterException.class, () -> registration.register(user2),
                "minimum length for password is 6");
        assertThrows(RegisterException.class, () -> registration.register(user3),
                "minimum length for password is 6");
    }

    @Test
    void register_UnderAge_NotOk() {
        User user1 = new User();
        user1.setLogin(OK_LOGIN);
        user1.setPassword(OK_PASSWORD);
        user1.setAge(NOT_OK_YEARS_OLD);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "U have to accept only persons with 18 y.o.");
    }
}
