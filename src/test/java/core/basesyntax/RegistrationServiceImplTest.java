package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private StorageDaoImpl storageDao;
    private RegistrationServiceImpl registrationService;
    private User user;

    @BeforeEach
    void beforeEach() {
        user = new User();
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setPassword("validPassword");
        user.setAge(18);
        user.setLogin(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("validLogin");
        user.setAge(18);
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        user.setLogin("a");
        user.setPassword("validPassword");
        user.setAge(18);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        user.setLogin("abcde");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        user.setLogin("validLogin");
        user.setPassword("a");
        user.setAge(18);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        user.setPassword("abcde");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underage_notOk() {
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(-18);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        user.setAge(0);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
        user.setAge(17);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_existingLogin_notOk() {
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(18);
        storageDao.add(user);
        User newUser = new User();
        newUser.setLogin("validLogin");
        newUser.setPassword("validPassword");
        newUser.setAge(18);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(newUser));
    }

    @Test
    void register_validUser_ok() {
        user.setLogin("secondValidLogin");
        user.setPassword("validPassword");
        user.setAge(18);
        User returnedUser = registrationService.register(user);
        assertEquals(user, returnedUser);
        assertEquals(user, storageDao.get(user.getLogin()));
    }

    @Test
    void register_loginAndPassword_length6_ok() {
        user.setLogin("abcdef");
        user.setPassword("abcdef");
        user.setAge(18);
        User returnedUser = registrationService.register(user);
        assertEquals(user, returnedUser);
        assertEquals(user, storageDao.get(user.getLogin()));
    }
}
