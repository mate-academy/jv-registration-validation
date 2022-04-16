package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User user1 = new User();
    private User user2 = new User();
    private User user3 = new User();
    private StorageDao storageDao = new StorageDaoImpl();
    private RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void setUp() {
        user1.setLogin("userLogin");
        user1.setPassword("password");
        user1.setAge(18);
        user2.setLogin("userLogin2");
        user2.setPassword("password2");
        user2.setAge(16);
        user3.setLogin("userLogin3");
        user3.setPassword("pass");
        user3.setAge(20);
    }

    @Test
    void userRegistered() {
        User actualUser = registrationService.register(user1);
        assertEquals(user1, actualUser);
        storageDao.add(actualUser);
        assertEquals(storageDao.get(actualUser.getLogin()), user1);
    }

    @Test
    void passwordLengthLessThenSixSymbols() {
        try {
            registrationService.register(user3);
        } catch (RuntimeException e) {
            return;
        }
        fail("test should fail -> password length less then 6 symbols");
    }

    @Test
    void user_isNull() {
        try {
            registrationService.register(null);
        } catch (RuntimeException e) {
            return;
        }
        fail("test should fail -> user is null");
    }

    @Test
    void checkUserLoginWithSameLogin() {
        storageDao.add(user1);
        storageDao.add(user2);
        try {
            registrationService.register(user1);
        } catch (RuntimeException e) {
            return;
        }
        fail("test should fail -> this login exist in DB");
    }

    @Test
    void userAgeLessThen18() {
        try {
            registrationService.register(user2);
        } catch (RuntimeException e) {
            return;
        }
        fail("Test should fall, your age is less then 18");
    }
}
