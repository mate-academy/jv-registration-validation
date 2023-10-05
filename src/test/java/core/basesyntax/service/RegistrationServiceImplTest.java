package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService serviceRegistr;
    private static StorageDao storageDao;
    private User user;

    @BeforeAll
    static void beforeAll() {
        serviceRegistr = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("Mikola", "123456", 18);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class,() ->
                        serviceRegistr.register(user),
                "If Login is Null then should be Exception");
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class,() ->
                        serviceRegistr.register(user),
                "If Age is Null then should be Exception");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class,() ->
                        serviceRegistr.register(user),
                "If Password is Null then should be Exception");
    }

    @Test
    void register_lengthPassword_notOk() {
        String [] passwords = {"","df431","d31","43","1"};
        for (String password :passwords) {
            user.setPassword(password);
            Assertions.assertThrows(RegistrationException.class, () ->
                            serviceRegistr.register(user),
                    "If Password length least als 6 Symbol then should be Exception");
        }
    }

    @Test
    void register_userAge_notOk() {
        Integer[] ages = {1, 15, 3, 17, -6};
        for (Integer age : ages) {
            user.setAge(age);
            Assertions.assertThrows(RegistrationException.class, () ->
                            serviceRegistr.register(user),
                    "If Age least als 18 years old then should be Exception");
        }
    }

    @Test
    void register_lengthLogin_notOk() {
        String[] logins = {"", "ls", "lr1", "a", "lalar"};
        for (String login : logins) {
            user.setLogin(login);
            Assertions.assertThrows(RegistrationException.class, () ->
                            serviceRegistr.register(user),
                    "If Login length least als 6 Symbol then should be Exception");
        }
    }

    @Test
    void register_userClone_notOk() {
        serviceRegistr.register(user);
        Assertions.assertThrows(RegistrationException.class, () ->
                        serviceRegistr.register(user),
                "If User already exists in DB then should be Exception");
    }

    @Test
    void register_lengthPassword_Ok() {
        String [] passwords = {"assder","df3qwdf431","!w2@34d31"};
        String [] logins = {"Dimaksy","Viktor","Georgiivich"};
        for (int i = 0; i < passwords.length; i++) {
            serviceRegistr.register(new User(logins[i],passwords[i],50 + i));
            String actual = storageDao.get(logins[i]).getPassword();
            Assertions.assertEquals(passwords[i],actual,
                    "User Password in DB not equals current Password User");
        }
    }

    @Test
    void register_lengthLogin_Ok() {
        String [] passwords = {"assder","df3qwdf431","!w2@34d31"};
        String [] logins = {"Katerina","Katyry","Eralash"};
        for (int i = 0; i < passwords.length; i++) {
            serviceRegistr.register(new User(logins[i],passwords[i],30 + i));
            String actual = storageDao.get(logins[i]).getLogin();
            Assertions.assertEquals(logins[i],actual,
                    "User Login in DB not equals current Login User");
        }
    }

    @Test
    void register_userAge_Ok() {
        Integer[] ages = {18,33,86};
        String [] passwords = {"assder","df3qwdf431","!w2@34d31"};
        String [] logins = {"Oleksiy","Vyacheslav","Georgi"};
        for (int i = 0; i < passwords.length; i++) {
            serviceRegistr.register(new User(logins[i],passwords[i],ages[i]));
            Integer actual = storageDao.get(logins[i]).getAge();
            Assertions.assertEquals(ages[i],actual,
                    "User Age in DB not equals current Age User");
        }
    }
}

