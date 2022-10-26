package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDao storage;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @BeforeEach
    public void init() {
        user = new User("spider@gmail.com", "qwerty12345", 25);
    }

    @AfterEach
    public void clear() {
        user = null;
        Storage.people.clear();
    }

    @Test
    public void register_alreadyExistedUser_notOk() {
        storage.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullUser_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_underMinAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_lengthOfPasswordTooShort_notOk() {
        user.setPassword("sdf");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLoginInUser_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_theSameUserWithDifferentAge_notOk() {
        storage.add(user);
        User anotherUser = new User("spider@gmail.com", "qwerty12345", 27);
        assertThrows(RuntimeException.class, () -> registrationService.register(anotherUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPasswordInUser_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAgeInUser_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_registerValidUser_isOk() {
        int storageCapacityBefore = Storage.people.size();
        assertEquals(user, registrationService.register(user));
        assertEquals(Storage.people.size(), storageCapacityBefore + 1);
    }
}
