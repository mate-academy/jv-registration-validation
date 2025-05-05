package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_PASSWORD = "qwertyQWERTY";
    private static final String VALID_LOGIN = "testLogin";
    private static final int VALID_AGE = 37;
    private static StorageDao storageDao;
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setAge(VALID_AGE);
        user.setPassword(VALID_PASSWORD);
        user.setLogin(VALID_LOGIN);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_login_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

        user.setLogin("a");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

        user.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_notOk() {
        user.setAge(-12);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_password_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

        user.setPassword("d");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));

        user.setPassword("qwert");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_ok() {
        User firstUser = new User();
        firstUser.setAge(18);
        firstUser.setLogin("loginFor18YearOld");
        firstUser.setPassword(VALID_PASSWORD);
        assertDoesNotThrow(() -> registrationService.register(firstUser));

        User secondUser = new User();
        secondUser.setAge(101);
        secondUser.setLogin("loginFor101YearOld");
        secondUser.setPassword(VALID_PASSWORD);
        assertDoesNotThrow(() -> registrationService.register(secondUser));
    }

    @Test
    void register_login_ok() {
        User firstUser = new User();
        firstUser.setAge(VALID_AGE);
        firstUser.setLogin("abcdef");
        firstUser.setPassword(VALID_PASSWORD);
        assertDoesNotThrow(() -> registrationService.register(firstUser));

        User secondUser = new User();
        secondUser.setAge(VALID_AGE);
        secondUser.setLogin("abcdef_asf_sdf");
        secondUser.setPassword(VALID_PASSWORD);
        assertDoesNotThrow(() -> registrationService.register(secondUser));
    }

    @Test
    void register_password_ok() {
        User firstUser = new User();
        firstUser.setAge(VALID_AGE);
        firstUser.setLogin("loginFor_qwerty_pass");
        firstUser.setPassword("qwerty");
        assertDoesNotThrow(() -> registrationService.register(firstUser));

        User secondUser = new User();
        secondUser.setAge(VALID_AGE);
        secondUser.setLogin("loginFor_qwerty1234QWERTY_pass");
        secondUser.setPassword("qwerty1234QWERTY");
        assertDoesNotThrow(() -> registrationService.register(secondUser));
    }

    @Test
    void register_addUser_ok() {
        User expected = new User();
        expected.setAge(VALID_AGE);
        expected.setPassword(VALID_PASSWORD);
        expected.setLogin("login_for_addUser_ok");
        User actual = storageDao.add(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_sameLogin_notOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
