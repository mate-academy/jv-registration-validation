package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @Before
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void testRegister_ValidUser_Success() throws UserRegistrationException {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = new User();
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(20);
        User registeredUser = registrationService.register(user);
        Assert.assertEquals(user, registeredUser);
    }

    @Test
    public void testRegister_UserWithExistingLogin_Failure() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user1 = new User();
        user1.setLogin("username");
        user1.setPassword("password1");
        user1.setAge(20);
        User user2 = new User();
        user2.setLogin("username");
        user2.setPassword("password2");
        user2.setAge(25);

        try {
            registrationService.register(user1);
            registrationService.register(user2);
            Assert.fail("Expected UserRegistrationException");
        } catch (UserRegistrationException e) {
            Assert.assertEquals("User with login username already exists.", e.getMessage());
        }
    }

    @Test
    public void testRegister_InvalidLoginLength_Failure() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = new User();
        user.setLogin("user");
        user.setPassword("password");
        user.setAge(22);

        try {
            registrationService.register(user);
            Assert.fail("Expected UserRegistrationException");
        } catch (UserRegistrationException e) {
            Assert.assertEquals("Login must have more than 6 characters!", e.getMessage());
        }
    }

    @Test
    public void testRegister_InvalidPasswordLength_Failure() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = new User();
        user.setLogin("username11");
        user.setPassword("pass");
        user.setAge(23);

        try {
            registrationService.register(user);
            Assert.fail("Expected UserRegistrationException");
        } catch (UserRegistrationException e) {
            Assert.assertEquals("Password must have more than 6 characters!", e.getMessage());
        }
    }

    @Test
    public void testRegister_UnderageUser_Failure() {
        RegistrationService registrationService = new RegistrationServiceImpl();
        User user = new User();
        user.setLogin("username");
        user.setPassword("password");
        user.setAge(15);

        try {
            registrationService.register(user);
            Assert.fail("Expected UserRegistrationException");
        } catch (UserRegistrationException e) {
            Assert.assertEquals("Sorry, but your age not exist our rules", e.getMessage());
        }
    }
}
