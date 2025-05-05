package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 20;
    private static final int MIN_VALID_AGE = 18;
    private static final int NOT_VALID_AGE = 15;
    private static final int NEGATIVE_AGE = -1;
    private static final int LARGE_AGE = 234534255;
    private static final Integer NULL_AGE = null;
    private static final String VALID_LOGIN = "Antony";
    private static final String ANOTHER_VALID_LOGIN = "Vlad";
    private static final String NOT_VALID_NAME = "";
    private static final String VALID_PASSWORD = "654321";
    private static final String NOT_VALID_PASSWORD = "111";
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private User user = new User();

    @Test
    void register_emptyUser_NotOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginNull_NotOk() {
        user.setLogin(null);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(InvalidDataException.class,() -> registrationService.register(user));
    }

    @Test
    void register_ageLessThanEighteen_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(NOT_VALID_AGE);
        assertThrows(InvalidDataException.class,() -> registrationService.register(user));
    }

    @Test
    void register_ageNull_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(NULL_AGE);
        assertThrows(InvalidDataException.class,() -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(null);
        user.setAge(VALID_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_notValidPassword_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(NOT_VALID_PASSWORD);
        user.setAge(VALID_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin(NOT_VALID_NAME);
        user.setPassword(null);
        user.setAge(VALID_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExist_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        registrationService.register(user);
    }

    @Test
    void register_nullArgument_NotOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_veryLargeAge_NotOk() {
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(LARGE_AGE);
        assertThrows(InvalidDataException.class,() -> registrationService.register(user));
    }

    @Test
    void register_eighteenAge_Ok() {
        user.setLogin(ANOTHER_VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(MIN_VALID_AGE);
        assertDoesNotThrow(() -> registrationService.register(user));
    }
}
