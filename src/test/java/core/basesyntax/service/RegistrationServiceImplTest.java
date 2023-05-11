package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.MyValidatorException;
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
    void login_Null_NotOk() {
        defaultUser.setLogin(null);
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertNull(defaultUser.getLogin());
        Assertions.assertEquals("Login can't be null", exception.getMessage());
    }

    @Test
    void login_IsBlank_NotOk() {
        defaultUser.setLogin("          ");
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertEquals("The login cannot consist of spaces", exception.getMessage());
    }

    @Test
    void login_LessThanMin_length_2_NotOk() {
        defaultUser.setLogin("13");
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertEquals("Login can't fewer less symbols than "
                + MIN_COUNT_OF_CHAR, exception.getMessage());
    }

    @Test
    void login_LessThanMin_length_5_NotOk() {
        defaultUser.setLogin("12345");
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertEquals("Login can't fewer less symbols than "
                + MIN_COUNT_OF_CHAR, exception.getMessage());
    }

    @Test
    void login_existUser_NotOk() {
        Storage.people.add(existUser);
        defaultUser.setLogin(LOGIN_EXIST_USER);
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));

        Assertions.assertEquals("A user already exists with this login", exception.getMessage());
    }

    @Test
    void password_Null_NotOk() {
        defaultUser.setPassword(null);
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertNull(defaultUser.getPassword());
        Assertions.assertEquals("Password can not be null", exception.getMessage());
    }

    @Test
    void password_IsBlank_NotOk() {
        defaultUser.setPassword("          ");
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertEquals("The password cannot consist of spaces", exception.getMessage());
    }

    @Test
    void password_LessThanMin_length_5_NotOk() {
        defaultUser.setPassword("12345");
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertEquals("Password can't fewer less symbol than "
                + MIN_COUNT_OF_CHAR, exception.getMessage());
    }

    @Test
    void password_LessThanMin_length_2_NotOk() {
        defaultUser.setPassword("12");
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertEquals("Password can't fewer less symbol than "
                + MIN_COUNT_OF_CHAR, exception.getMessage());
    }

    @Test
    void age_Null_NotOk() {
        defaultUser.setAge(null);
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertNull(defaultUser.getAge());
        Assertions.assertEquals("Age can not be null", exception.getMessage());
    }

    @Test
    void age_LessThanMin_NotOk() {
        defaultUser.setAge(17);
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertEquals("Age cannot be less than " + MIN_AGE
                + " ,but you entered: " + defaultUser.getAge(), exception.getMessage());
    }

    @Test
    void age_LessThanZero_NotOk() {
        defaultUser.setAge(-5);
        MyValidatorException exception = Assertions.assertThrows(MyValidatorException.class,
                () -> registrationService.register(defaultUser));
        Assertions.assertEquals("Age cannot be less than " + MIN_AGE
                + " ,but you entered: " + defaultUser.getAge(), exception.getMessage());
    }

    @Test
    void add_User_WithValidData_Ok() {
        Storage.people.add(existUser);
        registrationService.register(defaultUser);
        Assertions.assertTrue(Storage.people.contains(defaultUser));
        Assertions.assertEquals(2, Storage.people.size());
        Assertions.assertEquals(LOGIN_DEFAULT_USER, Storage.people.get(1).getLogin());
        Assertions.assertEquals(PASSWORD, Storage.people.get(1).getPassword());
        Assertions.assertEquals(USER_AGE, Storage.people.get(1).getAge());
    }
}
