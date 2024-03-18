package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user1;
    private User user2;
    private User user3;

    @BeforeAll
    public static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void setUpEach() {
        Storage.people.clear();
        user1 = new User();
        user1.setLogin("test_user");
        user1.setPassword("test_pass1");
        user1.setAge(18);

        user2 = new User();
        user2.setLogin("test2_user");
        user2.setPassword("test_pass2");
        user2.setAge(19);

        user3 = new User();
        user3.setLogin("test3_user");
        user3.setPassword("test_pass3");
        user3.setAge(20);
    }

    @AfterEach
    public void tearDownEach() {
        Storage.people.clear();
    }

    @Test
    public void loginDuplicate_notOk() {
        User user = new User();
        user.setLogin("Salana");
        user.setPassword("123456");
        user.setAge(18);
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    public void register_newUserAdd_ok() {
        User user = new User();
        user.setLogin("Salana");
        user.setPassword("123456");
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    public void register_nullUser_notOk() {
        user1 = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_nullLogin_notOk() {
        user1.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_ShortLogin_notOk() {
        user1.setLogin("ee45k");
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_nullPassword_notOk() {
        user1.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_nullAge_notOk() {
        user1.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_negativeAge_notOk() {
        user1.setAge(-20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_edgeCase_notOk() {
        user1.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    public void register_underage_notOk() {
        user1.setAge(15);
        assertThrows(RegistrationException.class, () -> registrationService.register(user1));
        assertTrue(Storage.people.isEmpty());
    }
}
