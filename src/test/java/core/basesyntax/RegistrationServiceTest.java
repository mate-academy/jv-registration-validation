package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("user");
        user.setAge(18);
        user.setPassword("password");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void ageMoreThan18_NotOk() {
        user.setAge(12);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void nullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLength_NotOk() {
        user.setPassword("short");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void emptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUser_Ok() {
        User registerUser = registrationService.register(user);
        assertEquals(user.getLogin(),registerUser.getLogin());
        assertEquals(user.getAge(), registerUser.getAge());
        assertEquals(user.getPassword(), registerUser.getPassword());
    }

    @Test
    void existLogin_NotOk() {
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setAge(18);
        newUser.setPassword("1a2s3c4d");
        assertThrows(RuntimeException.class, () -> registrationService.register(newUser));
    }

    @Test
    void nullLogin_NotOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}

