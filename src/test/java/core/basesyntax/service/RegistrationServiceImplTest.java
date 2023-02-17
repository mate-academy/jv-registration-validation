package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    private static User user;
    private int expectedSize;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        expectedSize = 0;
        user = user.createUser(750_001L, DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_ADULT_AGE);
    }

    @Test
    void registering_MultipleValidUsers_OK() {
        User[] users = new User[3];
        users[0] = user.createUser(750_001L, "User1", DEFAULT_PASSWORD, DEFAULT_ADULT_AGE);
        users[1] = user.createUser(750_002L, "User2", VALID_PASSWORD, DEFAULT_ELDERLY_AGE);
        users[2] = user.createUser(750_003L, "User3", DEFAULT_PASSWORD, DEFAULT_VALID_AGE);

        for (User user :users) {
            long oldId = user.getId();
            User actual = registrationService.register(user);
            assertNotNull(actual, "Returned User cannot be null");
            assertEquals(actual, user, "Registration method "
                    + "must return registered User");
            assertNotEquals(oldId, user.getId(), "User Id method "
                    + "should be changed due to registration");
            expectedSize++;
        }

        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void registering_nullAgeUser_notOK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user),
                "There must RuntimeException throw "
                        + "when NULL AGE field in User registration");
        assertEquals(expectedSize, Storage.people.size(),
                "Storage size not changed properly");
    }

    @Test
    void registering_underValidAgeUser_notOK() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw "
                        + "when UNDER VALID AGE field in User registration");
        assertEquals(expectedSize, Storage.people.size(),
                "Storage size not changed properly");
    }

    @Test
    void registering_negativeAgeUser_notOK() {
        user.setAge(-23);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw "
                        + "when NEGATIVE AGE field in User registration");
        assertEquals(expectedSize, Storage.people.size(),
                "Storage, size not changed properly");
    }

    @Test
    void registering_existentUser_notOK() {
        long oldId = user.getId();
        User actual = registrationService.register(user);
        assertNotNull(actual,"Returned User cannot be null");
        assertEquals(actual, user, "Registration method "
                + "must return registered User");
        assertNotEquals(oldId, user.getId(), "User Id must should be changed "
                + "due to registration");
        expectedSize++;

        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw "
                        + "when try REGISTER SAME User");
        assertEquals(expectedSize, Storage.people.size(),
                "Storage size not changed");
    }

    @Test
    void registering_existentLoginUser_notOK() {
        User[] users = new User[2];
        users[0] = user.createUser(750_001L, DEFAULT_LOGIN, "password1", DEFAULT_ADULT_AGE);
        users[1] = user.createUser(750_002L, DEFAULT_LOGIN, "password2", DEFAULT_ADULT_AGE);

        long oldId = users[0].getId();
        User actual = registrationService.register(users[0]);
        assertNotNull(actual, "Returned User cannot be null");
        assertEquals(actual, users[0], "Registration method "
                + "must return registered User");
        assertNotEquals(oldId, users[0].getId(), "User Id must should be changed "
                + "due to registration");
        expectedSize++;

        assertThrows(RuntimeException.class, () -> registrationService.register(users[1]),
                "There must RuntimeException throw "
                        + "when try register User with SAME LOGIN");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void registering_emptyLoginUser_notOK() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw "
                        + "when try register User with EMPTY LOGIN");
        assertEquals(expectedSize, Storage.people.size(),
                "Storage size not changed properly");
    }

    @Test
    void registering_nullFieldUser_notOK() {
        user = new User();
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw when "
                        + "NULL ALL fields User registration");
        assertEquals(expectedSize, Storage.people.size(),
                "Storage size not changed properly");
    }

    @Test
    void registering_nullLoginUser_notOK() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw when "
                        + "NULL LOGIN field in User registration");
        assertEquals(expectedSize, Storage.people.size(),
                "Storage size not changed properly");
    }

    @Test
    void registering_nullPasswordUser_notOK() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw when "
                        + "NULL PASSWORD field in User registration");
        assertEquals(expectedSize, Storage.people.size(),
                "Storage size not changed properly");
    }

    @Test
    void registering_emptyPasswordUser_notOK() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw "
                        + "when EMPTY PASSWORD field in User registration");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void registering_shortPasswordUser_notOK() {
        user.setPassword("abc123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "There must RuntimeException throw "
                        + "when PASSWORD LESS THEN NINE symbols");
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @Test
    void registering_testingCorrectID_OK() {
        for (int i = 0; i < 10; i++) {
            user = user.createUser(750_001L * i, DEFAULT_LOGIN + i,
                    DEFAULT_PASSWORD, DEFAULT_VALID_AGE + i);
            User actual = registrationService.register(user);
            assertNotNull(actual, "Returned User cannot be null");
            assertEquals(actual, user, "Registration method "
                    + "must return registered User");
            expectedSize++;
        }

        long perviousUserId = Storage.people.get(0).getId();
        for (int i = 1; i < 10; i++) {
            assertEquals(perviousUserId + 1, Storage.people.get(i).getId());
            perviousUserId = Storage.people.get(i).getId();
        }
        assertEquals(expectedSize, Storage.people.size(), "Storage size not changed properly");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
