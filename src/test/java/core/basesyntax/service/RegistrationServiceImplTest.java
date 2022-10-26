package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationService REGISTRATION_SERVICE = new RegistrationServiceImpl();
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();
    private static final User DEFAULT_USER = new User();
    private static final String CORRECT_LOGIN = "Johnson";
    private static final String CHECK_LOGIN = "Lara";
    private static final int CORRECT_AGE = 20;
    private static final int YOUNG_AGE = 10;
    private static final int INCORRECT_AGE = -5;
    private static final String CORRECT_PASSWORD = "John328";
    private static final String WRONG_PASSWORD = "lol";

    @Test
    void correctData_ok() {
        DEFAULT_USER.setLogin(CHECK_LOGIN);
        DEFAULT_USER.setAge(CORRECT_AGE);
        DEFAULT_USER.setPassword(CORRECT_PASSWORD);
        assertEquals(DEFAULT_USER, REGISTRATION_SERVICE.register(DEFAULT_USER));
    }

    @Test
    public void checkAlreadyExistedUser_notOk() {
        User user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        STORAGE_DAO.add(user);
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(user));
    }

    @Test
    void registerUserWithNullLogin_notOk() {
        DEFAULT_USER.setLogin(null);
        DEFAULT_USER.setAge(CORRECT_AGE);
        DEFAULT_USER.setPassword(CORRECT_PASSWORD);
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(DEFAULT_USER));
    }

    @Test
    void registerUserWithNullAge_notOk() {
        DEFAULT_USER.setLogin(CORRECT_LOGIN);
        DEFAULT_USER.setAge(null);
        DEFAULT_USER.setPassword(CORRECT_PASSWORD);
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(DEFAULT_USER));
    }

    @Test
    void registerUserWithSmallAge_notOk() {
        DEFAULT_USER.setLogin(CORRECT_LOGIN);
        DEFAULT_USER.setAge(YOUNG_AGE);
        DEFAULT_USER.setPassword(CORRECT_PASSWORD);
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(DEFAULT_USER));
    }

    @Test
    void registerUserWithInvalidAge_notOk() {
        DEFAULT_USER.setLogin(CORRECT_LOGIN);
        DEFAULT_USER.setAge(INCORRECT_AGE);
        DEFAULT_USER.setPassword(CORRECT_PASSWORD);
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(DEFAULT_USER));
    }

    @Test
    void registerUserWithSmallPassword_notOk() {
        DEFAULT_USER.setLogin(CORRECT_LOGIN);
        DEFAULT_USER.setAge(CORRECT_AGE);
        DEFAULT_USER.setPassword(WRONG_PASSWORD);
        assertThrows(RuntimeException.class, () -> REGISTRATION_SERVICE.register(DEFAULT_USER));
    }
}
