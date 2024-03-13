package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationExceprtion;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static User Andy = new User();
    private static User Eva = new User();
    private static User Julia = new User();
    private static User Twin = new User();
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeAll
    public static void setUp() {
        Andy.setLogin("Andy");
        Andy.setAge(23);
        Andy.setPassword("123456");
        Twin.setLogin("Andy");
        Twin.setAge(43);
        Twin.setPassword("654321");
        Julia.setLogin("Julia");
        Julia.setAge(19);
        Julia.setPassword("12345");
        Eva.setLogin("Eva");
        Eva.setAge(17);
        Eva.setPassword("eva23456");
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_NullUser_ThrowsIllegalArgumentException() {
        User user = null;
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(user));
    }

    @Test
    void register_SuccessfulRegistration_ReturnsUserObject() {
        User registeredUser = registrationService.register(Andy);
        assertNotNull(registeredUser);
    }

    @Test
    void register_SuccessfulRegistration_UserStoredInDatabase() {
        User user = new User();
        user.setLogin("NewUser");
        user.setAge(25);
        user.setPassword("password123");

        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_UserAlreadyExists_ThrowsUserAlreadyExistsException() {
        User user1 = new User();
        user1.setLogin("NewUser");
        user1.setAge(25);
        user1.setPassword("123456");

        User user2 = new User();
        user2.setLogin("NewUser");
        user2.setAge(45);
        user2.setPassword("password123");
        registrationService.register(user1);
        assertThrows(RegistrationExceprtion.class, () -> registrationService.register(user2));
    }

    @Test
    void register_MinimumAgeUser_ThrowsIllegalArgumentException() {
        User user = new User();
        user.setLogin("NewUser");
        user.setAge(18);
        user.setPassword("123456");

        assertThrows(IllegalArgumentException.class, () -> registrationService.register(Eva));
    }

    @Test
    void register_UserAgeBelowMinimum_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(Eva));
    }

    @Test
    void register_NullPassword_ThrowsPasswordLengthException() {
        Andy.setPassword(null);

        assertThrows(RegistrationExceprtion.class, () -> registrationService.register(Andy));
    }

    @Test
    void register_UserPasswordLengthBelowMinimum_ThrowsPasswordLengthException() {
        assertThrows(RegistrationExceprtion.class, () -> registrationService.register(Julia));
    }

    @Test
    void register_MinimumUserPasswordLength_Ok() {
        User user = new User();
        user.setLogin("UserMinPasswrd");
        user.setAge(24);
        user.setPassword("123456");

        assertEquals(user, registrationService.register(user));
    }
}
