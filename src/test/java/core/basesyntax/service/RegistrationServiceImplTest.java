package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String EMPTY_STRING = "";
    private static final char SPACE = ' ';
    private static final String DEFAULT_LOGIN = "TestUser";
    private static final String DEFAULT_PASSWORD = "test_Passwrd1";
    private static final int DEFAULT_ACCEPTED_AGE = 18;
    private static final int MAX_AGE = 150;
    private static final int NEGATIVE_AGE = -20;
    private static final String REPEATED_SPACE_LOGIN = "Test  User";
    private static final String SHORT_LOGIN = "A";
    private static final String SHORT_PASSWORD = "test1";
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_ACCEPTED_AGE);
    }

    @Test
    void register_newUser_ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingUserLogin_notOk() {
        User existingUser = new User();
        existingUser.setLogin(DEFAULT_LOGIN);
        existingUser.setAge(DEFAULT_ACCEPTED_AGE + 5);
        existingUser.setPassword("enother_password");
        registrationService.register(existingUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emtyUserName_notOk() {
        user.setLogin(EMPTY_STRING);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_prefixSpaceUserName_notOk() {
        user.setLogin(DEFAULT_LOGIN + SPACE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_postfixSpaceUserName_notOk() {
        user.setLogin(SPACE + DEFAULT_LOGIN);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_repeatedSpacesUserName_notOk() {
        user.setLogin(REPEATED_SPACE_LOGIN);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minLengthUserName_notOk() {
        user.setLogin(SHORT_LOGIN);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_moreThanMaxAge_NotOk() {
        user.setAge(MAX_AGE + 1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emtyPassword_notOk() {
        user.setPassword(EMPTY_STRING);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessThanMinCharsPassword_notOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
