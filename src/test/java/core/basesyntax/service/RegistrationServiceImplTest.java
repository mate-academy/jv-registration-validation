package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidInputDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;
    private static final int VALID_AGE = 18;
    private static final String VALID_PASSWORD = "userPassword";
    private static final String VALID_LOGIN = "validLogin";
    private static final String SHORT_PASSWORD = "abc";

    @BeforeAll
    public static void init() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        registrationService.register(user);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userIsNull_notOk() {
        User newUser = null;
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(newUser)
        );
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(InvalidInputDataException.class,
                () -> registrationService.register(user), "Login can't be null"
        );
    }

    @Test
    void register_userWithSuchLoginAlreadyExist_notOk() {
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_userAgeLessThanMin_notOk() {
        user.setAge(VALID_AGE - 5);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(user), "Not valid age"
        );
    }

    @Test
    public void register_ageFloorEdge_notOk() {
        user.setAge(VALID_AGE - 1);
        assertThrows(InvalidInputDataException.class, () ->
                registrationService.register(user));
    }

    @Test
    public void register_userAgeIsNull_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () ->
                registrationService.register(user), "Age cant be null");
    }

    @Test
    void register_passwordIsNull_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_shortPasswordLength_notOk() {
        user.setPassword(SHORT_PASSWORD);
        Assertions.assertThrows(RuntimeException.class,
                () -> registrationService.register(user)
        );
    }
}
