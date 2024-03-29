package core.basesyntax.model;

import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private User validUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setLogin("testuser");
        validUser.setPassword("strongpassword");
        validUser.setAge(20);
    }

    @Test
    void register_ValidUser_SuccessfulRegistration() throws InvalidUserDataException {
        User registeredUser = registrationService.register(validUser);
        Assertions.assertNotNull(registeredUser);
        Assertions.assertEquals(validUser.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_UserWithShortLogin_ThrowsException() {
        validUser.setAge(25);
        Assertions.assertThrows(InvalidUserDataException.class, () -> registrationService
                .register(validUser));
    }

    @Test
    void register_UserWithShortPassword_ThrowsException() {
        validUser.setPassword("short");
        validUser.setAge(30);

        Assertions.assertThrows(InvalidUserDataException.class, () -> registrationService
                .register(validUser));
    }

    @Test
    void register_UserWithUnderage_ThrowsException() {
        validUser.setAge(16);
        Assertions.assertThrows(InvalidUserDataException.class, () -> registrationService
                .register(validUser));
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
