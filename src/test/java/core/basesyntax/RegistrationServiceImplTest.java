package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private final StorageDao storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        Storage.people.clear();
    }

    @Test
    void register_oneValidUser_ok() {
        User bob = new User();
        bob.setLogin("BobLogin");
        bob.setPassword("BobSecret");
        bob.setAge(18);

        User actual = registrationService.register(bob);
        assertEquals(storageDao.get(bob.getLogin()), actual,
                "Returned user must be equals with saved user!");
    }

    @Test
    void register_fewValidUser_ok() {
        User bob = new User();
        bob.setLogin("BobLogin");
        bob.setPassword("BobSecret");
        bob.setAge(18);

        User alice = new User();
        alice.setLogin("AliceLogin");
        alice.setPassword("AliceSecret");
        alice.setAge(22);

        User jack = new User();
        jack.setLogin("JackLogin");
        jack.setPassword("JackSecret");
        jack.setAge(31);

        User actualBob = registrationService.register(bob);
        assertEquals(storageDao.get(bob.getLogin()), actualBob,
                "Returned user must be equals with saved user!");
        User actualAlice = registrationService.register(alice);
        assertEquals(storageDao.get(alice.getLogin()), actualAlice,
                "Returned user must be equals with saved user!");
        User actualJack = registrationService.register(jack);
        assertEquals(storageDao.get(jack.getLogin()), actualJack,
                "Returned user must be equals with saved user!");
        assertEquals(Storage.people.size(), 3,
                "Number of saved users must be 3!");
    }

    @Test
    void register_marginalData_ok() {
        User bob = new User();
        bob.setLogin("666666");
        bob.setPassword("666666");
        bob.setAge(18);

        User actual = registrationService.register(bob);
        assertEquals(storageDao.get(bob.getLogin()), actual,
                "Returned user must be equals with saved user!");
    }

    @Test
    void register_nullUser_notOk() {
        try {
            registrationService.register(null);
        } catch (InvalidUserDataException invalidUserDataException) {
            assertEquals("User must not be null!", invalidUserDataException.getMessage());
            return;
        }
        fail("You must throw 'InvalidUserDataException'"
                + " with message: 'User must not be null!' if user = null.");
    }

    @Test
    void register_invalidLogin_notOk() {
        User userWithInvalidLogin = new User();
        userWithInvalidLogin.setLogin("");
        userWithInvalidLogin.setPassword("ValidPassword");
        userWithInvalidLogin.setAge(25);
        try {
            registrationService.register(userWithInvalidLogin);
        } catch (InvalidUserDataException invalidUserDataException) {
            assertEquals("Login must be at least 6 characters. ",
                    invalidUserDataException.getMessage());
            return;
        }
        fail("You must throw 'InvalidUserDataException'"
                 + " with message: 'Login must be at least 6 characters. '"
                 + " if login of user less then 6 character.");
    }

    @Test
    void register_invalidPassword_notOk() {
        User userWithInvalidPassword = new User();
        userWithInvalidPassword.setLogin("ValidLogin");
        userWithInvalidPassword.setPassword("");
        userWithInvalidPassword.setAge(25);
        try {
            registrationService.register(userWithInvalidPassword);
        } catch (InvalidUserDataException invalidUserDataException) {
            assertEquals("Password must be at least 6 characters. ",
                    invalidUserDataException.getMessage());
            return;
        }
        fail("You must throw 'InvalidUserDataException'"
                + " with message: 'Password must be at least 6 characters. '"
                + " if password of user less then 6 character.");
    }

    @Test
    void register_invalidAge_notOk() {
        User userWithInvalidAge = new User();
        userWithInvalidAge.setLogin("ValidLogin");
        userWithInvalidAge.setPassword("ValidPassword");
        userWithInvalidAge.setAge(15);
        try {
            registrationService.register(userWithInvalidAge);
        } catch (InvalidUserDataException invalidUserDataException) {
            assertEquals("User must be at least 18 years old.",
                    invalidUserDataException.getMessage());
            return;
        }
        fail("You must throw 'InvalidUserDataException'"
                + " with message: 'User must be at least 18 years old.'"
                + " if user under 18 years of age.");
    }

    @Test
    void register_allDataNotValid_notOk() {
        User userWithAllNotValidData = new User();
        userWithAllNotValidData.setLogin("");
        userWithAllNotValidData.setPassword("");
        userWithAllNotValidData.setAge(0);
        try {
            registrationService.register(userWithAllNotValidData);
        } catch (InvalidUserDataException invalidUserDataException) {
            assertEquals("Login must be at least 6 characters. "
                     + "Password must be at least 6 characters. "
                     + "User must be at least 18 years old.",
                    invalidUserDataException.getMessage());
            return;
        }
        fail("You must throw 'InvalidUserDataException'"
                + " with message: 'Login must be at least 6 characters. "
                + "Password must be at least 6 characters. User must be at least 18 years old.'"
                + " if login and password of user have less then 6 characters"
                + " and less than 18 years old.");
    }

    @Test
    void register_nullLogin_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setLogin(null);
        userWithNullLogin.setPassword("ValidPassword");
        userWithNullLogin.setAge(22);
        try {
            registrationService.register(userWithNullLogin);
        } catch (InvalidUserDataException invalidUserDataException) {
            assertEquals("Login must be at least 6 characters. ",
                    invalidUserDataException.getMessage());
            return;
        }
        fail("You must throw 'InvalidUserDataException'"
                + " with message: 'Login must be at least 6 characters. '"
                + " if login of user is null.");
    }

    @Test
    void register_nullPassword_notOk() {
        User userWithNullPassword = new User();
        userWithNullPassword.setLogin("ValidLogin");
        userWithNullPassword.setPassword(null);
        userWithNullPassword.setAge(22);
        try {
            registrationService.register(userWithNullPassword);
        } catch (InvalidUserDataException invalidUserDataException) {
            assertEquals("Password must be at least 6 characters. ",
                    invalidUserDataException.getMessage());
            return;
        }
        fail("You must throw 'InvalidUserDataException'"
                + " with message: 'Login must be at least 6 characters. '"
                + " if password of user is null.");
    }

    @Test
    void register_nullData_notOk() {
        User userWithNullData = new User();
        userWithNullData.setLogin(null);
        userWithNullData.setPassword(null);
        userWithNullData.setAge(22);
        try {
            registrationService.register(userWithNullData);
        } catch (InvalidUserDataException invalidUserDataException) {
            assertEquals("Login must be at least 6 characters. "
                    + "Password must be at least 6 characters. ",
                    invalidUserDataException.getMessage());
            return;
        }
        fail("You must throw 'InvalidUserDataException'"
                + " with message: 'Login must be at least 6 characters. "
                + "Login must be at least 6 characters. '"
                + " if login and password of user are null.");
    }

    @Test
    void register_alreadyRegistered_notOk() {
        User bob = new User();
        bob.setLogin("BobLogin");
        bob.setPassword("BobSecret");
        bob.setAge(18);

        registrationService.register(bob);
        try {
            registrationService.register(bob);
        } catch (InvalidUserDataException invalidUserDataException) {
            assertEquals("Such user has already registered!",
                    invalidUserDataException.getMessage());
            return;
        }
        fail("You must throw 'InvalidUserDataException'"
                + " with message: 'Such user has already registered!'"
                + " if such user has already in storage.");
    }
}
