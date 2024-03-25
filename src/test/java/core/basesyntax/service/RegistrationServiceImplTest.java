package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_USER_ACCEPTABLE_AGE = 18;
    private static final String validUserLogin = "some_valid_login";
    private static RegistrationServiceImpl registrationService;
    private static User testUser;
    private static User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        defaultUser = new User();
        defaultUser.setId(24356876L);
        defaultUser.setLogin(validUserLogin);
        defaultUser.setPassword("some_valid_password");
        defaultUser.setAge(30);
    }

    @BeforeEach
    void setUp() throws CloneNotSupportedException {
        testUser = defaultUser.clone();
    }

    @AfterEach
    void tearDown() {
        if (!Storage.people.isEmpty()) {
            Storage.people.clear();
        }
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_nullUserLogin_NotOk() {
        testUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_nullUserPassword_NotOk() {
        testUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_nullUserAge_NotOk() {
        testUser.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_emptyUserLogin_NotOk() {
        testUser.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_shortUserLogin_NotOk() {
        testUser.setLogin("login");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_emptyUserPassword_NotOk() {
        testUser.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_shortUserPassword_NotOk() {
        testUser.setPassword("short");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_negativeUserAge_NotOk() {
        testUser.setAge(-20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_noAcceptableUserAge_NotOk() {
        testUser.setAge(10);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_sameUserLoginRegistered_NotOk() throws CloneNotSupportedException {
        String repeatingLogin = "same_login_user";
        testUser.setLogin(repeatingLogin);
        Storage.people.add(testUser);
        User sameLoginUser = defaultUser.clone();
        sameLoginUser.setLogin(repeatingLogin);
        Storage.people.add(testUser);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(testUser);
        });
    }

    @Test
    void register_correctLengthUserLogin_Ok() throws RegistrationException {
        testUser.setLogin("normal_length_login");
        registrationService.register(testUser);
        assertTrue(testUser.getLogin().length() >= MIN_LOGIN_LENGTH);
    }

    @Test
    void register_minLengthUserLogin_Ok() throws RegistrationException {
        testUser.setLogin("normal");
        registrationService.register(testUser);
        assertTrue(testUser.getLogin().length() == MIN_LOGIN_LENGTH);
    }

    @Test
    void register_correctLengthUserPassword_Ok() throws RegistrationException {
        testUser.setPassword("normal_length_password");
        registrationService.register(testUser);
        assertTrue(testUser.getPassword().length() >= MIN_PASSWORD_LENGTH);
    }

    @Test
    void register_minLengthUserPassword_Ok() throws RegistrationException {
        testUser.setPassword("passwd");
        registrationService.register(testUser);
        assertTrue(testUser.getPassword().length() == MIN_PASSWORD_LENGTH);
    }

    @Test
    void register_minUserAge_Ok() throws RegistrationException {
        testUser.setAge(18);
        registrationService.register(testUser);
        assertTrue(testUser.getAge() == MIN_USER_ACCEPTABLE_AGE);
    }

    @Test
    void register_acceptableUserAge_Ok() throws RegistrationException {
        testUser.setAge(28);
        registrationService.register(testUser);
        assertTrue(testUser.getAge() >= MIN_USER_ACCEPTABLE_AGE);
    }

    @Test
    void register_validUserRegistration_Ok()
            throws RegistrationException, CloneNotSupportedException {
        assertTrue(Storage.people.isEmpty());
        registrationService.register(testUser);
        assertTrue(Storage.people.size() == 1);
        User anotherUser = defaultUser.clone();
        anotherUser.setLogin("some_another_user");
        registrationService.register(anotherUser);
        assertTrue(Storage.people.size() == 2);
    }
}
