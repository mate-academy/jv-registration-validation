package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
    }

    private User createUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @Test
    void register_loginExist_NotOk() {
        User user1 = createUser("gooodlogin@gmail.com", "bayraktar",
                27);
        registrationService.register(user1);
        User user2 = createUser("gooodlogin@gmail.com", "123456", 64);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_checkAge_Ok() {
        User user1 = createUser("gooooodlogin@gmail.com", "bayraktar",
                27);
        registrationService.register(user1);
        assertEquals(storageDao.get("gooooodlogin@gmail.com"), user1);
    }

    @Test
    void register_checkAge_NotOk() {
        User user1 = createUser("goodlogiin@gmail.com", "bayraktar",
                17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_ageNull_NotOk() {
        User user1 = new User();
        user1.setAge(null);
        user1.setPassword("12346987");
        user1.setLogin("goodloginn@gmail.com");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_checkPassword_Ok() throws RegistrationException {
        User user1 = createUser("goodlogin@gmail.com", "bayraktar",
                18);
        registrationService.register(user1);
        assertEquals(storageDao.get("goodlogin@gmail.com"), user1);
    }

    @Test
    void register_checkPasswordLength_NotOk() {
        User user1 = createUser("goodlogin@gmail.com", "", 19);
        User user2 = createUser("goodlogin2gmmail.com", "99999", 23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_CheckPLoginLessThatMinCharactersOrEmpty_NotOk() {
        User user1 = createUser("", "454545", 19);
        User user2 = createUser("Sunny", "878685", 23);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_CheckLogin_Ok() {
        User user1 = createUser("iloveukraine", "12345698", 19);
        User user2 = createUser("jebacputina", "65432100", 23);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(storageDao.get("iloveukraine"), user1);
        assertEquals(storageDao.get("jebacputina"), user2);
    }

    @Test
    void register_CheckPasswordNull_Ok() {
        User user1 = new User();
        user1.setAge(20);
        user1.setPassword(null);
        user1.setLogin("grafmonte");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_checkLoginNull_NotOk() {
        User user1 = new User();
        user1.setAge(26);
        user1.setPassword("956238");
        user1.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
    }
}
