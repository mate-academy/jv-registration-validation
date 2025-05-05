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
    void test_If_User_Is_Null() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_User_With_Null_Login_Password_Age() {
        User user = createUser(null, null, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_If_Login_Is_Null() {
        User user = createUser(null, "199jjdat", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_If_Password_Is_Null() {
        User user = createUser("bob1234", null, 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_If_Age_Is_Null() {
        User user = createUser("bob1234", "199jjdat", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_If_The_Login_Is_Less_Then_6_Characters() {
        User user = createUser("b34", "199jjdat", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_If_The_Password_Is_Less_Then_6_Characters() {
        User user = createUser("bob1234", "199j", 19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void test_If_User_Age_Is_At_Least_18_Years_Old() {
        User user = createUser("bob1234", "199jjdat", 17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_Duplicate_User() {
        User user = createUser("bob1234", "199jjdat", 19);
        registrationService.register(user);
        User user2 = createUser("bob1234", "134jj4at", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user2));
        core.basesyntax.db.Storage.people.add(user);
    }
}
