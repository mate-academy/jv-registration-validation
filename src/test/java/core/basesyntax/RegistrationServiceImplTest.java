package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private StorageDao storageDao;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    public void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(null));
    }

    @Test
    public void register_passwordIsNull_notOk() {
        Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(null));
    }

    @Test
    public void register_loginIsNull_notOk() {
        Assertions.assertThrows(RegistrationException.class, ()
                -> registrationService.register(null));
    }

    @Test
    public void register_validUser_Ok() {
        User expected = new User();
        expected.setAge(52);
        expected.setLogin("Uzhgorod");
        expected.setPassword("Pass@WorD");
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    public void register_loginAlreadyExists_notOk() {
        User user = new User();
        user.setAge(45);
        user.setLogin("Cherkassy");
        user.setPassword("MT69@11");
        Storage.people.add(user);
        User newUser = new User();
        newUser.setAge(30);
        newUser.setLogin("Cherkassy");
        newUser.setPassword("password123");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
        assertEquals("User with login Cherkassy already exists!", exception.getMessage());
    }

    @Test
    public void register_ageIsLessThanEighteen_notOk() {
        User youngUser = new User();
        youngUser.setAge(14);
        youngUser.setLogin("Kropyvnytskii");
        youngUser.setPassword("MYR3655");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(youngUser));
        assertEquals(String.format("You age should be at least %d y.o.", 18),
                exception.getMessage());
    }

    @Test
    public void register_shortUserLogin_notOk() {
        User shortUserLogin = new User();
        shortUserLogin.setAge(38);
        shortUserLogin.setLogin("Kyiv");
        shortUserLogin.setPassword("PO@1235");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(shortUserLogin));
        assertEquals("Your Login should contain %s or more symbols!", exception.getMessage());
    }

    @Test
    public void register_shortUserPassword_notOk() {
        User shortUserPassword = new User();
        shortUserPassword.setAge(45);
        shortUserPassword.setLogin("Mykolaiv");
        shortUserPassword.setPassword("KI23");
        RegistrationException exception = Assertions.assertThrows(RegistrationException.class,
                () -> registrationService.register(shortUserPassword));
        assertEquals("Your password should contain 6 or more symbols!", exception.getMessage());
    }
}
