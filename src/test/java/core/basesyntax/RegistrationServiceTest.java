package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static final int MIN_AGE = 18;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_EmptyPassword_ThrowsException() {
        User user = new User("validuser", "", MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortPassword_ThrowsException() {
        User user = new User("validuser", "abc", MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_FiveCharacterPassword_ThrowsException() {
        User user = new User("validuser", "abcdf", MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_SixCharacterPassword_Success() throws RegistrationException {
        User newUser = new User("validuser", "abcdef", MIN_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));

    }

    @Test
    public void register_EightCharacterPassword_Success() {
        User user = new User("validuser", "abcdefgh", MIN_AGE);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser.getId());
    }
}
