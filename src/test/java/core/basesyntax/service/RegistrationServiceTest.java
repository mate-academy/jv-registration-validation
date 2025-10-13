package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
        registrationService = null;
        storageDao = null;
    }

    @Test
    void register_validUser_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);
        
        User registeredUser = registrationService.register(user);
        
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
        assertEquals(user, storageDao.get(user.getLogin()));
    }
    
    @Test
    void register_nullUser_notOk() {
        assertThrows(ValidationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setPassword("validPassword2");
        user.setAge(20);
        
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin("short");
        user.setPassword("validPassword3");
        user.setAge(20);
        
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_duplicateLogin_notOk() {
        User user1 = new User();
        user1.setLogin("validLogin2");
        user1.setPassword("validPassword4");
        user1.setAge(20);
        
        User user2 = new User();
        user2.setLogin("validLogin2");
        user2.setPassword("anotherPassword4");
        user2.setAge(25);

        storageDao.add(user1);
        
        assertThrows(ValidationException.class,
                () -> registrationService.register(user2));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin5");
        user.setAge(20);
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin6");
        user.setPassword("short");
        user.setAge(20);
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin7");
        user.setPassword("validPassword");
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_underageUser_notOk() {
        User user = new User();
        user.setLogin("validLogin8");
        user.setPassword("validPassword");
        user.setAge(17);
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginExactlyMinLength_Ok() {
        User user = new User();
        user.setLogin("validLogin9");
        user.setPassword("validPassword9");
        user.setAge(20);
        User saved = registrationService.register(user);
        assertNotNull(saved);
        assertEquals(user, storageDao.get("validLogin9"));
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setPassword("validPassword10");
        user.setAge(20);
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(null, storageDao.get(""));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin10");
        user.setPassword("");
        user.setAge(20);
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(null, storageDao.get("validLogin10"));
    }

    @Test
    void register_ageIs18_ok() {
        User user = new User();
        user.setLogin("validLogin11");
        user.setPassword("validPassword11");
        user.setAge(18);
        User saved = registrationService.register(user);
        assertNotNull(saved);
        assertEquals(user, storageDao.get("validLogin11"));
    }

    @Test
    void register_zeroAge_NotOk() {
        User user = new User();
        user.setLogin("validLogin12");
        user.setPassword("validPassword12");
        user.setAge(0);
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(null, storageDao.get("validLogin12"));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = new User();
        user.setLogin("validLogin13");
        user.setPassword("validPassword13");
        user.setAge(-1);
        assertThrows(ValidationException.class,
                () -> registrationService.register(user));
        assertEquals(null, storageDao.get("validLogin13"));
    }
}
