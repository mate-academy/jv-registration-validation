package core.basesyntax;

import core.basesyntax.exceptions.InvalidDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(users.get(INDEX_OF_SECOND_USER));
        });
    }
}
