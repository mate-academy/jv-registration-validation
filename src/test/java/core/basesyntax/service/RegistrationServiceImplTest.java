package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationServiceImplTest {
    private static RegistrationService service;
    private static User testedUser;


    @BeforeAll
    public static void setUp() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    public void refreshData() {
        testedUser = new User();
        Storage.people.clear();
    }

    @Test
    public void register_nullAge_notOk() {
        testedUser.setPassword("password");
        testedUser.setLogin("userLogin");
        assertThrows(RuntimeException.class, () -> service.register(testedUser));
    }

    @Test
    public void register_18Age_Ok() {
        testedUser.setAge(18);
        testedUser.setLogin("userLogin");
        testedUser.setPassword("password");
        assertNotNull(service.register(testedUser));
    }

    @Test
    public void register_17Age_notOk() {
        testedUser.setAge(17);
        testedUser.setLogin("userLogin");
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> service.register(testedUser));
    }

    @Test
    public void register_negativeAge_notOk() {
        testedUser.setAge(-4);
        testedUser.setLogin("userLogin");
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> service.register(testedUser));
    }

    @Test
    public void register_nullLogin_notOk() {
        testedUser.setAge(22);
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> service.register(testedUser));
    }

    @Test
    public void register_emptyLogin_notOk() {
        testedUser.setAge(22);
        testedUser.setLogin("");
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> service.register(testedUser));
    }

    @Test
    public void register_nullPassword_notOk() {
        testedUser.setAge(22);
        testedUser.setLogin("userLogin");
        assertThrows(RuntimeException.class, () -> service.register(testedUser));
    }

    @Test
    public void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () -> service.register(null));
    }

    @Test
    public void register_notAdultUser_notOk() {
        testedUser.setAge(14);
        testedUser.setLogin("userLogin");
        testedUser.setPassword("password");
        assertThrows(RuntimeException.class, () -> service.register(testedUser));
    }

    @Test
    public void register_passwordLengthLess6_notOk() {
        testedUser.setAge(21);
        testedUser.setLogin("userLogin");
        testedUser.setPassword("pass");
        assertThrows(RuntimeException.class, () -> service.register(testedUser));
    }

    @Test
    public void register_passwordLength5_notOk() {
        testedUser.setAge(21);
        testedUser.setLogin("userLogin");
        testedUser.setPassword("12345");
        assertThrows(RuntimeException.class, () -> service.register(testedUser));
    }

    @Test
    public void register_userExist_notOk() {
        testedUser.setAge(19);
        testedUser.setLogin("userLogin");
        testedUser.setPassword("password");
        service.register(testedUser);
        assertThrows(RuntimeException.class, () -> service.register(testedUser));
    }

    @Test
    public void register_returnNull_notOk(){
        testedUser.setAge(21);
        testedUser.setLogin("userLogin");
        testedUser.setPassword("password");
        assertNotEquals(service.register(testedUser), null);
    }
}
