package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService serviceRegistr;
    private static Map<String,User> myMap;

    @BeforeAll
    static void beforeAll() {
        serviceRegistr = new RegistrationServiceImpl();
        myMap = new HashMap<>();
    }

    @BeforeEach
    void setUp() {
        myMap.put("login5Sym",new User("Petro","Qwerty",22));
        myMap.put("passw5Sym", new User("Mikolay","Qwert",99));
        myMap.put("loginBlank", new User("","Qwert123",29));
        myMap.put("passwBlank", new User("Baba Lyusya","",88));
        myMap.put("age0", new User("Babysh","SkoroBudet",0));
        myMap.put("age17", new User("Krasotka","S657!Gtr=07`'}",17));
        myMap.put("loginNull", new User(null,"Qwerty",19));
        myMap.put("passNull", new User("Pechkin",null,19));
        myMap.put("userOk", new User("Vladislav","Qwerty!!",22));
    }

    @Test
    void testLoginIsNull() {
        assertThrows(RegisterException.class,() ->
                serviceRegistr.register(myMap.get("loginNull")));
    }

    @Test
    void testPasswordIs_Null() {
        assertThrows(RegisterException.class,() ->
                serviceRegistr.register(myMap.get("passNull")));
    }

    @Test
    void testPasswordLengthIs_Ok() {
      //  User actual = myMap.get("userOk");
    }

    @Test
    void testPasswordLengthIs_NotOk() {
        boolean actual = serviceRegistr.register("");
    assertNotEquals(false,actual,"Password schould be greater als 6 Symbol");
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