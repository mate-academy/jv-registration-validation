package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final int CORRECT_AGE = 50;
    private static final int SMALLER_THAN_ALLOWED_AGE = 2;
    private static final int NEGATIVE_AGE = -100;
    private static final String CORRECT_LOGIN = "CorrectLogin@gmail.login";
    private static final String CORRECT_PASSWORD = "greatPassword12345";
    private static final String SMALL_LENGTH_CREDENTIAL_VALUE = "small";
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_addAndReturnCorrectUser_ok() {
        User correctUser = new User();
        correctUser.setAge(CORRECT_AGE);
        correctUser.setLogin(CORRECT_LOGIN);
        correctUser.setPassword(CORRECT_PASSWORD);
        User returnedUser = registrationService.register(correctUser);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        User actualAddedUser = storageDao.get(correctUser.getLogin());
        assertEquals(actualAddedUser, correctUser);
        assertEquals(returnedUser, correctUser);
    }

    @Test
    void register_nullUser_notOk() {
        User nullUser = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(nullUser));
    }

    @Test
    void register_ageIsNull_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_notOk() {
        User user = new User();
        user.setAge(CORRECT_AGE);
        user.setLogin(null);
        user.setPassword(CORRECT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        User user = new User();
        user.setAge(CORRECT_AGE);
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_isNull_notOk() {
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_existingUser_notOk() {
        User newUser = new User();
        newUser.setAge(CORRECT_AGE);
        newUser.setPassword(CORRECT_PASSWORD);
        newUser.setLogin(CORRECT_LOGIN);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(newUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_ageIsLessThan18_notOk() {
        User youngUser = new User();
        youngUser.setAge(SMALLER_THAN_ALLOWED_AGE);
        youngUser.setPassword(CORRECT_PASSWORD);
        youngUser.setLogin(CORRECT_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(youngUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User userWithNegativeAge = new User();
        userWithNegativeAge.setAge(NEGATIVE_AGE);
        userWithNegativeAge.setPassword(CORRECT_PASSWORD);
        userWithNegativeAge.setLogin(CORRECT_LOGIN);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithNegativeAge));
    }

    @Test
    void register_passwordIsUnderSixSymbols() {
        User userWithSmallPassword = new User();
        userWithSmallPassword.setAge(CORRECT_AGE);
        userWithSmallPassword.setPassword(SMALL_LENGTH_CREDENTIAL_VALUE);
        userWithSmallPassword.setLogin(CORRECT_LOGIN);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithSmallPassword));
    }

    @Test
    void register_loginIsUnderSixSymbols() {
        User userWithSmallLogin = new User();
        userWithSmallLogin.setAge(CORRECT_AGE);
        userWithSmallLogin.setPassword(CORRECT_PASSWORD);
        userWithSmallLogin.setLogin(SMALL_LENGTH_CREDENTIAL_VALUE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithSmallLogin));
    }
}
