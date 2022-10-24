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
    void register_addUserAsNull_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () ->
                service.register(user), "User can't be null");
    }

    @Test
    void register_userAgeIsNegative_notOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () ->
                 service.register(user), "Age can't be negative");
    }

    @Test
    void register_userLoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "Login can't be null");
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "Password can't be null");
    }

    @Test
    void register_userAgeIsTooYoung_notOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "User is too young: age" + user.getAge());
    }

    @Test
    void register_addSameUser_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () ->
                service.register(user), "We can't register same user");
    }

    @Test
    void register_userPasswordIsTooShort_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                service.register(user), "Password must be at least "
                + MIN_PASSWORD_LENGTH + " chars length");
    }

    @Test
    void register_normalUser_ok() {
        assertEquals(service.register(user), user,
                    "User " + user + " must be registered");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
