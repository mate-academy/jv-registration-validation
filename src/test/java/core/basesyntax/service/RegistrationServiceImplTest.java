package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "test_test";
    private static final String VALID_PASSWORD = "password123";
    private static final int VALID_AGE = 21;
    private static final String INVALID_LOGIN = "test";
    private static final String INVALID_PASSWORD = "pass";
    private static final int INVALID_AGE = 14;
    private static RegistrationService registrationService;
    private User validUser;
    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setLogin(VALID_LOGIN);
        validUser.setPassword(VALID_PASSWORD);
        validUser.setAge(VALID_AGE);
    }

    @Test
    void register_nullUser_notOK() {
        Assertions.assertThrows(InvalidUserException.class, () ->
                registrationService.register(null),
        "InvalidUserException should be thrown if the user is NULL"
                );
    }

    @Test
    void register_user_OK() {
        User registeredUser = registrationService.register(validUser);
        Assertions.assertNotNull(registeredUser);
        Assertions.assertNotEquals(0, registeredUser.getId());
        Assertions.assertEquals(validUser.getLogin(), registeredUser.getLogin());
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        Assertions.assertThrows(InvalidUserException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_nullPassword_notOK() {
        validUser.setPassword(null);
        Assertions.assertThrows(InvalidUserException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_notValidAge_notOK() {
        validUser.setAge(INVALID_AGE);
        Assertions.assertThrows(InvalidUserException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_notValidPassword_notOK() {
        validUser.setPassword(INVALID_PASSWORD);
        Assertions.assertThrows(InvalidUserException.class, () -> registrationService.register(validUser));
    }

    @Test
    void register_notValidLogin_notOK() {
        validUser.setLogin(INVALID_LOGIN);
        Assertions.assertThrows(InvalidUserException.class, () -> registrationService.register(validUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
 }