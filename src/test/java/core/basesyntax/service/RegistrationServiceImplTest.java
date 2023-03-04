package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final User correctUser = new User();
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeEach
    void correctUser() {
        correctUser.setLogin("Travis");
        correctUser.setAge(55);
        correctUser.setId(1L);
        correctUser.setPassword("123456789");
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userId_notOk() {
        correctUser.setId(-1L);
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
        correctUser.setId(0L);
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
    }

    @Test
    void register_login_notOk() {
        correctUser.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(correctUser));
        correctUser.setLogin("");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(correctUser));
    }

    @Test
    void register_age_notOk() {
        correctUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
        correctUser.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
        correctUser.setAge(220);
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
        correctUser.setAge(-6);
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
    }

    @Test
    void register_password_notOk() {
        correctUser.setPassword("123");
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
        correctUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
    }

    @Test
    void storage_add_notOk() {
        storageDao.add(correctUser);
        storageDao.add(correctUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
    }

    @Test
    void storage_add_Ok() {
        storageDao.add(correctUser);

        correctUser.setLogin("Stas");
        correctUser.setAge(18);
        correctUser.setId(1L);
        correctUser.setPassword("987654321");
        storageDao.add(correctUser);
        assertThrows(RegistrationException.class, () -> registrationService.register(correctUser));
    }

    @Test
    void register_Ok() {
        registrationService.register(correctUser);
        final User firstActualValue = storageDao.get(correctUser.getLogin());

        User user2 = new User();
        user2.setLogin("Stas");
        user2.setAge(18);
        user2.setId(2L);
        user2.setPassword("987654321");
        registrationService.register(user2);
        final User secondActualValue = storageDao.get(user2.getLogin());

        User user3 = new User();
        user3.setLogin("Sveta");
        user3.setAge(22);
        user3.setId(3L);
        user3.setPassword("987654321");
        registrationService.register(user3);
        final User thirdActualValue = storageDao.get(user3.getLogin());

        assertEquals(storageDao.get("Travis"), firstActualValue);
        assertEquals(storageDao.get("Stas"), secondActualValue);
        assertEquals(storageDao.get("Sveta"), thirdActualValue);
    }
}
