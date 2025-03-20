package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_STRING = "abcdef";
    private static final int MIN_LENGTH_STRING = 6;
    private static final int MIN_USER_AGE = 18;
    private static final int MAX_USER_AGE = 127;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_UserIsNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "If the user is null, a RegistrationException should be thrown.");
    }

    @Test
    void register_LoginIsNull_notOk() {
        User currentUser = new User(null, DEFAULT_STRING, MIN_USER_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(currentUser),
                "If the user's login is null, a RegistrationException should be thrown.");
    }

    @Test
    void register_PasswordIsNull_notOk() {
        User currentUser = new User(DEFAULT_STRING, null, MIN_USER_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(currentUser),
                "If the user's password is null, a RegistrationException should be thrown.");
    }

    @Test
    void register_AgeIsNull_notOk() {
        User currentUser = new User(DEFAULT_STRING, DEFAULT_STRING, null);
        assertThrows(RegistrationException.class, () -> registrationService.register(currentUser),
                "If the user's age is null, a RegistrationException should be thrown.");
    }

    @Test
    void register_LoginIsEmpty_notOk() {
        User currentUser = new User("", DEFAULT_STRING, MIN_USER_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(currentUser),
                "If the user's login is less than " + MIN_LENGTH_STRING
                        + " characters, a RegistrationException should be thrown.");
    }

    @Test
    void register_LoginIsTooShort_notOk() {
        for (int i = 0; i < MIN_LENGTH_STRING; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            String userLogin = stringBuilder.append((char) i).toString();
            User currentUser = new User(userLogin, DEFAULT_STRING, MIN_USER_AGE);
            assertThrows(RegistrationException.class,
                    () -> registrationService.register(currentUser),
                    "If the user's login is less than " + MIN_LENGTH_STRING
                            + " characters, a RegistrationException should be thrown.");
        }
    }

    @Test
    void register_PasswordIsEmpty_notOk() {
        User currentUser = new User("", DEFAULT_STRING, MIN_USER_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(currentUser),
                "If the user's password is less than " + MIN_LENGTH_STRING
                        + " characters, a RegistrationException should be thrown.");
    }

    @Test
    void register_PasswordIsTooShort_notOk() {
        for (int i = 0; i < MIN_LENGTH_STRING; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            String userPassword = stringBuilder.append((char) i).toString();
            User currentUser = new User(DEFAULT_STRING, userPassword, MIN_USER_AGE);
            assertThrows(RegistrationException.class,
                    () -> registrationService.register(currentUser),
                    "If the user's password is less than " + MIN_LENGTH_STRING
                            + " characters, a RegistrationException should be thrown.");
        }
    }

    @Test
    void register_UserIsMinor_notOk() {
        for (int i = 0; i < MIN_USER_AGE; i++) {
            User currentUser = new User(DEFAULT_STRING, DEFAULT_STRING,i);
            assertThrows(RegistrationException.class,
                    () -> registrationService.register(currentUser),
                    "If the user's age is less than " + MIN_USER_AGE
                            + " years old, a RegistrationException should be throw.");
        }
    }

    @Test
    void register_ageIsMinUserAge_Ok() {
        User currentUser = new User(DEFAULT_STRING, DEFAULT_STRING, MIN_USER_AGE);
        User actual = registrationService.register(currentUser);
        assertEquals(currentUser, actual,
                "If registration is successful, the method returns the registered user.");
        assertNotNull(storageDao.get(currentUser.getLogin()),
                "In case of successful user registration"
                        + ", the user should already exist in the repository.");
    }

    @Test
    void register_AgeIsNegativeValue_notOk() {
        for (int i = -1; i >= (-MIN_USER_AGE); i--) {
            User currentUser = new User(DEFAULT_STRING, DEFAULT_STRING,i);
            assertThrows(RegistrationException.class,
                    () -> registrationService.register(currentUser),
                    "In case the user's age is negative"
                            + ", a RegistrationException should be throw.");
        }
    }

    @Test
    void register_UserAlreadyExists_notOk() {
        for (int i = MIN_USER_AGE; i <= MAX_USER_AGE; i++) {
            User currentUser = new User();
            currentUser.setAge(i);
            currentUser.setLogin(DEFAULT_STRING + (char) i);
            currentUser.setPassword(DEFAULT_STRING + (char) i);
            registrationService.register(currentUser);
            assertThrows(RegistrationException.class,
                    () -> registrationService.register(currentUser),
                    "If the user already exists, a RegistrationException should be throw.");
        }
    }

    @Test
    void register_addUser_Ok() {
        for (int i = MIN_USER_AGE; i <= MAX_USER_AGE; i++) {
            User currentUser = new User();
            currentUser.setLogin(DEFAULT_STRING + i);
            currentUser.setPassword(DEFAULT_STRING + i);
            currentUser.setAge(i);
            User actualUser = registrationService.register(currentUser);
            assertEquals(currentUser, actualUser,
                    "The register method must return the registered user.");
            assertNotNull(storageDao.get(currentUser.getLogin()),
                    "If the user has been added, then he must already be registered.");
        }
    }
}

