package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService serviceRegistr;
    private static Map<String,User> myMap;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        serviceRegistr = new RegistrationServiceImpl();
        myMap = new HashMap<>();
        storageDao = new StorageDaoImpl();
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
        Assertions.assertThrows(RegisterException.class,() ->
                        serviceRegistr.register(myMap.get("loginNull")),
                "If Login is Null then should be Exception");
    }

    @Test
    void testPasswordIs_Null() {
        Assertions.assertThrows(RegisterException.class,() ->
                        serviceRegistr.register(myMap.get("passNull")),
                "If Password is Null then should be Exception");
    }

    @Test
    void testPasswordLengthIs_NotOk() {
        Assertions.assertThrows(RegisterException.class, () ->
                        serviceRegistr.register(myMap.get("passw5Sym")),
                "If Password length least als 6 Symbol then should be Exception");
        Assertions.assertThrows(RegisterException.class, () ->
                        serviceRegistr.register(myMap.get("passwBlank")),
                "If Password length least als 6 Symbol then should be Exception");
    }

    @Test
    void testAgeUserIs_NotOk() {
        Assertions.assertThrows(RegisterException.class, () ->
                        serviceRegistr.register(myMap.get("age0")),
                "If Age least als 18 years old then should be Exception");
        Assertions.assertThrows(RegisterException.class, () ->
                        serviceRegistr.register(myMap.get("age17")),
                "If Age least als 18 years old then should be Exception");
    }

    @Test
    void testLoginLengthIs_NotOk() {
        Assertions.assertThrows(RegisterException.class, () ->
                        serviceRegistr.register(myMap.get("login5Sym")),
                "If Login length least als 6 Symbol then should be Exception");
        Assertions.assertThrows(RegisterException.class, () ->
                        serviceRegistr.register(myMap.get("loginBlank")),
                "If Login length least als 6 Symbol then should be Exception");
    }

    @Test
    void testUserOk() {
        User expected = serviceRegistr.register(myMap.get("userOk"));
        Assertions.assertEquals(expected,storageDao.get(myMap.get("userOk").getLogin()),
                "User in DB not equals");
    }

    @Test
    void testUserClone() {
        User actual = serviceRegistr.register(myMap.get("userOk"));
        Assertions.assertThrows(RegisterException.class, () ->
                        serviceRegistr.register(myMap.get("userOk")),
                "If User already exists in DB then should be Exception");
    }
}
