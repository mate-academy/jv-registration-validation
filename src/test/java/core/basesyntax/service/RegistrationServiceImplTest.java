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
    private static final int YOUNG_AGE = 12;
    private static final int LOWEST_VALID_AGE = 18;
    private static final int ADULT_AGE = 32;
    private static final int NEGATIVE_AGE = -1;
    private static final String LOWEST_VALID_PASS = "qw21re";
    private static final String EMPTY_PASS = "";
    private static final String NOT_VALID_PASS = "2we3a";
    private static User validUser1;
    private static User validUser2;
    private static User validUser3;
    private static User nullUser;
    private static RegistrationService registrationService;
    private int expectingSize;

    @BeforeAll
    static void beforeAll() {
        validUser1 = new User();
        validUser2 = new User();
        validUser3 = new User();
        nullUser = new User();
    }

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        validUser1.setId(101L);
        validUser1.setLogin("ValidUser1");
        validUser1.setPassword("q1w2e3r4");
        validUser1.setAge(ADULT_AGE);

        validUser2.setId(102L);
        validUser2.setLogin("ValidUser2");
        validUser2.setPassword(LOWEST_VALID_PASS);
        validUser2.setAge(ADULT_AGE + ADULT_AGE);

        validUser3.setId(103L);
        validUser3.setLogin("ValidUser3");
        validUser3.setPassword("q1w2e3r4");
        validUser3.setAge(LOWEST_VALID_AGE);

        expectingSize = 0;
    }

    @Test
    void register_newValidUsers_Ok() {
        User actual;
        long oldId;
        oldId = validUser1.getId();
        actual = registrationService.register(validUser1);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, validUser1, "Registration method should return registered "
                + "User Object");
        assertNotEquals(oldId, validUser1.getId(), "User Id must should be changed "
                + "due to registration");
        expectingSize++;

        oldId = validUser2.getId();
        actual = registrationService.register(validUser2);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, validUser2, "Registration method should return registered "
                + "User Object");
        assertNotEquals(oldId, validUser2.getId(), "User Id must should be changed "
                + "due to registration");
        expectingSize++;

        oldId = validUser3.getId();
        actual = registrationService.register(validUser3);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, validUser3, "Registration method should return registered "
                + "User Object");
        assertNotEquals(oldId, validUser3.getId(), "User Id must should be changed "
                + "due to registration");
        expectingSize++;
    }

    @Test
    void register_nullAge_notOk() {
        validUser1.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser1));
    }

    @Test
    void register_lowAge_notOk() {
        validUser1.setAge(YOUNG_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser1));
    }

    @Test
    void register_negativeAge_notOk() {
        validUser1.setAge(NEGATIVE_AGE);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser1));
    }

    @Test
    void register_sameUser_notOk() {
        User actual;
        long oldId;
        oldId = validUser1.getId();
        actual = registrationService.register(validUser1);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, validUser1, "Registration method should return "
                + "registered User Object");
        assertNotEquals(oldId, validUser1.getId(), "User Id must should be changed "
                + "due to registration");
        expectingSize++;

        assertThrows(RuntimeException.class, () -> registrationService.register(validUser1),
                "There must RuntimeException throw when try register same User");
    }

    @Test
    void register_sameLoginUser_notOk() {
        User actual;
        long oldId;
        oldId = validUser1.getId();
        actual = registrationService.register(validUser1);
        assertNotNull(actual, "Returned Object must be not null");
        assertEquals(actual, validUser1, "Registration method should return "
                + "registered User Object");
        assertNotEquals(oldId, validUser1.getId(), "User Id must should be changed "
                + "due to registration");
        expectingSize++;

        validUser2.setLogin(validUser1.getLogin());
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser2),
                "There must RuntimeException throw when try register User with same login");
    }

    @Test
    void register_nullFieldsUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(nullUser),
                "There must RuntimeException throw when null ALL fields User registration");

        validUser1.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser1),
                "There must RuntimeException throw when null AGE field in User registration");

        validUser2.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser2),
                "There must RuntimeException throw when null LOGIN field in User registration");

        validUser3.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser3),
                "There must RuntimeException throw when null PASSWORD field in User registration");
    }

    @Test
    void register_emptyPassword_NotOk() {
        validUser1.setPassword(EMPTY_PASS);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser1),
                "There must RuntimeException throw when EMPTY PASSWORD field in "
                        + "User registration");
    }

    @Test
    void register_lowPassword_NotOk() {
        validUser1.setPassword(NOT_VALID_PASS);
        assertThrows(RuntimeException.class, () -> registrationService.register(validUser1),
                "There must RuntimeException throw when PASSWORD less then six symbols");
    }

    @AfterEach
    void tearDown() {
        assertEquals(expectingSize, Storage.people.size(), "Storage size not changed properly");

        Storage.people.clear();
    }
}
