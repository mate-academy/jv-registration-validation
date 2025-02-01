package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.exception.AlreadyExistsException;
import core.basesyntax.service.exception.InvalidAgeException;
import core.basesyntax.service.exception.InvalidLoginException;
import core.basesyntax.service.exception.InvalidPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void userPasswordInvalid_notOk() {
        User user = new User();
        user.setLogin("CoolBob");
        user.setPassword("12313");
        user.setAge(18);

        assertThrows(InvalidPasswordException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userLoginInvalid_notOk() {
        User user = new User();
        user.setLogin("xMax");
        user.setPassword("1231312321");
        user.setAge(21);

        assertThrows(InvalidLoginException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void userLoginExists_notOk() {
        User user1 = new User();
        user1.setLogin("JohnTheMighty");
        user1.setPassword("1231312321");
        user1.setAge(27);
        registrationService.register(user1);

        assertTrue(Storage.people.contains(user1));

        User user2 = new User();
        user2.setLogin("JohnTheMighty");
        user2.setPassword("987654321");
        user2.setAge(30);

        assertThrows(AlreadyExistsException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void userAge_notOk() {
        User user1 = new User();
        user1.setLogin("KingSlayer");
        user1.setPassword("1231312321");
        user1.setAge(15);

        assertThrows(InvalidAgeException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void nullUser_notOk() {
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void userRegistration_ok() {
        User user = new User();
        user.setLogin("ValidUser");
        user.setPassword("StrongPass123");
        user.setAge(25);

        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });

        assertTrue(Storage.people.contains(user));
    }
}
