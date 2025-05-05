package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exeption.UserInvalidExeption;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    public static final String VALID_LOGIN = "John_Doe";
    public static final String VALID_USERPASSWORD = "strongPassword";
    public static final String SHORT_LOGIN = "Joe";
    public static final String SHORT_PASSWORD = "abcde";
    public static final int VALID_USERAGE = 25;
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;
    private User validUser;

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setAge(VALID_USERAGE);
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_USERPASSWORD);
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_ValidUser_Ok() {
        registrationService.register(validUser);
        assertEquals(validUser, storageDao.get(validUser.getLogin()));
    }

    @Test
    void register_UserWithNullLogin_Not_Ok() {
        validUser.setLogin(null);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        UserInvalidExeption userInvalidExeption = assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        assertEquals("Please enter the login!", userInvalidExeption.getMessage());
    }

    @Test
    void register_UserWithShortLogin_Not_Ok() {
        validUser.setLogin(SHORT_LOGIN);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        UserInvalidExeption userInvalidExeption = assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        assertEquals("Login " + validUser.getLogin() + " must be at least 6 characters!",
                userInvalidExeption.getMessage());
    }

    @Test
    void register_UserWithExistingLogin_Not_ok() {
        User userWithExistingLogin = new User();
        userWithExistingLogin.setAge(38);
        userWithExistingLogin.setPassword(VALID_USERPASSWORD);
        userWithExistingLogin.setLogin(VALID_LOGIN);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(userWithExistingLogin));
        UserInvalidExeption userInvalidExeption = assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(userWithExistingLogin));
        assertEquals("Login " + userWithExistingLogin.getLogin() + " already taken!",
                userInvalidExeption.getMessage());
    }

    @Test
    void register_UnderAgeUser_Not_Ok() {
        validUser.setAge(17);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        UserInvalidExeption userInvalidExeption = assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        assertEquals("Age need to be 18 or over!", userInvalidExeption.getMessage());
    }

    @Test
    void register_NullAgeUser_Not_Ok() {
        validUser.setAge(0);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        UserInvalidExeption userInvalidExeption = assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        assertEquals("Age cant be 0", userInvalidExeption.getMessage());
    }

    @Test
    void register_UserWithShortPassword_Not_Ok() {
        validUser.setPassword(SHORT_PASSWORD);
        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        UserInvalidExeption userInvalidExeption = assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        assertEquals("Password must be at least 6 characters!", userInvalidExeption.getMessage());
    }

    @Test
    void register_UserWithEmptyPassword_Not_Ok() {
        validUser.setPassword("");

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        UserInvalidExeption userInvalidExeption = assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        assertEquals("Please enter password!", userInvalidExeption.getMessage());
    }

    @Test
    void register_UserWithNullPassword_Not_Ok() {
        validUser.setPassword(null);

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        UserInvalidExeption userInvalidExeption = assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        assertEquals("Please enter password!", userInvalidExeption.getMessage());
    }

    @Test
    void register_NullUser_Not_Ok() {
        validUser = null;

        assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        UserInvalidExeption userInvalidExeption = assertThrows(UserInvalidExeption.class, () ->
                registrationService.register(validUser));
        assertEquals("User cant be null!", userInvalidExeption.getMessage());
    }
}
