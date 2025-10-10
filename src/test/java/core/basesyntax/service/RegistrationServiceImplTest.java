package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String NORMAL_LOGIN = "alexvolodin@gmail.com";
    private static final String NORMAL_PASSWORD = "volodinalex10";
    private static final int NORMAL_AGE = 23;
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        storageDao.clearStorage();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_validUser_isOkay() {
        User validUser = new User(NORMAL_LOGIN, NORMAL_PASSWORD, NORMAL_AGE);
        User registered = assertDoesNotThrow(() -> registrationService.register(validUser));

        assertEquals(storageDao.get(validUser.getLogin()), registered);
    }

    @Test
    void register_nullLogin_notOk() {
        User userNullLogin = new User(null, NORMAL_PASSWORD, NORMAL_AGE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(userNullLogin));
    }

    @Test
    void register_nullPassword_notOk() {
        User userNullPassword = new User(NORMAL_LOGIN, null, NORMAL_AGE);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(userNullPassword));
    }

    @Test
    void register_nullAge_notOk() {
        User userNullAge = new User(NORMAL_LOGIN, NORMAL_PASSWORD, null);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(userNullAge));
    }

    @Test
    void register_existedUser_notOk() {
        User existedUser = new User(NORMAL_LOGIN, NORMAL_PASSWORD, NORMAL_AGE);
        storageDao.add(existedUser);

        User newUser = new User(NORMAL_LOGIN, "alexNewPassword", 26);
        assertThrows(InvalidDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_lowLengthLogin_notOk() {
        User user1LowLengthLogin = new User("", NORMAL_PASSWORD, NORMAL_AGE);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1LowLengthLogin));

        User user2LowLengthLogin = new User("ale", NORMAL_PASSWORD, NORMAL_AGE);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user2LowLengthLogin));

        User user3LowLengthLogin = new User("alexa", NORMAL_PASSWORD, NORMAL_AGE);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user3LowLengthLogin));
    }

    @Test
    void register_validLengthLogin_isOkay() {
        User user1ValidLengthLogin = new User("alex10", NORMAL_PASSWORD, NORMAL_AGE);
        User registered1 = assertDoesNotThrow(
                () -> registrationService.register(user1ValidLengthLogin));
        assertEquals(storageDao.get(user1ValidLengthLogin.getLogin()), registered1);

        User user2ValidLengthLogin = new User("alex10volodinLogin",
                NORMAL_PASSWORD, NORMAL_AGE);
        User registered2 = assertDoesNotThrow(
                () -> registrationService.register(user2ValidLengthLogin));
        assertEquals(storageDao.get(user2ValidLengthLogin.getLogin()), registered2);

        User user3ValidLengthLogin = new User(
                "alexandrVolodinBigLoginHasBigPowerToProtectMyAccaunt@gmail.com",
                NORMAL_PASSWORD, NORMAL_AGE);
        User registered3 = assertDoesNotThrow(
                () -> registrationService.register(user3ValidLengthLogin));
        assertEquals(storageDao.get(user3ValidLengthLogin.getLogin()), registered3);
    }

    @Test
    void register_lowLengthPassword_notOk() {
        User user1LowLengthPassword = new User(NORMAL_LOGIN.concat("a"),
                "", NORMAL_AGE);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user1LowLengthPassword));

        User user2LowLengthPassword = new User(NORMAL_LOGIN.concat("b"),
                "vol", NORMAL_AGE);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user2LowLengthPassword));

        User user3LowLengthPassword = new User(NORMAL_LOGIN.concat("c"),
                "volod", NORMAL_AGE);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(user3LowLengthPassword));
    }

    @Test
    void register_validLengthPassword_isOkay() {
        User user1ValidLengthPassword = new User(NORMAL_LOGIN.concat("a"),
                "volodi", NORMAL_AGE);
        User registered1 = assertDoesNotThrow(
                () -> registrationService.register(user1ValidLengthPassword));
        assertEquals(storageDao.get(user1ValidLengthPassword.getLogin()), registered1);

        User user2ValidLengthPassword = new User(NORMAL_LOGIN.concat("b"),
                "volodinMiddlePassword", NORMAL_AGE);
        User registered2 = assertDoesNotThrow(
                () -> registrationService.register(user2ValidLengthPassword));
        assertEquals(storageDao.get(user2ValidLengthPassword.getLogin()), registered2);

        User user3ValidLengthPassword = new User(NORMAL_LOGIN.concat("c"),
                "volodinBigPasswordHasBigPowerToProtectMyAccaunt", NORMAL_AGE);
        User registered3 = assertDoesNotThrow(
                () -> registrationService.register(user3ValidLengthPassword));
        assertEquals(storageDao.get(user3ValidLengthPassword.getLogin()), registered3);
    }

    @Test
    void register_lowAge_notOk() {
        User user1LowAge = new User(NORMAL_LOGIN.concat("a"),
                NORMAL_PASSWORD, Integer.MIN_VALUE);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user1LowAge));

        User user2LowAge = new User(NORMAL_LOGIN.concat("b"),
                NORMAL_PASSWORD, 0);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user2LowAge));

        User user3LowAge = new User(NORMAL_LOGIN.concat("c"),
                NORMAL_PASSWORD, 10);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user3LowAge));

        User user4LowAge = new User(NORMAL_LOGIN.concat("d"),
                NORMAL_PASSWORD, 17);
        assertThrows(InvalidDataException.class, () -> registrationService.register(user4LowAge));
    }

    @Test
    void register_validAge_isOkay() {
        User user1ValidAge = new User(NORMAL_LOGIN.concat("a"),
                NORMAL_PASSWORD, 18);
        User registered1 = assertDoesNotThrow(() -> registrationService.register(user1ValidAge));
        assertEquals(storageDao.get(user1ValidAge.getLogin()), registered1);

        User user2ValidAge = new User(NORMAL_LOGIN.concat("b"),
                NORMAL_PASSWORD, 51);
        User registered2 = assertDoesNotThrow(() -> registrationService.register(user2ValidAge));
        assertEquals(storageDao.get(user2ValidAge.getLogin()), registered2);

        User user3ValidAge = new User(NORMAL_LOGIN.concat("c"),
                NORMAL_PASSWORD, 99);
        User registered3 = assertDoesNotThrow(() -> registrationService.register(user3ValidAge));
        assertEquals(storageDao.get(user3ValidAge.getLogin()), registered3);
    }
}
