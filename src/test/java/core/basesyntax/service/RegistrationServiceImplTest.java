package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.exceptions.UserAlreadyExistsException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User validUser1;
    private User validUser2;
    private User validUser3;
    private User invalidUserLowAge;
    private User invalidUserShortPassword;
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        validUser1 = new User();
        validUser2 = new User();
        validUser3 = new User();
        invalidUserLowAge = new User();
        invalidUserShortPassword = new User();

        validUser1.setLogin("validUser1");
        validUser1.setAge(21);
        validUser1.setPassword("validPassword1");

        validUser2.setLogin("validUser2");
        validUser2.setAge(28);
        validUser2.setPassword("validPassword2");

        validUser3.setLogin("validUser3");
        validUser3.setAge(38);
        validUser3.setPassword("validPassword3");

        invalidUserLowAge.setLogin("invalidUserLowAge");
        invalidUserLowAge.setAge(15);
        invalidUserLowAge.setPassword("validPasswordLowAge");

        invalidUserShortPassword.setLogin("invalidUserShortPassword");
        invalidUserShortPassword.setAge(18);
        invalidUserShortPassword.setPassword("inv");

    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(validUser1, registrationService.register(validUser1));
        assertEquals(1, Storage.people.size());
    }

    @Test
    void register_addThreeValidUsers_Ok() {
        assertEquals(validUser1, registrationService.register(validUser1));
        assertEquals(1, Storage.people.size());

        assertEquals(validUser2, registrationService.register(validUser2));
        assertEquals(2, Storage.people.size());

        assertEquals(validUser3, registrationService.register(validUser3));
        assertEquals(3, Storage.people.size());
    }

    @Test
    void register_checkValidUsersPresentInDB_Ok() {
        registrationService.register(validUser1);
        registrationService.register(validUser2);
        registrationService.register(validUser3);

        User actual1 = storageDao.get(validUser1.getLogin());
        User actual2 = storageDao.get(validUser2.getLogin());
        User actual3 = storageDao.get(validUser3.getLogin());

        assertEquals(validUser1, actual1);
        assertEquals(validUser2, actual2);
        assertEquals(validUser3, actual3);
    }

    @Test
    void register_addSameUser_NotOk() {
        registrationService.register(validUser1);
        assertThrows(UserAlreadyExistsException.class,
                () -> registrationService.register(validUser1));

        registrationService.register(validUser2);
        assertThrows(UserAlreadyExistsException.class,
                () -> registrationService.register(validUser2));
    }

    @Test
    void register_addInvalidUserLowAge_NotOk() {
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUserLowAge));
    }

    @Test
    void register_addInvalidUserShortPassword() {
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUserShortPassword));
    }

    @Test
    void register_addNull_NotOk() {
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(null));
    }
}
