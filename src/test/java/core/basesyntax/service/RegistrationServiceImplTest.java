package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void setRegistrationService() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUpUser() {
        user = new User();
    }

    @Test
    void register_EmptyPassword_NotOk() {
        user.setLogin("username");
        user.setPassword("");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void register_ShortPassword_NotOk() {
        user.setLogin("username");
        user.setPassword("abc");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void register_EdgeCasePassword_NotOk() {
        user.setLogin("username");
        user.setPassword("abcdf");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void register_ValidPasswordEdgeCase_Ok() {
        user.setLogin("username");
        user.setPassword("abcdef");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void register_ValidPassword_Ok() {
        user.setLogin("username");
        user.setPassword("abcdefgh");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void register_LongPassword_Ok() {
        user.setLogin("username");
        user.setPassword("abcdefghi");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void register_EmptyUser_NotOk() {
        user.setLogin("");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void register_ShortLogin_NotOk() {
        user.setLogin("user");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void register_EdgeCaseLogin_NotOk() {
        user.setLogin("usern");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void register_ValidLoginEdgeCase_Ok() {
        user.setLogin("usernm");
        user.setPassword("password");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void register_ValidLogin_Ok() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void register_LongLogin_Ok() {
        user.setLogin("username1234567890");
        user.setPassword("password");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void register_ZeroAge_NotOk() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "You must be over 18 to register");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void register_AgeLessThanEighteen_NotOk() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "You must be over 18 to register");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void register_EdgeCaseAge_NotOk() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "You must be over 18 to register");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void register_ValidAgeEdgeCase_Ok() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(user),
                "You must be over 18 to register");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void register_ValidAge_Ok() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "You must be over 18 to register");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void register_tLongAge_Ok() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(80);
        assertDoesNotThrow(() -> registrationService.register(user),
                "You must be over 18 to register");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void register_AlreadyExists_NotOk() {
        User user1 = new User();
        user1.setLogin("khtos'");
        user1.setAge(18);
        user1.setPassword("yakyys");
        User user2 = new User();
        user2.setLogin("khtos'");
        user2.setAge(19);
        user2.setPassword("909090");
        registrationService.register(user1);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2),
                "User already exists!");
    }

    @Test
    void newUser_Ok() {
        registrationService = new RegistrationServiceImpl();
        User user1 = new User();
        user1.setLogin("khtos'");
        user1.setAge(18);
        user1.setPassword("yakyys");
        User user2 = new User();
        user2.setLogin("she_khtos'");
        user2.setAge(19);
        user2.setPassword("909090");
        assertDoesNotThrow(() -> registrationService.register(user1),
                "User already exists!");
        assertTrue(Storage.getPeople().contains(user1));
        assertDoesNotThrow(() -> registrationService.register(user2),
                "User already exists!");
        assertTrue(Storage.getPeople().contains(user2));
    }

    @AfterEach
    void cleanUp() {
        registrationService.removeAllUsers();
    }
}
