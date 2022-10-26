package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "testUser";
    private static final String VALID_PASSWORD = "123user";
    private static final String INVALID_PASSWORD = "123";
    private static final Integer VALID_AGE = 20;
    private static final Integer INVALID_AGE = 15;

    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void userInit() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    public void register_validUser_Ok() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
        StorageDao storageDao = new StorageDaoImpl();
        User actualFromStorage = storageDao.get(RegistrationServiceImplTest.user.getLogin());
        assertEquals(user, actualFromStorage);
    }

    @Test
    public void register_loginExists_notOk() {
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_invalidPasswordLength_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_invalidAge_notOk() {
        user.setAge(INVALID_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }
}
