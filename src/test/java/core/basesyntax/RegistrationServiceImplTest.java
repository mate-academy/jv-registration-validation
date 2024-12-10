package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidDataException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User();
    }

    @Test
    void register_newUser_Ok() {
        testUser.setLogin("validTestUser");
        testUser.setAge(50);
        testUser.setPassword("incred1blePassw0rd");
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_userExists_notOk() {
        testUser.setLogin("unitTestUserLogin");
        Storage.people.add(testUser);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_invalidLogin_notOk() {
        testUser.setAge(20);
        testUser.setPassword("password");
        testUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
        testUser.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
        testUser.setLogin("abc");
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
        testUser.setLogin("abcde");
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_validLogin_Ok() {
        testUser.setAge(20);
        testUser.setPassword("password");
        testUser.setLogin("abcdef");
        assertEquals(testUser, registrationService.register(testUser));
        testUser = new User();
        testUser.setAge(20);
        testUser.setPassword("password");
        testUser.setLogin("11111111111111111");
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_invalidPassword_notOk() {
        testUser.setAge(20);
        testUser.setLogin("validLogin");
        testUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
        testUser.setPassword("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
        testUser.setPassword("pas");
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
        testUser.setPassword("passw");
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_validPassword_Ok() {
        testUser.setAge(20);
        testUser.setLogin("Login1");
        testUser.setPassword("validP");
        assertEquals(testUser, registrationService.register(testUser));
        testUser = new User();
        testUser.setAge(20);
        testUser.setLogin("Login2");
        testUser.setPassword("fullyValidPassword");
        assertEquals(testUser, registrationService.register(testUser));
    }

    @Test
    void register_invalidAge_notOk() {
        testUser.setLogin("validLogin");
        testUser.setPassword("validPass");
        testUser.setAge(-100);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
        testUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
        testUser.setAge(3);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
        testUser.setAge(17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_validAge_Ok() {
        testUser.setLogin("validLogin1");
        testUser.setPassword("validPass");
        testUser.setAge(18);
        assertEquals(testUser, registrationService.register(testUser));
        testUser = new User();
        testUser.setLogin("validLogin2");
        testUser.setPassword("validPass");
        testUser.setAge(100);
        assertEquals(testUser, registrationService.register(testUser));
    }
}
