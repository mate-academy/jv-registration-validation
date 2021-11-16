package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
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
    void null_age_not_ok() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(nullAgeUser);
        });
    }

    @Test
    void null_user_not_ok() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void short_password_not_ok() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(shortPasswordUser);
        });
    }

    @Test
    void underAge_not_ok() {
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(underAgeUser);
        });
    }

    @Test
    void same_login_not_ok() {
        registrationService.register(normalUser);
        assertThrows(RuntimeException.class, () -> {
            registrationService.register(sameLoginUser);
        });
    }

    @Test
    void normal_user_ok() {
        registrationService.register(normalUser);
        assertTrue(Storage.people.contains(normalUser));
        assertEquals(Storage.people.get(0), normalUser);
    }
}
