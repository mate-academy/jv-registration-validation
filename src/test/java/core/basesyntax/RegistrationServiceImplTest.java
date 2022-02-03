package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private RegistrationServiceImpl service;
    private Map<String,User> users;

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
        users = new HashMap<String,User>();
        users.put("normalUser",new User("Georg","qwerty123",20));
        users.put("nullUser",null);
        users.put("invalidAgeUser",new User("Alice","QWERTY312",10));
        users.put("invalidAgeUser2",new User("Alex","QWERTY312",0));
        users.put("invalidAgeUser3",new User("Mary","QWERTY312",-4));
        users.put("nullAgeUser",new User("Bob","QWERTY312",null));
        users.put("invalidPasswordUser",new User("Oleg","qqq1",33));
        users.put("nullPasswordUser",new User("Ann",null,33));
        users.put("nullLoginUser",new User(null,"Rsafgewqqwe",22));
        users.put("invalidLoginUser",new User("","qqq131413",42));
        users.put("sameLoginUser1",new User("Stefan","stefan1234",20));
        users.put("sameLoginUser2",new User("Stefan","stefan1234",20));
    }

    @Test
    void addNormalUserTest_Ok() {
        User actual = service.register(users.get("normalUser"));
        assertEquals(actual,users.get("normalUser"));
    }

    @Test
    void addNullUser_NotOk() {
        assertThrows(RuntimeException.class, () -> service.register(users.get("nullUser")),
                "we shouldn't be able to add the null user");
    }

    @Test
    void addInvalidAgeUser_NotOk() {
        assertThrows(RuntimeException.class, () -> service.register(users.get("invalidAgeUser")),
                "we shouldn't be able to add the user under 18");
        assertThrows(RuntimeException.class, () -> service.register(users.get("invalidAgeUser2")),
                "we shouldn't be able to add the user under 18");
        assertThrows(RuntimeException.class, () -> service.register(users.get("invalidAgeUser3")),
                "value of age cant be less than 0");
        assertThrows(RuntimeException.class, () -> service.register(users.get("nullAgeUser")),
                "we shouldn't be able to add the user with null value of age");
    }

    @Test
    void addInvalidPasswordUser_NotOk() {
        assertThrows(RuntimeException.class,
                () -> service.register(users.get("invalidPasswordUser")),
                "we shouldn't be able to add the user with invalid password");
        assertThrows(RuntimeException.class,
                () -> service.register(users.get("nullPasswordUser")),
                "we shouldn't be able to add the user with null value password");
    }

    @Test
    void addInvalidLoginUser_NotOk() {
        assertThrows(RuntimeException.class,
                () -> service.register(users.get("invalidLoginUser")),
                "length() of login will be more that 0");
    }

    @Test
    void addNullLoginUser_NotOk() {
        assertThrows(RuntimeException.class,
                () -> service.register(users.get("nullLoginUser")),
                "we shouldn't be able to add the user with invalid password");
    }

    @Test
    void addSameLoginUser_NotOk() {
        service.register(users.get("sameLoginUser1"));
        assertThrows(RuntimeException.class, () -> service.register(users.get("sameLoginUser2")),
                "we shouldn't be able to add user with login, that already exists in storage");
    }
}
