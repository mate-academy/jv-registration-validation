package core.basesyntax;

import core.basesyntax.exceptions.InvalidRegistrationData;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegistrationServiceImplTest {
    private static final int ADULT_AGE = 24;
    private static final String VALID_LOGIN = "employee";
    private static final String ANOTHER_VALID_LOGIN = "employee1";
    private static final String VALID_PASSWORD = "password";
    private static final int NOT_ADULT_AGE = 15;
    private static final String SHORT_LOGIN = "login";
    private static final String SHORT_PASSWORD = "pass";
    private static RegistrationServiceImpl registrationServiceImpl;
    private User user;

    @BeforeAll
    public static void init() {
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        this.user = new User();
        this.user.setLogin(VALID_LOGIN);
        this.user.setPassword(VALID_PASSWORD);
        this.user.setAge(ADULT_AGE);
    }

    @Test
    public void register_nullUser_notOk() {
        this.user = null;
        assertThrows(InvalidRegistrationData.class,
                () -> registrationServiceImpl.register(this.user));
    }

    @Test
    public void register_nullLogin_notOk() {
        this.user.setLogin(null);
        assertThrows(InvalidRegistrationData.class,
                () -> registrationServiceImpl.register(this.user));
    }

    @Test
    public void register_nullPassword_notOk() {
        this.user.setPassword(null);
        assertThrows(InvalidRegistrationData.class,
                () -> registrationServiceImpl.register(this.user));
    }

    @Test
    public void register_nullAge_notOk() {
        this.user.setAge(null);
        assertThrows(InvalidRegistrationData.class,
                () -> registrationServiceImpl.register(this.user));
    }

    @Test
    public void register_shortLogin_notOk() {
        this.user.setLogin(SHORT_LOGIN);
        assertThrows(InvalidRegistrationData.class,
                () -> registrationServiceImpl.register(this.user));
    }

    @Test
    public void register_shortPassword_notOk() {
        this.user.setPassword(SHORT_PASSWORD);
        assertThrows(InvalidRegistrationData.class,
                () -> registrationServiceImpl.register(this.user));
    }

    @Test
    public void register_notAdultAge_notOk() {
        this.user.setAge(NOT_ADULT_AGE);
        assertThrows(InvalidRegistrationData.class,
                () -> registrationServiceImpl.register(this.user));
    }

    @Test
    public void register_validInput_Ok() {
        assertEquals(this.user, registrationServiceImpl.register(this.user));
    }

    @Test
    public void register_duplicateLogin_notOk() {
        this.user.setLogin(ANOTHER_VALID_LOGIN);
        registrationServiceImpl.register(this.user);
        assertThrows(InvalidRegistrationData.class,
                () -> registrationServiceImpl.register(this.user));
    }
}
