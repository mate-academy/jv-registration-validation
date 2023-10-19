package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    private User createUser(String login, String password, Integer age) {
        return new User(login, password, age);
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void testIfUserIsNull() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void testRegisterUserWithNullLoginPasswordAge() {
        User user = createUser(null, null, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void testIfLoginIsNull() {
        User user = createUser(null, "199jjdat", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void testIfPasswordIsNull() {
        User user = createUser("bob1234", null, 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void testIfAgeIsNull() {
        User user = createUser("bob1234", "199jjdat", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void testIfTheLoginIsLessThen6Characters() {
        User user = createUser("b34", "199jjdat", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void testIfThePasswordIsLessThen6Characters() {
        User user = createUser("bob1234", "199j", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void testIfUserAgeIsAtLeast18YearsOld() {
        User user = createUser("bob1234", "199jjdat", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void tesRegisterDuplicateUser() {
        User user = createUser("bob1234", "199jjdat", 19);
        registrationService.register(user);
        User user2 = createUser("bob1234", "134jj4at", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }
}
