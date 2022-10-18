package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void setupRegistrationService() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registration_validUser_ok() {
        user = new User();
        user.setLogin("QAValid1");
        user.setPassword("12345667");
        user.setAge(22);
        user.setId(1L);
        User registrationResult = registrationService.register(user);
        assertTrue(user.equals(registrationResult));
    }

    @Test
    void registration_minimumPasswordLength_ok() {
        user = new User();
        user.setLogin("QAValid2");
        user.setPassword("123456");
        user.setAge(22);
        user.setId(2L);
        User registrationResult = registrationService.register(user);
        assertEquals(user, registrationResult);
    }

    @Test
    void registration_minimumAge_ok() {
        user = new User();
        user.setLogin("QAValid3");
        user.setPassword("12345667");
        user.setAge(18);
        user.setId(3L);
        User registrationResult = registrationService.register(user);
        assertEquals(user, registrationResult);
    }

    @Test
    void registration_twoUsersEqualFieldsButLogin_ok() {
        user = new User();
        user.setLogin("QAValid4");
        user.setPassword("12345667");
        user.setAge(18);
        user.setId(4L);
        User secondUser = new User();
        secondUser.setLogin("QAValid5");
        secondUser.setPassword(user.getPassword());
        secondUser.setAge(user.getAge());
        secondUser.setId(user.getId());
        User registrationResult1 = registrationService.register(user);
        User registrationResult2 = registrationService.register(secondUser);
        assertEquals(user, registrationResult1);
        assertEquals(secondUser, registrationResult2);
    }

    @Test
    void registration_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void registration_nullLogin_notOk() {
        user = new User();
        user.setAge(22);
        user.setPassword("123456");
        user.setId(4L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_notUniqueLogin_notOk() {
        user = new User();
        user.setLogin("QAInvalid1");
        user.setPassword("123456");
        user.setAge(22);
        user.setId(5L);
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_nullPassword_notOk() {
        user = new User();
        user.setLogin("QAInvalid2");
        user.setAge(22);
        user.setId(6L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_aLotLessThanMinimumPasswordLength_notOk() {
        user = new User();
        user.setLogin("QAInvalid3");
        user.setPassword("123");
        user.setAge(22);
        user.setId(7L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_oneLessThenMinimumPasswordLength_notOk() {
        user = new User();
        user.setLogin("QAInvalid4");
        user.setPassword("12345");
        user.setAge(22);
        user.setId(8L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_nullAge_notOk() {
        user = new User();
        user.setLogin("QAInvalid5");
        user.setPassword("12345678");
        user.setId(9L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_aLotLessThanMinimumAge_notOk() {
        user = new User();
        user.setLogin("QAInvalid6");
        user.setPassword("1234567");
        user.setAge(2);
        user.setId(10L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registration_oneLessThanMinimumAge_notOk() {
        user = new User();
        user.setLogin("QAInvalid7");
        user.setPassword("1234567");
        user.setAge(17);
        user.setId(11L);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
