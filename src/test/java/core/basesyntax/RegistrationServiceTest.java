package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Test;
import org.testng.annotations.BeforeClass;

public class RegistrationServiceTest {

    private RegistrationServiceImpl registerSvc = new RegistrationServiceImpl();

    @BeforeClass
    public void setUp() {
        this.registerSvc = new RegistrationServiceImpl();
    }

    @Test
    public void register_validUser_OK() {
        User user = new User();
        user.setId((long)0);
        user.setAge(20);
        user.setPassword("P@sSSw0rd");
        user.setLogin("userValidLogin");
        assertEquals(user,registerSvc.register(user));
    }

    @Test
    public void register_nulldUser_Exception() {
        User user = null;

        assertThrows(RuntimeException.class,() -> registerSvc.register(user));
    }

    @Test
    public void register_DublicateUser_Exception() {
        User user = new User();
        user.setId((long)0);
        user.setAge(20);
        user.setPassword("P@sSSw0rd");
        user.setLogin("userDublicateLogin");
        registerSvc.register(user);
        assertThrows(RuntimeException.class,() -> registerSvc.register(user));
    }

    @Test
    public void register_ShortPassword_Exception() {
        User user = new User();
        user.setId((long)0);
        user.setAge(18);
        user.setPassword("pass");
        user.setLogin("edik");
        assertThrows(RuntimeException.class,() -> registerSvc.register(user));
    }

    @Test
    public void register_ShortAge_Exception() {
        User user = new User();
        user.setId((long) 0);
        user.setAge(17);
        user.setPassword("pass");
        user.setLogin("edik");
        assertThrows(RuntimeException.class, () -> registerSvc.register(user));
    }

    @Test
    public void register_nullLogin_Exception() {
        User user = new User();
        user.setId((long)0);
        user.setAge(18);
        user.setPassword("passwd");
        user.setLogin(null);
        assertThrows(RuntimeException.class,() -> registerSvc.register(user));
    }

    @Test
    public void register_nullAge_Exception() {
        User user = new User();
        user.setId((long)0);
        user.setAge(null);
        user.setPassword("passwd");
        user.setLogin("userlogin");
        assertThrows(RuntimeException.class,() -> registerSvc.register(user));
    }
}
