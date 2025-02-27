package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_notNullUser_Ok() {
        user = new User(34540L, "exclusiveLogin", "123456789", 20);
        User actual = registrationService.register(user);
        assertNotNull(actual);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User(34540L, "notExclusiveLogin", "123456789", 20);
        registrationService.register(user1);

        User user2 = new User(40L, "notExclusiveLogin", "123498789", 21);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void register_exclusiveLogin_Ok() {
        User user1 = new User(34540L, "ExclusiveLogin1", "123456789", 20);
        registrationService.register(user1);

        User user2 = new User(40L, "ExclusiveLogin2", "123498789", 21);
        registrationService.register(user2);

        assertNotEquals(user1.getLogin(), user2.getLogin());
    }

    @Test
    void register_nullId_notOk() {
        User user = new User(null, "ExclusiveLogin", "123456789", 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User(1234L, "short", "123456789", 20);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageLess18_notOk() {
        User user = new User(1234L, "exclusiveLogin", "123456789", 15);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordLengthLess6_notOk() {
        User user = new User(1234L, "exclusiveLogin", "12345", 19);

        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
