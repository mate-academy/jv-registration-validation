package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exceptions.NotEnoughAgeException;
import core.basesyntax.exceptions.NotEnoughSizeException;
import core.basesyntax.exceptions.UserIsNullException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;

class StorageTest {

    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @Test
    void register_nullUser_notOk() {
        assertThrows(UserIsNullException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("bobAlison");
        user.setPassword("12345678");
        user.setAge(null);
        assertThrows(NotEnoughAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("12345678");
        user.setAge(20);
        assertThrows(NotEnoughSizeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("bobAlison");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(NotEnoughSizeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        User user = new User();
        user.setLogin("bobAlison");
        user.setPassword("12345678");
        user.setAge(17);
        assertThrows(NotEnoughAgeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidLogin_notOk() {
        User user = new User();
        user.setLogin("bob");
        user.setPassword("12345678");
        user.setAge(20);
        assertThrows(NotEnoughSizeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassword_notOk() {
        User user = new User();
        user.setLogin("Anton45");
        user.setPassword("3783d");
        user.setAge(20);
        assertThrows(NotEnoughSizeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("bobAlison");
        user.setPassword("98675f");
        user.setAge(18);
        assertEquals(registrationService.register(user), user);
    }

    @Test
    void register_getPresentUser_ok() {
        User user = new User();
        user.setLogin("AliceAlison");
        user.setPassword("3783d3");
        user.setAge(20);
        assertEquals(registrationService.register(user), user);
        assertEquals(storageDao.get(user.getLogin()), user);
    }

    @Test
    void register_getNonExistent_ok() {
        User user = new User();
        user.setLogin("DenisAlison");
        user.setPassword("3783d3");
        user.setAge(26);
        assertNull(storageDao.get(user.getLogin()));
    }

    @Test
    void register_doubleAddUser_notOk() {
        User user = new User();
        user.setLogin("NewUser");
        user.setPassword("3783d3");
        user.setAge(90);
        assertEquals(registrationService.register(user), user);
        assertNull(registrationService.register(user));
    }

    @Test
    void register_addPresentUser_notOk() {
        User user = new User();
        user.setLogin("Andrey");
        user.setPassword("3783d3");
        user.setAge(30);
        user.setId(1L);
        assertEquals(registrationService.register(user), user);
        User sameUser = new User();
        sameUser.setLogin("Andrey");
        sameUser.setPassword("3783d3");
        sameUser.setAge(30);
        sameUser.setId(1L);
        assertNull(registrationService.register(sameUser));
    }
}
