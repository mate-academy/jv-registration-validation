package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void afterEach() {
        storageDao.clear();
    }

    @Test
    void lowAge_notOkay() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Germiona", "Grenger", 0));
        });

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Germiona", "Grenger", 12));
        });

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Germiona", "Grenger", 17));
        });
    }

    @Test
    void shortPassword_notOkay() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Germiona", "", 25));
        });

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Germiona", "Bye", 25));
        });

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Germiona", "Denis", 25));
        });
    }

    @Test
    void shortLogin_notOkay() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("", "Grenger", 25));
        });

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Gven", "Grenger", 25));
        });

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Brown", "Grenger", 25));
        });
    }

    @Test
    void nullUser_notOkay() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registerUserWithExistingLogin_notOkay() {
        registrationService.register(new User("Germiona", "Grenger", 35));

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(new User("Germiona", "Grenger", 25));
        });
    }

    @Test
    void nullLogin_notOkay() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(new User(null, "Grenger", 20));
        });
    }

    @Test
    void nullPassword_notOkay() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(new User("Germiona", null, 20));
        });
    }

    @Test
    void nullAge_notOkay() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(new User("Germiona", "Grenger", null));
        });
    }

    @Test
    void registrationWithGoodLogin_isOkay() {
        User registredUser1 = registrationService
                .register(new User("Staisy", "Grenger", 35));
        User registredUser2 = registrationService
                .register(new User("UltraMegaPowerfulLogin", "Grenger", 35));

        assertEquals(registredUser1, storageDao.get(registredUser1.getLogin()));
        assertEquals(registredUser2, storageDao.get(registredUser2.getLogin()));
    }

    @Test
    void registrationWithGoodPassword_isOkay() {
        User registredUser1 = registrationService
                .register(new User("Germiona", "Queen6", 35));
        User registredUser2 = registrationService
                .register(new User("Beverly", "UltraMegaPowerfulPassword", 35));

        assertEquals(registredUser1, storageDao.get(registredUser1.getLogin()));
        assertEquals(registredUser2, storageDao.get(registredUser2.getLogin()));
    }

    @Test
    void registrationWithGoodAge_isOkay() {
        User registredUser1 = registrationService
                .register(new User("Germiona", "Grenger", 18));
        User registredUser2 = registrationService
                .register(new User("Beverly", "MeawMeawMeaw", 85));

        assertEquals(registredUser1, storageDao.get(registredUser1.getLogin()));
        assertEquals(registredUser2, storageDao.get(registredUser2.getLogin()));
    }

}
