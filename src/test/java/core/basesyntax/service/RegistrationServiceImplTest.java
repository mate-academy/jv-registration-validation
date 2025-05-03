package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User CORRECT_USER = new User("CorrectUserLogin", "CorrectUserPass", 18);
    private static final User SAME_LOGIN_1_USER = new User("sameLogin", "sameLogin1UserPass", 19);
    private static final User SAME_LOGIN_USER = new User("sameLogin", "sameLogin2UserPass", 20);
    private static final User SHORT_LOGIN_USER = new User("ShLgn", "ShortLoginUserPass", 21);
    private static final User SHORT_PASSWORD_USER = new User("ShortPassUserLogin", "Pswrd", 22);
    private static final User TOO_YOUNG_USER = new User("YoungUserLogin", "YoungUserPass", 17);
    private static final User NULL_USER = new User(null, null, null);
    private static final User NULL_LOGIN_USER = new User(null, "nullLoginUserPass", 24);
    private static final User NULL_PASSWORD_USER = new User("nullPassUserLogin", null, 25);
    private static final User NULL_AGE_USER = new User("nullAgeUserLogin", "nullAgeUserPass", null);
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_allCorrectData_ok() {
        User actual = registrationService.register(CORRECT_USER);
        User expected = storageDao.get(CORRECT_USER.getLogin());
        assertEquals(actual,expected);
    }

    @Test
    void register_loginTooShort_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(SHORT_LOGIN_USER);
        });
    }

    @Test
    void register_passwordTooShort_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(SHORT_PASSWORD_USER);
        });
    }

    @Test
    void register_underage_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(TOO_YOUNG_USER);
        });
    }

    @Test
    void register_nullUserInput_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_USER);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_LOGIN_USER);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_PASSWORD_USER);
        });
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_AGE_USER);
        });
    }

    @Test
    void register_nullInput_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_addUserIsInStorage_notOk() {
        Storage.people.add(SAME_LOGIN_1_USER);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(SAME_LOGIN_USER);
        });
    }
}
