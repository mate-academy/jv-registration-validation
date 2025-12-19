package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exeption.InvalidInputException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private User userOk;
    private User registeredUser;
    private User userPasswordNotOK;
    private User userLoginNotOk;
    private User userAgeNegativeNumberNotOk;
    private User userAgeNotOk;
    private User userPasswordNullNotOk;
    private User userLoginNullNotOk;
    private User userAgeNullNotOk;
    private RegistrationServiceImpl registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();

        userOk = new User();
        userOk.setLogin("Loggin");
        userOk.setPassword("Password");
        userOk.setAge(18);

        registeredUser = new User();
        registeredUser.setLogin("Logginn");
        registeredUser.setPassword("Password");
        registeredUser.setAge(18);

        storageDao.add(registeredUser);

        userPasswordNotOK = new User();
        userPasswordNotOK.setPassword("Pword");
        userPasswordNotOK.setLogin("PassNotOk");
        userPasswordNotOK.setAge(19);

        userLoginNotOk = new User();
        userLoginNotOk.setPassword("passwordOK");
        userLoginNotOk.setLogin("Lno");
        userLoginNotOk.setAge(23);

        userAgeNegativeNumberNotOk = new User();
        userAgeNegativeNumberNotOk.setAge(-18);
        userAgeNegativeNumberNotOk.setLogin("NegativeAge");
        userAgeNegativeNumberNotOk.setPassword("Password");

        userAgeNotOk = new User();
        userAgeNotOk.setPassword("Password");
        userAgeNotOk.setAge(11);
        userAgeNotOk.setLogin("AgeNotOk");

        userPasswordNullNotOk = new User();
        userPasswordNullNotOk.setLogin("LoginIsNull");
        userPasswordNullNotOk.setPassword(null);
        userPasswordNullNotOk.setAge(19);

        userLoginNullNotOk = new User();
        userLoginNullNotOk.setAge(30);
        userLoginNullNotOk.setPassword("Password");
        userLoginNullNotOk.setLogin(null);

        userAgeNullNotOk = new User();
        userAgeNullNotOk.setLogin("AgeIsNull");
        userAgeNullNotOk.setAge(null);
        userAgeNullNotOk.setPassword("Password");
    }

    @Test
    void register_validUser_ok() {
        registrationService.register(userOk);
        assertEquals(storageDao.get(userOk.getLogin()), userOk);
    }

    @Test
    void register_existingUser_notOk() {
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(registeredUser));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_userPassword_NotOK() {
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(userPasswordNotOK));
    }

    @Test
    void register_userLogin_NotOk() {
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(userLoginNotOk));
    }

    @Test
    void register_userAgeNegativeNumber_NotOk() {
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(userAgeNegativeNumberNotOk));
    }

    @Test
    void register_userAge_NotOk() {
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(userAgeNotOk));
    }

    @Test
    void register_userPasswordNull_NotOk() {
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(userPasswordNullNotOk));
    }

    @Test
    void register_userLogin_NullNotOk() {
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(userLoginNullNotOk));
    }

    @Test
    void register_userAgeNull_NotOk() {
        assertThrows(InvalidInputException.class,
                () -> registrationService.register(userAgeNullNotOk));
    }
}
