package core.basesyntax;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService = new RegistrationServiceImpl();
    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }
    @Test
    void checkIfUserIsNull_NotOk() {
        User user = new User();
        assertThrows(NullPointerException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void checkIfUserIsAlreadyExist_NotOk() {
        User user1 = new User(10l, "Mykola", "1234567", 85);
        registrationService.register(user1);
        User user2 = new User(11l, "Mykola", "1234567", 85);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }

    @Test
    void checkIfMethodCanAddNewUser_Ok() {
        User user1 = new User(10l, "Mykola1", "1234567", 18);
        User user2 = new User(10l, "Mykola2", "1234567", 27);
        User user3 = new User(10l, "Mykola30", "1234567", 19);
        User user4 = new User(10l, "Mykola478", "1234567", 85);
        User user5 = new User(10l, "Mykola87985", "1234567", 54);
        registrationService.register(user1);
        registrationService.register(user2);
        registrationService.register(user3);
        registrationService.register(user4);
        registrationService.register(user5);
        int expected = 5;
        int actual = Storage.people.size();
        assertEquals(expected, actual, "Test failed! Size of array should be "
                + expected + ", but it is " + Storage.people.size());
    }

    @Test
    void checkIfMethodAddDuplicateUsers_NotOk() {
        User user1 = new User(10l, "Mykola", "1234567", 18);
        User user2 = new User(10l, "Mykola", "1234567", 27);
        User user3 = new User(10l, "Mykola3", "1234567", 19);
        User user4 = new User(10l, "Mykola4", "1234567", 85);
        User user5 = new User(10l, "Mykola5", "1234567", 54);
        registrationService.register(user1);
        registrationService.register(user3);
        registrationService.register(user4);
        registrationService.register(user5);
        int expected = 4;
        int actual = Storage.people.size();
        assertEquals(expected, actual, "Test failed! Size of array should be "
                + expected + ", but it is " + Storage.people.size());
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user2);
        });
    }
}
