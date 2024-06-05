package core.basesyntax;

import core.basesyntax.exeptions.RegisterServiceException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.*;

public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_invalidUser_NotOk() {
        Assertions.assertThrows(RegisterServiceException.class, () -> {
            registrationService.register(null);
        });
        Assertions.assertThrows(RegisterServiceException.class, () -> {
            registrationService.register(new User(null, null, null, null));
        });
        User invalidUser = new User(1L, "Login", "Hello!", 18);
        Assertions.assertThrows(RegisterServiceException.class, () -> {
            registrationService.register(invalidUser);
        });
    }

    @Test
    void register_UserWithSameLogin_NotOk() throws RegisterServiceException {
        User user1 = new User(1L, "SameLogin123", "HardPassword123!", 18);
        User user2 = new User(2L, "SameLogin123", "SuperPassword$54", 22);
        registrationService.register(user1);
        Assertions.assertThrows(RegisterServiceException.class, () -> {
           registrationService.register(user2);
        });
    }

    @Test
    void register_differentValidUsers_Ok() throws RegisterServiceException {
        User user1 = new User(1L, "Mate123", "HardPassword123!", 18);
        User user2 = new User(2L, "JoeBaiden753", "SuperPassword$54", 22);
        User registeredUser1 = registrationService.register(user1);
        Assertions.assertEquals(registeredUser1, user1);
        User registeredUser2 = registrationService.register(user2);
        Assertions.assertEquals(registeredUser2, user2);
    }

    @AfterEach
    void clearUp() {
        registrationService = null;
    }

}
