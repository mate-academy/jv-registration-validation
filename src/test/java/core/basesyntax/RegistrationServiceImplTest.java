package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUpBeforeEach() {
        Storage.people.clear();
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
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_invalidLogin_notOk() {
        User userWithInvalidLogin = new User();
        userWithInvalidLogin.setLogin("");
        userWithInvalidLogin.setPassword("ValidPassword");
        userWithInvalidLogin.setAge(25);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(userWithInvalidLogin));
    }

    @Test
    void register_invalidPassword_notOk() {
        User userWithInvalidPassword = new User();
        userWithInvalidPassword.setLogin("ValidLogin");
        userWithInvalidPassword.setPassword("");
        userWithInvalidPassword.setAge(25);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(userWithInvalidPassword));
    }

    @Test
    void register_notValidAge_notOk() {
        User userWithNotValidAge = new User();
        userWithNotValidAge.setLogin("ValidLogin");
        userWithNotValidAge.setPassword("ValidPassword");
        userWithNotValidAge.setAge(15);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(userWithNotValidAge));
    }

    @Test
    void register_invalidAge_notOk() {
        User userWithInvalidAge = new User();
        userWithInvalidAge.setLogin("ValidLogin");
        userWithInvalidAge.setPassword("ValidPassword");
        userWithInvalidAge.setAge(-10);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(userWithInvalidAge));
    }

    @Test
    void register_allDataNotValid_notOk() {
        User userWithAllNotValidData = new User();
        userWithAllNotValidData.setLogin("");
        userWithAllNotValidData.setPassword("");
        userWithAllNotValidData.setAge(0);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(userWithAllNotValidData));
    }

    @Test
    void register_nullLogin_notOk() {
        User userWithNullLogin = new User();
        userWithNullLogin.setLogin(null);
        userWithNullLogin.setPassword("ValidPassword");
        userWithNullLogin.setAge(22);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(userWithNullLogin));
    }

    @Test
    void register_nullPassword_notOk() {
        User userWithNullPassword = new User();
        userWithNullPassword.setLogin("ValidLogin");
        userWithNullPassword.setPassword(null);
        userWithNullPassword.setAge(22);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(userWithNullPassword));
    }

    @Test
    void register_nullData_notOk() {
        User userWithNullData = new User();
        userWithNullData.setLogin(null);
        userWithNullData.setPassword(null);
        userWithNullData.setAge(22);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(userWithNullData));
    }

    @Test
    void register_alreadyRegistered_notOk() {
        User bob = new User();
        bob.setLogin("BobLogin");
        bob.setPassword("BobSecret");
        bob.setAge(18);

        Storage.people.add(bob);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(bob));
    }
}
