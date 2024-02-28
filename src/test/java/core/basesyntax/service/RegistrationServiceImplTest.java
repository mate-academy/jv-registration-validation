package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exeptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "Max199";
    private static final String VALID_PASSWORD = "34dS83";
    private static final int VALID_AGE = 18;
    private static RegistrationService service;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        Storage.people.add(user);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(ValidationException.class, () ->
                service.register(null));
    }

    @Test
    void register_sameLogin_notOk() {
        User newUser = new User();
        newUser.setLogin(VALID_LOGIN);
        newUser.setPassword("654W321q");
        newUser.setAge(36);
        assertThrows(ValidationException.class, () ->
                service.register(newUser));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void register_tooShortLoginLength_notOk() {
        user.setLogin("1");
        assertThrows(ValidationException.class, () ->
                service.register(user));
        user.setLogin("w42");
        assertThrows(ValidationException.class, () ->
                service.register(user));
        user.setLogin("u125R");
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void register_tooShortPasswordLength_notOk() {
        user.setPassword("12");
        assertThrows(ValidationException.class, () ->
                service.register(user));
        user.setPassword("123");
        assertThrows(ValidationException.class, () ->
                service.register(user));
        user.setPassword("12345");
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void service_nullAge_notOk() {
        user.setAge(null);
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void service_invalidAge_notOk() {
        user.setAge(-20);
        assertThrows(ValidationException.class, () ->
                service.register(user));
        user.setAge(0);
        assertThrows(ValidationException.class, () ->
                service.register(user));
        user.setAge(11);
        assertThrows(ValidationException.class, () ->
                service.register(user));
    }

    @Test
    void service_validUser_ok() {
        assertTrue(Storage.people.contains(user));
        User newUser = new User();
        newUser.setLogin("1022Gaga3");
        newUser.setPassword("1284dE345");
        newUser.setAge(45);
        Storage.people.add(newUser);
        assertTrue(Storage.people.contains(newUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
