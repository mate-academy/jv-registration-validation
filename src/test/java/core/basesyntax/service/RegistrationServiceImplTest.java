package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int VALID_AGE = 22;
    private static final String VALID_PASSWORD = "123456";
    private static final String VALID_LOGIN = "nazari";
    private static final int INVALID_AGE = 17;
    private static final String INVALID_LOGIN = "nazar";
    private static final String INVALID_PASSWORD = "12345";
    private static RegistrationServiceImpl registrationService;
    private User correctUser;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void createUser() {
        correctUser = new User(VALID_LOGIN,VALID_PASSWORD,VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_validUser_ok() {
        registrationService.register(correctUser);
        assertTrue(Storage.people.contains(correctUser),"Does not add a correct user");
    }

    @Test
    public void register_nullAge_notOk() {
        correctUser.setAge(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(correctUser),
                "Method should throw InvalidUserDataException");
    }

    @Test
    public void register_LessAge_notOk() {
        correctUser.setAge(INVALID_AGE);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(correctUser),
                "Method should throw InvalidUserDataException");
    }

    @Test
    public void register_nullLogin_notOk() {
        correctUser.setLogin(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(correctUser),
                "Method should throw InvalidUserDataException");
    }

    @Test
    public void register_IncorrectLogin_notOk() {
        correctUser.setLogin(INVALID_LOGIN);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(correctUser),
                "Method should throw InvalidUserDataException");
    }

    @Test
    public void register_nullPassword_notOk() {
        correctUser.setPassword(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(correctUser),
                "Method should throw InvalidUserDataException");
    }

    @Test
    public void register_incorrectPassword_notOk() {
        correctUser.setPassword(INVALID_PASSWORD);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(correctUser),
                "Method should throw InvalidUserDataException");
    }

    @Test
    public void register_existingUser_notOk() {
        Storage.people.add(correctUser);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(correctUser),
                "Adding existing user you need throw InvalidUserDataException");
    }
}
