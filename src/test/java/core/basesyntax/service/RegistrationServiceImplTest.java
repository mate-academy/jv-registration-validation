package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static RegistrationService service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Jack");
        user.setPassword("qwertyu");
        user.setAge(20);
    }

    @Test
    void register_AddUserAsNull_NotOk() {
        user = null;
        assertThrows(RuntimeException.class, () ->
                service.register(user), "User can't be null");
    }

    @Test
    void register_UserAgeIsNegative_NotOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () ->
                 service.register(user), "Age can't be negative");
    }

    @Test
    void register_UserLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "Login can't be null");
    }

    @Test
    void register_UserPasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "Password can't be null");
    }

    @Test
    void register_UserAgeIsTooYoung_NotOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "User is too young: age" + user.getAge());
    }

    @Test
    void register_AddSameUser_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "We can't register same user");
    }

    @Test
    void register_UserPasswordIsTooShort_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                service.register(user), "Password must be at least "
                + MIN_PASSWORD_LENGTH + " chars length");
    }

    @Test
    void register_NormalUser_Ok() {
        assertEquals(service.register(user), user,
                    "User " + user + " must be registered");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
