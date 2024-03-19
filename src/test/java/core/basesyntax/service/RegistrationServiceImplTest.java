package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;
    private static User first_valid_user;
    private static User second_valid_user;
    private static User user_same_login_first;
    private static User invalid_password_user;
    private static User invalid_login_user;
    private static User invalid_age_user;
    private static User null_login_user;
    private static User null_password_user;

    @BeforeAll
    static void beforeAll() {
        first_valid_user = new User("validUser", "password", 20);
        second_valid_user = new User("validSecondUser", "password12345", 25);
        user_same_login_first = new User("validUser", "pass1478", 23);
        invalid_password_user = new User("invalidPassword", "pass", 23);
        invalid_login_user = new User("user", "password", 29);
        invalid_age_user = new User("userAge", "password", 10);
        null_login_user = new User(null, "password", 20);
        null_password_user = new User("UserNew", null, 28);
    }

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    @Test
    void register_validRegistration_ok() {
        service.register(first_valid_user);
        service.register(second_valid_user);
        int actual = Storage.people.size();
        assertEquals(2, actual);
    }

    @Test
    void register_UserExist_notOk() {
        assertDoesNotThrow(() -> service.register(first_valid_user));
        assertThrows(RegistrationException.class, () -> service.register(user_same_login_first), "No exception");
    }

    @Test
    void register_invalidPassword_notOk() {
        assertThrows(RegistrationException.class, ()-> service.register(invalid_password_user)
                , "No exception");
    }

    @Test
    void register_invalidLogin_notOk() {
        assertThrows(RegistrationException.class, ()-> service.register(invalid_login_user)
                , "No exception");
    }

    @Test
    void register_invalidAge_notOk() {
        assertThrows(RegistrationException.class, ()-> service.register(invalid_age_user)
                , "No exception");
    }

    @Test
    void register_nullUserLogin_notOk() {
        assertThrows(RegistrationException.class, ()-> service.register(null_login_user)
                , "No exception");
    }

    @Test
    void register_nullUserPassword() {
        assertThrows(RegistrationException.class, ()-> service.register(null_password_user)
                , "No exception");
    }
}