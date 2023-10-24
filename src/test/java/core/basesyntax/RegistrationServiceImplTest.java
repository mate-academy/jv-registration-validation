package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int INDEX_OF_FIRST_USER = 0;
    private static final int INDEX_OF_SECOND_USER = 1;
    private RegistrationService registrationService = new RegistrationServiceImpl();
    private List<User> users;

    @BeforeEach
    void createUserList() {
        User user1 = new User();
        user1.setId(38452693L);
        user1.setLogin("newboblogin");
        user1.setPassword("u48V8j207");
        user1.setAge(20);
        User user2 = new User();
        user2.setId(503684295L);
        user2.setLogin("alicelogin");
        user2.setPassword("i5y8QJ4vc");
        user2.setAge(25);
        users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
    }

    @Test
    public void register_nullUser_notOk() {
        User user = null;
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    public void register_nullId_notOk() {
        users.get(INDEX_OF_FIRST_USER).setId(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    public void register_nullLogin_notOk() {
        users.get(INDEX_OF_FIRST_USER).setLogin(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    public void register_nullPassword_notOk() {
        users.get(INDEX_OF_FIRST_USER).setPassword(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    public void register_nullAge_notOk() {
        users.get(INDEX_OF_FIRST_USER).setAge(null);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    public void register_existingUser_notOk() {
        Storage.people.add(users.get(INDEX_OF_SECOND_USER));
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }

    @Test
    public void register_emptyLogin_notOk() {
        users.get(INDEX_OF_SECOND_USER).setLogin("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }

    @Test
    public void register_tooShortLogin_notOk() {
        users.get(INDEX_OF_SECOND_USER).setLogin("log");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
        users.get(INDEX_OF_SECOND_USER).setLogin("short");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }

    @Test
    public void register_sufficientLoginLength_ok() {
        users.get(INDEX_OF_SECOND_USER).setLogin("abcdef");
        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
        users.get(INDEX_OF_FIRST_USER).setLogin("qwertyuiop");
        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    public void register_emptyPassword_notOk() {
        users.get(INDEX_OF_SECOND_USER).setPassword("");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }

    @Test
    public void register_tooShortPassword_notOk() {
        users.get(INDEX_OF_SECOND_USER).setPassword("log");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
        users.get(INDEX_OF_SECOND_USER).setPassword("short");
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }

    @Test
    public void register_sufficientPasswordLength_ok() {
        users.get(INDEX_OF_SECOND_USER).setPassword("abcdef");
        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
        users.get(INDEX_OF_FIRST_USER).setPassword("qwertyuiop");
        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    public void register_negativeAge_notOk() {
        users.get(INDEX_OF_FIRST_USER).setAge(-23);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
        users.get(INDEX_OF_FIRST_USER).setAge(-1);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    public void register_zeroAge_notOk() {
        users.get(INDEX_OF_FIRST_USER).setAge(0);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    public void register_insufficientAge_notOk() {
        users.get(INDEX_OF_FIRST_USER).setAge(5);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
        users.get(INDEX_OF_FIRST_USER).setAge(17);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
    }

    @Test
    public void register_sufficientAge_ok() {
        users.get(INDEX_OF_FIRST_USER).setAge(18);
        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_FIRST_USER));
        });
        users.get(INDEX_OF_SECOND_USER).setAge(26);
        assertDoesNotThrow(() -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }
}
