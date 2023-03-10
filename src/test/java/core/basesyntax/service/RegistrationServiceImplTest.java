package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE_LIMIT = 18;
    private static final int LESS_AGE_LIMIT = 15;
    private static final int MORE_AGE_LIMIT = 25;
    private static final int NEGATIVE_AGE = -20;
    private static final String LESS_PASSWORD_LIMIT = "12345";
    private static final String DEFAULT_PASSWORD = "1234665";

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
       user = new User();
       user.setPassword(DEFAULT_PASSWORD);
        user.setAge(MORE_AGE_LIMIT);
    }

    @Test
    void register_nullLogin_notOk() throws RegisterException {
        user.setLogin(null);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsEmpty_notOk() throws RegisterException {
        user.setLogin("");
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() throws RegisterException {
        user.setLogin("Bin");
        user.setPassword("");
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsEmpty_notOk() throws RegisterException {
        user.setLogin("");
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() throws RegisterException {
        user.setLogin("Rio");
        user.setAge(null);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessAgeLimit_notOk() throws RegisterException {
        user.setLogin("Dark");
        user.setAge(LESS_AGE_LIMIT);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessMinPasswordLength_notOk() throws RegisterException {
        user.setLogin("Binner");
        user.setPassword(LESS_PASSWORD_LIMIT);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_alreadyInDatabase_notOk() throws RegisterException {
        user.setLogin("Simpson");
        registrationService.register(user);
        User userExtend = user;
        assertThrows(RegisterException.class, () -> registrationService.register(userExtend));
    }

    @Test
    void register_AgeLimitEqualAgeLimit_Ok() throws RegisterException {
        user.setLogin("Simpson");
        user.setAge(MIN_AGE_LIMIT);
        User userExtend = new User();
        userExtend.setAge(MIN_AGE_LIMIT);
        registrationService.register(user);
        storageDao.add(user);
        assertEquals(userExtend.getAge(), user.getAge());
    }

    @Test
    void register_olderAgeLimit_Ok() throws RegisterException {
        user.setLogin("Dickson");
        User userExtend = new User();
        userExtend.setAge(MORE_AGE_LIMIT);
        registrationService.register(user);
        storageDao.add(user);
        assertEquals(userExtend.getAge(), user.getAge());
    }

    @Test
    void register_negativeAgeLimit_notOk() throws RegisterException {
        user.setLogin("Dickson3");
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_returnsTheSameObject_Ok() throws RegisterException {
        user.setLogin("Pilot");
        User userExtand = registrationService.register(user);
        assertEquals(userExtand, user);
    }

    @Test
    void register_nullUser_notOk() throws RegisterException {
        assertThrows(NullPointerException.class, () -> registrationService.register(null));
    }
}
