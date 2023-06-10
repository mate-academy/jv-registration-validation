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

    public User createUser(String login, String password, int age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }

    @Test
    void checkUserWithSuchLoginExists_NotOk() {
        User user1 = createUser("Geneva","123456", 19);
        registrationService.register(user1);
        User user2 = createUser("Geneva","999999", 64);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });

    }

    @Test
    void register_CheckAge_Ok() {
        User user1 = createUser("GLORIA","657483", 19);
        User user2 = createUser("Sunnyy", "098473", 23);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(storageDao.get("GLORIA"), user1);
        assertEquals(storageDao.get("Sunnyy"), user2);
    }

    @Test
    void register_CheckAge_NotOk() {
        User user1 = createUser("ALASKA","987263", -1);
        User user2 = createUser("MONKEY", "77889966", 0);
        User user3 = createUser("BLUUUUR", "0102034", 17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user3);
        });
    }

    @Test
    void register_AgeNull_NotOk() {
        User user1 = new User();
        user1.setAge(null);
        user1.setPassword("123456");
        user1.setLogin("AKAPULKO");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_CheckPassword_Ok() throws RegistrationException {
        User user1 = createUser("LAPTOP","000666", 19);
        User user2 = createUser("STARRR", "989796", 23);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(storageDao.get("LAPTOP"), user1);
        assertEquals(storageDao.get("STARRR"), user2);
    }

    @Test
    void register_CheckPaswordLessThatMinCharactersOrEmpty_NotOk() {
        User user1 = createUser("MONTENEGRO","", 19);
        User user2 = createUser("FISHMAN", "99999", 23);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_CheckPLoginLessThatMinCharactersOrEmpty_NotOk() {
        User user1 = createUser("","454545", 19);
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
        User user1 = createUser("HANOVER","12345698", 19);
        User user2 = createUser("COLUMBIA", "65432100", 23);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(storageDao.get("HANOVER"), user1);
        assertEquals(storageDao.get("COLUMBIA"), user2);
    }

    @Test
    void register_CheckPasswodNull_Ok() {
        User user1 = new User();
        user1.setAge(20);
        user1.setPassword(null);
        user1.setLogin("MONACO");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void register_CheckLoginNull_Ok() {
        User user1 = new User();
        user1.setAge(26);
        user1.setPassword("999111");
        user1.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
    }
}
