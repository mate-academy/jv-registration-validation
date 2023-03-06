package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static User bob;
    private static User alice;
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();

    @BeforeAll
    static void beforeAll() {
        bob = new User();
        bob.setLogin("bob");
        bob.setPassword("1234567");
        bob.setAge(21);
        alice = new User();
        alice.setLogin("alice");
        alice.setPassword("456789");
        alice.setAge(27);
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void registration_userWithValidData_Ok() {
        registrationService.register(bob);
        registrationService.register(alice);
        int actual = Storage.people.size();
        assertEquals(2, actual);
    }

    @Test
    void registration_userWithSameLogin_notOk() {
        registrationService.register(bob);
        registrationService.register(alice);
        User alice2 = new User();
        alice2.setLogin("alice");
        alice2.setPassword("789000");
        alice2.setAge(20);
        assertThrows(InvalidDataException.class, () -> registrationService.register(alice2));
    }

    @Test
    void registration_userAgeUnder18_notOk() {
        User max = new User();
        max.setLogin("max");
        max.setPassword("789123");
        max.setAge(16);
        assertThrows(InvalidDataException.class, () -> registrationService.register(max));
    }

    @Test
    void registration_userPasswordUnder6Characters_notOk() {
        User max = new User();
        max.setLogin("max");
        max.setPassword("1234");
        max.setAge(31);
        assertThrows(InvalidDataException.class, () -> registrationService.register(max));
    }

    @Test
    void registration_userWithNullValue_notOk() {
        User max = new User();
        max.setLogin(null);
        max.setPassword("1234");
        max.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(max));
    }

    @Test
    void registration_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void registration_userWithEmptyLogin_notOk() {
        User max = new User();
        max.setLogin("");
        max.setPassword("1234");
        max.setAge(22);
        assertThrows(InvalidDataException.class, () -> registrationService.register(max));
    }
}
