package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_ok() {
        User user = createUser("kandibober", "ibrahim13", 33);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertNotNull(registeredUser.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
    }

    @Test
    public void register_userWithExistingLogin_exceptionThrown() {
        User user = createUser("kandibober", "ibrahim13", 25);
        storageDao.add(user);

        User userWithSameLogin = createUser("kandibober", "ibrahim13", 30);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(userWithSameLogin));
    }

    @Test
    public void register_nullLogin_exceptionThrown() {
        User user = createUser(null, "ibrahim13", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortLogin_exceptionThrown() {
        User user = createUser("kandi", "ibrahim13", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_exceptionThrown() {
        User user = createUser("kandibober", null, 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_shortPassword_exceptionThrown() {
        User user = createUser("kandibober", "ibr13", 25);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullAge_exceptionThrown() {
        User user = createUser("kandibober", "ibrahim13", null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underageUser_exceptionThrown() {
        User user = createUser("kandibober", "ibrahim13", 16);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }
}
