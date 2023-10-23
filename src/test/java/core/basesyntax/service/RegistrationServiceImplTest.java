package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.cases.of.exceptions.ExceptionDuringRegistration;
import core.basesyntax.cases.of.exceptions.NullExceptionDuringRegistration;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String CORRECT_PASSWORD = "password123";
    private static final String CORRECT_LOGIN = "testUser";
    private static final String INCORRECT_LOGIN_PASSWORD = "short";
    private static final int CORRECT_AGE = 20;
    private static final int INCORRECT_AGE = 15;
    private static RegistrationService registrationService;
    private User expected;

    @BeforeEach
    public void initialisation() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        expected = new User();
    }

    @Test
    public void register_ValidUser() {
        expected.setLogin(CORRECT_LOGIN);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(CORRECT_AGE);
        User registeredUser = registrationService.register(expected);
        assertNotNull(registeredUser);
    }

    @Test
    public void register_NullUser() {
        assertThrows(NullExceptionDuringRegistration.class, ()
                -> registrationService.register(null));
    }

    @Test
    public void register_ShortLogin() {
        expected.setLogin(INCORRECT_LOGIN_PASSWORD);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(CORRECT_AGE);
        assertThrows(ExceptionDuringRegistration.class, ()
                -> registrationService.register(expected));
    }

    @Test
    public void register_ShortPassword() {
        expected.setLogin(CORRECT_LOGIN);
        expected.setPassword(INCORRECT_LOGIN_PASSWORD);
        expected.setAge(CORRECT_AGE);
        assertThrows(ExceptionDuringRegistration.class, ()
                -> registrationService.register(expected));
    }

    @Test
    public void register_UnderageUser() {
        expected.setLogin(CORRECT_LOGIN);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(INCORRECT_AGE);
        assertThrows(ExceptionDuringRegistration.class, ()
                -> registrationService.register(expected));
    }
}
