package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceTest {
    private  static RegistrationService registrationService;
    private static User user;
    private static User user1;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user1 = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setLogin("user1");
        user.setPassword("user123");
        user.setAge(23);
        user1.setLogin("user2");
        user1.setPassword("user345");
        user1.setAge(18);
    }

    @Test
    void register_ValidUser_Ok() {
        User expected = registrationService.register(user);
        assertEquals(user, expected);
        expected = registrationService.register(user1);
        assertEquals(user1, expected);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_RepeatUser_NotOk() {
        registrationService.register(user);
        registrationService.register(user1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        assertEquals(2, Storage.people.size());
    }

   @Test
    void register_NullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(new User()));
   }

   @Test
    void register_NotValidPassword_NotOk() {
        user.setPassword("user");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setPassword("       ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setPassword("^$%user");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
   }

    @Test
    void register_NotValidAge_NotOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setAge(200);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setAge(-99);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_notValidLogin_NotOk() {
        user.setLogin(" ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setLogin("&ghj");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setLogin("user 3");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}

