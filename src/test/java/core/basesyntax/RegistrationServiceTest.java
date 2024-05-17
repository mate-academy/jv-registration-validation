package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
    private User user = new User();
    private Random random = new Random();
    private String login = "admin" + random.nextInt(6, 99999);
    private String password = "admin" + random.nextInt(6, 99999);
    private int userAge = random.nextInt(18, 99);

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_auth_ok() {
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(userAge);

        assertEquals(registrationService.register(user), user);

        User user2 = new User();
        user2.setLogin("tre" + random.nextInt(9) + random.nextInt(9) + random.nextInt(9));
        user2.setPassword("123456");
        user2.setAge(18);
        assertEquals(registrationService.register(user2), user2);

    }

    @Test
    public void register_auth_notok() {
        user.setLogin("five5");
        user.setPassword("five");
        user.setAge(17);

        assertThrows(ValidationException.class, () -> registrationService.register(user));

        User user2 = new User();
        user2.setLogin("");
        user2.setPassword("");
        user2.setAge(0);

        assertThrows(ValidationException.class, () -> registrationService.register(user2));

    }

    @Test
    public void register_setidafterauth_ok() {
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(userAge);
        registrationService.register(user);

        assertNotNull(user.getId());
    }

    @Test
    public void register_notvalidpassword_notok() {
        String toShortPassword = "1";
        user.setLogin(login);
        user.setPassword(toShortPassword);

        assertThrows(ValidationException.class, () -> registrationService.register(user));

        toShortPassword = null;
        user.setPassword(toShortPassword);
        assertThrows(NullPointerException.class, () -> registrationService.register(user));

        toShortPassword = "trese";
        user.setPassword(toShortPassword);
        assertThrows(ValidationException.class, () -> registrationService.register(user));

    }

    @Test
    public void register_notvalidage_notok() {
        int failAge = 17;
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(failAge);

        assertThrows(ValidationException.class, () -> registrationService.register(user));

        failAge = 0;
        user.setAge(failAge);
        assertThrows(ValidationException.class, () -> registrationService.register(user));

        failAge = -1;
        user.setAge(failAge);
        assertThrows(ValidationException.class, () -> registrationService.register(user));

    }

    @Test
    public void accordance_twouserswithdifferentparametrs_notok() {
        User user1 = new User();
        user1.setLogin("user11");
        user1.setPassword("password1");
        user1.setAge(25);

        User user2 = new User();
        user2.setLogin("user21");
        user2.setPassword("password1");
        user2.setAge(25);

        assertNotEquals(user1, user2);
    }

}
