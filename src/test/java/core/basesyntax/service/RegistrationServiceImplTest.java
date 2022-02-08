package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user = new User();
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        user.setLogin("login");
        user.setPassword("password");
        user.setAge(20);
    }

    @Test
    void register_validUser_ok() {
        registrationService.register(user);
        User expected = user;
        User actual = storageDao.get(user.getLogin());
        assertEquals(expected, actual);
    }

    @Test
    void register_userLoginAlreadyExist_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userWithInvalidPassword_notOk() {
        user.setPassword("pass");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userYounger18_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

}
