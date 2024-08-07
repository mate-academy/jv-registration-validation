package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final String SHORT_LOGIN = "short";
    private static final String VALID_LOGIN = "validLogin";
    private static final String SHORT_PASSWORD = "short";
    private static final String VALID_PASSWORD = "validPassword";
    private static final Integer VALID_AGE = 20;
    private static final Integer UNDERAGE = 17;
    private static final String EXISTING_LOGIN = "existingLogin";

    private static RegistrationService registrationService;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    public static void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    public void register_shortLogin_notOk() {
        User user = createUser(SHORT_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortPassword_notOk() {
        User user = createUser(VALID_LOGIN, SHORT_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underageUser_notOk() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, UNDERAGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_existingLogin_notOk() {
        User existingUser = createUser(EXISTING_LOGIN, VALID_PASSWORD, VALID_AGE);
        storageDao.add(existingUser);

        User newUser = createUser(EXISTING_LOGIN, VALID_PASSWORD, VALID_AGE);
        assertThrows(RegistrationException.class, () -> registrationService.register(newUser));
    }

    @Test
    public void register_validUser_ok() {
        User user = createUser(VALID_LOGIN, VALID_PASSWORD, VALID_AGE);
        User registeredUser = registrationService.register(user);

        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(VALID_LOGIN, registeredUser.getLogin());
        assertEquals(VALID_PASSWORD, registeredUser.getPassword());
        assertEquals(VALID_AGE, registeredUser.getAge());
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
