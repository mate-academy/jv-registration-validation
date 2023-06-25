package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.exception.UserExistsException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User testUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.add(new User("login1", "password1", 18));
        testUser = new User("validLogin", "validPassword", 28);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        testUser = null;
        assertThrows(IllegalArgumentException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        testUser.setLogin("login1");
        assertThrows(UserExistsException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userLoginIsNull_notOk() {
        testUser.setLogin(null);
        assertThrows(IllegalStateException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userLoginIsEmpty_notOk() {
        testUser.setLogin("");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userLoginUnderSixSymbols_notOk() {
        testUser.setLogin("asd");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userLoginIsFiveSymbolsEdgeCase_notOk() {
        testUser.setLogin("asdas");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userLoginIsSixSymbolsEdgeCase_ok() {
        testUser.setLogin("asdasd");
        User registered = registrationService.register(testUser);
        assertNotEquals(0L, registered.getId());
    }

    @Test
    void register_userLoginIsMoreThanSixSymbols_ok() {
        User registered = registrationService.register(testUser);
        assertNotEquals(0L, registered.getId());
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        testUser.setPassword(null);
        assertThrows(IllegalStateException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userPasswordIsEmpty_notOk() {
        testUser.setPassword("");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userPasswordUnderSixSymbols_notOk() {
        testUser.setPassword("asd");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userPasswordIsFiveSymbolsEdgeCase_notOk() {
        testUser.setPassword("asdas");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userPasswordIsSixSymbolsEdgeCase_ok() {
        testUser.setPassword("asdasd");
        User registered = registrationService.register(testUser);
        assertNotEquals(0L, registered.getId());
    }

    @Test
    void register_userPasswordIsMoreThanSixSymbols_ok() {
        User registered = registrationService.register(testUser);
        assertNotEquals(0L, registered.getId());
    }

    @Test
    void register_userAgeIsNull_notOk() {
        testUser.setAge(null);
        assertThrows(IllegalStateException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAgeNegative_notOk() {
        testUser.setAge(-8);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAgeUnderEighteen_notOk() {
        testUser.setAge(8);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(testUser));
    }

    @Test
    void register_userAgeIsEighteenEdgeCase_ok() {
        testUser.setAge(18);
        User registered = registrationService.register(testUser);
        assertNotEquals(0L, registered.getId());
    }

    @Test
    void register_userAgeIsMoreThanEighteen_ok() {
        User registered = registrationService.register(testUser);
        assertNotEquals(0L, registered.getId());
    }

    @Test
    void register_validUser_ok() {
        User registered = registrationService.register(testUser);
        assertNotEquals(0L, registered.getId());
    }
}
