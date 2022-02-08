package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final User registrateFirstUser = new User();
    private final User registrateNotSameFirstLoginUser = new User();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        registrateFirstUser.setLogin("nafania");
        registrateFirstUser.setPassword("oldDogBob");
        registrateFirstUser.setAge(25);
    }

    @Test
    void register_userWithAgeLessThanMin_notOk() {
        registrateFirstUser.setAge(17);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(registrateFirstUser));
    }

    @Test
    void register_userWithShortPassword_notOk() {
        registrateFirstUser.setPassword("hello");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(registrateFirstUser));
    }

    @Test
    void register_userWithEmptyPassword_notOk() {
        registrateFirstUser.setPassword("");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(registrateFirstUser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        registrateFirstUser.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(registrateFirstUser));
    }

    @Test
    void register_nullPassword_notOk() {
        registrateFirstUser.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(registrateFirstUser));
    }

    @Test
    void register_nullLogin_notOk() {
        registrateFirstUser.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(registrateFirstUser));
    }

    @Test
    void register_userSuccessRegistration_Ok() {
        registrateNotSameFirstLoginUser.setLogin("nafaniaNotSame");
        registrateNotSameFirstLoginUser.setPassword("youngBob");
        registrateNotSameFirstLoginUser.setAge(20);
        registrationService.register(registrateNotSameFirstLoginUser);
        assertNotNull(storageDao.get(registrateFirstUser.getLogin()));
        assertNotNull(storageDao.get(registrateNotSameFirstLoginUser.getLogin()));
    }

    @Test
    void register_userWithCurrentLoginAlreadyExist_notOk() {
        storageDao.add(registrateFirstUser);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(registrateFirstUser));
    }
}
