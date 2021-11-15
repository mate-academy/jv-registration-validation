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
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
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
    void register_notExistedUser_Ok() {
        assertEquals(user,registrationService.register(user));
    }

    @Test
    void register_validAge_Ok() {
        assertTrue(user.getAge() >= MIN_AGE);
    }

    @Test
    void register_validPassword_Ok() {
        assertTrue(user.getPassword().length() >= MIN_LENGTH);
    }

    @Test
    void register_validLogin_Ok() {
        assertTrue(user.getPassword().length() >= MIN_LENGTH);
    }

    @Test
    void register_alreadyExistUser_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LessThanMinLengthLogin_NotOk() {
        user.setLogin("1a2b3");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_NotOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void  register_emptyUser_NotOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(new User()));
    }

    @Test
    void register_PassEqualLogin_notOk() {
        user.setPassword("mateAcademy");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_LessThenZeroAge_NotOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_NotOk() {
        user.setAge(16);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_lessThanNeedPassword_NotOk() {
        user.setPassword("#$133");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }


}
