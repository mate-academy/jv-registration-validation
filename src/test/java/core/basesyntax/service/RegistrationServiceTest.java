package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationServiceImpl registrationServiceImp;
    private static final String DEFAULT_LOGIN = "Princess";
    private static final String DEFAULT_PASSWORD = "winter1";
    private static final int DEFAULT_VALID_AGE_FROM = 18;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationServiceImp = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void create() {
        user = new User();
    }

    @Test
    void register_User_ValidData_Ok() {
        User user = preparedValidUser();
        User registerUser = registrationServiceImp.register(user);
        assertSame(user, registerUser);
    }

    @Test
    void register_User_existLogin_notOk() {
        User user = preparedValidUser();
        storageDao.add(user);
        User userSecond = new User();
        userSecond.setLogin(DEFAULT_LOGIN);
        userSecond.setPassword("summer1");
        userSecond.setAge(24);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> registrationServiceImp.register(userSecond));
        assertEquals("Provided username already in use"
                + ".Please create a new login", exception.getMessage());
    }

    @Test
    void register_User_LoginIsNull_notOk() {
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_VALID_AGE_FROM);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> registrationServiceImp.register(user));
        assertEquals("Please enter your login."
                + "Login field should not be null", exception.getMessage());
    }

    @Test
    void register_User_LoginIsEmpty_notOk() {
        user.setLogin("");
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_VALID_AGE_FROM);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> registrationServiceImp.register(user));
        assertEquals("Please enter your login."
                + "Login field should not be empty", exception.getMessage());
    }

    @Test
    void register_User_Password_IsNull_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_VALID_AGE_FROM);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> registrationServiceImp.register(user));
        assertEquals("Please create your password."
                + "Password field should not be null", exception.getMessage());
    }

    @Test
    void register_User_Password_IsEmpty_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword("");
        user.setAge(DEFAULT_VALID_AGE_FROM);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> registrationServiceImp.register(user));
        assertEquals("Please create your password"
                + ".Password field should not be empty", exception.getMessage());
    }

    @Test
    void register_User_Password_IsLess_Requirement_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword("12345");
        user.setAge(DEFAULT_VALID_AGE_FROM);
        Exception exception = assertThrows(InputMismatchException.class,
                () -> registrationServiceImp.register(user));
        assertEquals("Your password must be at least 6 characters long", exception.getMessage());
    }

    @Test
    void register_User_Age_IsNull_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        Exception exception = assertThrows(InvalidParameterException.class,
                () -> registrationServiceImp.register(user));
        assertEquals("The Age field can't be null", exception.getMessage());
    }

    @Test
    void register_User_Age_IsLess_Requirement_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(17);
        Exception exception = assertThrows(InvalidParameterException.class,
                () -> registrationServiceImp.register(user));
        assertEquals("users over the age of "
                + DEFAULT_VALID_AGE_FROM + " can only registered", exception.getMessage());
    }

    @Test
    void register_User_Age_IsMore_Requirement_notOk() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(19);
        User registerUser = registrationServiceImp.register(user);
        assertSame(user, registerUser);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private User preparedValidUser() {
        User userValid = new User();
        userValid.setLogin(DEFAULT_LOGIN);
        userValid.setPassword(DEFAULT_PASSWORD);
        userValid.setAge(DEFAULT_VALID_AGE_FROM);
        return userValid;
    }
}
