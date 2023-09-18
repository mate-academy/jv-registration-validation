package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.FailedRegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_ageIsLessThenMinAge_notOk() {
        User user = new User("NewUser1", "querty", 13);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        User user = new User("NewUser1", "querty", 19);
        Storage.PEOPLE.add(user);
        User user2 = new User("NewUser1", "querty1", 20);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void register_validUser_ok() {
        User expected = new User("NewUser1", "querty", 19);
        User actual = registrationService.register(expected);
        assertEquals(actual, expected);
    }

    @Test
    void register_loginIsNull_notOk() {
        User user = new User(null, "querty", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsBlank_notOk() {
        User user = new User("         ", "querty", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User("Ann", "querty", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsNull_notOk() {
        User user = new User("NewUser1", null, 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordsIsBlank_notOk() {
        User user = new User("NewUser1", "         ", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User("NewUser1", "pass", 19);
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIsNull_notOk() {
        User user = null;
        assertThrows(FailedRegistrationException.class, () -> registrationService.register(user));
    }
}
