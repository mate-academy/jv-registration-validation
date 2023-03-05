package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.IncorrectUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private final User user = new User();

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user.setAge(18);
        user.setLogin("user1_login");
        user.setPassword("user1Password1");
    }

    @Test
    void register_storageHasUser_notOk() {
        Storage.people.add(user);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw an error if storage has such user");
    }

    @Test
    void register_receivedUserIsNotNull_Ok() {
        registrationService.register(user);
        boolean actual = Storage.people.contains(user);
        assertTrue(actual, "Storage must have such user");
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(null),
                "The register function should throw an error if user is null");
    }

    @Test
    void register_userLoginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw an error if user login is null");
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw an error if user login is empty");
    }

    @Test
    void register_userAgeIsAtLeast18_notOk() {
        user.setAge(17);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw an error if user age lower than 18");
    }

    @Test
    void register_userAgeIsLowerThan140_notOk() {
        user.setAge(141);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw an error if user age is higher than 140");
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw an error if user password is null");
    }

    @Test
    void register_userPasswordHasAtLeastSixSymbols_notOk() {
        user.setPassword("12345");
        assertThrows(IncorrectUserDataException.class,
                () -> registrationService.register(user),
                "The register function should throw an error "
                        + "if user password doesn't has at least 6 symbols");
    }

    @AfterEach
    void endTest() {
        Storage.people.clear();
    }
}
