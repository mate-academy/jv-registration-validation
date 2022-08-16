package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationService service;
    private User user1;
    private User user2;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
        user1 = new User();
        user2 = new User();
    }

    @Test
    public void registerUserUnderagedUNotOkey() {
        user1.setAge(16);
        user1.setLogin("Bob");
        user1.setPassword("123456");
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                });
        assertEquals("User must be 18 years old or older", exception.getMessage());
    }

    @Test
    public void registerUserPasswordNotOkey() {
        user1.setAge(26);
        user1.setLogin("Tom");
        user1.setPassword("1236");
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                });
        assertEquals("Password must be at least 6 characters long",
                exception.getMessage());
    }

    @Test
    public void registerUserSameLoginNotOkey() {
        user1.setAge(26);
        user1.setLogin("Bob");
        user1.setPassword("123456");
        user2.setAge(100);
        user2.setLogin("Bob");
        user2.setPassword("123456");
        RuntimeException exception =
                assertThrows(RuntimeException.class,() -> {
                    service.register(user1);
                    service.register(user2);
                });
        assertEquals("There is already user with such login", exception.getMessage());
    }

    @Test
    public void registerdUserAge18Okey() {
        user1.setAge(18);
        user1.setLogin("Jill");
        user1.setPassword("1234567");
        assertEquals(user1, service.register(user1));
    }

    @Test
    public void registerdUserAgeOver18Okey() {
        user1.setAge(25);
        user1.setLogin("Jack");
        user1.setPassword("1234567");
        assertEquals(user1, service.register(user1));
    }
}
