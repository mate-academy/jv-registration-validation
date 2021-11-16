package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static User firstUser;
    private static User secondUser;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        firstUser = new User();
        secondUser = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        firstUser.setLogin("user1");
        firstUser.setPassword("user123");
        firstUser.setAge(23);
        secondUser.setLogin("user2");
        secondUser.setPassword("user345");
        secondUser.setAge(18);
    }

    @Test
    void register_validUser_ok() {
        User expected = registrationService.register(firstUser);
        assertEquals(firstUser, expected);
        expected = registrationService.register(secondUser);
        assertEquals(secondUser, expected);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_repeatUser_notOk() {
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

   @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
   }

   @Test
   void register_shortPassword_notOk() {
        firstUser.setPassword("user");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
   }

   @Test
   void register_notValidSymbolsPassword_notOk() {
       firstUser.setPassword("       ");
       assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
   }

   @Test
   void register_nullPassword_notOk() {
       firstUser.setPassword(null);
       assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
   }

    @Test
    void register_nullAge_notOk() {
        firstUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_userAge0_notOk() {
        firstUser.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_userAgeNegative_notOk() {
        firstUser.setAge(-99);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_userYounger18_notOk() {
        firstUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_notValidLogin_notOk() {
        firstUser.setLogin("&ghj");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_loginWithSpace_notOk() {
        firstUser.setLogin(" ");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
        firstUser.setLogin("user 3");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_loginEmptyLine_notOk() {
        firstUser.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void register_nullLogin_notOk() {
        firstUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(firstUser));
    }
}
