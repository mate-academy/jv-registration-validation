package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private final RegistrationService registrationUser = new RegistrationServiceImpl();

    @Test
    void register_nullParameters_notOk() {
        final User nullUser = null;
        final User nullAgeUser = new User();
        final User nullLoginUser = new User();
        final User nullPassUser = new User();
        nullAgeUser.setAge(null);
        nullAgeUser.setLogin("Null Age User");
        nullAgeUser.setPassword("01928374");
        nullLoginUser.setAge(36);
        nullLoginUser.setLogin(null);
        nullLoginUser.setPassword("0987654");
        nullPassUser.setAge(56);
        nullPassUser.setLogin("Null Pass User");
        nullPassUser.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(nullUser),
                "User can't be null");
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(nullAgeUser),
                "Users age can't be null");
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(nullLoginUser),
                "User should have not-null login");
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(nullPassUser),
                "Password can't be null, expected 6 or more characters");
    }

    @Test
    void register_wrongAge_notOk() {
        final User wrongAgeUser = new User();
        wrongAgeUser.setAge(15);
        wrongAgeUser.setLogin("Wrong Age User");
        wrongAgeUser.setPassword("7654321");
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(wrongAgeUser),
                "Users age should be greater than 18yo");

    }

    @Test
    void register_wrongPass_notOk() {
        final User wrongPassUser = new User();
        wrongPassUser.setAge(30);
        wrongPassUser.setLogin("Wrong Pass User");
        wrongPassUser.setPassword("1234");
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(wrongPassUser),
                "Password length should be 6 or more characters");
    }

    @Test
    void register_sameLogin_notOk() {
        final User firstUser = new User();
        final User firstSameUser = new User();
        firstUser.setAge(18);
        firstUser.setLogin("First User");
        firstUser.setPassword("1234567");
        firstSameUser.setAge(18);
        firstSameUser.setLogin("First User");
        firstSameUser.setPassword("1234567");
        assertDoesNotThrow(() -> registrationUser.register(firstUser));
        assertThrows(RuntimeException.class,
                () -> registrationUser.register(firstSameUser),
                "User with this login already exists");
    }

    @Test
    void register_allowablePass_Ok() {
        final User secondUser = new User();
        final User thirdUser = new User();
        secondUser.setAge(57);
        secondUser.setLogin("Second User");
        secondUser.setPassword("345876906");
        thirdUser.setAge(57);
        thirdUser.setLogin("Third User");
        thirdUser.setPassword("34576906");
        registrationUser.register(secondUser);
        registrationUser.register(thirdUser);
        assertEquals("345876906", secondUser.getPassword());
        assertEquals("34576906", thirdUser.getPassword());
    }

    @Test
    void register_emptyPassOrLogin_notOk() {
        final User emptyPassUser = new User();
        emptyPassUser.setAge(42);
        emptyPassUser.setLogin("Empty Pass User");
        emptyPassUser.setPassword("");
        final User emptyLoginUser = new User();
        emptyLoginUser.setAge(42);
        emptyLoginUser.setLogin("");
        emptyLoginUser.setPassword("786854444");
        assertThrows(RuntimeException.class, () ->
                registrationUser.register(emptyPassUser),
                "Password can't be empty, password length should be 6 or more characters");
        assertThrows(RuntimeException.class, () ->
                        registrationUser.register(emptyLoginUser),
                "Login can't be empty");
    }

    @Test
    void register_negativeAge_notOk() {
        final User negativeAgeUser = new User();
        negativeAgeUser.setAge(-47);
        negativeAgeUser.setLogin("Negative Age User");
        negativeAgeUser.setPassword("76353422");
        assertThrows(RuntimeException.class, () ->
                        registrationUser.register(negativeAgeUser),
                "Age can't be negative, Users age should be greater than 18yo");
    }
}
