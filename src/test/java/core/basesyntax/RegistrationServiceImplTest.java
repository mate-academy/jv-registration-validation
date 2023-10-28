package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String DEFAULT_PASS = "password123";
    private static final String SHORT_PASS = "short";
    private static final int DEFAULT_AGE = 20;
    private static final String DEFAULT_NAME = "testuser";
    private static final String VALID_NAME = "validuser";
    private RegistrationServiceImpl registrationService;
    private StorageDao storageDao;
    private User user;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
        user.setLogin(DEFAULT_NAME);
        user.setPassword(DEFAULT_PASS);
        user.setAge(DEFAULT_AGE);
    }

    @Test
    public void register_ValidUserRegistration_ok() {
        User validUser = new User();
        validUser.setId(55L);
        validUser.setPassword(DEFAULT_PASS);
        validUser.setLogin(VALID_NAME);
        validUser.setAge(DEFAULT_AGE);
        User registeredUser = registrationService.register(validUser);

        Assertions.assertEquals(validUser, registeredUser);
    }

    @Test
    public void register_InvalidUserRegistration_ok() {
        user.setPassword(SHORT_PASS);

        Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertNotNull(user);
    }

    @Test
    public void register_InvalidUserAge_ok() {
        user.setAge(17);

        Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertNotNull(user);
    }

    @Test
    public void register_DuplicateUser_ok() {
        storageDao.add(user);

        Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertNotNull(user);
    }

    @Test
    public void register_NullFields_ok() {
        user.setLogin(null);
        user.setPassword(null);
        user.setAge(null);

        Assertions.assertThrows(InvalidUserException.class, () -> {
            registrationService.register(user);
        });

        Assertions.assertNotNull(user);
    }
}
