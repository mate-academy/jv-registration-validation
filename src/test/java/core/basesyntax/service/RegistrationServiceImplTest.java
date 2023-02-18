package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "login";
    private static final String DEFAULT_PASSWORD = "password123$*#";
    private static final String VALID_PASSWORD = "abc123$*#";
    private static final int DEFAULT_VALID_AGE = 18;
    private static final int DEFAULT_ADULT_AGE = 35;
    private static final int DEFAULT_ELDERLY_AGE = 70;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setId(750_001L);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
        user.setAge(DEFAULT_ADULT_AGE);
    }

    @Test
    void register_MultipleValid_OK() {
        User[] users = new User[3];
        users[0] = createUser(750_001L, "User1", DEFAULT_PASSWORD, DEFAULT_ADULT_AGE);
        users[1] = createUser(750_002L, "User2", VALID_PASSWORD, DEFAULT_ELDERLY_AGE);
        users[2] = createUser(750_003L, "User3", DEFAULT_PASSWORD, DEFAULT_VALID_AGE);

        for (User user :users) {
            storageDao.add(user);
        }
    }

    @Test
    void register_nullAge_notOK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user),
                "There must RuntimeException throw "
                        + "when NULL AGE field in User registration");
    }

    @Test
    void register_underValidAge_notOK() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(user),
                "There must RuntimeException throw "
                        + "when UNDER VALID AGE field in User registration");
    }

    @Test
    void register_negativeAge_notOK() {
        user.setAge(-23);
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(user),
                "There must RuntimeException throw "
                        + "when NEGATIVE AGE field in User registration");
    }

    @Test
    void register_existentUser_notOK() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(user),
                "There must RuntimeException throw "
                        + "when try REGISTER SAME User");
    }

    @Test
    void register_existentLogin_notOK() {
        User user1 = createUser(750_001L, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_ADULT_AGE);
        registrationService.register(user1);
        User user2 = createUser(750_002L, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_ADULT_AGE);
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(user2),
                "There must RuntimeException throw "
                        + "when try register User with SAME LOGIN");
    }

    @Test
    void register_emptyLogin_notOK() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(user),
                "There must RuntimeException throw "
                        + "when try register User with EMPTY LOGIN");
    }

    @Test
    void register_nullField_notOK() {
        user = new User();
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(user),
                "There must RuntimeException throw when "
                        + "NULL ALL fields User registration");
    }

    @Test
    void register_nullLogin_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(user),
                "There must RuntimeException throw when "
                        + "NULL LOGIN field in User registration");
    }

    @Test
    void register_nullPassword_notOK() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(user),
                "There must RuntimeException throw when "
                        + "NULL PASSWORD field in User registration");
    }

    @Test
    void register_emptyPassword_notOK() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(user),
                "There must RuntimeException throw "
                        + "when EMPTY PASSWORD field in User registration");
    }

    @Test
    void register_shortPassword_notOK() {
        user.setPassword("abc123");
        assertThrows(RuntimeException.class, () ->
                        registrationService.register(user),
                "There must RuntimeException throw "
                        + "when PASSWORD LESS THEN NINE symbols");
    }

    @Test
    void register_testingCorrectID_OK() {
        for (int i = 0; i < 10; i++) {
            user = createUser(750_001L * i, DEFAULT_LOGIN + i,
                    DEFAULT_PASSWORD, DEFAULT_VALID_AGE + i);
            storageDao.add(user);
        }

        long previousUserId = Storage.people.get(0).getId();
        for (int i = 1; i < 10; i++) {
            assertEquals(previousUserId + 1, Storage.people.get(i).getId());
            previousUserId = Storage.people.get(i).getId();
        }
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    private User createUser(long id, String login, String password, int age) {
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
