package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidInputException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String STANDARD_LOGIN_OK = "Nick";
    private static final String PASSWORD_OK = "adgfaw123";
    private static final long STANDARD_ID_OK = 633444;
    private static final int STANDARD_AGE_OK = 18;
    private static final String SHORT_LOGIN = "Hi";
    private static final String SHORT_PASSWORD = "5";
    private static final int AGE_LESS = 15;
    private static final int AGE_BIGGEST = 165;
    private static final long NEGATIVE_ID = -4324;
    private static final String EMPTY = "";
    private static final String SPECIAL_CHARACTERS = "@#$%";
    private static final String LARGE_LOGIN = "HelloMyNameIsJohnPhilips";
    private static final String LARGE_PASSWORD = "fesgeFEFr32453ft4t3t43534543";
    private static RegistrationService registrationService;
    private static User standartUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        standartUser = userConstructor(STANDARD_LOGIN_OK,
                PASSWORD_OK,
                STANDARD_AGE_OK,
                STANDARD_ID_OK);
    }

    @AfterEach
    void clear() {
        Storage.people.clear();
    }

    @Test
    void register_duplicateUser_notOk() {
        User secondUser = userConstructor(STANDARD_LOGIN_OK,
                PASSWORD_OK,
                STANDARD_AGE_OK,
                STANDARD_ID_OK);
        Storage.people.add(standartUser);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(secondUser),
                "User already exist");
    }

    @Test
    void register_nullUser_notOk() {
        standartUser = null;
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "You can't register null user");
    }

    @Test
    void register_user_specialCharacterLogin_notOk() {
        standartUser.setLogin(SPECIAL_CHARACTERS);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "You can't register login with special characters, use only letters and numbers");
    }

    @Test
    void register_user_NullLogin_notOk() {
        standartUser.setLogin(null);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Username can't be null");
    }

    @Test
    void register_user_NullPassword_notOk() {
        standartUser.setPassword(null);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Password can't be null");
    }

    @Test
    void register_user_ageLess18_notOk() {
        standartUser.setAge(AGE_LESS);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Age can't be less that " + AGE_LESS);
    }

    @Test
    void register_user_ageBiggest_notOk() {
        standartUser.setAge(AGE_BIGGEST);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "The age can't be so great");
    }

    @Test
    void register_user_negativeId_notOk() {
        standartUser.setId(NEGATIVE_ID);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "The id can't be negative");
    }

    @Test
    void register_user_emptyLogin_notOk() {
        standartUser.setLogin(EMPTY);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Login can't be empty");
    }

    @Test
    void register_user_emptyPassword_notOk() {
        standartUser.setPassword(EMPTY);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Login can't be empty");
    }

    @Test
    void equals_nullObject_notOk() {
        assertNotEquals(null, standartUser, "User can't equal null");
    }

    @Test
    void equals_sameUser_ok() {
        assertEquals(standartUser, standartUser, "User should equal same User");
    }

    @Test
    void user_equalHashcode_ok() {
        User secondUser = userConstructor(STANDARD_LOGIN_OK,
                PASSWORD_OK,
                STANDARD_AGE_OK,
                STANDARD_ID_OK);
        assertEquals(standartUser.hashCode(), secondUser.hashCode());
        assertEquals(standartUser, secondUser);
    }

    @Test
    void register_user_largeLogin_notOk() {
        standartUser.setLogin(LARGE_LOGIN);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Login can't be so large");
    }

    @Test
    void register_user_shortLogin_notOk() {
        standartUser.setLogin(SHORT_LOGIN);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Login can't be so short");
    }

    @Test
    void register_user_shortPassword_notOk() {
        standartUser.setLogin(SHORT_PASSWORD);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Password can't be so short");
    }

    @Test
    void register_user_largePassword_notOk() {
        standartUser.setPassword(LARGE_PASSWORD);
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Password can't be so large");
    }

    @Test
    void register_user_sameCredentials_notOk() {
        standartUser.setPassword(standartUser.getLogin());
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Credentials can't be same");
    }

    @Test
    void register_user_lineSeparatorLogin_notOk() {
        standartUser.setLogin(System.lineSeparator());
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(standartUser),
                "Password can't contains space");
    }

    @Test
    void register_user_ok() {
        assertDoesNotThrow(() -> registrationService.register(standartUser),
                "User can't be added");
    }

    private User userConstructor(String login, String password, int age, long setId) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        user.setId(setId);
        return user;
    }
}
