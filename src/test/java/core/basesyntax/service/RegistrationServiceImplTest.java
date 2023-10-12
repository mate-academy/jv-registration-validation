package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    }

    @Test
    void userIsNull_notOk() {
        assertThrows(RegistrationServiceException.class, () -> service.register(usersTest.get(3)));
    }

    @Test
    void userLoginIsNull_notOk() {
        assertThrows(RegistrationServiceException.class, () -> service.register(usersTest.get(5)));
    }

    @Test
    void userPasswordIsNull_notOk() {
        assertThrows(RegistrationServiceException.class, () -> service.register(usersTest.get(6)));
    }

    @Test
    void userLoginLessSix_notOk() {
        assertThrows(RegistrationServiceException.class, () -> service.register(usersTest.get(1)));
    }

    @Test
    void userPasswordLessSix_notOk() {
        assertThrows(RegistrationServiceException.class, () -> service.register(usersTest.get(2)));
    }

    @Test
    void userAgeLessEighteen_notOk() {
        assertThrows(RegistrationServiceException.class, () -> service.register(usersTest.get(4)));
    }

    @Test
    void userValid_isOk() {
        User userLogin = service.register(usersTest.get(0));
        assertNotNull(userLogin);
    }

    @Test
    void userLogin() {
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Testing is over");
    }
}
