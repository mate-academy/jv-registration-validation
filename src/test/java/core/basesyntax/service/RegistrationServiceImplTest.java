package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.IncorrectUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String SHORT_PASSWORD = "12345";
    private static final String DEFAULT_PASSWORD = "userPassword";
    private static final String DEFAULT_LOGIN = "user_login";
    private static final String EMPTY_LOGIN = "";
    private static final int DEFAULT_AGE = 18;
    private static final int LOW_AGE = 10;
    private static final int HIGH_AGE = 141;
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_storageHasUser_notOk() {
        Storage.people.add(user);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user), "The register function should throw "
                        + IncorrectUserDataException.class.getName()
                        + " if storage has such user");
    }

    @Test
    void register_receivedUserIsNotNull_Ok() {
        User actualUser = registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertNotNull(actualUser.getId(), "Id don't has to be null");
        assertTrue(actual, "Storage must have such user");
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(null),
                "The register function should throw "
                        + IncorrectUserDataException.class.getName() + " if user is null");
    }

    @Test
    void register_userLoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw "
                        + IncorrectUserDataException.class.getName() + " if user login is null");
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        user.setLogin(EMPTY_LOGIN);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw "
                        + IncorrectUserDataException.class.getName() + " if user login is empty");
    }

    @Test
    void register_userAgeIsNull_notOk() {
        user.setAge(null);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw "
                        + IncorrectUserDataException.class.getName() + " if user age is null");
    }

    @Test
    void register_userAgeIsAtLeast18_notOk() {
        user.setAge(LOW_AGE);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw "
                        + IncorrectUserDataException.class.getName()
                        + " if user age lower than 18");
    }

    @Test
    void register_userAgeIsGreaterThan140_notOk() {
        user.setAge(HIGH_AGE);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw "
                        + IncorrectUserDataException.class.getName()
                        + " if user age is bigger than 140");
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw "
                        + IncorrectUserDataException.class.getName()
                        + " if user password is null");
    }

    @Test
    void register_userPasswordHasAtLeastSixSymbols_notOk() {
        user.setPassword(SHORT_PASSWORD);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw "
                        + IncorrectUserDataException.class.getName()
                        + "if user password doesn't has at least 6 symbols");
    }

    @AfterEach
    void endTest() {
        Storage.people.clear();
    }
}
