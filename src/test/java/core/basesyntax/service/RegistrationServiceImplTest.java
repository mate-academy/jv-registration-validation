package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User userIsNull;
    private User userLoginIsNull;
    private User userLoginIs5Char;
    private User userLoginIs6Char;
    private User userLoginIs7Char;
    private User userPassIsNull;
    private User userPassIs5Char;
    private User userPassIs6Char;
    private User userPassIs7Char;
    private User userAgeIsNull;
    private User userAgeIs17;
    private User userAgeIs18;
    private User userAgeIs19;
    private User userOk1;
    private User userOk2;
    private User userOk3;
    private User userOk4;
    private User userOk5;
    private User userOk6;
    private User theSameUserOk1;
    private User theSameUserOk3;
    private String user1;
    private String user2;
    private String userNotExist;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        userIsNull = null;
        userLoginIsNull = new User(0L,null,"password",19);
        userLoginIs5Char = new User(0L,"login","password",19);
        userLoginIs6Char = new User(0L,"login6","password",19);
        userLoginIs7Char = new User(0L,"login77","password",19);
        userPassIsNull = new User(0L,"login1",null,19);
        userPassIs5Char = new User(0L,"login2","passw",19);
        userPassIs6Char = new User(0L,"login3","passwo",19);
        userPassIs7Char = new User(0L,"login4","passwor",19);
        userAgeIsNull = new User(0L,"login5","password",null);
        userAgeIs17 = new User(0L,"login6","password",17);
        userAgeIs18 = new User(0L,"login7","password",18);
        userAgeIs19 = new User(0L,"login8","password",19);
        userOk1 = new User(0L,"login9","password1",18);
        userOk2 = new User(0L,"login10","password2",19);
        userOk3 = new User(0L,"login11","password3",20);
        userOk4 = new User(0L,"login12","password1",21);
        userOk5 = new User(0L,"login13","password2",22);
        userOk6 = new User(0L,"login14","password3",23);
        theSameUserOk1 = new User(0L,"login9","password1",18);
        theSameUserOk3 = new User(0L,"login11","password3",20);
        user1 = "login12";
        user2 = "login14";
        userNotExist = "login12345678";
    }

    @Test
    void user_Null_Not_OK() {
        assertThrows(ValidationException.class, () -> registrationService.register(userIsNull));
    }

    @Test
    void login_Null_Not_OK() {
        assertThrows(ValidationException.class,
                () -> registrationService.register(userLoginIsNull));
    }

    @Test
    void login_Less_6_Char_Not_OK() {
        assertThrows(ValidationException.class,
                () -> registrationService.register(userLoginIs5Char));
    }

    @Test
    void login_6_Char_Is_OK() throws ValidationException {
        assertEquals(userLoginIs6Char, registrationService.register((userLoginIs6Char)),
                "Current USER must be returned");
    }

    @Test
    void login_More_6_Char_Is_OK() throws ValidationException {
        assertEquals(userLoginIs7Char, registrationService.register((userLoginIs7Char)),
                "Current USER must be returned");
    }

    @Test
    void password_Null_Not_OK() {
        assertThrows(ValidationException.class,
                () -> registrationService.register(userPassIsNull));
    }

    @Test
    void password_Less_6_Char_Not_OK() {
        assertThrows(ValidationException.class,
                () -> registrationService.register(userPassIs5Char));
    }

    @Test
    void password_6_Char_Is_OK() throws ValidationException {
        assertEquals(userPassIs6Char, registrationService.register((userPassIs6Char)),
                "Current USER must be returned");
    }

    @Test
    void password_More_6_Char_Is_OK() throws ValidationException {
        assertEquals(userPassIs7Char, registrationService.register((userPassIs7Char)),
                "Current USER must be returned");
    }

    @Test
    void age_Null_Not_OK() {
        assertThrows(ValidationException.class,
                () -> registrationService.register(userAgeIsNull));
    }

    @Test
    void age_Less_18_Not_OK() {
        assertThrows(ValidationException.class,
                () -> registrationService.register(userAgeIs17));
    }

    @Test
    void age_18_Is_OK() throws ValidationException {
        assertEquals(userAgeIs18, registrationService.register((userAgeIs18)),
                "Current USER must be returned");
    }

    @Test
    void age_More_18_Is_OK() throws ValidationException {
        assertEquals(userAgeIs19, registrationService.register((userAgeIs19)),
                "Current USER must be returned");
    }

    @Test
    void same_Login_Not_OK() throws ValidationException {
        Storage.people.add(userOk1);
        Storage.people.add(userOk2);
        Storage.people.add(userOk3);
        assertThrows(ValidationException.class,
                () -> registrationService.register(theSameUserOk1));
        assertThrows(ValidationException.class,
                () -> registrationService.register(theSameUserOk3));
    }

    @Test
    void get_User_With_Null_Login_Not_OK() {
        assertThrows(ValidationException.class,
                () -> registrationService.getUser(null));
    }
    
    @Test
    void get_Not_Existed_Login_Not_OK() {
        assertThrows(ValidationException.class,
                () -> registrationService.getUser(userNotExist));
    }

    @Test
    void get_User_By_Login_OK() throws ValidationException {
        Storage.people.add(userOk4);
        Storage.people.add(userOk5);
        Storage.people.add(userOk6);
        assertEquals(userOk4, registrationService.getUser(user1), "User must be returned");
        assertEquals(userOk6, registrationService.getUser(user2), "User must be returned");
    }
}
