package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private static final int EDGE_SHIFT = 1;
    private static RegistrationService registrationService;
    private User testingUser;

    @BeforeAll
    static void startUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void init() {
        testingUser = new User("testingLogin", "testingPassword", 25);
        Storage.people.addAll(List.of(
                new User("login1", "password1", 18),
                new User("login2", "password2", 20),
                new User("login3", "password3", 21),
                new User("login4", "password4", 22),
                new User("login5", "password5", 23)
        ));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validData_ok() {
        User actualUser = registrationService.register(testingUser);
        User registeredUser = Storage.people.stream()
                .filter(user -> user.getLogin().equals(testingUser.getLogin()))
                .findFirst()
                .orElse(null);
        assertEquals(testingUser, actualUser);
        assertNotNull(registeredUser);
        assertTrue(Storage.people.contains(testingUser));
        assertEquals(testingUser, registeredUser);

        assertEquals(testingUser.getId(), registeredUser.getId());
        assertEquals(testingUser.getLogin(), registeredUser.getLogin());
        assertEquals(testingUser.getPassword(), registeredUser.getPassword());
        assertEquals(testingUser.getAge(), registeredUser.getAge());
    }

    @Test
    void register_nullUser_notOk() {
        testingUser = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testingUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testingUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

    @Test
    void register_nullAge_notOk() {
        testingUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        testingUser.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        testingUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

    @Test
    void register_negativeAge_notOk() {
        testingUser.setAge(-5);
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

    @Test
    void register_minLoginLength_notOk() {
        testingUser.setLogin("a".repeat(MIN_LOGIN_LENGTH - EDGE_SHIFT));
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

    @Test
    void register_minPasswordLength_notOk() {
        testingUser.setPassword("a".repeat(MIN_PASSWORD_LENGTH - EDGE_SHIFT));
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

    @Test
    void register_minAge_notOk() {
        testingUser.setAge(MIN_AGE - EDGE_SHIFT);
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

    @Test
    void register_edgeLoginLength_ok() {
        testingUser.setLogin("a".repeat(MIN_LOGIN_LENGTH));
        assertDoesNotThrow(() -> registrationService.register(testingUser));
    }

    @Test
    void register_edgePasswordLength_ok() {
        testingUser.setPassword("a".repeat(MIN_PASSWORD_LENGTH));
        assertDoesNotThrow(() -> registrationService.register(testingUser));
    }

    @Test
    void register_edgeAge_ok() {
        testingUser.setAge(MIN_AGE);
        assertDoesNotThrow(() -> registrationService.register(testingUser));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        User existingUser = Storage.people.stream()
                .findAny()
                .orElseThrow();
        testingUser.setLogin(existingUser.getLogin());
        assertThrows(RegistrationException.class, () -> registrationService.register(testingUser));
    }

}
