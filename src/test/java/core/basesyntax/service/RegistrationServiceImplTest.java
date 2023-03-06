package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_AGE_LIMIT = 18;
    private static final int LESS_AGE_LIMIT = 15;
    private static final int MORE_AGE_LIMIT = 25;
    private static final int NEGATIVE_AGE = -20;
    private static final String LESS_PASSWORD_LIMIT = "12345";
    private static final String PASSWORD = "1234665";

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_nullLogin_notOk() throws RegisterException {
        User user = new User();
        user.setLogin(null);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsEmpty_notOk() throws RegisterException {
        User user = new User();
        user.setPassword(PASSWORD);
        user.setAge(MORE_AGE_LIMIT);
        user.setLogin("");
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() throws RegisterException {
        User user = new User();
        user.setLogin("Bin");
        user.setPassword("");
        user.setAge(MORE_AGE_LIMIT);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsEmpty_notOk() throws RegisterException {
        User user = new User();
        user.setPassword(PASSWORD);
        user.setAge(MORE_AGE_LIMIT);
        user.setLogin("");
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() throws RegisterException {
        User user = new User();
        user.setLogin("Rio");
        user.setPassword(PASSWORD);
        user.setAge(null);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessAgeLimit_notOk() throws RegisterException {
        User user = new User();
        user.setLogin("Dark");
        user.setPassword(PASSWORD);
        user.setAge(LESS_AGE_LIMIT);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessMinPasswordLength_notOk() throws RegisterException {
        User user = new User();
        user.setLogin("Binner");
        user.setAge(MORE_AGE_LIMIT);
        user.setPassword(LESS_PASSWORD_LIMIT);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_alreadyInDatabase_notOk() throws RegisterException {
        User userActual = new User();
        userActual.setLogin("Simpson");
        userActual.setPassword(PASSWORD);
        userActual.setAge(MORE_AGE_LIMIT);
        registrationService.register(userActual);
        User userExtend = userActual;
        assertThrows(RegisterException.class, () -> registrationService.register(userExtend));
    }

    @Test
    void register_AgeLimitEqualAgeLimit_Ok() throws RegisterException {
        User userActual = new User();
        userActual.setLogin("Simpson");
        userActual.setPassword(PASSWORD);
        userActual.setAge(MIN_AGE_LIMIT);
        User userExtend = new User();
        userExtend.setAge(MIN_AGE_LIMIT);
        registrationService.register(userActual);
        storageDao.add(userActual);
        assertEquals(userExtend.getAge(), userActual.getAge());
    }

    @Test
    void register_olderAgeLimit_Ok() throws RegisterException {
        User userActual = new User();
        userActual.setLogin("Dickson");
        userActual.setPassword(PASSWORD);
        userActual.setAge(MORE_AGE_LIMIT);
        User userExtend = new User();
        userExtend.setAge(MORE_AGE_LIMIT);
        registrationService.register(userActual);
        storageDao.add(userActual);
        assertEquals(userExtend.getAge(), userActual.getAge());
    }

    @Test
    void register_negativeAgeLimit_notOk() throws RegisterException {
        User user = new User();
        user.setLogin("Dickson3");
        user.setPassword(PASSWORD);
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegisterException.class, () -> registrationService.register(user));
    }

    @Test
    void register_returnsTheSameObject_Ok() throws RegisterException {
        User userActual = new User();
        userActual.setAge(MORE_AGE_LIMIT);
        userActual.setLogin("Pilot");
        userActual.setPassword(PASSWORD);
        User userExtand = registrationService.register(userActual);
        assertEquals(userExtand, userActual);
    }

    @Test
    void register_nullUser_notOk() throws RegisterException {
        User user = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }
}
