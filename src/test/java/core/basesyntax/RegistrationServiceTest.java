package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static StorageDao storage;
    private static User user;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
        storage = new StorageDaoImpl();
    }

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setAge(20);
        user.setLogin("login123");
        user.setPassword("password123");

        Storage.setPeople(new ArrayList<>());
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    public void register_notValidUser_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_existsInStorage_notOk() {
        Storage.addPeople(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_validUserInDb_Ok() {
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });

        assertEquals(user, storage.get(user.getLogin()));
    }

    @Test
    public void register_validUserReturned_Ok() {
        AtomicReference<User> actual = new AtomicReference<>();
        assertDoesNotThrow(() -> {
            actual.set(registrationService.register(user));
        });
        assertEquals(user, actual.get());
    }

    @Test
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_edgeCaseLogin_notOk() {
        user.setLogin("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_edgeCaseLogin_Ok() {
        user.setLogin("123456");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_edgeCasePassword_notOk() {
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_edgeCasePassword_Ok() {
        user.setPassword("123456");
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_negativeAge_notOk() {
        user.setAge(-10);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_edgeCaseAge_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_edgeCaseAge_Ok() {
        user.setAge(18);
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_validUser_Ok() {
        assertDoesNotThrow(() -> {
            registrationService.register(user);
        });
    }
}
