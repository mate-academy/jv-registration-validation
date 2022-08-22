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
    private User firstUser;
    private User secondUser;
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
        Storage.people.clear();
        firstUser = new User();
        firstUser.setPassword("123456");
        firstUser.setAge(25);
        firstUser.setLogin("Dick");
        secondUser = new User();
    }

    @Test
    public void register_negativeAge_notOkey() {
        firstUser.setAge(-100);
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(firstUser);
                });
        assertEquals("User age must be positive number",
                exception.getMessage());
    }

    @Test
    public void register_userUnderaged_notOkey() {
        firstUser.setAge(16);
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
        firstUser.setPassword("1234");
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
        firstUser.setAge(18);
        assertEquals(firstUser, service.register(firstUser));
    }

    @Test
    public void register_userAgeOver18_okey() {
        assertEquals(firstUser, service.register(firstUser));
    }
}
