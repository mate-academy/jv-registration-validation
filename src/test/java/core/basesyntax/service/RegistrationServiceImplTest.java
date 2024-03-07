package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final String USER_LOGIN = "TestUser";
    private static final String INVALID_LOGIN = "1234";
    private static final String USER_PASSWORD = "Test1234";
    private static final String INVALID_PASSWORD = "1234";
    private static final int USER_AGE = 20;
    private static final int INVALID_AGE = 17;
    private static User user = new User();

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setLogin(USER_LOGIN);
        user.setPassword(USER_PASSWORD);
        user.setAge(USER_AGE);
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void loginNotExist_ok() {
        User actual = registrationService.register(user);
        assertNotNull(actual.getId());
    }

    @Test
    public void loginExist_notOk() {
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void invalidLogin_notOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void invalidPassword_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void invalidAge_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void nullAge_notOk() {
        user.setAge(null);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void userNull_notOk() {
        User emptyUser = null;
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(emptyUser));
    }
}
