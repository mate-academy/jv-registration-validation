package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.UserRegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_COUNT_OF_CHAR = 6;
    private static final int MIN_AGE = 18;
    private static final String LOGIN_EXIST_USER = "ExistLogin";
    private static final String LOGIN_DEFAULT_USER = "MercedesCar";
    private static final String PASSWORD = "Password";
    private static final int USER_AGE = 25;
    private RegistrationServiceImpl registrationService;
    private User existUser;
    private User defaultUser;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        existUser = new User();
        existUser.setLogin(LOGIN_EXIST_USER);
        existUser.setPassword(PASSWORD);
        existUser.setAge(USER_AGE);
        defaultUser = new User();
        defaultUser.setLogin(LOGIN_DEFAULT_USER);
        defaultUser.setPassword(PASSWORD);
        defaultUser.setAge(USER_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_NotOk() {
        defaultUser.setLogin(null);
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertNull(defaultUser.getLogin());
        assertEquals("Login can't be null", exception.getMessage());
    }

    @Test
    void register_loginIsBlank_NotOk() {
        defaultUser.setLogin("          ");
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertEquals("The login cannot consist of spaces", exception.getMessage());
    }

    @Test
    void register_loginLessThanMinLengthTwoSymbols_NotOk() {
        defaultUser.setLogin("13");
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertEquals("Login shouldn't be less than "
                + MIN_COUNT_OF_CHAR, exception.getMessage());
    }

    @Test
    void register_loginLessThanMinLengthFifthSymbols_NotOk() {
        defaultUser.setLogin("12345");
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertEquals("Login shouldn't be less than "
                + MIN_COUNT_OF_CHAR, exception.getMessage());
    }

    @Test
    void register_loginExistUser_NotOk() {
        Storage.people.add(existUser);
        defaultUser.setLogin(LOGIN_EXIST_USER);
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertEquals("A user already exists with this login", exception.getMessage());
    }

    @Test
    void register_nullPassword_NotOk() {
        defaultUser.setPassword(null);
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertNull(defaultUser.getPassword());
        assertEquals("Password can not be null", exception.getMessage());
    }

    @Test
    void register_passwordIsBlank_NotOk() {
        defaultUser.setPassword("          ");
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertEquals("The password cannot consist of spaces", exception.getMessage());
    }

    @Test
    void register_passwordLessThanMinLengthFifthSymbols_NotOk() {
        defaultUser.setPassword("12345");
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertEquals("Password shouldn't be less than "
                + MIN_COUNT_OF_CHAR, exception.getMessage());
    }

    @Test
    void register_passwordLessThanMinLengthTwoSymbols_NotOk() {
        defaultUser.setPassword("12");
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertEquals("Password shouldn't be less than "
                + MIN_COUNT_OF_CHAR, exception.getMessage());
    }

    @Test
    void register_nullAge_NotOk() {
        defaultUser.setAge(null);
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertNull(defaultUser.getAge());
        assertEquals("Age can not be null", exception.getMessage());
    }

    @Test
    void register_ageLessThanMin_NotOk() {
        defaultUser.setAge(17);
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertEquals("Age shouldn't be less than " + MIN_AGE
                + " ,but you entered: " + defaultUser.getAge(), exception.getMessage());
    }

    @Test
    void register_ageLessThanZero_NotOk() {
        defaultUser.setAge(-5);
        UserRegistrationException exception
                = Assertions.assertThrows(UserRegistrationException.class,
                    () -> registrationService.register(defaultUser));
        assertEquals("Age shouldn't be less than " + MIN_AGE
                + " ,but you entered: " + defaultUser.getAge(), exception.getMessage());
    }

    @Test
    void register_addUserWithValidData_Ok() {
        Storage.people.add(existUser);
        registrationService.register(defaultUser);
        assertTrue(Storage.people.contains(defaultUser));
        assertEquals(2, Storage.people.size());
        assertEquals(LOGIN_DEFAULT_USER, Storage.people.get(1).getLogin());
        assertEquals(PASSWORD, Storage.people.get(1).getPassword());
        assertEquals(USER_AGE, Storage.people.get(1).getAge());
    }
}
