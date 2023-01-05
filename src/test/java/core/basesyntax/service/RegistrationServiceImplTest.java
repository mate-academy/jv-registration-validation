package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static final int VALID_AGE = 18;
    private static final String VALID_PASSWORD = "userPassword";
    private static final String VALID_LOGIN = "validLogin";
    private static final String SECOND_VALID_LOGIN = "qwerty";
    private static final String DEFAULT_LOGIN = "default login";

    @Test
    void register_userIsNull_NotOk() {
        User newUser = null;
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(newUser)
        );
    }

    @Test
    void register_loginIsNull_NotOk() {
        User newUser = new User(null, VALID_PASSWORD, VALID_AGE);
        Assertions.assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(newUser), "Login can't be null"
        );
    }

    @Test
    void register_userWithSuchLoginAlreadyExist_NotOk() {
        User newUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_userAgeLessThanMin_NotOk() {
        User newUser = new User(DEFAULT_LOGIN, VALID_PASSWORD, VALID_AGE - 5);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(newUser), "Not valid age"
        );
    }

    @Test
    void register_userAgeEnough_Ok() {
        User user = new User(DEFAULT_LOGIN, VALID_PASSWORD, VALID_AGE + 5);
        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_ageFloorEdge_NotOk() {
        User newUser = new User(DEFAULT_LOGIN, VALID_PASSWORD, VALID_AGE - 1);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    public void register_userAgeIsNull_NotOk() {
        User user = new User(DEFAULT_LOGIN, VALID_PASSWORD, null);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user), "Age cant be null");
    }

    @Test
    void register_passwordIsNull_NotOk() {
        User newUser = new User(DEFAULT_LOGIN, null, VALID_AGE);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(newUser)
        );
    }

    @Test
    void register_checkPasswordLength_NotOk() {
        User newUser = new User(DEFAULT_LOGIN, "abc", VALID_AGE);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(newUser)
        );
    }

    @Test
    void register_passwordLengthOk_Ok() {
        User newUser = new User(DEFAULT_LOGIN, "enoughLengthPassword", VALID_AGE);
        assertEquals(newUser, registrationService.register(newUser));
    }

    @BeforeAll
    public static void init() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        User firstUser = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User secondUser = new User(SECOND_VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        storageDao.add(firstUser);
        storageDao.add(secondUser);
    }
}
