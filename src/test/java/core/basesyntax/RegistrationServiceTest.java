package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserInvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();

        User user = new User();
        user.setId(11111111L);
        user.setLogin("user_login");
        user.setPassword("user_password");
        user.setAge(20);

        Storage.people.add(user);
    }

    @Test
    void register_ageIsUnder18_throwsException() {
        User userTest = new User();
        userTest.setAge(10);
        userTest.setLogin("validLogin");
        userTest.setPassword("validPassword");

        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_loginIsNull_throwsException() {
        User userTest = new User();
        userTest.setAge(20);
        userTest.setLogin(null);
        userTest.setPassword("validPassword");

        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_loginIsInvalid_throwsException() {
        User userTest = new User();
        userTest.setAge(20);
        userTest.setLogin("123");
        userTest.setPassword("validPassword");

        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_passwordIsNull_throwsException() {
        User userTest = new User();
        userTest.setAge(20);
        userTest.setLogin("validLogin");
        userTest.setPassword(null);

        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_passwordIsInvalid_throwsException() {
        User userTest = new User();
        userTest.setAge(20);
        userTest.setLogin("validLogin");
        userTest.setPassword("123");

        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userTest));
    }

    @Test
    void register_validData_registered() {
        User newUser = new User();
        newUser.setAge(20);
        newUser.setLogin("new_login");
        newUser.setPassword("securePassword");

        assertEquals(newUser, registrationService.register(newUser));
    }

    @Test
    void register_userAlreadyExists_throwsException() {
        User userTest = new User();
        userTest.setAge(20);
        userTest.setLogin("user_login");
        userTest.setPassword("anotherPassword");

        assertThrows(UserInvalidDataException.class,
                () -> registrationService.register(userTest));
    }
}
