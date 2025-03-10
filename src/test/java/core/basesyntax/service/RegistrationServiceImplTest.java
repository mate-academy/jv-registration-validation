package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final StorageDaoImpl storageDao = new StorageDaoImpl();
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User user;

    @BeforeEach
    void setUp() {
        storageDao.clear();
        user = new User(123740682L, "validLogin", "2206080", 18);
    }

    @Test
    void nullUser_NotOk() {
        user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void nullUser_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerLogin_Ok() {
        user.setLogin("sdfghj");
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void registerLogin_NotOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void loginLength_Ok() {
        user.setLogin("longEnough");
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void loginLength_NotOk() {
        user.setLogin("short");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLength_Ok() {
        user.setPassword("longPassword");
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void passwordLength_NotOk() {
        user.setPassword("short");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void userAge_Ok() {
        user.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void userAge_NotOk() {
        user.setAge(10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void nullId_Ok() {
        user.setId(2145678L);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void nullId_NotOk() {
        user.setId(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void registerById_Ok() {
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void registerById_NotOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
