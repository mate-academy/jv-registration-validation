package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final User firstUser = new User();
    private final User secondUser = new User();

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @BeforeEach
    void setUp() {
        firstUser.setId(1L);
        firstUser.setLogin("example_1+2.Q-r@ukr.net");
        firstUser.setPassword("q!W_r-%hf");
        firstUser.setAge(18);
        secondUser.setLogin("example@gmail.com");
        secondUser.setId(2L);
        secondUser.setPassword("qwerty");
        secondUser.setAge(19);
    }

    @Test
    void registration_userLoginIsNull_NotOk() {
        firstUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_userLoginIsEmpty_NotOk() {
        firstUser.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_userPasswordIsNull_NotOk() {
        firstUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_userPasswordIsEmpty_NotOk() {
        firstUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_AgeLessThan18_NotOk() {
        firstUser.setAge(-6);
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_AgeIsMore18_Ok() {
        assertNotNull(registrationService.register(firstUser));
    }

    @Test
    void registration_userNameLessThanSixSymbols_NotOk() {
        firstUser.setLogin("exgsd@ukr.net");
        assertNull(registrationService.register(firstUser));
    }

    @Test
    void registration_userNameIsMoreFiveSymbols_Ok() {
        assertNotNull(registrationService.register(firstUser));
    }

    @Test
    void registration_validUser_Ok() {
        assertNotNull(registrationService.register(firstUser));
    }

    @Test
    void registration_notValidUser_NotOk() {
        firstUser.setLogin("ex@gsd@ukr.net");
        assertNull(registrationService.register(firstUser));
    }

    @Test
    void registration_AlreadyRegisteredUser_NotOk() {
        Storage.people.add(firstUser);
        assertNull(registrationService.register(firstUser));
    }

    @Test
    void registration_addAndGetValidUsers_Ok() {
        assertNotNull(registrationService.register(firstUser));
        assertNotNull(registrationService.register(secondUser));
        assertNotNull(storageDao.get(firstUser.getLogin()));
        assertNotNull(storageDao.get(secondUser.getLogin()));
    }

    @Test
    void registration_userPasswordLessThanSixSymbols_NotOk() {
        firstUser.setPassword("1A@n!");
        assertNull(registrationService.register(firstUser));
    }

    @Test
    void registration_userPasswordIsMoreFiveSymbols_Ok() {
        assertNotNull(registrationService.register(firstUser));
    }
}
