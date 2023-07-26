package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.RegistrationException;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User firstUser = new User("firstUser", "123456", 22);
    private static final User secondUser = new User("secondUser", "myPassword", 16);
    private static final User thirdUser = new User("thirdUser", "small", 19);
    private static final User fourthUser = new User("fourthUser", "password", 18);
    private static final User fifthUser = new User("fifthUser", null, 21);
    private static final User sixthUser = new User("sixthUser", "password", null);
    private static final User seventhUser = new User(null, "validPassword", 19);
    private static final User eighthUser = new User("eighthUser", "validPassword", -1);
    private static final User ninthUser = new User("small", "validPassword", 18);
    private static final User sameFirstUser = new User("firstUser", "validPassword", 23);
    private RegistrationService service;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(null));
    }

    @Test
    void register_validCase_Ok() {
        User actual = service.register(firstUser);
        assertEquals(firstUser, actual);
        User actual2 = service.register(fourthUser);
        assertEquals(fourthUser, actual2);
        assertEquals(2, Storage.people.size());
    }

    @Test
    void register_ageLessMinAge_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(secondUser));
    }

    @Test
    void register_passwordLessMinLength_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(thirdUser));
    }

    @Test
    void register_nullPassword_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(fifthUser));
    }

    @Test
    void register_nullAge_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(sixthUser));
    }

    @Test
    void register_nullLogin_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(seventhUser));
    }

    @Test
    void register_negativeAge_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(eighthUser));
    }

    @Test
    void register_loginLessMinLength_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(ninthUser));
    }

    @Test
    void register_sameLogin_notOk() {
        User actual = service.register(firstUser);
        assertEquals(firstUser, actual);
        assertThrows(RegistrationException.class, () -> service.register(sameFirstUser));
    }
}












