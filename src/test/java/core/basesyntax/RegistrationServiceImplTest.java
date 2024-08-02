package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao = new StorageDaoImpl();
    private User user;

    @BeforeAll
    public static void setupGlobalResources() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setAge(18);
        user.setLogin("123456");
        user.setPassword("123456");
        Storage.people.clear();
    }

    @Test
    public void register_validUser_ok() {
        registrationService.register(user);
        User stored = Storage.people.get(0);
        assertNotNull(stored, "User should be stored in Storage");
    }

    @Test
    public void register_multipleValidUsers_ok() {
        User user2 = new User();
        user2.setLogin("secondLogin");
        user2.setPassword("secondPassword");
        user2.setAge(31);
        User user3 = new User();
        user3.setLogin("thirdLogin");
        user3.setPassword("thirdPassword");
        user3.setAge(21);
        storageDao.add(user);
        storageDao.add(user2);
        registrationService.register(user3);
        boolean expected1 = Storage.people.get(0).equals(user);
        boolean expected2 = Storage.people.get(1).equals(user2);
        boolean expected3 = Storage.people.get(2).equals(user3);
        assertTrue(expected1, "First user wasn't added");
        assertTrue(expected2, "Second user wasn't added");
        assertTrue(expected3, "Third user wasn't added");
    }

    @Test
    public void register_nullUser_notOk() {
        user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_duplicateLogin_notOk() {
        Storage.people.add(user);
        User sameUser = new User();
        sameUser.setAge(25);
        sameUser.setLogin("123456");
        sameUser.setPassword("654321");
        assertThrows(RegistrationException.class, () -> registrationService.register(sameUser));
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortLogin_notOk() {
        user.setLogin("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_ShortPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_tooYoung_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
