package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String SET_UP_NAME = "Travis";
    private static final String SET_UP_PASSWORD = "123456789";
    private static final int SET_UP_AGE = 55;
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User user;
    private StorageDaoImpl storageDao;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(SET_UP_NAME);
        user.setAge(SET_UP_AGE);
        user.setPassword(SET_UP_PASSWORD);
        user.setId(null);
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userId_notOk() {
        user.setId(-1L);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_highAge_notOk() {
        user.setAge(220);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-6);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setPassword("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addSameUser_notOk() {
        storageDao.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_addSameLogin_notOk() {
        user.setPassword("1236547532159");
        user.setAge(26);
        storageDao.add(user);
        user.setId(null);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Ok() {
        registrationService.register(user);
        final User firstActualValue = storageDao.get(user.getLogin());
        assertEquals(storageDao.get("Travis"), firstActualValue);
    }
}
