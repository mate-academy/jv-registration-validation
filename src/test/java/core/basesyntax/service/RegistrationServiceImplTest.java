package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
     static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        User bob = new User();
        bob.setLogin("bob");
        bob.setAge(19);
        bob.setPassword("123456789");
        User alice = new User();
        alice.setLogin("alice");
        alice.setAge(26);
        alice.setPassword("234567890");
        StorageDao storageDao = new StorageDaoImpl();
        storageDao.add(bob);
        storageDao.add(alice);
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("dima");
        user.setAge(35);
        user.setPassword("45825806");
    }

    @Test
    void register_newUser_Ok() {
        user.setLogin("nata");
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_minRequiredAge_Ok() {
        user.setLogin("vova");
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_userIsNull_NotOk() {
        User userNull = new User();
        assertThrows(RuntimeException.class, () -> registrationService.register(userNull));
    }

    @Test
    void register_loginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageIsNull_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minRequiredLightPassword_Ok() {
        User actual = registrationService.register(user);
        assertEquals(actual, user);
    }

    @Test
    void register_newUserLoginExist_NotOk() {
        user.setLogin("bob");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThanRequired_NotOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLessThanRequired_NotOk() {
        user.setPassword("4582");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
