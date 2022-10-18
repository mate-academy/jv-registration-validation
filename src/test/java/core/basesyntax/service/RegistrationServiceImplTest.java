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
        rightUserExist = new User();
        rightUserExist.setLogin(RIGHT_LOGIN_EXIST);
        rightUserExist.setAge(RIGHT_AGE);
        rightUserExist.setPassword(RIGHT_PASSWORD);
        new StorageDaoImpl().add(rightUserExist);

        wrongUser = new User();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userExistTest() {
        assertThrows(RuntimeException.class, () -> registration.register(rightUserExist));
    }

    @Test
    void register_userIsRight_And_DoesNotExist() {
        User rightDoesNotExist = new User();
        rightDoesNotExist.setLogin(LOGIN_DOES_NOT_EXIST);
        rightDoesNotExist.setAge(RIGHT_AGE);
        rightDoesNotExist.setPassword(RIGHT_PASSWORD);
        assertDoesNotThrow(() -> registration.register(rightDoesNotExist));
    }

    @Test
    void userWillBeNull() {
        assertThrows(RuntimeException.class, () -> registration.validatingUser(null));
    }

    @Test
    void userLoginDoesNotExist() {
        wrongUser.setLogin(LOGIN_DOES_NOT_EXIST);
        assertDoesNotThrow(() -> registration.validatingUserLogin(wrongUser));
    }

    @Test
    void userLoginExist() {
        assertThrows(RuntimeException.class,
                () -> registration.validatingUserLogin(rightUserExist));
    }

    @Test
    void userLoginNull() {
        wrongUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registration.validatingUserLogin(wrongUser));
    }

    @Test
    void userAgeIsOk() {
        assertDoesNotThrow(() -> registration.validatingUserAge(rightUserExist));
    }

    @Test
    void userAgeLessThanMinimum() {
        wrongUser.setAge(WRONG_AGE);
        assertThrows(RuntimeException.class, () -> registration.validatingUserAge(wrongUser));
    }

    @Test
    void userPasswordIsOK() {
        assertDoesNotThrow(() -> registration.validatingUserPassword(rightUserExist));
    }

    @Test
    void userPasswordLessThanSix() {
        wrongUser.setPassword(WRONG_PASSWORD);
        assertThrows(RuntimeException.class, () -> registration.validatingUserPassword(wrongUser));
    }

    @Test
    void userPasswordIsNull() {
        wrongUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registration.validatingUserPassword(wrongUser));
    }
}
