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
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        validUser1 = new User();
        validUser2 = new User();

        validUser1.setLogin("validUser1");
        validUser1.setPassword("validPassword1");
        validUser1.setAge(21);

        validUser2.setLogin("validUser2");
        validUser2.setPassword("validPassword2");
        validUser2.setAge(28);
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
    void register_addTwoValidUsers_Ok() {
        assertEquals(validUser1, registrationService.register(validUser1));
        assertEquals(1, Storage.people.size());

        assertEquals(validUser2, registrationService.register(validUser2));
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_checkValidUsersPresentInDB_Ok() {
        registrationService.register(validUser1);
        registrationService.register(validUser2);

        User actualUser1 = storageDao.get(validUser1.getLogin());
        User actualUser2 = storageDao.get(validUser2.getLogin());

        assertEquals(validUser1, actualUser1);
        assertEquals(validUser2, actualUser2);
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
    void register_addInvalidUserNullAge_NotOk() {
        User invalidUserNullAge = new User();
        invalidUserNullAge.setLogin("InvalidUserNullLogin");
        invalidUserNullAge.setPassword("InvalidUser");
        invalidUserNullAge.setAge(null);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUserNullAge));
    }

    @Test
    void register_addInvalidUserNullLogin_NotOk() {
        User invalidUserNullLogin = new User();
        invalidUserNullLogin.setLogin(null);
        invalidUserNullLogin.setPassword("InvalidUser");
        invalidUserNullLogin.setAge(30);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUserNullLogin));
    }

    @Test
    void register_addInvalidUserNullPassword_NotOk() {
        User invalidUserNullPassword = new User();
        invalidUserNullPassword.setLogin("InvalidUserNullPassword");
        invalidUserNullPassword.setPassword(null);
        invalidUserNullPassword.setAge(15);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUserNullPassword));
    }

    @Test
    void register_addInvalidUserNegativeAge_NotOk() {
        User invalidUserNegativeAge = new User();
        invalidUserNegativeAge.setLogin("invalidUserNegativeAge");
        invalidUserNegativeAge.setPassword("invalidUserNegativeAge");
        invalidUserNegativeAge.setAge(-2);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUserNegativeAge));
    }

    @Test
    void register_addInvalidUserLowAge_NotOk() {
        User invalidUserLowAge = new User();
        invalidUserLowAge.setLogin("invalidUserLowAge");
        invalidUserLowAge.setPassword("validPasswordLowAge");
        invalidUserLowAge.setAge(15);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUserLowAge));
    }

    @Test
    void register_addInvalidUserShortPassword_NotOk() {
        User invalidUserShortPassword = new User();
        invalidUserShortPassword.setLogin("invalidUserShortPassword");
        invalidUserShortPassword.setPassword("inv");
        invalidUserShortPassword.setAge(18);
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(invalidUserShortPassword));
    }

    @Test
    void register_addNull_NotOk() {
        assertThrows(InvalidDataException.class,
                () -> registrationService.register(null));
    }
}
