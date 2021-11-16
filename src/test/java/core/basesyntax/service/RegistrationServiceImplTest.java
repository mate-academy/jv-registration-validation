package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;
    private static User standardUser;

    @BeforeAll
    static void beforeAll(){
        service = new RegistrationServiceImpl();
        standardUser = new User();
    }

    @BeforeEach
    void setUp(){
        Storage.people.clear();
        standardUser.setLogin("mate");
        standardUser.setPassword("123456");
        standardUser.setAge(19);
    }

    @Test
    public void correctUserCheck_Ok(){
        assertEquals(standardUser, service.register(standardUser));
    }

    @Test
    public void userIsNullCheck_notOk(){
        assertThrows(RuntimeException.class, () -> service.register(null));
    }

    @Test
    public void ageIsNullCheck_notOk(){
        standardUser.setAge(null);
        assertThrows(RuntimeException.class, () -> service.register(standardUser));
    }

    @Test
    public void ageIsNegativeCheck_notOk(){
        standardUser.setAge(-3);
        assertThrows(RuntimeException.class, () -> service.register(standardUser));
    }

    @Test
    public void loginIsNullCheck_notOk(){
        standardUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> service.register(standardUser));
    }

    @Test
    public void passwordIsNullCheck_notOk(){
        standardUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> service.register(standardUser));
    }

    @Test
    public void sameLoginCheck_notOk(){
        service.register(standardUser);
        assertThrows(RuntimeException.class, () -> service.register(standardUser));
    }

    @Test
    public void smallAgeCheck_notOk(){
        standardUser.setAge(17);
        assertThrows(RuntimeException.class, () -> service.register(standardUser));
    }

    @Test
    public void wrongPasswordCheck_notOk(){
        standardUser.setPassword("11111");
        assertThrows(RuntimeException.class, () -> service.register(standardUser));
    }
}
