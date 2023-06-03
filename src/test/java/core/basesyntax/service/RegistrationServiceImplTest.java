package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String NULL_TESTER_STRING = null;
    private static final Integer NULL_TESTER_INT = null;
    private RegistrationService registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void testRegister_ValidUser_Success() throws UserRegistrationException {
        User user = createDefaultUser();
        User registeredUser = registrationService.register(user);
        Assertions.assertEquals(user, registeredUser);
    }

    @Test
    public void testRegister_UserWithExistingLogin_Failure() {
        User user1 = createDefaultUser();
        User user2 = createDefaultUser();
        user2.setPassword("password2");

        Assertions.assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user1);
            registrationService.register(user2);
        });
    }

    @Test
    public void testRegister_InvalidLoginLength_Failure() {
        User user = createDefaultUser();
        user.setLogin("user");

        Assertions.assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void testRegister_InvalidPasswordLength_Failure() {
        User user = createDefaultUser();
        user.setPassword("pass");

        Assertions.assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void testRegister_UnderageUser_Failure() {
        User user = createDefaultUser();
        user.setAge(15);

        Assertions.assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void testRegister_LoginNull_Failure() {
        User user = createDefaultUser();
        user.setLogin(NULL_TESTER_STRING);

        Assertions.assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void testRegister_PasswordNull_Failure() {
        User user = createDefaultUser();
        user.setPassword(NULL_TESTER_STRING);

        Assertions.assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void testRegister_AgeNull_Failure() {
        User user = createDefaultUser();
        user.setAge(NULL_TESTER_INT);

        Assertions.assertThrows(UserRegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    private User createDefaultUser() {
        User user = new User();
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(20);
        return user;
    }
}
