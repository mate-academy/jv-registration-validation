package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String LOGIN = "PERSON";
    private static final String PASSWORD = "111111";
    private static final String SHORT_PASSWORD = "11111";
    private static final String EMPTY_LOGIN = "";
    private static final int AGE = 18;
    private static final int MINOR_AGE = 17;
    private static final int TALE_AGE = 121;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(LOGIN, PASSWORD, AGE);
    }

    @Test
    void nullUser_notOk() {
        user = null;
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void userWithNullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userWithEmptyLogin_notOk() {
        user.setLogin(EMPTY_LOGIN);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void duplicateUser_notOk() {
        Storage.people.add(user);
        User secondUser = user;
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(secondUser));
    }

    @Test
    void userWithNullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userWithMinorAge_notOk() {
        user.setAge(MINOR_AGE);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userWithTaleAge_notOk() {
        user.setAge(TALE_AGE);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userWithShortPassword_notOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(InvalidDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userWithValidParameters_ok() {
        User actual = registrationService.register(user);
        assertEquals(Storage.people.size(), 1);
        assertEquals(Storage.people.get(0), actual);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
