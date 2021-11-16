package core.basesyntax;

import static org.junit.Assert.assertEquals;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.BeforeClass;
import org.junit.Before;
import org.junit.Test;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationServiceImpl;
    private static User user;
    
    @BeforeClass
    public static void beforeClass() {
        registrationServiceImpl = new RegistrationServiceImpl();
        user = new User();
    }
    
    @Before
    public void setUp() {
        Storage.people.clear();
        user.setLogin("login");
        user.setPassword("password");
        user.setAge(20);
    }
    
    @Test(expected = RuntimeException.class)
    public void register_nullUser_notOk() {
        registrationServiceImpl.register(null);
    }
    
    @Test(expected = RuntimeException.class)
    public void register_nullLogin_notOk() {
        user.setLogin(null);
        registrationServiceImpl.register(user);
    }
    
    @Test(expected = RuntimeException.class)
    public void register_nullPassword_notOk() {
        user.setPassword(null);
        registrationServiceImpl.register(user);
    }
    
    @Test(expected = RuntimeException.class)
    public void register_nullAge_notOk() {
        user.setAge(null);
        registrationServiceImpl.register(user);
    }
    
    @Test(expected = RuntimeException.class)
    public void register_ageLessThanMin_notOk() {
        user.setAge(-25);
        registrationServiceImpl.register(user);
    }
    
    @Test(expected = RuntimeException.class)
    public void register_passwordLengthLessThanMin_notOk() {
        user.setPassword("1234");
        registrationServiceImpl.register(user);
    }
    
    @Test(expected = RuntimeException.class)
    public void register_userAlreadyExists_notOk() {
        registrationServiceImpl.register(user);
        registrationServiceImpl.register(user);
    }
    
    @Test
    public void register_validUser_ok() {
        assertEquals(user, registrationServiceImpl.register(user));
    }
}
