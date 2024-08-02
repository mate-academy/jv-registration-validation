package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private StorageDaoImpl storageDao = new StorageDaoImpl();
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setAge(18);
        user.setLogin("123456");
        user.setPassword("123456");
        Storage.people.clear();
    }

    @Test
    public void register_ok() {
        registrationService.register(user);
        User stored = Storage.people.get(0);
        assertNotNull(stored, "User should be stored in Storage");
    }

    @Test
    public void register_someUsers_ok() {
        User user2 = new User();
        user2.setLogin("secondLogin");
        user2.setPassword("secondPassword");
        user2.setAge(31);
        User user3 = new User();
        user3.setLogin("thirdLogin");
        user3.setPassword("thirdPassword");
        user3.setAge(21);
        registrationService.register(user);
        registrationService.register(user2);
        registrationService.register(user3);
        boolean expected1 = Storage.people.get(0).equals(user);
        boolean expected2 = Storage.people.get(1).equals(user2);
        boolean expected3 = Storage.people.get(2).equals(user3);
        assertTrue(expected1, "First user wasn't added");
        assertTrue(expected2, "Second user wasn't added");
        assertTrue(expected3, "Third user wasn't added");
    }

    @Test
    public void register_NullUser_throwException() {
        user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_RegisterUsersWithSameLogins_throwException() {
        Storage.people.add(user);
        User sameUser = new User();
        sameUser.setAge(25);
        sameUser.setLogin("123456");
        sameUser.setPassword("654321");
        assertThrows(RegistrationException.class, () -> registrationService.register(sameUser));
    }

    @Test
    public void register_NullLogin_throwException() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_NullPassword_throwException() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_NullAge_throwException() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortLogin_throwException() {
        user.setLogin("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortPassword_throwException() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_tooYoung_throwException() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
