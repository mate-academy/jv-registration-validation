package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    public static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User("Mike", "mike12345", 22);
    }

    @Test
    public void register_passwordLength_ok() {
        user.setPassword("dddssssssssad");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_passwordLength_notOk() {
        user.setPassword("-=12?");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_0k() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_age_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullAge_ok() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_validUser_ok() {
        registrationService.register(user);
        assertEquals(1, Storage.people.size());
    }

    @Test
    public void register_existUser_ok() {
        User sameLoginUser = new User("Mike","goodPassword",29);
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(sameLoginUser));
    }

    @AfterEach
    public void tearDown() {
        Storage.people.clear();
    }
}
