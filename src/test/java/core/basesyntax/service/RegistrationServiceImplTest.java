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
    public static final int VALID_AGE = 18;
    public static final int LOW_AGE = 17;
    public static final int HIGH_AGE = 123;
    public static final int NEGATIVE_AGE = -17;
    public static final String VALID_PASSWORD = "MatriX";
    public static final String SHORT_PASSWORD = "M";
    public static final String VALID_LOGIN = "Sam_Smith69";

    private static RegistrationServiceImpl registrationService;
    private static User testUser;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        testUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_passwordNull_notOk() {
        testUser.setPassword(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_passwordTooShort_notOk() {
        testUser.setPassword(SHORT_PASSWORD);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginNull_notOk() {
        testUser.setLogin(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginEmpty_notOk() {
        testUser.setLogin("");
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageNull_notOk() {
        testUser.setAge(null);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageNegative_notOk() {
        testUser.setAge(NEGATIVE_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_ageZero_notOk() {
        testUser.setAge(0);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userTooOld_notOk() {
        testUser.setAge(LOW_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userTooYoung_notOk() {
        testUser.setAge(HIGH_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_loginAlreadyTaken_notOk() {
        Storage.people.add(testUser);
        assertThrows(InvalidDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userWasRegistered_Ok() {
        assertEquals(registrationService.register(testUser), testUser);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
