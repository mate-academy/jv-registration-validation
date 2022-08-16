package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private User user1;
    private User user2;
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
        user1 = new User();
        user1.setPassword("123456");
        user1.setAge(25);
        user1.setLogin("Dick");
        user2 = new User();
    }

    @Test
    public void register_negativeAge_notOkey() {
        user1.setAge(-100);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                });
        assertEquals("User age must be positive number",
                exception.getMessage());
    }

    @Test
    public void register_userUnderaged_notOkey() {
        user1.setAge(16);
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
        user1.setPassword("1236");
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
        user1.setAge(18);
        assertEquals(user1, service.register(user1));
    }

    @Test
    public void registerd_userAgeOver18_okey() {
        assertEquals(user1, service.register(user1));
    }
}
