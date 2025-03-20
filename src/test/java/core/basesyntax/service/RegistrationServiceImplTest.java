package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_STRING = "abcdef";
    private static final int MIN_LENGTH_STRING = 6;
    private static final int MIN_USER_AGE = 18;
    private static final int MAX_USER_AGE = 127;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private User defaultUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        defaultUser = new User(DEFAULT_STRING, DEFAULT_STRING, MIN_USER_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
        StorageDaoImpl.setIndex(0L);
    }

    @Test
    void register_UserIsNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null),
                "If the user is null, a RegistrationException should be thrown.");
    }

    @Test
    void register_LoginIsNull_notOk() {
        defaultUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "If the user's login is null, a RegistrationException should be thrown.");
    }

    @Test
    void register_PasswordIsNull_notOk() {
        defaultUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "If the user's password is null, a RegistrationException should be thrown.");
    }

    @Test
    void register_AgeIsNull_notOk() {
        defaultUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "If the user's age is null, a RegistrationException should be thrown.");
    }

    @Test
    void register_StorageDaoIsFull_notOk() {
        StorageDaoImpl.setIndex((long) Integer.MAX_VALUE);
        assertThrows(RegistrationException.class, () -> registrationService.register(defaultUser),
                "If the storageDao is full, a RegistrationException should be thrown.");
    }

    @Test
    void register_LoginIsEmpty_notOk() {
        defaultUser.setLogin("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser),
                "If the user's login is less than " + MIN_LENGTH_STRING
                        + " characters, a RegistrationException should be thrown.");
    }

    @Test
    void register_LoginIsTooShort_notOk() {
        for (int i = 0; i < MIN_LENGTH_STRING; i++) {
            User currentUser = new User(String.valueOf(i), DEFAULT_STRING, MIN_USER_AGE);
            assertThrows(RegistrationException.class,
                    () -> registrationService.register(currentUser),
                    "If the user's login is less than " + MIN_LENGTH_STRING
                            + " characters, a RegistrationException should be thrown.");
        }
    }

    @Test
    void register_PasswordIsEmpty_notOk() {
        defaultUser.setPassword("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(defaultUser),
                "If the user's password is less than " + MIN_LENGTH_STRING
                        + " characters, a RegistrationException should be thrown.");
    }

    @Test
    void register_PasswordIsTooShort_notOk() {
        for (int i = 0; i < MIN_LENGTH_STRING; i++) {
            User currentUser = new User(DEFAULT_STRING, String.valueOf(i), MIN_USER_AGE);
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
        defaultUser.setAge(MIN_USER_AGE);
        User actual = registrationService.register(defaultUser);
        assertEquals(defaultUser, actual,
                "If registration is successful, the method returns the registered user.");
        assertNotNull(storageDao.get(defaultUser.getLogin()),
                "In case of successful user registration"
                        + ", the user should already exist in the repository.");
    }

    @Test
    void register_AgeIsNegativeValue_notOk() {
        for (int i = -1; i >= (-MAX_USER_AGE); i--) {
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
            currentUser.setLogin(DEFAULT_STRING + i);
            currentUser.setPassword(DEFAULT_STRING + i);
            registrationService.register(currentUser);
            assertThrows(RegistrationException.class,
                    () -> registrationService.register(currentUser),
                    "If the user already exists, a RegistrationException should be throw.");
        }
    }

    @Test
    void register_addUser_Ok() {
        List<User> users = new ArrayList<>();
        int indexOut = 0;
        for (int i = 0; i <= MAX_USER_AGE; i++) {
            User currentUser = new User();
            currentUser.setLogin(String.valueOf(i));
            currentUser.setPassword(String.valueOf(i));
            currentUser.setAge(i);
            if (i < MIN_USER_AGE) {
                assertThrows(RegistrationException.class, () -> {
                    registrationService.register(currentUser);
                }, "If the user's age is less than "
                        + MIN_USER_AGE + " years old, a RegistrationException should be throw.");
                indexOut = i;
                break;
            }
            if (i < MIN_LENGTH_STRING) {
                assertThrows(RegistrationException.class, () -> {
                    registrationService.register(currentUser);
                }, "If the user's login or password is less than "
                        + MIN_LENGTH_STRING + " characters"
                        + ", a RegistrationException should be thrown.");
                indexOut = i;
                break;
            }
            users.add(currentUser);
            Long indexPrevious = StorageDaoImpl.getIndex();
            User actual = registrationService.register(currentUser);
            assertEquals(currentUser, actual,
                    "The register method must return the registered user.");
            Long indexActual = StorageDaoImpl.getIndex() - 1L;
            assertEquals(indexPrevious, indexActual,
                    "In case of user registration"
                            + ", the StorageDaoImpl.index should increase by one.");
            assertNotNull(storageDao.get(currentUser.getLogin()),
                    "If the user has been added, then he must already be registered.");
            indexOut++;
        }
        List<User> actual = Storage.people;
        assertEquals(users, actual);
        boolean indexStorageDaoImpl = StorageDaoImpl.getIndex() == indexOut;
        assertTrue(indexStorageDaoImpl,
                "In case of user registration"
                        + ", StorageDaoImpl.index must be equal to " + MAX_USER_AGE + ".");
    }
}

