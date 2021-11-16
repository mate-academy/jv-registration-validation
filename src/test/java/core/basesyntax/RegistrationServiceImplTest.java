package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static User testedUser;

    @BeforeEach
    void setUp() {
        testedUser = new User();
        Storage.people.clear();
        testedUser.setAge(27);
        testedUser.setLogin("thisIsUserLogin");
        testedUser.setPassword("password");
    }

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_user_Ok() {
        assertNotNull(registrationService.register(testedUser));
    }

    @Test
    void register_userExist_notOk() {
        registrationService.register(testedUser);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_returnNull_notOk(){
        assertNotEquals(registrationService.register(testedUser), null);
    }

    @Test
    public void register_ageLessThan18_notOk() {
        testedUser.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    public void register_negativeAge_notOk() {
        testedUser.setAge(-5);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullAge_notOk() {
        testedUser.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullPassword_notOk() {
        testedUser.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_passwordIsLessThan6_notOk() {
        testedUser.setPassword("passw");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        testedUser.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_nullLogin_notOk() {
        testedUser.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        testedUser.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }

    @Test
    void register_loginContainsSpace_notOk() {
        testedUser.setLogin("Login login");
        assertThrows(RuntimeException.class, () -> registrationService.register(testedUser));
    }
}
