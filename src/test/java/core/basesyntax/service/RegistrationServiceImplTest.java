package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.List;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User nullUser = null;
    private static final User nullLoginUser = new User(null, "1234567", 11);
    private static final User nullPasswordUser = new User("????", null, 16);
    private static final User nullAgeUser = new User("????", "????????", null);
    private static final User illegalAgeUser = new User("Pudj2008", "asdfg2002", 13);
    private static final User illegalAgeSecondUser = new User("Phgedsa8", "asdhdr", -124);
    private static final User illegalPasswordLengthUser = new User("Pudj2002", "asd", 18);
    private static final User Bohdan = new User("Bohdan", "password", 58);
    private static final User Dan = new User("Dan", "NothingPassword", 68);
    private static final User Igor = new User("ar1n04cka", "ytrewq1236", 27);
    private static final User anotherIgor = new User("Igor1992", "Igor123456", 24);

    @Test
    void setNullUser_notOk() {
        RegistrationService service = new RegistrationServiceImpl();
        assertThrows(RuntimeException.class, () -> {
            service.register(nullUser);
        });
        assertTrue(Storage.people.isEmpty());
    }

    @Test
    void setNullLoginUser_notOk() {
        RegistrationService service = new RegistrationServiceImpl();
        assertThrows(RuntimeException.class, () -> {
            service.register(nullLoginUser);
        });
    }

    @Test
    void setNullPasswordUser_notOk() {
        RegistrationService service = new RegistrationServiceImpl();
        assertThrows(RuntimeException.class, () -> {
            service.register(nullPasswordUser);
        });
    }

    @Test
    void setNullAgeUser_notOk() {
        RegistrationService service = new RegistrationServiceImpl();
        assertThrows(RuntimeException.class, () -> {
            service.register(nullAgeUser);
        });
    }

    @Test
    void setMinorUser_notOk() {
        RegistrationService service = new RegistrationServiceImpl();
        assertThrows(RuntimeException.class, () -> {
            service.register(illegalAgeUser);
            service.register(illegalAgeSecondUser);
        });
    }

    @Test
    void setIllegalPasswordUser_noOk() {
        RegistrationService service = new RegistrationServiceImpl();
        assertThrows(RuntimeException.class, () -> {
            service.register(illegalPasswordLengthUser);
        });
    }

    @Test
    void goodExampleUser() {
        RegistrationService service = new RegistrationServiceImpl();
        service.register(Bohdan);
        service.register(Dan);
        service.register(Igor);
        service.register(anotherIgor);
        assertFalse(Storage.people.isEmpty());
        List<User> users = List.of(Bohdan, Dan, Igor, anotherIgor);
        assertEquals(users.size(), Storage.people.size());
    }
}
