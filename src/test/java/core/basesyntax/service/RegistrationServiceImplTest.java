package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String USER_ALREADY_EXIST =
            "User already exist with login - %s ";
    private static final String USER_LOGIN_EXCEPTION =
            "User login must be at least 6 characters current login length - %s ";
    private static final String USER_PASSWORD_EXCEPTION =
            "User password must be at least 6 characters current password length - %s ";
    private static final String USER_AGE_EXCEPTION =
            "User age must be at least 18 current age - %s ";
    private static final int NUM_OF_ARRAY_ELEMENTS = 10;
    private static final int FIRST_ID = 6;
    private static final int FIRST_AGE = 23;

    private static RegistrationServiceImpl underTest;
    private static User userCorrect;
    private static User userExist;
    private static User userWrongLogin;
    private static User userWrongPassword;
    private static User userWrongAge;

    @BeforeAll
    static void setUp() {
        underTest = new RegistrationServiceImpl();
        userCorrect = new User(1L, "login1", "password1", 18);
        userExist = new User(1L, "login1", "password1", 18);
        userWrongLogin = new User(3L, "log", "password3", 20);
        userWrongPassword = new User(4L, "login4", "pass", 21);
        userWrongAge = new User(5L, "login5", "password5", 2);
        preparedStorage();
    }

    @Test
    void registerSuccess() {
        User actual = underTest.register(userCorrect);
        Assertions.assertEquals(userCorrect, actual);
    }

    @Test
    void registerUserExistException() {
        Exception exception = Assertions.assertThrows(ValidationException.class,
                () -> underTest.register(userExist));
        String expectedMessage = String.format(
                USER_ALREADY_EXIST, userExist.getLogin());
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void registerUserLoginException() {
        Exception exception = Assertions.assertThrows(ValidationException.class,
                () -> underTest.register(userWrongLogin));
        String expectedMessage = String.format(
                USER_LOGIN_EXCEPTION, userWrongLogin.getLogin().length());
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void registerUserPasswordException() {
        Exception exception = Assertions.assertThrows(ValidationException.class,
                () -> underTest.register(userWrongPassword));
        String expectedMessage = String.format(
                USER_PASSWORD_EXCEPTION, userWrongPassword.getPassword().length());
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void registerUserAgeException() {
        Exception exception = Assertions.assertThrows(ValidationException.class,
                () -> underTest.register(userWrongAge));
        String expectedMessage = String.format(
                USER_AGE_EXCEPTION, userWrongAge.getAge());
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    private static void preparedStorage() {
        StorageDaoImpl storageDao = new StorageDaoImpl();
        for (int i = 0; i < NUM_OF_ARRAY_ELEMENTS; i++) {
            long id = FIRST_ID + i;
            String login = "login" + id;
            String password = "password" + id;
            int age = (int) (FIRST_AGE + id);
            storageDao.add(new User(id, login, password, age));
        }
    }
}
