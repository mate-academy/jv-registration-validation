package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registration;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAge(23);
        user.setLogin("Login");
        user.setPassword("123456");
    }

    @Test
    void register_belowMinAge_notOk() {
        user.setAge(13);
        assertThrows(RuntimeException.class, () -> registration.register(user),
                "Age can`t be less than 18");
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registration.register(user),
                "User age can`t be null");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registration.register(user),
                "User password can`t be null");
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("12 4");
        assertThrows(RuntimeException.class, () -> registration.register(user),
                "User`s password should be greater than 5");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registration.register(user),
                "User login can`t be null");
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registration.register(user),
                "User login can`t be empty");
    }

    @Test
    void register_existingLogin_notOk() {
        registration.register(user);
        assertThrows(RuntimeException.class, () -> registration.register(user),
                "User already exist");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
