package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {

    RegistrationService registrationService;
    User normalUser;
    User underAgeUser;
    User nullLoginUser;
    User nullPasswordUser;
    User shortPasswordUser;
    User nullUser;
    User nullAgeUser;
    User sameLoginUser;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();

        normalUser = new User();
        normalUser.setLogin("Vasya");
        normalUser.setAge(19);
        normalUser.setPassword("abracadabra");

        sameLoginUser = new User();
        sameLoginUser.setLogin("Vasya");
        sameLoginUser.setAge(33);
        sameLoginUser.setPassword("JoshuaAshfieldMegaCool");

        underAgeUser = new User();
        underAgeUser.setLogin("Slavik");
        underAgeUser.setAge(15);
        underAgeUser.setPassword("MyHornyPony");

        nullLoginUser = new User();
        nullLoginUser.setLogin(null);
        nullLoginUser.setAge(19);
        nullLoginUser.setPassword("BestPassword");

        nullPasswordUser = new User();
        nullPasswordUser.setLogin("Alissa");
        nullPasswordUser.setAge(35);
        nullPasswordUser.setPassword(null);

        shortPasswordUser = new User();
        shortPasswordUser.setLogin("Anna");
        shortPasswordUser.setAge(19);
        shortPasswordUser.setPassword("qwer");

        nullUser = null;

        nullAgeUser = new User();
        nullAgeUser.setLogin("Fedir");
        nullAgeUser.setAge(null);
        nullAgeUser.setPassword("OctoPussy");
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(nullAgeUser);
        });
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_shortPassword_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(shortPasswordUser);
        });
    }

    @Test
    void register_underage_notOk() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(underAgeUser);
        });
    }

    @Test
    void register_sameLogin_notOk() {
        registrationService.register(normalUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(sameLoginUser);
        });
    }

    @Test
    void register_normalCase_ok() {
        registrationService.register(normalUser);
        assertTrue(Storage.people.contains(normalUser));
        assertEquals(Storage.people.get(0), normalUser);
    }
}
