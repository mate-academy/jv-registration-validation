package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationServiceException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static final String INVALID_LOGIN = "LOGIN";
    private static final String VALID_LOGIN = "LOGIN@GMAIL";
    private static final String EXISTING_LOGIN = "LOGIN_OLEG";
    private static final String INVALID_PASSWORD = "PASS";
    private static final String VALID_PASSWORD = "PASSWORD";
    private static final int INVALID_AGE = 15;
    private static final int VALID_AGE = 20;
    private static final User EXISTING = new User(EXISTING_LOGIN, VALID_PASSWORD, VALID_AGE);
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        storageDao.add(EXISTING);
    }

    @Test
    void validRegistration() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    void nullUserRegistration() {
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(null));
    }

    @Test
    void duplicateLoginRegistration() {
        User user = new User(EXISTING.getLogin(), VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void nullLoginRegistration() {
        User user = new User(null, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void invalidLoginLengthRegistration() {
        User user = new User(INVALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPasswordRegistration() {
        User user = new User(VALID_LOGIN, null, VALID_AGE);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void invalidPasswordLengthRegistration() {
        User user = new User(VALID_LOGIN, INVALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void nullAgeRegistration() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, null);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }

    @Test
    void invalidAgeRegistration() {
        User user = new User(VALID_LOGIN, VALID_PASSWORD, INVALID_AGE);
        assertThrows(RegistrationServiceException.class, () -> registrationService.register(user));
    }
}
