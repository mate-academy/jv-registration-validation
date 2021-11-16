package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StorageDaoTests {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setAge(21);
        user.setLogin("mateAcademy");
        user.setPassword("mateAcademy2021");
    }
    
    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_notExistedUser_ok() {
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void register_validAge_ok() {
        assertEquals(registrationService.register(user)
                .getAge(), user.getAge());
    }

    @Test
    void register_validPassword_ok() {
        assertEquals(registrationService.register(user)
                .getPassword(), user.getPassword());
    }

    @Test
    void register_validLogin_ok() {
        assertEquals(registrationService.register(user)
                .getLogin(), user.getLogin());
    }

    @Test
    void register_alreadyExistUser_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(new User()));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessThanMinLengthLogin_notOk() {
        user.setLogin("1a2b3");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordEqualLogin_notOk() {
        user.setPassword("mateAcademy");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessThanNeedPassword_notOk() {
        user.setPassword("#$133");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }
}
