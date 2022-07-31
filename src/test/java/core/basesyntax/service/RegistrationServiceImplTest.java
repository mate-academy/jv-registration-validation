package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.Provider;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private User DEFAULT_USER;
    private static User USER2;
    private static User USER3;
    public static User INVALID_USER;
    private static final String LOGIN_DEFAULT = "Kate";
    private static final String LOGIN_BRUCE = "Bruce";
    private static final String LOGIN_AMY = "Amy";
    private static final int MINUS_AGE = -5;
    public static final int HUNDRED_AGE = 100;
    private static final int ZERO_AGE = 0;
    private static final int EIGHTEEN_AGE = 18;
    private static final String INVALID_PASSWORD = "H";
    private static final String EMPTY_PASSWORD = "";
    private static final String GOOD_PASSWORD = "123456";
    private static final String GOOD_PASSWORD2 = "parol2";
    public static final String REVERSE_GOOD_PASSWORD = "654321";

    private static final User NULL_USER = new User();

    @BeforeAll
    static void beforeAll() {
        USER2 = new User();
        USER2.setLogin(LOGIN_BRUCE);
        USER2.setAge(HUNDRED_AGE);
        USER2.setPassword(REVERSE_GOOD_PASSWORD);
        INVALID_USER = new User();
        INVALID_USER.setLogin(LOGIN_AMY);
        USER3 = new User();
        USER3.setLogin(LOGIN_AMY);
        USER3.setAge(EIGHTEEN_AGE);
        USER3.setPassword(GOOD_PASSWORD2);

    }

    @BeforeEach
     void beforeEach() {
        DEFAULT_USER = new User();
        DEFAULT_USER.setLogin(LOGIN_DEFAULT);
        DEFAULT_USER.setAge(EIGHTEEN_AGE);
        DEFAULT_USER.setPassword(GOOD_PASSWORD);
    }




    @Test
    void addSameLoginUser_NotOk() {
        registrationService.register(DEFAULT_USER);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
    }

    @Test
    void addUserWithNullLogin_NotOk() {
        DEFAULT_USER.setLogin(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
    }

    @Test
    void addFewUsers_Ok() {
        registrationService.register(DEFAULT_USER);
        registrationService.register(USER2);
        registrationService.register(INVALID_USER);
        registrationService.register(USER3);
        registrationService.register(NULL_USER);
        assertEquals(3, Storage.people.size());
    }

    @Test
    void addUserYounger18_NotOk() {
        DEFAULT_USER.setAge(MINUS_AGE);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
        DEFAULT_USER.setLogin(LOGIN_BRUCE);
        DEFAULT_USER.setAge(ZERO_AGE);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
    }

    @Test
    void addUserWithNullAge_NotOk() {
        DEFAULT_USER.setAge(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
    }

    @Test
    void addInvalidUserPassword_NotOk() {
        DEFAULT_USER.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
        DEFAULT_USER.setLogin(LOGIN_BRUCE);
        DEFAULT_USER.setPassword(EMPTY_PASSWORD);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
    }

    @Test
    void addUserWithNullPassword_NotOk() {
        DEFAULT_USER.setPassword(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(DEFAULT_USER));
    }

    @Test
    void registerNullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(NULL_USER));
    }
}