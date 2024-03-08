package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void testCheckPasswordWithValidPassword() {
        User user = new User("testUser", "password123", 20);
        boolean result = registrationService.checkPassword(user);
        Assertions.assertTrue(result);
    }

    @Test
    public void testCheckPasswordWithNullPassword() {
        User user = new User("testUser", null, 20);
        boolean result = registrationService.checkPassword(user);
        Assertions.assertFalse(result);
    }

    @Test
    public void testCheckPasswordWithShortPassword() {
        User user = new User( "testUser", "pass", 20);
        boolean result = registrationService.checkPassword(user);
        Assertions.assertFalse(result);
    }
}
