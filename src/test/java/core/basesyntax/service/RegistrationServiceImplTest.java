package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    RegistrationService service;
    User standardUser;

    @BeforeEach
    void setUp(){
        service = new RegistrationServiceImpl();
        standardUser = new User();
        standardUser.setLogin("mate");
        standardUser.setAge(19);
        standardUser.setPassword("123456");
    }

    @Test
    public void correctUserCheck_Ok(){
        assertEquals(standardUser, service.register(standardUser));
    }

    @Test
    public void AgeIsNullCheck_NotOk(){
        standardUser.setAge(null);
        assertThrows(RuntimeException.class, () ->{
            service.register(standardUser);
        });
    }

    @Test
    public void LoginIsNullCheck_NotOk(){
        standardUser.setLogin(null);
        assertThrows(RuntimeException.class, () ->{
            service.register(standardUser);
        });
    }

    @Test
    public void PasswordIsNullCheck_NotOk(){
        standardUser.setPassword(null);
        assertThrows(RuntimeException.class, () ->{
            service.register(standardUser);
        });
    }

    @Test
    public void sameLoginCheck_NotOk(){
        standardUser.setLogin("mates");
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