package core.basesyntax.model;

import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_ValidUser_SuccessfulRegistration() throws InvalidUserDataException {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("strongpassword");
        user.setAge(20);

        User registeredUser = registrationService.register(user);
        Assertions.assertNotNull(registeredUser);
        Assertions.assertEquals(user.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_UserWithShortLogin_ThrowsException() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("strongpassword");
        user.setAge(25);

        Assertions.assertThrows(InvalidUserDataException.class, () -> registrationService
                .register(user));
    }

    @Test
    void register_UserWithShortPassword_ThrowsException() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("short");
        user.setAge(30);

        Assertions.assertThrows(InvalidUserDataException.class, () -> registrationService
                .register(user));
    }

    @Test
    void register_UserWithUnderage_ThrowsException() {
        User user = new User();
        user.setLogin("testuser");
        user.setPassword("strongpassword");
        user.setAge(16);

        Assertions.assertThrows(InvalidUserDataException.class, () -> registrationService
                .register(user));
    }

    @Test
    void register_DuplicateUser_ThrowsException() throws InvalidUserDataException {
        User user1 = new User();
        user1.setLogin("duplicate");
        user1.setPassword("strongpassword");
        user1.setAge(25);

        User user2 = new User();
        user2.setLogin("duplicate");
        user2.setPassword("anotherpassword");
        user2.setAge(30);

        registrationService.register(user1);

        Assertions.assertThrows(InvalidUserDataException.class, () -> registrationService
                .register(user2));
    }
}
