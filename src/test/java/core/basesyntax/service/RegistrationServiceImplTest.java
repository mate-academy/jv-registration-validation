package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String REGISTERED_USER_LOGIN = "Login1";
    private static final String REGISTERED_USER_PASSWORD = "Password123";
    private static final int REGISTERED_USER_AGE = 19;
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final User actualUser = new User();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        actualUser.setLogin(REGISTERED_USER_LOGIN);
        actualUser.setPassword(REGISTERED_USER_PASSWORD);
        actualUser.setAge(REGISTERED_USER_AGE);
        storageDao.add(actualUser);
    }

    @Test
    void register_nullValue_notOK() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userNullLogin_notOK() {
        actualUser.setLogin(null);
        actualUser.setPassword("abc123");
        actualUser.setAge(26);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_userBlankLogin_notOK() {
        actualUser.setLogin("");
        actualUser.setPassword("abc123");
        actualUser.setAge(26);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_userNullPassword_notOK() {
        actualUser.setLogin("Login2");
        actualUser.setAge(26);
        actualUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_userBlankPassword_notOK() {
        actualUser.setLogin("Login2");
        actualUser.setAge(26);
        actualUser.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));

    }

    @Test
    void register_userNullAge_notOK() {
        actualUser.setLogin("Login2");
        actualUser.setAge(null);
        actualUser.setPassword("abc123");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_containsAlreadyUserLogin_notOk() {
        actualUser.setLogin("Login1");
        actualUser.setAge(26);
        actualUser.setPassword("abc123");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_YoungerThan18_notOk() {
        actualUser.setLogin("Login2");
        actualUser.setAge(16);
        actualUser.setPassword("abc123");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_ShortPassword_notOk() {
        actualUser.setLogin("Login2");
        actualUser.setAge(26);
        actualUser.setPassword("abc12");
        assertThrows(RuntimeException.class, () -> registrationService.register(actualUser));
    }

    @Test
    void register_newUser_Ok() {
        User newUser = new User();
        newUser.setLogin("LoginNewUser");
        newUser.setPassword("PasswordNewUser");
        newUser.setAge(22);
        registrationService.register(newUser);
        assertNotNull(storageDao.get("LoginNewUser"));
        assertEquals(newUser, storageDao.get("LoginNewUser"));
        assertEquals("LoginNewUser", storageDao.get("LoginNewUser").getLogin());
        assertEquals("PasswordNewUser", storageDao.get("LoginNewUser").getPassword());
        assertEquals(22, storageDao.get("LoginNewUser").getAge());
    }
}
