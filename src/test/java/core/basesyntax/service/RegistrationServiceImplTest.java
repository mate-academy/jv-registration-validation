package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeEach
    public void setup() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserValidationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "ValidPassword1!", 25);
        assertThrows(UserValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("ValidLogin", null, 25);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void testValidUserRegistration_ok() {
        User validUser = new User("ValidLogin", "Password1!", 21);
        User returnedUser = registrationService.register(validUser);
        assertEquals(validUser, returnedUser);
    }

    @Test
    public void testRegistrationWithNullUser_notOk() {
        assertThrows(UserValidationException.class, () -> registrationService.register(null));
    }

    @Test
    public void testRegistrationWithExistingLogin_notOk() {
        User newUser = new User("existing_user", "Password1!", 22);
        registrationService.register(newUser);
        newUser.setPassword("Password1!");
        newUser.setAge(30);
        assertThrows(UserValidationException.class, () -> registrationService.register(newUser));
    }

    @Test
    public void testRegistrationWithUnderageUser_notOk() {
        User underageUser = new User("young_user", "Password1!", 17);
        assertThrows(UserValidationException.class,
                () -> registrationService.register(underageUser));
    }

    @Test
    public void testRegistrationWithInvalidLogin_notOk() {
        User userWithShortLogin = new User("abc", "Password1!", 22);
        assertThrows(UserValidationException.class,
                () -> registrationService.register(userWithShortLogin));
    }

    @Test
    public void testValidPassword_ok() {
        User user = new User("validUser1", "Password1!", 25);
        registrationService.register(user);
        User expected = storageDao.get(user.getLogin());
        assertEquals(user, expected);
    }

    @Test
    public void testShortPassword_notOk() {
        User user = new User("validUser", "Pas1!", 25);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void testPasswordWithoutDigit_notOk() {
        User user = new User("validUser", "Password!", 25);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void testPasswordWithoutUppercase_notOk() {
        User user = new User("validUser", "password1!", 25);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void testPasswordWithoutLowercase_notOk() {
        User user = new User("validUser", "PASSWORD1!", 25);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    public void testPasswordWithoutSpecialSymbol_notOk() {
        User user = new User("validUser", "Password1", 25);
        assertThrows(UserPasswordValidationException.class,
                () -> registrationService.register(user));
    }
}
