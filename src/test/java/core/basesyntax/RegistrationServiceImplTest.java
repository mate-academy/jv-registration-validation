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

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void null_User_NotOk() {
        User user = new User(null, null, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void null_Empty_Login_NotOk() {
        User user = new User(null, "199jjdat", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void null_Empty_Password_NotOk() {
        User user = new User("bob1234", null, 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void null_Empty_Age_NotOk() {
        User user = new User("bob1234", "199jjdat", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void loginLessThen6Characters_NotOk() {
        User user = new User("b34", "199jjdat", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void passwordLessThen6Characters_NotOk() {
        User user = new User("bob1234", "199j", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_DuplicateUser_NotOk() {
        User user1 = new User("bob1234", "199jjdat", 19);
        registrationService.register(user1);
        User user2 = new User("bob1234", "134jj4at", 25);

        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
    }

    @Test
    void userAgeIsAtLeast18YearsOld() {
        User user = new User("bob1234", "199jjdat", 17);

        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
