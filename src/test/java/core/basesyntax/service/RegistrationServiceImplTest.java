package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
    public void userIsNullCheck_NotOk(){
        assertThrows(RuntimeException.class, () ->{
            service.register(null);
        });
    }

    @Test
    public void ageIsNullCheck_NotOk(){
        standardUser.setAge(null);
        assertThrows(RuntimeException.class, () ->{
            service.register(standardUser);
        });
    }

    @Test
    public void ageIsNegativeCheck_NotOk(){
        standardUser.setAge(-3);
        assertThrows(RuntimeException.class, () ->{
            service.register(standardUser);
        });
    }

    @Test
    public void loginIsNullCheck_NotOk(){
        standardUser.setLogin(null);
        assertThrows(RuntimeException.class, () ->{
            service.register(standardUser);
        });
    }

    @Test
    public void passwordIsNullCheck_NotOk(){
        standardUser.setPassword(null);
        assertThrows(RuntimeException.class, () ->{
            service.register(standardUser);
        });
    }

    @Test
    public void sameLoginCheck_NotOk(){
        service.register(standardUser);
        assertThrows(RuntimeException.class, () ->{
            service.register(standardUser);
        });
    }

    @Test
    public void smallAgeCheck_NotOk(){
        standardUser.setAge(2);
        assertThrows(RuntimeException.class, () ->{
            service.register(standardUser);
        });
    }

    @Test
    public void wrongPasswordCheck_NotOk(){
        standardUser.setPassword("111");
        assertThrows(RuntimeException.class, () ->{
            service.register(standardUser);
        });
    }
}