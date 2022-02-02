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
        users.put("normalUser",new User((long)6542324,"Georg","qwerty123",20));
        users.put("nullUser",null);
        users.put("invalidAgeUser",new User((long)124252234,"Alice","QWERTY312",10));
        users.put("invalidIdUser",new User((long)-4251214,"Sasha","12345678",19));
        users.put("invalidPasswordUser",new User((long)523543,"Oleg","qqq1",33));
        users.put("sameLoginUser1",new User((long)435321,"Stefan","stefan1234",20));
        users.put("sameLoginUser2",new User((long)435321,"Stefan","stefan1234",20));
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
    }

    @Test
    void addInvalidIdUser_NotOk() {
        assertThrows(RuntimeException.class, () -> service.register(users.get("invalidIdUser")),
                "we shouldn't be able to add the user with invalid id");
    }

    @Test
    void addInvalidPasswordUser() {
        assertThrows(RuntimeException.class,
                () -> service.register(users.get("invalidPasswordUser")),
                "we shouldn't be able to add the user with invalid password");
    }

    @Test
    void addSameLoginUser() {
        service.register(users.get("sameLoginUser1"));
        assertThrows(RuntimeException.class, () -> service.register(users.get("sameLoginUser2")),
                "we shouldn't be able to add user which was added earlier");
    }
}
