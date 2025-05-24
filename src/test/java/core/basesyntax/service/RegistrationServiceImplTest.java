package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.CustomException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private User correctUser1;
    private User correctUser2;
    private User userIncorrectLogin;
    private User userNullLogin;
    private User userIncorrectPassword;
    private User userNullPassword;
    private User userIncorrectAge;
    private User userNullAge;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        correctUser1 = new User("qwertyu", "gadrgsdgf", 19);
        correctUser2 = new User("qfsdertyu", "gadrfsdsdgf", 29);
        userIncorrectLogin = new User("rs3", "da33dasd", 26);
        userNullLogin = new User(null, "dwdwdwddw", 27);
        userIncorrectPassword = new User("z45vbnmd", "45f", 28);
        userNullPassword = new User("ddddsdds", null, 28);
        userIncorrectAge = new User("fdfdfdfe", "fefefefex", 16);
        userNullAge = new User("sdsddedwdx", "4g5ydtgtdr", null);
    }

    @Test
    void registerCorrectUser_Ok() {
        User actual1 = service.register(correctUser1);
        User actual2 = service.register(correctUser2);
        assertEquals(actual1, correctUser1);
        assertEquals(actual2, correctUser2);
    }

    @Test
    void registerNullUser_NotOk() {
        assertThrows(CustomException.class, () -> {
            service.register(null);
        });
    }

    @Test
    void registerIncorrectLoginUser_NotOk() {
        assertThrows(CustomException.class, () -> {
            service.register(userIncorrectLogin);
        });
    }

    @Test
    void registerIncorrectPasswordUser_NotOk() {
        assertThrows(CustomException.class, () -> {
            service.register(userIncorrectPassword);
        });
    }

    @Test
    void registerIncorrectAgedUser_NotOk() {
        assertThrows(CustomException.class, () -> {
            service.register(userIncorrectAge);
        });
    }

    @Test
    void registerNullLoginUser_NotOk() {
        assertThrows(CustomException.class, () -> {
            service.register(userNullLogin);
        });
    }

    @Test
    void registerNullPasswordUser_NotOk() {
        assertThrows(CustomException.class, () -> {
            service.register(userNullPassword);
        });
    }

    @Test
    void registerNullAgedUser_NotOk() {
        assertThrows(CustomException.class, () -> {
            service.register(userNullAge);
        });
    }
}
