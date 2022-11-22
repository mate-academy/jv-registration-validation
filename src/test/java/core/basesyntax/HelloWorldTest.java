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

public class HelloWorldTest {
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
        user.setPassword("05011990");
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_login_ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_login_notOk() {
        registrationService.register(user);
        User user1 = new User();
        user1.setAge(23);
        user1.setLogin("test@gmail.com");
        user1.setPassword("898989898989");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user1));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_age_ok() {
        user.setAge(32);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_age_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_password_ok() {
        user.setPassword("minutes");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_password_notOk() {
        user.setPassword("car");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nllPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }
}
