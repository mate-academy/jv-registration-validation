package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static StorageDao storageDao;
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        User user1 = new User();
        user1.setAge(23);
        user1.setLogin("Alice");
        user1.setPassword("validpassword");
        storageDao.add(user1);
    }

    @BeforeEach
    void setUp(){
        user = new User();
    }

    @Test
    void register_NullUser_NotOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullAge_NotOk() {
        user.setLogin("Kate");
        user.setAge(null);
        user.setPassword("1234567");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NullLogin_NotOk(){
        user.setLogin(null);
        user.setAge(45);
        user.setPassword("fkjdk890");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_EmtyLogin_NotOk(){
        user.setLogin("");
        user.setAge(45);
        user.setPassword("fkjdk890");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }


    @Test
    void register_NullPassword_NotOk(){
        user.setLogin("Slavik");
        user.setAge(45);
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_SameLogin_NotOk(){
    user.setLogin("Alice");
    user.setAge(45);
    user.setPassword("78ghnurkk");
    assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_PasswordLessSixSymbol_NotOk(){
        user.setLogin("Olga");
        user.setAge(39);
        user.setPassword("78ghn");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ValidData_Ok(){
        user.setLogin("Andrew");
        user.setAge(32);
        user.setPassword("password");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_AgeLess18_NotOk(){
        user.setLogin("Katrin");
        user.setAge(13);
        user.setPassword("passwordd");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_NegativeAge_NotOk(){
        user.setLogin("Jack");
        user.setAge(-23);
        user.setPassword("somepassword");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}