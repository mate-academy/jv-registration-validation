package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final int CORRECT_AGE = 50;
    private static final int SMALLER_THAN_18_AGE = 2;
    private static final int NEGATIVE_AGE = -100;
    private static final String CORRECT_LOGIN = "CorrectLogin@gmail.login";
    private static final String CORRECT_PASSWORD = "greatPassword12345";
    private static final String SMALL_LENGTH_WORD = "small";

    private User user;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        user = new User();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_addAndReturnCorrectUser_ok() {
        user.setAge(CORRECT_AGE);
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        User returnedUser = registrationService.register(user);
        StorageDaoImpl storageDao = new StorageDaoImpl();
        User actualAddedUser = storageDao.get(user.getLogin());
        assertEquals(actualAddedUser, user);
        assertEquals(returnedUser, user);
    }

    @Test
    void register_nullUser_notOk() {
        user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_allFieldsAreNull_notOk() {
        user.setAge(null);
        user.setLogin(null);
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_onlyAgeIsNull_notOk() {
        user.setAge(null);
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_onlyLoginIsNull_notOk() {
        user.setAge(CORRECT_AGE);
        user.setLogin(null);
        user.setPassword(CORRECT_PASSWORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_onlyPasswordIsNull_notOk() {
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
        user.setAge(CORRECT_AGE);
        user.setPassword(CORRECT_PASSWORD);
        user.setLogin(CORRECT_LOGIN);

        User sameUser = new User();
        sameUser.setAge(CORRECT_AGE);
        sameUser.setPassword(CORRECT_PASSWORD);
        sameUser.setLogin(CORRECT_LOGIN);

        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(sameUser));

    }

    @Test
    void register_ageIsLessThan18_notOk() {
        user.setAge(SMALLER_THAN_18_AGE);
        user.setPassword(CORRECT_PASSWORD);
        user.setLogin(CORRECT_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        user.setPassword(CORRECT_PASSWORD);
        user.setLogin(CORRECT_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsUnderSixSymbols() {
        user.setAge(CORRECT_AGE);
        user.setPassword(SMALL_LENGTH_WORD);
        user.setLogin(CORRECT_LOGIN);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsUnderSixSymbols() {
        user.setAge(CORRECT_AGE);
        user.setPassword(CORRECT_PASSWORD);
        user.setLogin(SMALL_LENGTH_WORD);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
