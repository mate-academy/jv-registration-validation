package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static final String DEFAULT_LOGIN = "vitalii@gmail.com";
    private static final String DEFAULT_PASSWORD = "012345";
    private static final int DEFAULT_AGE = 30;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, DEFAULT_AGE);
    }

    @AfterEach
    void afterAll() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(null),
                "User should not be null");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "User age should not be null");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "User login should not be null");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "User password should not be null");
    }

    @Test
    void register_incorrectPassword_notOk() {
        String invalidPass = "01234";
        user.setPassword(invalidPass);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "Password must not be shorter than 6 characters");
    }

    @Test
    void register_incorrectLogin_notOk() {
        String invalidLogin = "01234";
        user.setLogin(invalidLogin);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "Login must not be shorter than 6 characters");
    }

    @Test
    void register_incorrectAge_notOk() {
        int invalidAge = 7;
        user.setAge(invalidAge);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "Age of the user must be at least 16");
    }

    @Test
    void register_loginAlreadyUse_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(user),
                "Login is already in use");
    }

    @Test
    void register_validData_ok() {
        registrationService.register(user);
        assertNotNull(user.getId(),
                "User id should not be null after registration.");
    }
}
