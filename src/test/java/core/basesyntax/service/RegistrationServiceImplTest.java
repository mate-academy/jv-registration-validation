package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import core.basesyntax.service.RegisterException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    Map<String,User> myMap;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        myMap = new HashMap<>();
        myMap.put("petro",new User("Petro","Qwerty",22));
        User mikolay = new User("Mikolay","Qwert",99);
        User nikol = new User("","Qwert123",29);
        User babaLyusya = new User("Baba Lyusya","",88);
        User baby = new User("Babysh","SkoroBudet",0);
        User maloletka = new User("Krasotka","S657!Gtr=07`'}",17);
        myMap.put("loginNull", new User(null,"Qwerty",19));
        User passNull = new User("Pechkin",null,19);
        User vladislav = new User("Vladislav","Qwerty!!",22);
        User vladislav2 = new User("Vladislav","Qwerty!!",22);
    }

    @Test
    void testLoginIsNull() {
        User actual = myMap.get("loginNull");
        assertThrows(RegisterException.class,() ->service.register(actual));
    }

    @Test
    void testPasswordIs_Null() {

    }

    @Test
    void testPasswordLengthIs_Ok() {
    }

    @Test
    void testPasswordLengthIs_NotOk() {

    }

    @Test
    void testLoginLengthIs_Ok() {
    }

    @Test
    void testLoginLengthIs_NotOk() {
    }

    @Test
    void testAgeUserIs_Ok() {
    }

    @Test
    void testAgeUserIs_NotOk() {
    }
}