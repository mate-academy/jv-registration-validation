package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User testedUser;

    @BeforeEach
    void setUp() {
        testedUser = new User();
        Storage.people.clear();
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void  register_user_Ok() {
        testedUser.setAge(27);
        testedUser.setLogin("thisIsUserLogin");
        testedUser.setPassword("password");
        assertNotNull(registrationService.register(testedUser));
    }

    @Test
    void register_userExist_notOk() {
        testedUser.setAge(27);
        testedUser.setLogin("loginlogin");
        testedUser.setPassword("password");
        registrationService.register(testedUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_returnNull_notOk(){
        testedUser.setAge(27);
        testedUser.setLogin("loginlogin");
        testedUser.setPassword("password");
        assertNotEquals(registrationService.register(testedUser), null);
    }

    @Test
    public void register_ageLessThan18_notOk() {
        testedUser.setAge(16);
        testedUser.setLogin("thisIsUserLogin");
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    public void register_negativeAge_notOk() {
        testedUser.setAge(-5);
        testedUser.setLogin("thisIsUserLogin");
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullAge_notOk() {
        testedUser.setLogin("thisIsUserLogin");
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testedUser.setAge(27);
        testedUser.setLogin("thisIsUserLogin");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_passwordIsLessThan6_notOk() {
        testedUser.setAge(27);
        testedUser.setLogin("thisIsUserLogin");
        testedUser.setPassword("passw");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        testedUser.setAge(27);
        testedUser.setLogin("thisIsUserLogin");
        testedUser.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testedUser.setAge(27);
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        testedUser.setAge(27);
        testedUser.setLogin("");
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_loginContainsSpace_notOk() {
        testedUser.setAge(27);
        testedUser.setLogin("Login login");
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }
}
