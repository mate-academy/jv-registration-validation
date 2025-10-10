package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private StorageDaoImpl storageDao;
    private RegistrationServiceImpl registrationService;

    @BeforeEach
    void initializeServices() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void clearStorage() {
        Storage.people.clear();
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setAge(null);
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin(null);
        user.setPassword("UserPassword");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin("UserLogin");
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lengthPassword_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin("UserLogin1");
        user.setPassword("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        User secondUser = new User();
        secondUser.setAge(20);
        secondUser.setLogin("UserLogin2");
        secondUser.setPassword("12345");
        assertThrows(RegistrationException.class, () -> registrationService.register(secondUser));
        User thirdUser = new User();
        thirdUser.setAge(20);
        thirdUser.setLogin("UserLogin2");
        thirdUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(thirdUser));
    }

    @Test
    void register_lengthPassword_ok() {
        User user = new User();
        user.setAge(20);
        user.setLogin("UserLogin1");
        user.setPassword("123456");
        assertEquals(user, registrationService.register(user),
                "User with proper password didn't registered");
        User secondUser = new User();
        secondUser.setAge(20);
        secondUser.setLogin("UserLogin2");
        secondUser.setPassword("12345678");
        assertEquals(secondUser, registrationService.register(secondUser),
                "User with proper password didn't registered");
    }

    @Test
    void register_lengthLogin_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin("Use");
        user.setPassword("123456");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        User secondUser = new User();
        secondUser.setAge(20);
        secondUser.setLogin("UserL");
        secondUser.setPassword("123456");
        assertThrows(RegistrationException.class, () -> registrationService.register(secondUser));
        User thirdUser = new User();
        thirdUser.setAge(20);
        thirdUser.setLogin("");
        thirdUser.setPassword("123456");
        assertThrows(RegistrationException.class, () -> registrationService.register(thirdUser));
    }

    @Test
    void register_lengthLogin_ok() {
        User user = new User();
        user.setAge(20);
        user.setLogin("UserLo");
        user.setPassword("123456");
        assertEquals(user, registrationService.register(user),
                "User with proper login didn't registered");
        User secondUser = new User();
        secondUser.setAge(20);
        secondUser.setLogin("UserLogin");
        secondUser.setPassword("123456");
        assertEquals(secondUser, registrationService.register(secondUser),
                "User with proper login didn't registered");
    }

    @Test
    void register_age_notOk() {
        User user = new User();
        user.setAge(-23);
        user.setLogin("UserLogin1");
        user.setPassword("123456");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        User secondUser = new User();
        secondUser.setAge(17);
        secondUser.setLogin("UserLogin2");
        secondUser.setPassword("123456");
        assertThrows(RegistrationException.class, () -> registrationService.register(secondUser));
        User thirdUser = new User();
        thirdUser.setAge(0);
        thirdUser.setLogin("UserLogin3");
        thirdUser.setPassword("123456");
        assertThrows(RegistrationException.class, () -> registrationService.register(thirdUser));
    }

    @Test
    void register_age_ok() {
        User user = new User();
        user.setAge(18);
        user.setLogin("UserLogin1");
        user.setPassword("123456");
        assertEquals(user, registrationService.register(user),
                "User with proper age didn't registered");
        User secondUser = new User();
        secondUser.setAge(99);
        secondUser.setLogin("UserLogin2");
        secondUser.setPassword("123456");
        assertEquals(secondUser, registrationService.register(secondUser),
                "User with proper age didn't registered");
    }

    @Test
    void register_duplicate_notOk() {
        User user = new User();
        user.setAge(20);
        user.setLogin("UserLogin1");
        user.setPassword("123456");
        storageDao.add(user);
        User secondUser = new User();
        secondUser.setAge(20);
        secondUser.setLogin("UserLogin1");
        secondUser.setPassword("123456");
        assertThrows(RegistrationException.class, () -> registrationService.register(secondUser));
    }
}
