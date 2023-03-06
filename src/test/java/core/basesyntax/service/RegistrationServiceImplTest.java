package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidRegistrationDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static final int VALID_AGE = 28;
    private static final String VALID_LOGIN = "userlogincorrect";
    private static final String INVALID_LOGIN = "invalide_login";
    private static final String VALID_PASSWORD = "userpasswordvalid";
    private static User user;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
    }

    @AfterEach
    public void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullArgument_notOk() {
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(null);
        }, "InvalidRegistrationDataException expected for null argument");
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for user.login = null");
    }

    @Test
    void register_ageIsNull_notOk() {
        user.setAge(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for user.age = null");
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException expected for user.password = null");
    }

    @Test
    void register_loginAlreadyInStorage_notOk() {
        User storageUser = new User();
        storageUser.setLogin(INVALID_LOGIN);
        Storage.people.add(storageUser);
        user.setLogin(INVALID_LOGIN);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, InvalidRegistrationDataException.class.getName()
                + ", method does not allow user login duplicates");
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException,"
                + "when login field empty an exception should be thrown");
    }

    @Test
    void register_default_ok() {
        User registerUser = registrationService.register(user);
        assertNotNull(registerUser);
        assertEquals(VALID_LOGIN, registerUser.getLogin());
        assertEquals(1, registerUser.getId());
        assertEquals(VALID_PASSWORD, registerUser.getPassword());
        assertEquals(VALID_AGE, registerUser.getAge());
        assertTrue(Storage.people.contains(registerUser));
    }

    @Test
    void register_passwordLengthLessThanMin_notOk() {
        user.setPassword("");
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException,"
                + "if password length less than 6 an exception should be thrown");
    }

    @Test
    void register_ageLessThan18_notOk() {
        user.setAge(16);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException, if age < 18 method you should thrown an exception");
    }

    @Test
    void register_ageEquals18_ok() {
        user.setAge(MIN_AGE);
        registrationService.register(user);
        assertEquals(MIN_AGE,
                user.getAge(), "Age not match");
    }

    @Test
    void register_ageIsNegative_notOk() {
        user.setAge(-999);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException, if age < 0 method should thrown an exception");
    }

    @Test
    void register_ageGreaterThan116_notOk() {
        user.setAge(88888);
        assertThrows(InvalidRegistrationDataException.class, () -> {
            registrationService.register(user);
        }, "InvalidRegistrationDataException, if age > 116 method should thrown an exception");
    }
}

