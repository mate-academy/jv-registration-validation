package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    private static RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

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
    void register_Check_Age_Ok() {
        User user1 = createUser("john1993", "123456", 20);
        User user2 = createUser("jack1993", "123456", 18);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(storageDao.get("john1993"), user1);
        assertEquals(storageDao.get("jack1993"), user2);
    }

    @Test
    void register_Check_Age_NotOk() {
        User user1 = createUser("qwerty123", "123456", 15);
        User user2 = createUser("wanted567", "123456", 12);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_Check_LoginNull_NotOk() {
        User user1 = createUser(null, "12345ppp6", 20);
        User user2 = createUser(null, "123456789", 25);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_Check_LoginLessLenght_Ok() {
        User user1 = createUser("Dima_ops", "12345ppp6", 20);
        User user2 = createUser("Angelika", "123456789", 25);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(storageDao.get("Dima_ops"), user1);
        assertEquals(storageDao.get("Angelika"), user2);
    }

    @Test
    void register_Check_LoginLessLenght_NotOk() {
        User user1 = createUser("Dima", "12345ppp6", 20);
        User user2 = createUser("Ange", "123456789", 25);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_Check_PasswordNull_NotOk() {
        User user1 = createUser("qwerty1234", null, 20);
        User user2 = createUser("teddybear", null, 25);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_Check_PasswordMinLenght_Ok() {
        User user1 = createUser("qwerty1234", "123456", 20);
        User user2 = createUser("teddybear", "makaroN", 25);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(storageDao.get("qwerty1234"), user1);
        assertEquals(storageDao.get("teddybear"), user2);
    }

    @Test
    void register_Check_PasswordMinLenght_NotOk() {
        User user1 = createUser("qwerty1234", "tr8", 20);
        User user2 = createUser("teddybear", "789", 25);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }
}
