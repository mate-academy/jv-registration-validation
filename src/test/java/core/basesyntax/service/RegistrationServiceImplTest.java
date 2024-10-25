package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private User second;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        first = new User();
        second = new User();
    }

    @Test
    void register_validAge_ok() {
        first.setAge(29);
        first.setLogin("first1");
        first.setPassword("passw1");
        User actual = registrationService.register(first);
        User expected = storageDao.get(first.getLogin());
        assertEquals(expected, actual);

        second.setAge(18);
        second.setLogin("sec55ond2");
        second.setPassword("passw2");
        actual = registrationService.register(second);
        expected = storageDao.get(second.getLogin());
        assertEquals(expected, actual);

        User third = new User();
        third.setAge(110);
        third.setLogin("second3");
        third.setPassword("passw3");
        actual = registrationService.register(third);
        expected = storageDao.get(third.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_lessThanMinValidAge_notOk() {
        first.setAge(6);
        first.setLogin("firsgddt1");
        first.setPassword("passfew1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(first);
        });
    }

    @Test
    void register_greaterThanMaxAge_notOk() {
        first.setAge(116);
        first.setLogin("firsttt");
        first.setPassword("passwww");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(first);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        first.setAge(-11);
        first.setLogin("firsrcttt");
        first.setPassword("67sswww");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(first);
        });
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
        User actual = registrationService.register(first);
        User expected = storageDao.get(first.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_notValidPassword_notOk() {
        first.setAge(19);
        first.setLogin("firhtrdst1");
        first.setPassword("sw1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(first);
        });

        second.setAge(99);
        second.setLogin("firktst1");
        second.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(second);
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
        User actual = registrationService.register(first);
        User expected = storageDao.get(first.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_notValidLogin_notOk() {
        first.setAge(19);
        first.setLogin("f1");
        first.setPassword("ppassew-w1");

        second.setAge(99);
        second.setLogin("");
        second.setPassword("as964sw1");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(first);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(second);
        });
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
    void register_thereIsNoUserYet_ok() {
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
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(first);
        });
    }
}
