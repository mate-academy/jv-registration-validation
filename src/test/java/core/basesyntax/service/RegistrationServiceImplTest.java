package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User validUser;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        validUser = new User("mateacademy", "password", 20);
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        Storage.people.add(validUser);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userNull_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_NotOk() {
        User nullLoginUser = new User(null, "password", 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullLoginUser));
    }

    @Test
    void register_nullPassword_NotOk() {
        User nullPasswordUser = new User("validlogin", null, 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullPasswordUser));
    }

    @Test
    void register_nullAge_NotOk() {
        User nullAgeUser = new User("validlogin", "validpassword", null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(nullAgeUser));
    }

    @Test
    void register_shortLogin_NotOk() {
        User shortLoginUser = new User("login", "validpassword", 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(shortLoginUser));
    }

    @Test
    void register_shortPassword_NotOk() {
        User shortPasswordUser = new User("validlogin", "12345", 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(shortPasswordUser));
    }

    @Test
    void register_ageLessThan18_NotOk() {
        User youngUser = new User("validlogin", "validpassword", 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(youngUser));
    }

    @Test
    void register_sameLogin_NotOk() {
        User sameLoginUser = new User("mateacademy", "password", 20);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(sameLoginUser));
    }

    @Test
    void register_addValidUser_Ok() {
        User validUser = new User("validlogin", "validpassword", 25);
        registrationService.register(validUser);
        assertEquals(validUser, storageDao.get(validUser.getLogin()));
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_addMinAgeLoginPassword_Ok() {
        User minAgeLoginPasswordUser = new User("loginn", "passwo", 18);
        registrationService.register(minAgeLoginPasswordUser);
        assertEquals(minAgeLoginPasswordUser, storageDao.get(minAgeLoginPasswordUser.getLogin()));
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_addValidUsers_Ok() {
        User firstUser = new User("firstlogin", "firstpassword", 25);
        User secondUser = new User("secondlogin", "secondpassword", 18);
        User thirdUser = new User("thirdlogin", "thirdpassword", 80);
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        registrationService.register(thirdUser);
        assertEquals(firstUser, storageDao.get(firstUser.getLogin()));
        assertEquals(secondUser, storageDao.get(secondUser.getLogin()));
        assertEquals(thirdUser, storageDao.get(thirdUser.getLogin()));
        assertEquals(4, Storage.people.size());
    }
}
