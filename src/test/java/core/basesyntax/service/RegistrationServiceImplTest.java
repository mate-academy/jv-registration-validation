package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @Test
    void register_validUser_ok() throws RegistrationUserException {
        User user = new User("Bogdan", "krutoi228", 88);
        User user1 = new User("Speedwagon", "coolguy2008", 36);
        User user2 = new User("Olegovich", "password", 43);
        User registeredUser = registrationService.register(user);
        User registeredUser1 = registrationService.register(user1);
        User registeredUser2 = registrationService.register(user2);
        assertTrue(Storage.people.contains(registeredUser));
        assertTrue(Storage.people.contains(registeredUser1));
        assertTrue(Storage.people.contains(registeredUser2));
        assertNotNull(registeredUser.getId());
        assertNotNull(registeredUser1.getId());
        assertNotNull(registeredUser2.getId());
        assertEquals(user.getLogin(), registeredUser.getLogin());
        assertEquals(user1.getLogin(), registeredUser1.getLogin());
        assertEquals(user2.getLogin(), registeredUser2.getLogin());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user1.getPassword(), registeredUser1.getPassword());
        assertEquals(user2.getPassword(), registeredUser2.getPassword());
        assertEquals(user.getAge(), registeredUser.getAge());
        assertEquals(user1.getAge(), registeredUser1.getAge());
        assertEquals(user2.getAge(), registeredUser2.getAge());
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userExists_notOk() throws RegistrationUserException {
        User user = new User("oleksandr", "average", 87);
        registrationService.register(user);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessSixLogin_notOk() {
        User user = new User("gachi", "orlcmsht", 26);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessSixPassword_notOk() {
        User user = new User("children", "asdgf", 26);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User(null, "qwe123", 34);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User("arthas", null, 65);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User("loginNotNull", "passwordNotNull", null);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_moreMinAge_notOk() {
        User user = new User("shadow_fiend", "zxc123", 9);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessMaxAge_notOk() {
        User user = new User("nevermind", "nevermind", 105);
        assertThrows(RegistrationUserException.class, () -> registrationService.register(user));
    }
}
