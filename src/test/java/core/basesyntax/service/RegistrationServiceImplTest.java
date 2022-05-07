package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl() {
        };
    }

    @BeforeEach
    void setUp() {
        user = new User("user", "111111", 22);
    }

    @Test
    void inputValidData_Ok() {
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void userWithThisLoginAlreadyExists_notOk() {
        user.setLogin("firstUser");
        registrationService.register(user);
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(new User(user.getLogin(), "password", 22)));
        assertEquals("This login already exists", exception.getLocalizedMessage());
    }

    @Test
    void inputPasswordIsLessThanSixCharacters_notOk() {
        user.setLogin("secondUser");
        user.setPassword("111");
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("Password can't be less than 6 characters", exception.getLocalizedMessage());
    }

    @Test
    void inputAgeLessThanEighteen_notOk() {
        user.setLogin("thirdUser");
        user.setAge(12);
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("Age under 18 is not allowed", exception.getLocalizedMessage());
    }

    @Test
    void inputPasswordIsNull_notOk() {
        user.setPassword(null);
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("Data can't be null", exception.getLocalizedMessage());
    }

    @Test
    void inputAgeIsNull_notOk() {
        user.setAge(null);
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("Data can't be null", exception.getLocalizedMessage());
    }

    @Test
    void inputLoginIsNull_notOk() {
        user.setLogin(null);
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("Data can't be null", exception.getLocalizedMessage());
    }
}
