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
    private static final String LOGIN_DEFAULT = "Kate";
    private static final String LOGIN_BRUCE = "Bruce";
    private static final String LOGIN_AMY = "Amy";
    private static final int MINUS_AGE = -5;
    private static final int HUNDRED_AGE = 100;
    private static final int ZERO_AGE = 0;
    private static final int EIGHTEEN_AGE = 18;
    private static final String INVALID_PASSWORD = "H";
    private static final String EMPTY_PASSWORD = "";
    private static final String GOOD_PASSWORD = "123456";
    private static final String GOOD_PASSWORD2 = "parol2";
    private static final String REVERSE_GOOD_PASSWORD = "654321";
    private static final User NULL_USER = new User();
    private static User user2;
    private static User user2Copy;
    private static User user3;
    private User defaultUser;
    private RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        user2 = new User();
        user2.setLogin(LOGIN_BRUCE);
        user2.setAge(HUNDRED_AGE);
        user2.setPassword(REVERSE_GOOD_PASSWORD);
        user3 = new User();
        user3.setLogin(LOGIN_AMY);
        user3.setAge(EIGHTEEN_AGE);
        user3.setPassword(GOOD_PASSWORD2);
        user2Copy = user2;
    }

    @BeforeEach
     void beforeEach() {
        registrationService = new RegistrationServiceImpl();

        defaultUser = new User();
        defaultUser.setLogin(LOGIN_DEFAULT);
        defaultUser.setAge(EIGHTEEN_AGE);
        defaultUser.setPassword(GOOD_PASSWORD);
    }

    @Test
    void addSameLoginUser_NotOk() {
        registrationService.register(defaultUser);
        assertThrows(RuntimeException.class,() -> registrationService.register(defaultUser));
    }

    @Test
    void addUser_NullLogin_NotOk() {
        defaultUser.setLogin(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(defaultUser));
    }

    @Test
    void addFewUsers_Ok() {
        registrationService.register(defaultUser);
        registrationService.register(user2);
        registrationService.register(user3);
        assertThrows(RuntimeException.class,() -> registrationService.register(user2Copy));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void addUser_Younger18_NotOk() {
        defaultUser.setAge(MINUS_AGE);
        assertThrows(RuntimeException.class,() -> registrationService.register(defaultUser));
        defaultUser.setLogin(LOGIN_BRUCE);
        defaultUser.setAge(ZERO_AGE);
        assertThrows(RuntimeException.class,() -> registrationService.register(defaultUser));
    }

    @Test
    void addUser_NullAge_NotOk() {
        defaultUser.setAge(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(defaultUser));
    }

    @Test
    void addUser_InvalidPassword_NotOk() {
        defaultUser.setPassword(INVALID_PASSWORD);
        assertThrows(RuntimeException.class,() -> registrationService.register(defaultUser));
        defaultUser.setLogin(LOGIN_BRUCE);
        defaultUser.setPassword(EMPTY_PASSWORD);
        assertThrows(RuntimeException.class,() -> registrationService.register(defaultUser));
    }

    @Test
    void addUser_NullPassword_NotOk() {
        defaultUser.setPassword(null);
        assertThrows(RuntimeException.class,() -> registrationService.register(defaultUser));
    }

    @Test
    void registerNullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(NULL_USER));
    }

    @AfterEach
    void cleanStorage() {
        Storage.people.clear();
    }
}
