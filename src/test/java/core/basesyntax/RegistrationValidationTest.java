package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationValidationTest {
    private static final Integer DEFAULT_AGE = 18;
    private static final String DEFAULT_LOGIN = "test@gmail.com";
    private static final String DEFAULT_PASSWORD = "123456";
    private static User user;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void getDefaultUser() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_age_ok() {
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_lessThanMinAge_notOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));

    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_password_ok() {
        user.setPassword("112233");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_lessThanMinPassword_notOk() {
        user.setPassword("1111");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_login_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_loginExists_notOk() {
        registrationService.register(user);
        User duplicatedLoginUser = new User();
        duplicatedLoginUser.setAge(25);
        duplicatedLoginUser.setLogin(DEFAULT_LOGIN);
        duplicatedLoginUser.setPassword("987654321");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(duplicatedLoginUser));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
