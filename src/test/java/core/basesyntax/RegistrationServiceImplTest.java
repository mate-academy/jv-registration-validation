package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static RegistrationService registrationService;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();

    }

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
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
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(newUser));
        assertEquals("User with login Cherkassy already exists!", exception.getMessage());

    }

    @Test
    public void register_shortUserLogin_notOk() {
        User shortUserLogin = new User();
        shortUserLogin.setAge(38);
        shortUserLogin.setLogin("Kyiv");
        shortUserLogin.setPassword("PO@1235");
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(shortUserLogin));
        assertEquals("Your Login should contain %s or more symbols!", exception.getMessage());
    }

    @Test
    public void register_shortUserPassword_notOk() {
        User shortUserPassword = new User();
        shortUserPassword.setAge(45);
        shortUserPassword.setLogin("Mykolaiv");
        shortUserPassword.setPassword("KI23");
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(shortUserPassword));
        assertEquals("Your password should contain 6 or more symbols!", exception.getMessage());
    }

    @Test
    public void register_ageIsLessThanEighteen_notOk() {
        User youngUser = new User();
        youngUser.setAge(14);
        youngUser.setLogin("Kropyvnytskii");
        youngUser.setPassword("MYR3655");
        RegistrationException exception = assertThrows(RegistrationException.class,
                () -> registrationService.register(youngUser));
        assertEquals(String.format("You age should be at least %d y.o.", 18),
                exception.getMessage());
    }

    @Test
    public void register_AgeIsNull_Ok() {
        User userAgeNull = new User();
        userAgeNull.setAge(null);
        userAgeNull.setLogin("Vinnytsya");
        userAgeNull.setPassword("null");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userAgeNull);
        });
    }

    @Test
    public void register_passwordIsNull_Ok() {
        User userPasswordNull = new User();
        userPasswordNull.setAge(25);
        userPasswordNull.setLogin("Kharkiv");
        userPasswordNull.setPassword("null");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userPasswordNull);
        });
    }

    @Test
    public void register_loginIsNull_Ok() {
        User userLoginNull = new User();
        userLoginNull.setAge(68);
        userLoginNull.setLogin(null);
        userLoginNull.setPassword("Woi23%2");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userLoginNull);
        });
    }
}
