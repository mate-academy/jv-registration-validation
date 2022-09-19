package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String PASSWORD_CORRECT = "Abcdfg";
    private static final String PASSWORD_INCORRECT = "dfg";
    private static final int AGE_CORRECT = 19;
    private static final int AGE_BOUND = 18;
    private static final int AGE_INCORRECT = 17;
    private static final int AGE_NEGATIVE = -18;
    private static RegistrationService service;
    private static StorageDao storageDao;
    private User actual;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        actual = new User();
    }

    @Test
    void register_nullUser_throwsException() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> service.register(null), "An Exception must be thrown here");
        String expectedExceptionMessage = "User is null.";
        assertEquals(expectedExceptionMessage,
                exception.getMessage(),
                "Different exception messages");
    }

    @Test
    void register_nullLogin_throwsException() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
        String expectedExceptionMessage = "User login is null.";
        assertEquals(expectedExceptionMessage,
                exception.getMessage(),
                "Different exception messages");
    }

    @Test
    void register_newUser_ok() {
        actual.setLogin("user0");
        actual.setPassword(PASSWORD_CORRECT);
        actual.setAge(AGE_CORRECT);
        service.register(actual);
        assertEquals(storageDao.get(actual.getLogin()), actual, "User hasn't been added");
    }

    @Test
    void register_existingUser_throwsException() {
        actual.setLogin("user1");
        actual.setPassword(PASSWORD_CORRECT);
        actual.setAge(AGE_CORRECT);
        service.register(actual);
        Exception exception = assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
        String expectedExceptionMessage = "User with login "
                + actual.getLogin() + " is already exists in the database.";
        assertEquals(expectedExceptionMessage,
                exception.getMessage(),
                "Different exception messages");
    }

    @Test
    void register_nullPassword_throwsException() {
        actual.setLogin("user2");
        actual.setPassword(null);
        actual.setAge(AGE_CORRECT);
        Exception exception = assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
        String expectedExceptionMessage = "Password login is null.";
        assertEquals(expectedExceptionMessage,
                exception.getMessage(),
                "Different exception messages");
    }

    @Test
    void register_incorrectPassword_throwsException() {
        actual.setLogin("user3");
        actual.setPassword(PASSWORD_INCORRECT);
        actual.setAge(AGE_CORRECT);
        Exception exception = assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
        String expectedExceptionMessage = "Password length must be equal or more than "
                + PASSWORD_CORRECT.length() + ".";
        assertEquals(expectedExceptionMessage,
                exception.getMessage(),
                "Different exception messages");
    }

    @Test
    void register_nullAge_throwsException() {
        actual.setLogin("user4");
        actual.setPassword(PASSWORD_CORRECT);
        actual.setAge(null);
        Exception exception = assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
        String expectedExceptionMessage = "Age login is null.";
        assertEquals(expectedExceptionMessage,
                exception.getMessage(),
                "Different exception messages");
    }

    @Test
    void register_negativeAge_throwsException() {
        actual.setLogin("user5");
        actual.setPassword(PASSWORD_CORRECT);
        actual.setAge(AGE_NEGATIVE);
        Exception exception = assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
        String expectedExceptionMessage = "Age must be positive";
        assertEquals(expectedExceptionMessage,
                exception.getMessage(),
                "Different exception messages");
    }

    @Test
    void register_incorrectAge_throwsException() {
        actual.setLogin("user6");
        actual.setPassword(PASSWORD_CORRECT);
        actual.setAge(AGE_INCORRECT);
        Exception exception = assertThrows(RuntimeException.class,
                () -> service.register(actual), "An Exception must be thrown here");
        String expectedExceptionMessage = "Age must be equal or more than " + AGE_BOUND + ".";
        assertEquals(expectedExceptionMessage,
                exception.getMessage(),
                "Different exception messages");
    }

    @Test
    void register_boundCorrectAge_ok() {
        actual.setLogin("user7");
        actual.setPassword(PASSWORD_CORRECT);
        actual.setAge(AGE_BOUND);
        service.register(actual);
        assertEquals(storageDao.get(actual.getLogin()), actual);
    }

    @Test
    void register_correctAge_ok() {
        actual.setLogin("user8");
        actual.setPassword(PASSWORD_CORRECT);
        actual.setAge(AGE_CORRECT);
        service.register(actual);
        assertEquals(storageDao.get(actual.getLogin()), actual);
    }
}
