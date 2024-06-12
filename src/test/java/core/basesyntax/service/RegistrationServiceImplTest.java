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
    private static final String LOGIN = "user";
    private static final String LOGIN_2 = "user2";
    private static final String VALID_PASSWORD = "12345qwe";
    private static final String INVALID_PASSWORD = "12345";
    private static final String EMPTY_LINE = "";
    private static final int VALID_AGE = 18;
    private static final int INVALID_AGE = 17;
    private static RegistrationService registrationService;
    private User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        defaultUser = new User();
        defaultUser.setLogin(LOGIN);
        defaultUser.setPassword(VALID_PASSWORD);
        defaultUser.setAge(VALID_AGE);
    }

    @Test
    void register_checkUserNotNull_notOk() {
        defaultUser = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_userWithNegativeAge_notOk() {
        defaultUser.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_adultUser_ok() {
        assertEquals(defaultUser, registrationService.register(defaultUser));
    }

    @Test
    void register_notAdultUser_notOk() {
        defaultUser.setAge(INVALID_AGE);
        assertThrows(RuntimeException.class,() -> registrationService.register(defaultUser));
    }

    @Test
    void register_checkIfAgeNotNull_notOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_userWithNullLogin_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_userWithEmptyLogin() {
        defaultUser.setLogin(EMPTY_LINE);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void registerUserWithExistingLogin_NotOk() {
        registrationService.register(defaultUser);
        User userWithSameLogin = new User(
                defaultUser.getLogin(),
                VALID_PASSWORD,
                VALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithSameLogin));
    }

    @Test
    void register_userWithNullPassword() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_userWithValidPassword_ok() {
        assertEquals(defaultUser, registrationService.register(defaultUser));
    }

    @Test
    void register_userWithShortPassword_not_Ok() {
        defaultUser.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void register_userWithEmptyLinePassword_notOk() {
        defaultUser.setPassword(EMPTY_LINE);
        assertThrows(RuntimeException.class, () -> registrationService.register(defaultUser));
    }

    @Test
    void checkStorageSizeAfterAddingTwoUsers() {
        User validUser1 = new User(LOGIN, VALID_PASSWORD, VALID_AGE);
        User validUser2 = new User(LOGIN_2, VALID_PASSWORD, VALID_AGE);
        registrationService.register(validUser1);
        registrationService.register(validUser2);
        assertEquals(2, Storage.people.size());
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }
}
