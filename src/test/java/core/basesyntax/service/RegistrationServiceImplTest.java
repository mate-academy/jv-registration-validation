package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static core.basesyntax.db.Storage.people;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private StorageDao storage = new StorageDaoImpl();
    private User user = new User();
    private User userOk = new User();
    private User userWithDuplicateLogin = new User();
    private User userWithLessAge = new User();
    private User userWithLessPassword = new User();

    @BeforeEach
    void setUp(){
        user.setId(1L);
        user.setAge(100);
        user.setLogin("userLogin");
        user.setPassword("userPassword");

        userOk.setId(2L);
        userOk.setAge(100);
        userOk.setLogin("loginOk");
        userOk.setPassword("userOk");

        userWithDuplicateLogin.setId(3L);
        userWithDuplicateLogin.setAge(50);
        userWithDuplicateLogin.setLogin("loginOk");
        userWithDuplicateLogin.setPassword("userWithDuplicateLogin");

        userWithLessAge.setId(4L);
        userWithLessAge.setAge(15);
        userWithLessAge.setLogin("userWithLessAge");
        userWithLessAge.setPassword("userWithLessAge");

        userWithLessPassword.setId(5L);
        userWithLessPassword.setAge(50);
        userWithLessPassword.setLogin("userWithLessPassword");
        userWithLessPassword.setPassword("pass");
    }

    @Test
    void storageNotEmpty_Ok() {
        registrationService.register(user);
        boolean actual = people.isEmpty();
        assertFalse(actual);
    }

    @Test
    void suchLogin_NotOk() {
        registrationService.register(userOk);
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithDuplicateLogin),
                "Expected RuntimeException");
    }
    @Test
    void ageLessThan18_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithLessAge),
                "Expected RuntimeException");
    }
    @Test
    void passwordLessThan6_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(userWithLessPassword),
                "Expected RuntimeException");
    }
}