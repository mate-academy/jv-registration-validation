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
    void testEmptyPassword_NotOk() {
        user.setLogin("username");
        user.setPassword("");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void testShortPassword_NotOk() {
        user.setLogin("username");
        user.setPassword("abc");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void testEdgeCasePassword_NotOk() {
        user.setLogin("username");
        user.setPassword("abcdf");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void testValidPasswordEdgeCase_Ok() {
        user.setLogin("username");
        user.setPassword("abcdef");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void testValidPassword_Ok() {
        user.setLogin("username");
        user.setPassword("abcdefgh");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void testLongPassword_Ok() {
        user.setLogin("username");
        user.setPassword("abcdefghi");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Password should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void testEmptyUser_NotOk() {
        user.setLogin("");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void testShortLogin_NotOk() {
        user.setLogin("user");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void testEdgeCaseLogin_NotOk() {
        user.setLogin("usern");
        user.setPassword("password");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void testValidLoginEdgeCase_Ok() {
        user.setLogin("usernm");
        user.setPassword("password");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void testValidLogin_Ok() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void testLongLogin_Ok() {
        user.setLogin("username1234567890");
        user.setPassword("password");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Login should have at least 6 characters!");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void testZeroAge_NotOk() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(0);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "You must be over 18 to register");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void testAgeLessThanEighteen_NotOk() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(10);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "You must be over 18 to register");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void testEdgeCaseAge_NotOk() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "You must be over 18 to register");
        assertFalse(Storage.getPeople().contains(user));
    }

    @Test
    void testValidAgeEdgeCase_Ok() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(user),
                "You must be over 18 to register");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void testValidAge_Ok() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user),
                "You must be over 18 to register");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void testLongAge_Ok() {
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(80);
        assertDoesNotThrow(() -> registrationService.register(user),
                "You must be over 18 to register");
        assertTrue(Storage.getPeople().contains(user));
    }

    @Test
    void userAlreadyExists_NotOk() {
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
