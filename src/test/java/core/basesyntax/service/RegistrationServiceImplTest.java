package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void registration_UserLoginIsNull_NotOk() {
        firstUser.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_UserLoginIsEmpty_NotOk() {
        firstUser.setLogin("");
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_UserNameLessThanSixSymbols_NotOk() {
        firstUser.setLogin("exd@ukr.net");
        assertNull(registrationService.register(firstUser));
    }

    @Test
    void registration_UserNameIsFiveSymbolsEdgeCase_NotOk() {
        firstUser.setLogin("exgsd@ukr.net");
        assertNull(registrationService.register(firstUser));
    }

    @Test
    void registration_UserNameIsSixSymbolsEdgeCase_Ok() {
        firstUser.setLogin("exampl@hotmail.com");
        assertNotNull(registrationService.register(firstUser));
        assertEquals(firstUser, storageDao.get(firstUser.getLogin()));
    }

    @Test
    void registration_UserNameIsMoreSixSymbols_Ok() {
        assertNotNull(registrationService.register(firstUser));
        assertEquals(firstUser, storageDao.get(firstUser.getLogin()));
    }

    @Test
    void registration_ValidUser_Ok() {
        assertNotNull(registrationService.register(firstUser));
        assertEquals(firstUser, storageDao.get(firstUser.getLogin()));
    }

    @Test
    void registration_NotValidUser_NotOk() {
        firstUser.setLogin("ex@gsd@ukr.net");
        assertNull(registrationService.register(firstUser),"Incorrect user login");
    }

    @Test
    void registration_AlreadyRegisteredUser_NotOk() {
        Storage.people.add(firstUser);
        assertNull(registrationService.register(firstUser));
    }

    @Test
    void registration_SeveralValidUsers_Ok() {
        assertNotNull(registrationService.register(firstUser));
        assertNotNull(registrationService.register(secondUser));
        assertEquals(firstUser, storageDao.get(firstUser.getLogin()));
        assertEquals(secondUser, storageDao.get(secondUser.getLogin()));
    }

    @Test
    void registration_UserPasswordIsNull_NotOk() {
        firstUser.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_UserPasswordIsEmpty_NotOk() {
        firstUser.setPassword("");
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_UserPasswordIsLessSixSymbols_NotOk() {
        firstUser.setPassword("1A@");
        assertNull(registrationService.register(firstUser));
    }

    @Test
    void registration_UserPasswordIsFiveSymbolsEdgeCase_NotOk() {
        firstUser.setPassword("1A@n!");
        assertNull(registrationService.register(firstUser));
    }

    @Test
    void registration_UserPasswordIsSixSymbolsEdgeCase_Ok() {
        firstUser.setPassword("1A@n!$");
        assertNotNull(registrationService.register(firstUser));
        assertEquals(firstUser, storageDao.get(firstUser.getLogin()));
    }

    @Test
    void registration_UserPasswordIsMoreSixSymbols_Ok() {
        assertNotNull(registrationService.register(firstUser));
        assertEquals(firstUser, storageDao.get(firstUser.getLogin()));
    }

    @Test
    void registration_AgeUserIsNull_NotOk() {
        firstUser.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_NegativeAgeUserLessThan18_NotOk() {
        firstUser.setAge(-6);
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_AgeUserLessThan18_NotOk() {
        firstUser.setAge(14);
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_AgeUserIsSeventeenEdgeCase_NotOk() {
        firstUser.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(firstUser));
    }

    @Test
    void registration_AgeUserIsEighteenEdgeCase_Ok() {
        assertNotNull(registrationService.register(firstUser));
        assertEquals(firstUser, storageDao.get(firstUser.getLogin()));
    }

    @Test
    void registration_AgeUserIsMoreEighteen_Ok() {
        assertNotNull(registrationService.register(secondUser));
        assertEquals(secondUser, storageDao.get(secondUser.getLogin()));
    }
}
