package core.basesyntax;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.IncorrectInputDataException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationServiceImplTests {
    private RegistrationServiceImpl service;
    private final StorageDaoImpl storageDao = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_ageNullUser_notOk() {
        User actual = new User();
        actual.setAge(null);
        actual.setId(1L);
        actual.setLogin("12345678");
        actual.setPassword("qwerty");
        Assertions.assertThrows(IncorrectInputDataException.class, () -> service.register(actual));
    }

    @Test
    void register_inappropriateAgeUser_notOk() {
        User actual = new User();
        actual.setAge(5);
        actual.setId(1L);
        actual.setLogin("12345678");
        actual.setPassword("qwerty");
        Assertions.assertThrows(IncorrectInputDataException.class, () -> service.register(actual));
    }

    @Test
    void register_IdNullUser_notOk() {
        User actual = new User();
        actual.setAge(20);
        actual.setId(null);
        actual.setLogin("12345678");
        actual.setPassword("qwerty");
        Assertions.assertThrows(IncorrectInputDataException.class, () -> service.register(actual));
    }

    @Test
    void register_incorrectIdUser_notOk() {
        User actual = new User();
        actual.setAge(20);
        actual.setId(-2L);
        actual.setLogin("12345678");
        actual.setPassword("qwerty");
        Assertions.assertThrows(IncorrectInputDataException.class, () -> service.register(actual));
    }

    @Test
    void register_passwordNullUser_notOk() {
        User actual = new User();
        actual.setAge(20);
        actual.setId(1L);
        actual.setLogin("12345678");
        actual.setPassword(null);
        Assertions.assertThrows(IncorrectInputDataException.class, () -> service.register(actual));
    }

    @Test
    void register_shortPasswordUser_notOk() {
        User actual = new User();
        actual.setAge(20);
        actual.setId(1L);
        actual.setLogin("12345678");
        actual.setPassword("q");
        Assertions.assertThrows(IncorrectInputDataException.class, () -> service.register(actual));
    }

    @Test
    void register_LoginNullUser_notOk() {
        User actual = new User();
        actual.setAge(20);
        actual.setId(1L);
        actual.setLogin(null);
        actual.setPassword("qwerty");
        Assertions.assertThrows(IncorrectInputDataException.class, () -> service.register(actual));
    }

    @Test
    void register_loginEmptyUser_notOk() {
        User actual = new User();
        actual.setAge(20);
        actual.setId(1L);
        actual.setLogin("");
        actual.setPassword("qwerty");
        Assertions.assertThrows(IncorrectInputDataException.class, () -> service.register(actual));
    }

    @Test
    void register_shortLoginUser_notOk() {
        User actual = new User();
        actual.setAge(20);
        actual.setId(1L);
        actual.setLogin("123");
        actual.setPassword("qwerty");
        Assertions.assertThrows(IncorrectInputDataException.class, () -> service.register(actual));
    }

    @Test
    void register_userWithInputLoginAlreadyExists_notOk() {
        User first = new User();
        first.setAge(20);
        first.setId(1L);
        first.setLogin("12345678");
        first.setPassword("qwerty");
        service.register(first);

        User withSameLogin = new User();
        withSameLogin.setAge(25);
        withSameLogin.setId(2L);
        withSameLogin.setLogin("12345678");
        withSameLogin.setPassword("qwerty");
        Assertions.assertThrows(IncorrectInputDataException.class,
                () -> service.register(withSameLogin));
    }
}
