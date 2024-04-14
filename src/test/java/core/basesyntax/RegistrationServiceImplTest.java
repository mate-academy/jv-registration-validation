package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @Test
    public void register_validUser_successfulRegistration_OK() {
        user.setLogin("testUser");
        user.setPassword("testPassword");
        user.setAge(20);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    public void register_minimumValidAge_Ok() {
        user.setLogin("testUserMinAge");
        user.setPassword("testPassword");
        user.setAge(18);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Registration using minimum valid age should be successful");
    }

    @Test
    public void register_maximumValidAge_OK() {
        user.setLogin("testUserMaxAge");
        user.setPassword("testPassword");
        user.setAge(120);
        assertDoesNotThrow(() -> registrationService.register(user),
                "Registration using maximum valid age should be successful");
    }

    @Test
    public void register_invalidAge_notOk() {
        user.setLogin("testUserInvalidAge");
        user.setPassword("testPassword");
        user.setAge(15);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Registration with age below minimum should throw InvalidUserDataException");
    }

    @Test
    public void register_shortLogin_notOk() {
        user.setLogin("short");
        user.setPassword("testPassword");
        user.setAge(18);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Registration with short login should throw InvalidUserDataException");
    }

    @Test
    public void register_shortPassword_notOk() {
        user.setLogin("testUserShortPassword");
        user.setPassword("short");
        user.setAge(20);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Registration with short password should throw InvalidUserDataException");
    }

    @Test
    public void register_duplicateUser_notOk() {
        User user1 = new User();
        user1.setLogin("testUser1");
        user1.setPassword("testPassword");
        user1.setAge(20);
        registrationService.register(user1);

        user.setLogin("testUser1");
        user.setPassword("anotherPassword");
        user.setAge(25);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Registration of user with same login should throw InvalidUserDataException");
    }

    @Test
    public void register_emptyLogin_notOk() {
        user.setLogin("");
        user.setPassword("testPassword");
        user.setAge(20);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Registration using empty login should throw InvalidUserDataException");
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        user.setPassword("testPassword");
        user.setAge(20);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Registration using null login should throw InvalidUserDataException");
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setLogin("testUser");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Registration using null password should throw InvalidUserDataException");
    }

    @Test
    public void register_nullAge_notOk() {
        user.setLogin("testUserNullAge");
        user.setPassword("testPassword");
        user.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Registration using null age should throw InvalidUserDataException");
    }

    @Test
    public void register_allFieldsEmpty_notOk() {
        user.setLogin("");
        user.setPassword("");
        user.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Registration using all fields empty should throw InvalidUserDataException");
    }

    @Test
    public void register_negativeAge_notOK() {
        user.setLogin("testUserNegativeAge");
        user.setPassword("testPassword");
        user.setAge(-10);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Registration using negative age should throw InvalidUserDataException");
    }
}
