package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void createRegistrationService() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;

        assertThrows(
                RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("141251531");
        user.setAge(23);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_shortLogin3Symbols_notOk() {
        User user = new User();
        user.setLogin("rad");
        user.setPassword("3223532fewfwe3");
        user.setAge(22);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_shortLogin5Symbols_notOk() {
        User user = new User();
        user.setLogin("radik");
        user.setPassword("3223532fewfwe3");
        user.setAge(22);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_existingLogin_notOk() {
        User user1 = new User();
        user1.setLogin("325325325");
        user1.setPassword("325325");
        user1.setAge(25);

        StorageDaoImpl storageDao = new StorageDaoImpl();
        storageDao.add(user1);

        User user2 = new User();
        user2.setLogin("325325325");
        user2.setPassword("325325");
        user2.setAge(25);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user2)
        );
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("323fdvvrrg");
        user.setPassword(null);
        user.setAge(24);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_shortPassword3Symbols_notOk() {
        User user = new User();
        user.setLogin("323fdvvrrg");
        user.setPassword("213");
        user.setAge(24);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_shortPassword5Symbols_notOk() {
        User user = new User();
        user.setLogin("323fdvvrrg");
        user.setPassword("21345");
        user.setAge(24);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("323fdvvrrg");
        user.setPassword("2136346346346");
        user.setAge(null);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_age10_notOk() {
        User user = new User();
        user.setLogin("323fdvvrrg");
        user.setPassword("213465656");
        user.setAge(10);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_age17_notOk() {
        User user = new User();
        user.setLogin("32g43g34g43");
        user.setPassword("32r32freg4ggr");
        user.setAge(17);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("32g43g34g43");
        user.setPassword("32r32freg4ggr");
        user.setAge(-2);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user)
        );
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("32g43g34g43");
        user.setPassword("32r32freg4ggr");
        user.setAge(25);

        User actualUser = registrationService.register(user);
        assertEquals(user, actualUser);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_loginLength6_ok() {
        User user = new User();
        user.setLogin("qwerty");
        user.setPassword("123456");
        user.setAge(18);
        User actualUser = registrationService.register(user);

        assertEquals(user, actualUser);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_passwordLength6_ok() {
        User user = new User();
        user.setLogin("qwerty");
        user.setPassword("123456");
        user.setAge(18);
        User actualUser = registrationService.register(user);

        assertEquals(user, actualUser);
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_age18_ok() {
        User user = new User();
        user.setLogin("qwerty");
        user.setPassword("123456");
        user.setAge(18);
        User actualUser = registrationService.register(user);

        assertEquals(user, actualUser);
        assertTrue(Storage.people.contains(user));
    }
}
