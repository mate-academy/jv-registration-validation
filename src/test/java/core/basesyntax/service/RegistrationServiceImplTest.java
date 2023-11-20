package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void shouldRegisterUser() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(25);

        User addedUser = storageDao.add(user);

        assertEquals(user, addedUser);
        assertNotNull(addedUser.getId());
    }

    @Test
    void shouldThrowExceptionForEmptyLogin() {
        User user = new User();
        user.setLogin("");
        user.setPassword("correct_Password");
        user.setAge(25);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid login - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullLogin() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("correct_Password");
        user.setAge(25);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid login - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForShortLogin() {
        User user = new User();
        user.setLogin("Ola");
        user.setPassword("correct_Password");
        user.setAge(25);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid login - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExpcetionForEdgeCaseLogin() {
        User user = new User();
        user.setLogin("1");
        user.setPassword("correct_Password");
        user.setAge(25);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid login - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void shouldRegisterUserWithMinLengthLogin() {
        User user = new User();
        user.setLogin("abcdef");
        user.setPassword("correct_Password");
        user.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void shouldRegisterUserWithMaxLengthLogin() {
        User user = new User();
        user.setLogin("abcdefgh");
        user.setPassword("correct_Password");
        user.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void shouldRegisterUserWithLongerThanMaxLengthLogin() {
        User user = new User();
        user.setLogin("abcdefghi");
        user.setPassword("correct_Password");
        user.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void shouldThrowExceptionForEmptyPassword() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("");
        user.setAge(25);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid password - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullPassword() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword(null);
        user.setAge(25);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid password - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForShortPassword() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("Ola");
        user.setAge(25);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid password - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForEdgeCasePassword() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("abcde");
        user.setAge(25);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid password - min 6 characters", invalidUserException.getMessage());
    }

    @Test
    void shouldRegisterUserWithMinLengthPassword() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("abcdef");
        user.setAge(30);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void shouldRegisterUserWithMaxLengthPassword() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("abcdefgh");
        user.setAge(30);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void shouldRegisterUserWithLongerThanMaxLengthPassword() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("abcdefghi");
        user.setAge(25);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void shouldThrowExceptionForShortAge() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(15);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid age - min 18 years old", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForEdgeCaseAge() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(17);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid age - min 18 years old", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullAge() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(null);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid age - min 18 years old", invalidUserException.getMessage());
    }

    @Test
    void shouldThrowExceptionForZeroAge() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(0);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals("Invalid age - min 18 years old", invalidUserException.getMessage());
    }

    @Test
    void shouldRegisterUserWithMinAge() {
        User user = new User();
        user.setLogin("correct_Login");
        user.setPassword("correct_Password");
        user.setAge(18);


        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void shouldRegisterUserWithMinLengthLoginAndPasswordAndMinAge() {
        User user = new User();
        user.setLogin("abcdef");
        user.setPassword("abcdef");
        user.setAge(18);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void shouldThrowExceptionForExistingUser() {
        User existingUser = new User();
        existingUser.setLogin("ExcellentLogin");
        existingUser.setPassword("GoodPassword");
        existingUser.setAge(25);


        storageDao.add(existingUser);

        User user = new User();
        user.setLogin("ExcellentLogin");
        user.setPassword("password");
        user.setAge(32);

        InvalidUserException invalidUserException = assertThrows(InvalidUserException.class, () -> storageDao.add(user));
        assertEquals("A user with this login already exists", invalidUserException.getMessage());
    }
}
