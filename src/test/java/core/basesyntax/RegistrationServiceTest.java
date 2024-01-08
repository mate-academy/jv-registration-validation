package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String USER_WASNT_ADD = "User was not add";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String VALID_LOGIN = "validLogin";
    private static final int VALID_AGE = 100;
    private static final String NULL_LOGIN = null;
    private static final String EMPTY_LOGIN = " ";
    private static final String INVALID_LOGIN = "hhh";
    private static final String NULL_PASSWORD = null;
    private static final String EMPTY_PASSWORD = " ";
    private static final String INVALID_PASSWORD = "not";
    private static final Integer NULL_AGE = null;
    private static final Integer NEGATIVE_AGE = -100;
    private static final Integer UNDER_18_AGE = 4;
    private static final User NULL_USER = null;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        user = NULL_USER;
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(NULL_PASSWORD);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));

    }

    @Test
    public void register_emptyPassword_notOk() {
        user.setPassword(EMPTY_PASSWORD);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_passwordTooShort_notOk() {
        user.setPassword(INVALID_PASSWORD);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(NULL_LOGIN);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_loginTooShort_notOk() {
        user.setLogin(INVALID_LOGIN);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_emptyLogin_notOk() {
        user.setLogin(EMPTY_LOGIN);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_ageUnderEighteen_notOk() {
        user.setAge(UNDER_18_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_nullAge_notOk() {
        user.setAge(NULL_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_negativeAge_notOk() {
        user.setAge(NEGATIVE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_validUser_Ok() {
        User actual = null;
        try {
            actual = registrationService.register(user);
        } catch (RegistrationException e) {
            throw new RuntimeException(USER_WASNT_ADD, e);
        }
        assertNotNull(actual.getId());
    }
}
