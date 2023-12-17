package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl(new StorageDaoImpl());
        Storage.people.clear();
    }

    @Test
    void register_user_ok() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(25);

        User addedUser = registrationService.register(user);

        assertEquals(user, addedUser);
        assertNotNull(addedUser.getId());
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("correct_Password");
        user.setAge(25);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid login - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("correct_Password");
        user.setAge(25);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid login - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("Ola");
        user.setPassword("correct_Password");
        user.setAge(25);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid login - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void register_edgeCaseLogin_notOk() {
        User user = new User();
        user.setLogin("1");
        user.setPassword("correct_Password");
        user.setAge(25);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid login - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void register_maxLengthLogin_ok() {
        User user = new User();
        user.setLogin("abcdefgh");
        user.setPassword("correct_Password");
        user.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_longerThanMaxLengthLogin_ok() {
        User user = new User();
        user.setLogin("abcdefghi");
        user.setPassword("correct_Password");
        user.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("");
        user.setAge(25);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid password - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void register_minLengthPassword_ok() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("abcdef");
        user.setAge(30);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword(null);
        user.setAge(25);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid password - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("Ola");
        user.setAge(25);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid password - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void register_edgeCasePassword_notOk() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("abcde");
        user.setAge(25);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid password - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void register_maxLengthPassword_ok() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("abcdefgh");
        user.setAge(30);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_shortAge_notOk() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(15);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid age - min 18 years old", invalidUserException.getMessage());
    }

    @Test
    void register_edgeCaseAge_notOk() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(17);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid age - min 18 years old", invalidUserException.getMessage());
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(null);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid age - min 18 years old", invalidUserException.getMessage());
    }

    @Test
    void register_zeroAge_notOk() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(0);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("Invalid age - min 18 years old", invalidUserException.getMessage());
    }

    @Test
    void register_minAge_ok() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(18);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_minLengthLoginAndPasswordAndMinAge_ok() {
        User user = new User();
        user.setLogin("abcdef");
        user.setPassword("abcdef");
        user.setAge(18);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_existingUser_notOk() {
        User existingUser = new User();
        existingUser.setLogin("ExcellentLogin");
        existingUser.setPassword("GoodPassword");
        existingUser.setAge(25);

        registrationService.register(existingUser);

        User user = new User();
        user.setLogin("ExcellentLogin");
        user.setPassword("password");
        user.setAge(32);

        RegistrationException invalidUserException = assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
        assertEquals("A user with this login already exists", invalidUserException.getMessage());
    }
}
