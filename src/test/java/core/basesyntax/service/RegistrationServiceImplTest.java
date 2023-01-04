package core.basesyntax.service;


import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        user = new User("loginTest", "1234567", 18);
    }

    @Test
    void register_Ok() {
        registrationService.register(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_threeUsers_Ok() {
        User user1 = new User("first", "password", 18);
        User user2 = new User("second", "password", 18);
        User user3 = new User("third", "password", 18);
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        assertEquals(user1, Storage.people.get(0));
        assertEquals(user2, Storage.people.get(1));
        assertEquals(user3, Storage.people.get(2));
    }

    @Test
    void register_passwordEmpty_notOk() {
        user.setPassword("");
        assertThrows(registerUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginEmpty_notOk() {
        user.setLogin("");
        assertThrows(registerUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginStartSymbol() {
        user.setLogin("-TestLogin");
        assertThrows(registerUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(registerUserException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userPassNull_notOk() {
        user.setPassword(null);
        assertThrows(registerUserException.class, () -> registrationService.register(user));
    }


    @Test
    void register_userLoginNull_notOk() {
        user.setLogin(null);
        assertThrows(registerUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeNull_notOk() {
        user.setAge(null);
        assertThrows(registerUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userExist_notOk() {
        storageDao.add(user);
        assertThrows(registerUserException.class, () -> registrationService.register(user));

    }

    @Test
    void register_userShortPassword_notOk() {
        user.setPassword("12345");
        assertThrows(registerUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userYoungerThan18_notOk() {
        user.setAge(17);
        assertThrows(registerUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginEmpty_notOk() {
        user.setLogin("");
        assertThrows(registerUserException.class, () -> registrationService.register(user));
    }
}