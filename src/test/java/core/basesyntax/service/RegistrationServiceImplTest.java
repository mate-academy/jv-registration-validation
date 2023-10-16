package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private List<User> usersTest;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        usersTest = new ArrayList<>();
        usersTest.add(new User("mylogin", "ilovecats", 20));
        usersTest.add(new User("maria", "Kuzmych", 23));
        usersTest.add(new User("Lyubov", "Kos", 40));
        usersTest.add(null);
        usersTest.add(new User("Valentyna", "Dyachuk", 17));
        usersTest.add(new User(null, "18081996", 26));
        usersTest.add(new User("Rostyslav", null, 29));
        usersTest.add(new User("Rosty slav", "13782864", 30));
        usersTest.add(new User("my_date_of_birthday", "1202cat", 34));
        usersTest.add(new User("random_user", "12092005", 23));
        usersTest.add(new User("bimbom", "lovecats", 56));
    }

    @Test
    void userIsNull_notOk() {
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(usersTest.get(3)));
    }

    @Test
    void userLoginSpaces_notOk() {
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(usersTest.get(7)));
    }

    @Test
    void userLoginIsNull_notOk() {
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(usersTest.get(5)));
    }

    @Test
    void userPasswordIsNull_notOk() {
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(usersTest.get(6)));
    }

    @Test
    void userLoginLessSix_notOk() {
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(usersTest.get(1)));
    }

    @Test
    void userPasswordLessSix_notOk() {
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(usersTest.get(2)));
    }

    @Test
    void userAgeLessEighteen_notOk() {
        assertThrows(InvalidRegistrationServiceException.class,
                () -> service.register(usersTest.get(4)));
    }

    @Test
    void userValid_isOk() {
        User user = service.register(usersTest.get(0));
        assertNotNull(user);
    }

    @Test
    void userLengthMoreTen_isOk() {
        User user = service.register(usersTest.get(8));
        assertNotNull(user);
    }

    @Test
    void userPassword_only_digits_isOk() {
        User user = service.register(usersTest.get(9));
        assertNotNull(user);
    }

    @Test
    void userAgeMoreFifty_isOk() {
        User user = service.register(usersTest.get(10));
        assertNotNull(user);
    }

    @AfterAll
    static void afterAll() {
        Storage.people.clear();
    }
}
