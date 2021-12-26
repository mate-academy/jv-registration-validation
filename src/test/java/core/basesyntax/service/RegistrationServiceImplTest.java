package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setAge(18);
        user.setPassword("password");
        user.setLogin("login");
        user.setId(126573934789L);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullId_notOk() {
        user.setId(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Password_isEmpty_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Login_isEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_wrongAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validId_Ok() {
        user.setId(987654321L);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_validAge_Ok() {
        user.setAge(29);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_validUser_Ok() {
        user.setId(123456789L);
        user.setAge(29);
        user.setPassword("qwerty");
        user.setLogin("Name777");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_validPassword_Ok() {
        user.setPassword("ValidPassword");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_validLogin_Ok() {
        user.setPassword("Batman");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_sameLogin_notOk() {
        User otherUser = new User();
        otherUser.setLogin(user.getLogin());
        assertThrows(RuntimeException.class, () -> registrationService.register(otherUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
