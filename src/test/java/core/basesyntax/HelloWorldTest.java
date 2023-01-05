package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private static final int DEFAULT_AGE = 18;
    private static final String DEFAULT_PASSWORD = "666666";
    private static final String DEFAULT_LOGIN = "user_login";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @Test
    void register_addAgeGreaterThanMinAge_ok() {
        user.setAge(19);
        assertEquals(user, registrationService.register(user), "User add in Storage");
    }

    @Test
    void register_addAdultUser_ok() {
        assertEquals(user, registrationService.register(user), "User add in Storage");
    }

    @Test
    void register_addNullUser_notOk() {
        user = null;
        assertThrows(ValidationException.class, () -> registrationService.register(null),
                "User can't be null");
    }

    @Test
    void register_addNullLogin_notOk() {
        user.setLogin(null);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Login can't be null");
    }

    @Test
    void register_addNullPassword_notOk() {
        user.setPassword(null);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Password can't be null");
    }

    @Test
    void register_passwordLengthIsGreaterThanMinLength_ok() {
        user.setPassword("7777777");
        assertEquals(user, registrationService.register(user), "User add in Storage");
    }

    @Test
    void register_passwordLengthIsLessThanMinLength_ok() {
        user.setPassword("55555");
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "password less than expected");
    }

    @Test
    void register_addUserWithAgeLessThanMinAge_notOk() {
        user.setAge(17);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Age less than expected");
    }

    @Test
    void register_addUserNegativeAge_notOk() {
        user.setAge(-57);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Age can't be negative");
    }

    @Test
    void register_addNullAge_notOk() {
        user.setAge(0);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "Age can't be null");
    }

    @Test
    void register_userRegistered_notOk() {
        registrationService.register(user);
        assertThrows(ValidationException.class, () -> registrationService.register(user),
                "User registered early");

    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
