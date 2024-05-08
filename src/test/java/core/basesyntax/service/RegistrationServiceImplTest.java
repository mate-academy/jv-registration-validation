package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registration = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_NullLogin_NotOk() {
        User user1 = new User();
        user1.setPassword("password");
        user1.setAge(18);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "Login cant be null");
    }

    @Test
    void register_NullPassword_NotOk() {
        User user1 = new User();
        user1.setLogin("login123");
        user1.setAge(18);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "Password cant be null");
    }

    @Test
    void register_BelowOrZeroAge_NotOk() {
        User user1 = new User();
        user1.setLogin("login123");
        user1.setPassword("password");
        user1.setAge(-16);

        User user2 = new User();
        user2.setLogin("newlogin123");
        user2.setPassword("nwepassword");
        user2.setAge(0);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "Age cant be below 0");
        assertThrows(RegisterException.class, () -> registration.register(user2),
                "Age cant be 0");
    }

    @Test
    void register_DuplicateLogin_NotOk() {
        User user1 = new User();
        user1.setLogin("login123");
        user1.setPassword("password1");
        user1.setAge(19);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(user1);

        User user2 = new User();
        user2.setLogin("login123");
        user2.setPassword("password2");
        user2.setAge(20);
        assertThrows(RegisterException.class, () -> registration.register(user2),
                "In storage we cant have 2 equals logins");
    }

    @Test
    void register_TooShortLogin_NotOk() {
        User user1 = new User();
        user1.setLogin("12345");
        user1.setPassword("password1");
        user1.setAge(18);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "minimum length for login is 6");
    }

    @Test
    void register_TooShortPassword_NotOk() {
        User user1 = new User();
        user1.setLogin("login123");
        user1.setPassword("12345");
        user1.setAge(18);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "minimum length for password is 6");
    }

    @Test
    void register_UnderAge_NotOk() {
        User user1 = new User();
        user1.setLogin("login123");
        user1.setPassword("password1");
        user1.setAge(17);

        assertThrows(RegisterException.class, () -> registration.register(user1),
                "U have to accept only persons with 18 y.o.");
    }
}
