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
    private static User user;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void getDefaultUser() {
        user = new User();
        user.setAge(18);
        user.setLogin("test@gmail.com");
        user.setPassword("123456");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void userAge_Ok() {
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userAge_NotOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));

    }

    @Test
    void nullUserAge_NotOk() {
        user.setAge(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void userPassword_Ok() {
        user.setPassword("112233");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userPassword_NotOk() {
        user.setPassword("1111");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void nullUserPassword_NotOk() {
        user.setPassword(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    void userLogin_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void userLogin_NotOk() {
        registrationService.register(user);
        User user1 = new User();
        user1.setAge(25);
        user1.setLogin("test@gmail.com");
        user1.setPassword("987654321");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user1));
    }

    @Test
    void nullUserLogin_NotOk() {
        user.setLogin(null);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }
}
