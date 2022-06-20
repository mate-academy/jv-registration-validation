package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final RegistrationServiceImpl service = new RegistrationServiceImpl();
    private static final StorageDao storage = new StorageDaoImpl();
    private static final User user = new User();
    private static final User userAge = new User();
    private static final User userPassword = new User();
    private static final User userCheck = new User();
    private static final User userWithOneField = new User();
    private static final User emptyFieldsUser = new User();
    private static final User userLongLogin = new User();
    private static final User newUser = new User();

    @BeforeAll
    static void beforeAll() {
        user.setLogin("User");
        user.setPassword("345678");
        user.setAge(25);
        service.register(user);
    }

    @Test
    void register_AgeIsAllowed_NotOk() {
        userAge.setLogin("minorUser");
        userAge.setPassword("123456");
        userAge.setAge(15);
        assertThrows(RuntimeException.class, () -> service.register(userAge));
        userAge.setAge(-1);
        assertThrows(RuntimeException.class, () -> service.register(userAge));
        userAge.setAge(101);
        assertThrows(RuntimeException.class, () -> service.register(userAge));
    }

    @Test
    void register_PasswordLessThan6Characters_NotOk() {
        userPassword.setLogin("InvalidPasswordUser");
        userPassword.setPassword("23457");
        userPassword.setAge(19);
        assertThrows(RuntimeException.class, () -> service.register(userPassword));
    }

    @Test
    void register_ExistedUser_NotOk() {
        userCheck.setLogin("User");
        userCheck.setPassword("567890");
        userCheck.setAge(20);
        assertThrows(RuntimeException.class, () -> service.register(userCheck));
    }

    @Test
    void register_NullUserOrEmptyFields_NotOk() {
        userWithOneField.setLogin("userWithOneField");
        assertThrows(RuntimeException.class, () -> service.register(userWithOneField));
        assertThrows(RuntimeException.class, () -> service.register(null));
        assertThrows(RuntimeException.class, () -> service.register(emptyFieldsUser));
    }

    @Test
    void register_LoginIsTooLong_NotOk() {
        userLongLogin.setLogin("userWithLongLogin12345");
        userLongLogin.setPassword("12345212");
        userLongLogin.setAge(28);
        assertThrows(RuntimeException.class, () -> service.register(userLongLogin));
    }

    @Test
    void add_IfUserIsAddedAfterRegister_Ok() {
        User actual = storage.get(user.getLogin());
        assertEquals(user, actual);
    }

    @Test
    void add_SizeOfStorage_Ok() {
        newUser.setLogin("newUser");
        newUser.setPassword("newUser123");
        newUser.setAge(18);
        service.register(newUser);
        int actual = Storage.people.size();
        assertEquals(2, actual);
    }
}
