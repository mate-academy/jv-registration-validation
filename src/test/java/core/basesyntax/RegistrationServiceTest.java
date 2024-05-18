package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.ValidationException;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceTest {
    private RegistrationServiceImpl registrationService;
    private User user;
    private Random random;
    private String login;
    private String password;
    private int userAge;

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        random = new Random();
        login = "admin" + random.nextInt(6, 99999);
        password = "admin" + random.nextInt(6, 99999);
        userAge = random.nextInt(18, 99);
    }

    @Test
    public void register_validUser_ok() {
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(userAge);

        assertEquals(user, registrationService.register(user));
    }

    @Test
    public void register_userAlreadyExists_notOk() {
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(userAge);
        registrationService.register(user);

        User duplicateUser = new User();
        duplicateUser.setLogin(login);
        duplicateUser.setPassword(password);
        duplicateUser.setAge(userAge);

        assertThrows(ValidationException.class, () -> registrationService.register(duplicateUser));
    }

    @Test
    public void register_shortLogin_notOk() {
        user.setLogin("short");
        user.setPassword(password);
        user.setAge(userAge);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        user.setPassword(password);
        user.setAge(userAge);

        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortPassword_notOk() {
        user.setLogin(login);
        user.setPassword("short");
        user.setAge(userAge);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setLogin(login);
        user.setPassword(null);
        user.setAge(userAge);

        assertThrows(NullPointerException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underageUser_notOk() {
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(17);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_negativeAge_notOk() {
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(-1);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_zeroAge_notOk() {
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(0);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_setIdAfterRegistration_ok() {
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(userAge);
        registrationService.register(user);

        assertNotNull(user.getId());
    }
}
