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
    private static final int MINOR_NOT_OK = 16;
    private static final String SHORT_PASSWORD_NOT_OK = "1234";
    private static final int NEGATIVE = -100;
    private static final int ADULT_AGE_MIN_OK = 18;
    private User user1;
    private User user2;
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
        user1 = new User();
        user1.setPassword(OK_PASSWORD);
        user1.setAge(AGE_OK);
        user1.setLogin(LOGIN);
        user2 = new User();
    }

    @Test
    public void register_negativeAge_notOkey() {
        user1.setAge(NEGATIVE);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                });
        assertEquals("User age must be positive number",
                exception.getMessage());
    }

    @Test
    public void register_userUnderaged_notOkey() {
        user1.setAge(MINOR_NOT_OK);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                });
        assertEquals("User must be 18 years old or older", exception.getMessage());
    }

    @Test
    public void register_nullLogin_notOkey() {
        user1.setLogin(null);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                });
        assertEquals("Provide login, please",
                exception.getMessage());
    }

    @Test
    public void register_nullAge_notOkey() {
        user1.setAge(null);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                });
        assertEquals("Provide age, please",
                exception.getMessage());
    }

    @Test
    public void register_nullPasword_notOkey() {
        user1.setPassword(null);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                });
        assertEquals("Provide password, please",
                exception.getMessage());
    }

    @Test
    public void register_userPassword_notOkey() {
        user1.setPassword(SHORT_PASSWORD_NOT_OK);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                });
        assertEquals("Password must be at least 6 characters long",
                exception.getMessage());
    }

    @Test
    public void register_userSameLogin_notOkey() {
        user2 = user1;
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                    service.register(user2);
                });
        assertEquals("There is already user with such login", exception.getMessage());
    }

    @Test
    public void registerd_userAge18_okey() {
        user1.setAge(ADULT_AGE_MIN_OK);
        assertEquals(user1, service.register(user1));
    }

    @Test
    public void registerd_userAgeOver18_okey() {
        assertEquals(user1, service.register(user1));
    }
}
