package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl service;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        service = new RegistrationServiceImpl(new StorageDaoImpl());
    }

    @Test
    void userNull_isNotOk() {
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(null));
    }

    @Test
    void userLoginSpaces_isNotOk() {
        User user = new User();
        user.setLogin("my login");
        user.setPassword("rostyk");
        user.setAge(20);
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(user));
    }

    @Test
    void userLoginNull_isNotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("rostyk111");
        user.setAge(23);
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(user));
    }

    @Test
    void userPasswordNull_isNotOk() {
        User user = new User();
        user.setLogin("Rostyslav");
        user.setPassword(null);
        user.setAge(24);
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(user));
    }

    @Test
    void checkingExistUser_isOk() {
        User user = new User();
        user.setLogin("Rostyslav");
        user.setPassword("123456");
        user.setAge(29);
        storageDao.add(user);
        User user1 = new User();
        user1.setLogin("Rostyslav");
        user1.setPassword("address");
        user1.setAge(24);
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(user1));
    }

    @Test
    void userLoginLessSix_notOk() {
        User user = new User();
        user.setLogin("fate");
        user.setPassword("my_date_of_birthday");
        user.setAge(30);
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(user));
    }

    @Test
    void userPasswordLessSix_isNotOk() {
        User user = new User();
        user.setLogin("Valentyna");
        user.setPassword("2001");
        user.setAge(40);
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(user));
    }

    @Test
    void userAgeLessEighteen_isNotOk() {
        User user = new User();
        user.setLogin("Vadym2020");
        user.setPassword("ilovecats");
        user.setAge(17);
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(user));
    }

    @Test
    void userValid_isOk() {
        User user = new User();
        user.setLogin("forest12");
        user.setPassword("myaddress");
        user.setAge(29);
        assertNotNull(service.register(user));
    }

    @Test
    void userLoginLengthMoreTen_isOk() {
        User user = new User();
        user.setLogin("ValentynaKos");
        user.setPassword("val123");
        user.setAge(29);
        assertNotNull(service.register(user));
    }

    @Test
    void userPassword_only_digits_isOk() {
        User user = new User();
        user.setLogin("Misha123");
        user.setPassword("0985553516");
        user.setAge(40);
        assertNotNull(service.register(user));
    }

    @Test
    void userAgeMoreFifty_isOk() {
        User user = new User();
        user.setLogin("Misha2002");
        user.setPassword("flower");
        user.setAge(59);
        assertNotNull(service.register(user));
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }
}
