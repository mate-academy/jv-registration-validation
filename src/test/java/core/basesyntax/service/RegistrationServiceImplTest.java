package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_USER_LOGIN = "JohnSmith";
    private static final String VALID_USER_PASSWORD = "123456";
    private static final Integer VALID_USER_AGE = 22;
    private static final String SHORT_LOGIN = "John";
    private static final String SHORT_PASSWORD = "1234";
    private static final Integer LOW_AGE = 17;
    private static final RegistrationServiceImpl registrationService
            = new RegistrationServiceImpl();

    public User createUser(String login, String password, Integer age) {
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
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(SHORT_LOGIN, VALID_USER_PASSWORD, VALID_USER_AGE)));
    }

    @Test
    void register_NullLogin_NotOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(null, VALID_USER_PASSWORD, VALID_USER_AGE)));
    }

    @Test
    void register_ShortPassword_NotOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(VALID_USER_LOGIN, SHORT_PASSWORD, VALID_USER_AGE)));
    }

    @Test
    void register_NullPassword_NotOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(VALID_USER_LOGIN, null, VALID_USER_AGE)));
    }

    @Test
    void register_LowAge_NotOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(VALID_USER_LOGIN, VALID_USER_PASSWORD, LOW_AGE)));
    }

    @Test
    void register_NullAge_NotOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(createUser(VALID_USER_LOGIN, VALID_USER_PASSWORD, null)));
    }

    @Test
    void register_UserAlreadyExist_NotOk() {
        User existedUser = createUser(VALID_USER_LOGIN, VALID_USER_PASSWORD, VALID_USER_AGE);
        registrationService.register(existedUser);
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(existedUser));
    }

    @Test
    void register_NullUser_NotOk() {
        assertThrows(UserRegistrationException.class, () -> registrationService
                .register(null));
    }
}
