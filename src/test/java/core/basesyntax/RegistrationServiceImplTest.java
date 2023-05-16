package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.UserRegistrationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {

    private static User user;
    private static final int DEFAULT_AGE = 25;
    private static final int INCORRECT_AGE = 15;
    private static final String DEFAULT_LOGIN = "user@gmail.com";
    private static final String DEFAULT_PASSWORD = "abc123";
    private static final String INCORRECT_LOGIN_AND_PASSWORD = "gg";
    private static final String MASSAGE = "UserRegistrationException error was expected";
    private static RegistrationServiceImpl registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setAge(DEFAULT_AGE);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(DEFAULT_PASSWORD);

    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserRegistrationException.class,
                () -> registrationService.register(null),
                MASSAGE);
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user),
                MASSAGE);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user),
                MASSAGE);
    }

    @Test
    void register_nullId_notOk() {
        user.setId(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user),
                MASSAGE);
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user),
                MASSAGE);
    }

    @Test
    void register_ageLessThanEighteen_notOk() {
        user.setAge(INCORRECT_AGE);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user),
                MASSAGE);
    }

    @Test
    void register_passwordLengthLessThanSix_notOk() {
        user.setPassword(INCORRECT_LOGIN_AND_PASSWORD);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user),
                MASSAGE);
    }

    @Test
    void register_loginLengthLessThanSix_notOk() {
        user.setLogin(INCORRECT_LOGIN_AND_PASSWORD);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user),
                MASSAGE);
    }

    @Test
    void register_loginAlreadyTaken_notOk() {
        Storage.people.add(user);
        assertThrows(UserRegistrationException.class, () -> registrationService.register(user),
                MASSAGE);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }
}
