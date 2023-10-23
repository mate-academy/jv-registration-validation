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
    void loginIsNull_isNotOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("qwerty23");
        user.setAge(21);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void passwordIsNull_isNotOk() {
        User user = new User();
        user.setLogin("AgroFix");
        user.setPassword(null);
        user.setAge(42);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void ageIsNull_isNotOk() {
        User user = new User();
        user.setLogin("Incognito");
        user.setPassword("89898989");
        user.setAge(null);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void userIsNull_isNotOk() {
        assertThrows(InvalidUserDataException.class,
                () -> service.register(null));
    }

    @Test
    void validateExistUser_isOk() {
        User user = new User();
        user.setLogin("MyTestLogin");
        user.setPassword("qwerty2023");
        user.setAge(23);
        storageDao.add(user);
        User user1 = new User();
        user1.setLogin("MyTestLogin");
        user1.setPassword("qwerty2023");
        user1.setAge(23);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user1));
    }

    @Test
    void loginLessSix_notOk() {
        User user = new User();
        user.setLogin("Strii");
        user.setPassword("random21");
        user.setAge(30);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void passwordLessSix_isNotOk() {
        User user = new User();
        user.setLogin("MyTest");
        user.setPassword("four");
        user.setAge(22);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void ageLessEighteen_isNotOk() {
        User user = new User();
        user.setLogin("MyNewTestLogin");
        user.setPassword("password");
        user.setAge(-12);
        assertThrows(InvalidUserDataException.class,
                () -> service.register(user));
    }

    @Test
    void userValid_isOk() {
        User user = new User();
        user.setLogin("Mates_jv");
        user.setPassword("23.10.2023");
        user.setAge(33);
        assertNotNull(service.register(user));
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }
}
