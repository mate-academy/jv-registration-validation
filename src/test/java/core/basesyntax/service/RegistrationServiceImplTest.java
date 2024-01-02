package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_USER_LOGIN = "JohnSmith";
    private static final String VALID_USER_PASSWORD = "1234567";
    private static final Integer VALID_USER_AGE = 22;
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_ValidUser_Ok() {
        int expectedStorageSize = 1;
        registrationService
                .register(createUser(VALID_USER_LOGIN, VALID_USER_PASSWORD, VALID_USER_AGE));
        assertEquals(expectedStorageSize, Storage.people.size());
    }

    @Test
    void register_ShortLogin_NotOk() {
        String shortLogin = "John";
        String emptyLogin = "";
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(shortLogin, VALID_USER_PASSWORD, VALID_USER_AGE)));
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(emptyLogin, VALID_USER_PASSWORD, VALID_USER_AGE)));
    }

    @Test
    void register_FiveCharLogin_NotOk() {
        String fiveCharLogin = "Alice";
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(fiveCharLogin, VALID_USER_PASSWORD, VALID_USER_AGE)));
    }

    @Test
    void register_SixCharLogin_Ok() {
        String sixCharLogin = "Maksim";
        int expectedStorageSize = 1;
        registrationService
                .register(createUser(sixCharLogin, VALID_USER_PASSWORD, VALID_USER_AGE));
        assertEquals(expectedStorageSize, Storage.people.size());
    }

    @Test
    void register_NullLogin_NotOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(null, VALID_USER_PASSWORD, VALID_USER_AGE)));
    }

    @Test
    void register_ShortPassword_NotOk() {
        String shortPassword = "1234";
        String emptyPassword = "";
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(VALID_USER_LOGIN, shortPassword, VALID_USER_AGE)));
    }

    @Test
    void register_FiveCharPassword_NotOk() {
        String fiveCharPassword = "12345";
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(VALID_USER_LOGIN, fiveCharPassword, VALID_USER_AGE)));
    }

    @Test
    void register_SixCharPassword_Ok() {
        String sixCharPassword = "123456";
        int expectedStorageSize = 1;
        registrationService.register(createUser(VALID_USER_LOGIN, sixCharPassword, VALID_USER_AGE));
        assertEquals(expectedStorageSize, Storage.people.size());
    }

    @Test
    void register_NullPassword_NotOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(VALID_USER_LOGIN, null, VALID_USER_AGE)));
    }

    @Test
    void register_LowAge_NotOk() {
        Integer lowAge = 17;
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(VALID_USER_LOGIN, VALID_USER_PASSWORD, lowAge)));
    }

    @Test
    void register_NullAge_NotOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(VALID_USER_LOGIN, VALID_USER_PASSWORD, null)));
    }

    @Test
    void register_UserAlreadyExist_NotOk() {
        String sameLogin = "JohnSmith";
        String userPassword = "12345678";
        Integer userAge = 33;
        User existedUser = createUser(VALID_USER_LOGIN, VALID_USER_PASSWORD, VALID_USER_AGE);
        registrationService.register(existedUser);
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(sameLogin, userPassword, userAge)));
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(null));
    }
}
