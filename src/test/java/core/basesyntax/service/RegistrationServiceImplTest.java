package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.cases.of.exceptions.ExceptionDuringRegistration;
import core.basesyntax.cases.of.exceptions.NullExceptionDuringRegistration;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    public void initialisation() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_ValidUser() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("password123");
        user.setAge(20);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    public void register_NullUser() {
        assertThrows(NullExceptionDuringRegistration.class, ()
                -> registrationService.register(null));
    }

    @Test
    public void register_ShortLogin() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("password123");
        user.setAge(20);
        assertThrows(ExceptionDuringRegistration.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortPassword() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("short");
        user.setAge(20);
        assertThrows(ExceptionDuringRegistration.class, () -> registrationService.register(user));
    }

    @Test
    public void register_UnderageUser() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("password123");
        user.setAge(15);
        assertThrows(ExceptionDuringRegistration.class, () -> registrationService.register(user));
    }
}
