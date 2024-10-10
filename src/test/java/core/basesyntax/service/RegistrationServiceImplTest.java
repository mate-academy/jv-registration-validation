package core.basesyntax.service;

<<<<<<< HEAD
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
=======
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private RegistrationServiceImpl registrationService;
    private StorageDaoImpl storageDao;

    RegistrationServiceImplTest(StorageDaoImpl storageDao) {
        this.storageDao = storageDao;
    }

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
        registrationService = new RegistrationServiceImpl(storageDao);
>>>>>>> 0c691a3ae050bbbdfb54b37d74a2655f0caee0d9
    }

    @Test
    public void register_validUser_ok() {
<<<<<<< HEAD
        User user = new User("validLogin", "password123", 20);
        User registeredUser = registrationService.register(user);
        assertEquals(user, registeredUser);
=======
        User user = new User(2147L, "validLogin", "password123", 20);
        Storage.people.size();
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
        });
        assertEquals(user, "User must be registered correctly");
>>>>>>> 0c691a3ae050bbbdfb54b37d74a2655f0caee0d9
    }

    @Test
    public void register_existingLogin_notOk() {
<<<<<<< HEAD
        User user = new User("existingUser", "password123", 20);
        Storage.people.add(user);
        User newUser = new User("existingUser", "password123", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(newUser);
=======
        User user = new User(2147L, "existingUser", "password123", 20);
        Storage.people.size();
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> {
            registrationService.register(user);
>>>>>>> 0c691a3ae050bbbdfb54b37d74a2655f0caee0d9
        });
    }

    @Test
    public void register_loginTooShort_notOk() {
<<<<<<< HEAD
        User user = new User("short", "password123", 20);
        assertThrows(RegistrationException.class, () -> {
=======
        User user = new User(2147L, "short", "password123", 20);
        Storage.people.size();
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> {
>>>>>>> 0c691a3ae050bbbdfb54b37d74a2655f0caee0d9
            registrationService.register(user);
        });
    }

    @Test
    public void register_passwordTooShort_notOk() {
<<<<<<< HEAD
        User user = new User("validLogin", "pass", 20);
        assertThrows(RegistrationException.class, () -> {
=======
        User user = new User(2147L, "validLogin", "pass", 20);
        Storage.people.size();
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> {
>>>>>>> 0c691a3ae050bbbdfb54b37d74a2655f0caee0d9
            registrationService.register(user);
        });
    }

    @Test
    public void register_ageLessThan18_notOk() {
<<<<<<< HEAD
        User user = new User("validLogin", "password123", 17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_loginNull_notOk() {
        User user = new User(null, "password123", 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_passwordNull_notOk() {
        User user = new User("validLogin", null, 20);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_ageNull_notOk() {
        User user = new User("validLogin", "password123", null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_negativeAge_notOk() {
        User user = new User("validLogin", "password123", -5);
        assertThrows(RegistrationException.class, () -> {
=======
        User user = new User(2147L, "validLogin", "password123", 17);
        Storage.people.size();
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> {
>>>>>>> 0c691a3ae050bbbdfb54b37d74a2655f0caee0d9
            registrationService.register(user);
        });
    }
}
