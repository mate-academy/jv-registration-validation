package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.RegistrationException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int MIM_PASSWORD_LENGTH = 6;
    private static final int AGE_18 = 18;
    private static final int AGE_17 = 17;
    private static final int AGE_60 = 60;
    private static final int NEGATIVE_AGE = -2;
    private static final String USER_NAME = "Docent";
    private static final String USER_NAME_2 = "Doc";
    private static final String USER_NAME_3 = "Nana";
    private static final String NEW_USER_NAME = "Daisy";
    private static final String NEW_USER_NAME_1 = "Nina";
    private static final String NEW_USER_NAME_2 = "Armin";
    private static final String USER_PASSWORD_OK = "123456";
    private static final String USER_PASSWORD_THREE_CHARS = "123";
    private static final String USER_PASSWORD_FIVE_CHARS = "12345";
    private static final String USER_PASSWORD_LENGTH_OK = "12345678";
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static User user;
    private static User newUser;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(USER_NAME);
        user.setAge(AGE_18);
        user.setPassword(USER_PASSWORD_OK);
        newUser = new User();
        newUser.setLogin(NEW_USER_NAME);
        newUser.setAge(AGE_60);
        newUser.setPassword(USER_PASSWORD_LENGTH_OK);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_sameLogin_notOk() {
        storageDao.add(user);
        newUser.setLogin(USER_NAME);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
        });
    }

    @Test
    void register_rightLogin_ok() {
        newUser.setLogin(NEW_USER_NAME);
        assertNotEquals(newUser.getLogin(), user.getLogin());
        registrationService.register(newUser);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_underAge_notOk() {
        user.setAge(AGE_17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_rightAge_ok() {
        user.setLogin(USER_NAME_2);
        boolean expected = user.getAge() == AGE_18;
        assertTrue(expected);
        registrationService.register(user);

        newUser.setLogin(NEW_USER_NAME_1);
        newUser.setAge(AGE_60);
        boolean expectedAge = newUser.getAge() > AGE_18;
        assertTrue(expectedAge);
        registrationService.register(newUser);
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_shortPasswordLength_notOk() {
        user.setPassword(" ");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword(USER_PASSWORD_THREE_CHARS);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
        user.setPassword(USER_PASSWORD_FIVE_CHARS);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void register_rightPassword_ok() {
        user.setLogin(USER_NAME_3);
        boolean expected = user.getPassword().length() == MIM_PASSWORD_LENGTH;
        assertTrue(expected);
        registrationService.register(user);

        newUser.setLogin(NEW_USER_NAME_2);
        boolean expectedEightCharacters = newUser.getPassword().length() > MIM_PASSWORD_LENGTH;
        assertTrue(expectedEightCharacters);
        registrationService.register(newUser);
    }

}
