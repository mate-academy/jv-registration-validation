package core.basesyntax.service;

import static core.basesyntax.service.RegistrationServiceImpl.MAX_AGE_ALLOWED;
import static core.basesyntax.service.RegistrationServiceImpl.MIN_AGE_ALLOWED;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    void setUp() {
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
        user.setAge(MIN_AGE_ALLOWED);
        user.setLogin(null);
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setAge(MIN_AGE_ALLOWED);
        user.setLogin("user");
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordIsTooShort_notOk() {
        user.setAge(MIN_AGE_ALLOWED);
        user.setLogin("user");
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsTaken_NotOk() {
        user.setAge(MIN_AGE_ALLOWED);
        user.setLogin("user");
        user.setPassword("123456");
        storageDao.add(user);
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
        user.setAge(MAX_AGE_ALLOWED + 1);
        user.setLogin("user");
        user.setPassword("123456");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_Ok() {
        user.setAge(MIN_AGE_ALLOWED);
        user.setLogin("user");
        user.setPassword("123456");
        registrationService.register(user);
        boolean actual = user.getAge() >= MIN_AGE_ALLOWED;
        assertTrue(actual);
    }
}
