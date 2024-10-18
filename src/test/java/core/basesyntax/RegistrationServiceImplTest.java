package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static final int MINIMAL_AMOUNT_OF_SYMBOLS = 6;
    private static final int MINIMAL_AMOUNT_OF_AGE = 18;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_DuplicateUsers_NotOk() {
        User user1 = new User("Mykola", "1234567", 85);
        Storage.people.add(user1);
        User user2 = new User("Mykola", "1234567", 85);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void check_ifMethodCanAddNewUser_ok() {
        User expecterUser = new User("Mykola1", "1234567", 18);
        User actualUser = registrationService.register(expecterUser);
        assertEquals(expecterUser, actualUser, "Expected User is: "
                + expecterUser + ", but was found: " + actualUser);
    }

    @Test
    void register_passwordIsLessThanMinLength_notOk() {
        User user = new User("Mykola1", "12345", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginIsLessThanMinLength_notOk() {
        User user = new User("Myk", "12345568979", 18);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsLessThanMinAge_notOk() {
        User user = new User("Mykola758", "12345568979", 17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_loginIsNull_notOk() {
        User user = new User(null, "12345568979", 19);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_passwordIsNull_notOk() {
        User user = new User("Mykola8908", null, 27);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_ageIsNull_notOk() {
        User user = new User("Mykola8908", "123456789", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User("Mykola8908", "123456789", -13);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
