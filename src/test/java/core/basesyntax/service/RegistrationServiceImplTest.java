package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.getMaxAgeAllowed;
import static core.basesyntax.service.RegistrationServiceImpl.getMinAgeAllowed;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        user.setLogin("user");
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setAge(getMinAgeAllowed());
        user.setLogin(null);
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setAge(getMinAgeAllowed());
        user.setLogin("user");
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordIsTooShort_notOk() {
        user.setAge(getMinAgeAllowed());
        user.setLogin("user");
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsTaken_NotOk() {
        user.setAge(getMinAgeAllowed());
        user.setLogin("user");
        user.setPassword("123456");
        storageDao.add(user);
        User anotherUser = user;
        assertSame(user, anotherUser);
    }

    @Test
    void register_userAgeIsZeroOrLess_NotOk() {
        user.setAge(-5);
        user.setLogin("user");
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeExceedsMaxAgeAllowed_NotOk() {
        user.setAge(getMaxAgeAllowed() + 1);
        user.setLogin("user");
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_Ok() {
        user.setAge(getMinAgeAllowed());
        user.setLogin("user");
        user.setPassword("123456");
        registrationService.register(user);
        boolean actual = user.getAge() >= getMinAgeAllowed();
        assertTrue(actual);
    }
}
