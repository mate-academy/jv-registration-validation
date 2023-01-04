package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        user = new User();
        user.setLogin("loginTest");
        user.setPassword("1234567");
        user.setAge(18);
    }

    @Test
    void register_Ok() {
        registrationService.register(user);
        Assertions.assertEquals(1, Storage.people.size());
    }

    @Test
    void register_threeUsers_Ok() {
        User user1 = new User();
        user1.setLogin("first");
        user1.setPassword("password");
        user1.setAge(18);
        User user2 = new User();
        user2.setLogin("second");
        user2.setPassword("password");
        user2.setAge(18);
        User user3 = new User();
        user3.setLogin("third");
        user3.setPassword("password");
        user3.setAge(18);
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        Assertions.assertEquals(user1, Storage.people.get(0));
        Assertions.assertEquals(user2, Storage.people.get(1));
        Assertions.assertEquals(user3, Storage.people.get(2));
    }

    @Test
    void register_passwordEmpty_notOk() {
        user.setPassword("");
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginEmpty_notOk() {
        user.setLogin("");
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginStartSymbol_notOk() {
        user.setLogin("-TestLogin");
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userNull_notOk() {
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_userPassNull_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginNull_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userAgeNull_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userExist_notOk() {
        storageDao.add(user);
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));

    }

    @Test
    void register_userShortPassword_notOk() {
        user.setPassword("12345");
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userYoungerThan18_notOk() {
        user.setAge(17);
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_userLoginEmpty_notOk() {
        user.setLogin("");
        Assertions.assertThrows(RegistrationUserException.class,
                () -> registrationService.register(user));
    }
}
