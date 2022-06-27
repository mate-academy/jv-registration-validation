package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final User user = new User();

    @BeforeEach
    void setUp() {
        user.setLogin("admin");
        user.setAge(18);
        user.setPassword("123456");
        user.setId(13L);
    }

    @Test
    void correctUserAge_Ok() {
        Integer actual = user.getAge();
        assertFalse(actual < 18);
    }

    @Test
    void correctUserAge_Nok() {
        User user1 = new User();
        user1.setAge(17);

        assertThrows(RuntimeException.class, () -> {
            boolean b = registrationService.register(user1).getAge() < 18;
        });
    }

    @Test
    void correctUserPasswordLength_Ok() {
        int actual = user.getPassword().length();
        assertTrue(actual >= 6);
    }

    @Test
    void correctUserPasswordLength_Nok() {
        User user1 = new User();
        user1.setPassword("12345");

        assertThrows(RuntimeException.class, () -> {
            boolean b = registrationService.register(user1).getPassword().length() < 6;
        });
    }

    @Test
    void repeatedUser_Nok() {
        User user1 = new User();
        user1.setLogin("admin");

        assertThrows(RuntimeException.class, () -> registrationService.register(user1)
        );
    }
}
