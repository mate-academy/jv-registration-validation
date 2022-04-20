package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("TheBestUser");
        user.setAge(31);
        user.setPassword("aVeryValidPassword");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerUserLoginIsNull_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void registerUserLoginIsEmpty_NotOK() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userLoginIsBlank_NotOk() {
        user.setLogin("     ");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userLoginIsAlreadyExists_NotOk() {
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin("TheBestUser");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userAgeIsNull_notOK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userAgeIsLessThan18_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userPasswordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userPasswordIsEmpty_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userPasswordIsBlank_NotOk() {
        user.setPassword("      ");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userPasswordLengthIsLessThanMinimal_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void userIsValid_OK() {
        User registerUser = registrationService.register(user);
        assertEquals(user, registerUser);
    }
}


