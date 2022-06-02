package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("ivan_yuriv");
        user.setAge(28);
        user.setPassword("Qwerty123456");
    }

    @Test
    void register_newUser_ok() {
        User newUser = registrationService.register(user);
        assertEquals(user, newUser);
    }

    @Test
    void register_newUserSameInformation_notOk() {
        assertEquals(user, registrationService.register(user));
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    //Tests for login checking:
    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_spacesLogin_notOk() {
        user.setLogin("         ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_theSameLoginExists_notOk() {
        registrationService.register(user);
        User currentUser = new User();
        currentUser.setLogin("ivan_yuriv");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    //Tests for password checking:
    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_spacesPassword_notOk() {
        user.setPassword("     ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessThanMinimaPassword_notOk() {
        user.setPassword("qwert");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    //Tests for ages checking:
    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incorrectAge_notOk() {
        user.setAge(-2);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        user.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void lessThanMinimalAge_notOk() {
        user.setAge(10);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
