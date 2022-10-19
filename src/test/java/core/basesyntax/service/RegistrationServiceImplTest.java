package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String RIGHT_LOGIN_EXIST = "qazwsx";
    private static final int RIGHT_AGE = 18;
    private static final String RIGHT_PASSWORD = "absabs";
    private static final String LOGIN_DOES_NOT_EXIST = "abrakadabra";
    private static final int WRONG_AGE = 17;
    private static final String WRONG_PASSWORD = "12345";
    private static RegistrationServiceImpl registration;
    private User rightUserExist;
    private User wrongUser;

    @BeforeAll
    static void beforeAll() {
        registration = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        rightUserExist = new User(RIGHT_LOGIN_EXIST, RIGHT_PASSWORD, RIGHT_AGE);
        new StorageDaoImpl().add(rightUserExist);
        wrongUser = new User();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userExist_notOk() {
        assertThrows(RuntimeException.class, () -> registration.register(rightUserExist));
    }

    @Test
    void register_userIsRightAndDoesNotExist_ok() {
        User rightDoesNotExist = new User(LOGIN_DOES_NOT_EXIST, RIGHT_PASSWORD, RIGHT_AGE);
        assertDoesNotThrow(() -> registration.register(rightDoesNotExist));
    }

    @Test
    void validateUser_UserIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> registration.validateUser(null));
    }

    @Test
    void validateUserLogin_userLoginDoesNotExist_ok() {
        wrongUser.setLogin(LOGIN_DOES_NOT_EXIST);
        assertDoesNotThrow(() -> registration.validateUserLogin(wrongUser));
    }

    @Test
    void validateUserLogin_userLoginExist_notOk() {
        assertThrows(RuntimeException.class,
                () -> registration.validateUserLogin(rightUserExist));
    }

    @Test
    void validateUserLogin_userLoginNull_notOk() {
        wrongUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registration.validateUserLogin(wrongUser));
    }

    @Test
    void validateUserAge_userAgeIsMinimum_ok() {
        assertDoesNotThrow(() -> registration.validateUserAge(rightUserExist));
    }

    @Test
    void validateUserAge_userAgeLessThanMinimum_notOk() {
        wrongUser.setAge(WRONG_AGE);
        assertThrows(RuntimeException.class, () -> registration.validateUserAge(wrongUser));
    }

    @Test
    void validateUserPassword_passwordIsMinimumLength_oK() {
        assertDoesNotThrow(() -> registration.validateUserPassword(rightUserExist));
    }

    @Test
    void validateUserPassword_userPasswordLessThanMinimum_notOk() {
        wrongUser.setPassword(WRONG_PASSWORD);
        assertThrows(RuntimeException.class, () -> registration.validateUserPassword(wrongUser));
    }

    @Test
    void validateUserPassword_userPasswordIsNull_notOk() {
        wrongUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registration.validateUserPassword(wrongUser));
    }
}
