package core.basesyntax;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import core.basesyntax.service.InvalidUserDataException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;
    private User defaultUser;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
        defaultUser = createDefaultUser();
    }

    private User createDefaultUser() {
        User user = new User();
        user.setLogin("myUser");
        user.setPassword("myPass");
        user.setAge(18);
        return user;
    }

    @Test
    void register_validUser_successfulRegistration() {
        assertEquals(defaultUser, storageDao.get(defaultUser.getLogin()));
    }

    @Test
    void register_existingLogin_notOk() {
        storageDao.add(defaultUser);
        assertThrows(InvalidUserDataException.class, () ->
                registrationService.register(defaultUser));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createDefaultUser();
        user.setLogin("short");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createDefaultUser();
        user.setPassword("short");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_notOk() {
        User user = createDefaultUser();
        user.setAge(17);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = createDefaultUser();
        user.setAge(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nonexistentUser_notOk() {
        User user = createDefaultUser();
        user.setLogin("nonexistent_user");
        assertDoesNotThrow(() -> registrationService.register(user));
    }
}
