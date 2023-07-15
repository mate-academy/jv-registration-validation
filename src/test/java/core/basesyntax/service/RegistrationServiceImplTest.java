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
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("user");
        user.setAge(18);
        user.setPassword("111111");
    }

    @Test
    void register_inputValidData_Ok() {
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void register_userWithThisLoginAlreadyExists_notOk() {
        user.setLogin("firstUser");
        registrationService.register(user);
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("This login already exists", exception.getLocalizedMessage());
    }

    @Test
    void register_inputPasswordIsLessThanSixCharacters_notOk() {
        user.setLogin("secondUser");
        user.setPassword("111");
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("Password can't be less than 6 characters", exception.getLocalizedMessage());
    }

    @Test
    void register_inputAgeLessThanEighteen_notOk() {
        user.setLogin("thirdUser");
        user.setAge(12);
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("Age under 18 is not allowed", exception.getLocalizedMessage());
    }

    @Test
    void register_inputPasswordIsNull_notOk() {
        user.setPassword(null);
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("Data can't be null", exception.getLocalizedMessage());
    }

    @Test
    void register_inputAgeIsNull_notOk() {
        user.setAge(null);
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("Data can't be null", exception.getLocalizedMessage());
    }

    @Test
    void register_inputLoginIsNull_notOk() {
        user.setLogin(null);
        Throwable exception = assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
        assertEquals("Data can't be null", exception.getLocalizedMessage());
    }
}
