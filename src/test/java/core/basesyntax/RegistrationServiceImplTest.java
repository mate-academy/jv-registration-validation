package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("Ivan_Ivanov");
        user.setAge(32);
        user.setPassword("Qq112345PP");
    }

    @Test
    void register_newUser_Ok() {
        User newUser = registrationService.register(user);
        assertEquals(user, newUser);
    }

    @Test
    void register_newUserSameInformation_notOk() {
        assertEquals(user, registrationService.register(user));
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_SpacesLogin_notOk() {
        user.setLogin("             ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_theSameLoginExists_notOk() {
        registrationService.register(user);
        User currentUser = new User();
        currentUser.setLogin("Ivan_Ivanov");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_spasesPassword_notOk() {
        user.setPassword("       ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LessThanMinPassword_notOk() {
        user.setPassword("qwedc");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_incurrectAge_notOk() {
        user.setAge(-10);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_zeroAge_notOk() {
        user.setAge(0);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessThanMinAge() {
        user.setAge(15);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}



