package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User user1;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_AlreadyExistedUser_notOk() {
        user1 = new User("spider@gmail.com", "qwerty12345", 25);
        registrationService.register(user1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    public void register_nullUser_notOk() {
        user1 = null;
        assertThrows(NullPointerException.class, () -> registrationService.register(user1));
    }

    @Test
    public void register_adult_notOk() {
        user1 = new User("spider@gmail.com", "qwerty12345", 25);
        user1.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    public void register_adult_isOk() {
        user1 = new User("spider@gmail.com", "qwerty12345", 25);
        user1.setAge(19);
        assertEquals(user1, registrationService.register(user1));
    }

    @Test
    public void register_lengthOfPassword_notOk() {
        user1 = new User("spider@gmail.com", "qwerty12345", 25);
        user1.setPassword("sdfsd");
        assertThrows(RuntimeException.class, () -> registrationService.register(user1));
    }

    @Test
    public void register_lengthOfPassword_isOk() {
        user1 = new User("spider@gmail.com", "qwerty12345", 25);
        user1.setPassword("sdfsddd");
        assertEquals(user1, registrationService.register(user1));
    }

    @Test
    void register_nullFieldsInUser_notOk() {
        user1 = new User();
        assertThrows(NullPointerException.class, () -> registrationService.register(user1));
    }
}
