package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService service;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> service.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User nullLoginUser = new User();
        nullLoginUser.setPassword("testpassword");
        nullLoginUser.setAge(19);
        assertThrows(RegistrationException.class, () -> service.register(nullLoginUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User nullPasswordUser = new User();
        nullPasswordUser.setLogin("dianazotsenko");
        nullPasswordUser.setAge(19);
        assertThrows(RegistrationException.class, () -> service.register(nullPasswordUser));
    }

    @Test
    void register_nullAge_notOk() {
        User nullAgeUser = new User();
        nullAgeUser.setLogin("bobforester");
        nullAgeUser.setPassword("testpassword");
        assertThrows(RegistrationException.class, () -> service.register(nullAgeUser));
    }

    @Test
    void register_emptyLogin_notOk() {
        User emptyLoginUser = new User();
        emptyLoginUser.setLogin("");
        emptyLoginUser.setPassword("testpassword");
        emptyLoginUser.setAge(19);
        assertThrows(RegistrationException.class, () -> service.register(emptyLoginUser));
    }

    @Test
    void register_threeCharacterLogin_notOk() {
        User threeCharacterLoginUser = new User();
        threeCharacterLoginUser.setLogin("dia");
        threeCharacterLoginUser.setPassword("testpassword");
        threeCharacterLoginUser.setAge(19);
        assertThrows(RegistrationException.class, () -> service.register(threeCharacterLoginUser));
    }

    @Test
    void register_fiveCharacterLogin_notOk() {
        User fivecharacterLoginUser = new User();
        fivecharacterLoginUser.setLogin("diana");
        fivecharacterLoginUser.setPassword("testpassword");
        fivecharacterLoginUser.setAge(19);
        assertThrows(RegistrationException.class, () -> service.register(fivecharacterLoginUser));
    }

    @Test
    void register_sixCharacterLogin_ok() {
        User sixCharacterLoginUser = new User();
        sixCharacterLoginUser.setLogin("dianaz");
        sixCharacterLoginUser.setPassword("testpassword");
        sixCharacterLoginUser.setAge(19);
        assertEquals(sixCharacterLoginUser, service.register(sixCharacterLoginUser));
    }

    @Test
    void register_eightCharacterLogin_ok() {
        User eightCharacterLoginUser = new User();
        eightCharacterLoginUser.setLogin("dianazot");
        eightCharacterLoginUser.setPassword("testpassword");
        eightCharacterLoginUser.setAge(19);
        assertEquals(eightCharacterLoginUser, service.register(eightCharacterLoginUser));
    }

    @Test
    void register_loginAlreadyExists_notOk() {
        User loginAlreadyExistsUser = new User();
        loginAlreadyExistsUser.setLogin("dianazot");
        loginAlreadyExistsUser.setPassword("testpassword");
        loginAlreadyExistsUser.setAge(19);
        assertThrows(RegistrationException.class, () -> service.register(loginAlreadyExistsUser));
    }

    @Test
    void register_emptyPassword_notOk() {
        User emptyPasswordUser = new User();
        emptyPasswordUser.setLogin("dianazotsenko");
        emptyPasswordUser.setPassword("");
        emptyPasswordUser.setAge(19);
        assertThrows(RegistrationException.class, () -> service.register(emptyPasswordUser));
    }

    @Test
    void register_threeCharacterPassword_notOk() {
        User threeCharacterPasswordUser = new User();
        threeCharacterPasswordUser.setLogin("dianazotsenko");
        threeCharacterPasswordUser.setPassword("tes");
        threeCharacterPasswordUser.setAge(19);
        assertThrows(RegistrationException.class,
                () -> service.register(threeCharacterPasswordUser));
    }

    @Test
    void register_fiveCharacterPassword_notOk() {
        User fivecharacterPasswordUser = new User();
        fivecharacterPasswordUser.setLogin("dianazotsenko");
        fivecharacterPasswordUser.setPassword("testp");
        fivecharacterPasswordUser.setAge(19);
        assertThrows(RegistrationException.class,
                () -> service.register(fivecharacterPasswordUser));
    }

    @Test
    void register_sixCharacterPassword_ok() {
        User sixCharacterPasswordUser = new User();
        sixCharacterPasswordUser.setLogin("dianazotsenko");
        sixCharacterPasswordUser.setPassword("testpa");
        sixCharacterPasswordUser.setAge(19);
        assertEquals(sixCharacterPasswordUser, service.register(sixCharacterPasswordUser));
    }

    @Test
    void register_eightCharacterPassword_ok() {
        User eightCharacterPasswordUser = new User();
        eightCharacterPasswordUser.setLogin("mariapetrenko");
        eightCharacterPasswordUser.setPassword("testpass");
        eightCharacterPasswordUser.setAge(19);
        assertEquals(eightCharacterPasswordUser, service.register(eightCharacterPasswordUser));
    }

    @Test
    void register_negativeAge_notOk() {
        User negativeAgeUser = new User();
        negativeAgeUser.setLogin("alicewhite");
        negativeAgeUser.setPassword("testpassword");
        negativeAgeUser.setAge(-5);
        assertThrows(RegistrationException.class, () -> service.register(negativeAgeUser));
    }

    @Test
    void register_zeroAge_notOk() {
        User zeroAgeUser = new User();
        zeroAgeUser.setLogin("alicewhite");
        zeroAgeUser.setPassword("testpassword");
        zeroAgeUser.setAge(0);
        assertThrows(RegistrationException.class, () -> service.register(zeroAgeUser));
    }

    @Test
    void register_underAge_notOk() {
        User underAgeUser = new User();
        underAgeUser.setLogin("alicewhite");
        underAgeUser.setPassword("testpassword");
        underAgeUser.setAge(10);
        assertThrows(RegistrationException.class, () -> service.register(underAgeUser));
    }

    @Test
    void register_eighteenAge_Ok() {
        User eighteenAgeUser = new User();
        eighteenAgeUser.setLogin("alicewhite");
        eighteenAgeUser.setPassword("testpassword");
        eighteenAgeUser.setAge(18);
        assertEquals(eighteenAgeUser, service.register(eighteenAgeUser));
    }

    @Test
    void register_overAge_Ok() {
        User overAgeUser = new User();
        overAgeUser.setLogin("john12345");
        overAgeUser.setPassword("testpassword");
        overAgeUser.setAge(30);
        assertEquals(overAgeUser, service.register(overAgeUser));
    }
}
