package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User first;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        first = new User();
    }

    @Test
    void register_validAge_ok() {
        first.setAge(19);
        first.setLogin("first1");
        first.setPassword("passw1");
        User actual;
        try {
            actual = registrationService.register(first);
        } catch (RegistrationException e) {
            return;
        }
        User expected = storageDao.get(first.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_minValidAge_ok() throws RegistrationException {
        first.setAge(18);
        first.setLogin("second2");
        first.setPassword("passw2");
        User actual = registrationService.register(first);
        User expected = storageDao.get(first.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_maxValidAge_ok() throws RegistrationException {
        first.setAge(110);
        first.setLogin("second3");
        first.setPassword("passw3");
        User actual = registrationService.register(first);
        User expected = storageDao.get(first.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_lessEighteen_notOk() {
        first.setAge(6);
        first.setLogin("firsgddt1");
        first.setPassword("passfew1");
        try {
            registrationService.register(first);
        } catch (RegistrationException e) {
            return;
        }
        fail("RegistrationException should be thrown if age is less than 18");
    }

    @Test
    void register_moreMaxAge_notOk() {
        first.setAge(116);
        first.setLogin("firsttt");
        first.setPassword("passwww");
        try {
            registrationService.register(first);
        } catch (RegistrationException e) {
            return;
        }
        fail("RegistrationException should be thrown if age is more than 110");
    }

    @Test
    void register_negativeAge_notOk() {
        first.setAge(-11);
        first.setLogin("firsrcttt");
        first.setPassword("67sswww");
        try {
            registrationService.register(first);
        } catch (RegistrationException e) {
            return;
        }
        fail("RegistrationException should be thrown if age is negative");
    }

    @Test
    void register_nullUserValue_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        first.setLogin("seco76nd2");
        first.setPassword("pasgggsw2");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(first);
        });
    }

    @Test
    void register_validPassword_ok() {
        first.setAge(19);
        first.setLogin("firserfdt1");
        first.setPassword("passdfsw1");
        User actual;
        try {
            actual = registrationService.register(first);
        } catch (RegistrationException e) {
            return;
        }
        User expected = storageDao.get(first.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_notValidPassword_notOk() {
        first.setAge(19);
        first.setLogin("firhtrdst1");
        first.setPassword("sw1");
        try {
            registrationService.register(first);
        } catch (RegistrationException e) {
            return;
        }
        fail("RegistrationException should be thrown if password is less than 6 characters");

        User user = new User();
        user.setAge(99);
        user.setLogin("firktst1");
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullPassword_notOk() {
        first.setLogin("sec876ond2");
        first.setAge(46);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(first);
        });
    }

    @Test
    void register_validLogin_ok() {
        first.setAge(19);
        first.setLogin("firlnyst1");
        first.setPassword("pasoppsw1");
        User actual;
        try {
            actual = registrationService.register(first);
        } catch (RegistrationException e) {
            return;
        }
        User expected = storageDao.get(first.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_notValidLogin_notOk() {
        first.setAge(19);
        first.setLogin("f1");
        first.setPassword("ppassew-w1");
        User user = new User();
        user.setAge(99);
        user.setLogin("");
        user.setPassword("as964sw1");
        try {
            registrationService.register(first);
            registrationService.register(user);
        } catch (RegistrationException e) {
            return;
        }
        fail("RegistrationException should be thrown if login is less than 6 characters");
    }

    @Test
    void register_nullLogin_notOk() {
        first.setAge(56);
        first.setPassword("grdvcgji");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(first);
        });
    }

    @Test
    void register_thereIsNoUserYet_ok() throws RegistrationException {
        first.setAge(19);
        first.setLogin("firs90brt1");
        first.setPassword("asреdasw1");
        User user = new User();
        user.setAge(99);
        user.setLogin("firk__tst1");
        user.setPassword("as99-64sw1");
        storageDao.add(user);
        User actual = registrationService.register(first);
        User expected = storageDao.get(first.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_userAlreadyExists_notOk() {
        first.setAge(19);
        first.setLogin("fi432brst1");
        first.setPassword("asesmnysw1");
        User user = new User();
        user.setAge(99);
        user.setLogin("firnvvktst1");
        user.setPassword("as964yysw1");
        storageDao.add(first);
        storageDao.add(user);
        try {
            registrationService.register(first);
        } catch (RegistrationException e) {
            return;
        }
        fail("RegistrationException should be thrown if a user with this login already exists");
    }
}
