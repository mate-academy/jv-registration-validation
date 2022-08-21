package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static final String OK_PASSWORD = "123456";
    private static final String LOGIN = "Dick";
    private static final int AGE_OK = 25;
    private static final int TOO_YOUNG = 16;
    private static final String SHORT_PASSWORD_NOT_OK = "1234";
    private static final int NEGATIVE_AGE = -100;
    private static final int ADULT_AGE_MIN_OK = 18;
    private User firstUser;
    private User secondUser;
    private RegistrationService service = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        firstUser = new User();
        firstUser.setPassword(OK_PASSWORD);
        firstUser.setAge(AGE_OK);
        firstUser.setLogin(LOGIN);
        secondUser = new User();
    }

    @Test
    public void register_negativeAge_notOkey() {
        firstUser.setAge(NEGATIVE_AGE);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(firstUser);
                });
        assertEquals("User age must be positive number",
                exception.getMessage());
    }

    @Test
    public void register_userUnderaged_notOkey() {
        firstUser.setAge(TOO_YOUNG);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(firstUser);
                });
        assertEquals("User must be 18 years old or older", exception.getMessage());
    }

    @Test
    public void register_nullLogin_notOkey() {
        firstUser.setLogin(null);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(firstUser);
                });
        assertEquals("Provide login, please",
                exception.getMessage());
    }

    @Test
    public void register_nullAge_notOkey() {
        firstUser.setAge(null);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(firstUser);
                });
        assertEquals("Provide age, please",
                exception.getMessage());
    }

    @Test
    public void register_nullPasword_notOkey() {
        firstUser.setPassword(null);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(firstUser);
                });
        assertEquals("Provide password, please",
                exception.getMessage());
    }

    @Test
    public void register_userPassword_notOkey() {
        firstUser.setPassword(SHORT_PASSWORD_NOT_OK);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(firstUser);
                });
        assertEquals("Password must be at least 6 characters long",
                exception.getMessage());
    }

    @Test
    public void register_userSameLogin_notOkey() {
        secondUser = firstUser;
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(firstUser);
                    service.register(secondUser);
                });
        assertEquals("There is already user with such login", exception.getMessage());
    }

    @Test
    public void register_userAge18_okey() {
        firstUser.setAge(ADULT_AGE_MIN_OK);
        assertEquals(firstUser, service.register(firstUser));
    }

    @Test
    public void register_userAgeOver18_okey() {
        assertEquals(firstUser, service.register(firstUser));
    }
}
