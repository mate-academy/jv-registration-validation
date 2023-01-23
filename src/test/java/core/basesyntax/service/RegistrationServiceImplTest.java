package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User validUserOne;
    private User validUserTwo;
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        validUserOne = new User();
        validUserTwo = new User();

        validUserOne.setLogin("validUserOne");
        validUserOne.setPassword("validPassword1");
        validUserOne.setAge(21);

        validUserTwo.setLogin("validUserTwo");
        validUserTwo.setPassword("validP");
        validUserTwo.setAge(18);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(validUserOne, registrationService.register(validUserOne));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_addTwoValidUsers_Ok() {
        assertEquals(validUserOne, registrationService.register(validUserOne));
        assertEquals(1, Storage.people.size());

        assertEquals(validUserTwo, registrationService.register(validUserTwo));
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_checkValidUsersPresentInDB_Ok() {
        registrationService.register(validUserOne);
        registrationService.register(validUserTwo);

        User actualUser1 = storageDao.get(validUserOne.getLogin());
        User actualUser2 = storageDao.get(validUserTwo.getLogin());

        assertEquals(validUserOne, actualUser1);
        assertEquals(validUserTwo, actualUser2);
    }

    @Test
    void register_addSameUser_NotOk() {
        registrationService.register(validUserOne);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserOne));

        registrationService.register(validUserTwo);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserTwo));
    }

    @Test
    void register_addInvalidUserNullAge_NotOk() {
        validUserOne.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserOne));

        validUserTwo.setAge(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserTwo));
    }

    @Test
    void register_addInvalidUserNullLogin_NotOk() {
        validUserOne.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserOne));

        validUserTwo.setLogin(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserTwo));
    }

    @Test
    void register_addInvalidUserNullPassword_NotOk() {
        validUserOne.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserOne));

        validUserTwo.setPassword(null);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserTwo));
    }

    @Test
    void register_addInvalidUserNegativeAge_NotOk() {
        validUserOne.setAge(-100);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserOne));

        validUserTwo.setAge(-1);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserTwo));
    }

    @Test
    void register_addInvalidUserLowAge_NotOk() {
        validUserOne.setAge(17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserOne));

        validUserTwo.setAge(10);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserTwo));
    }

    @Test
    void register_addInvalidUserShortPassword_NotOk() {
        validUserOne.setPassword("1");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserOne));

        validUserTwo.setPassword("inval");
        assertThrows(RegistrationException.class,
                () -> registrationService.register(validUserTwo));
    }

    @Test
    void register_addNull_NotOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }
}
