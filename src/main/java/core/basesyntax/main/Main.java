package core.basesyntax.main;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;

public class Main {
    public static void main(String[] args) {
        RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
        User user1 = new User(10l, "Mykola", "1234567", 21);
        registrationService.register(user1);
        User user2 = new User(10l, "Mykol", "1234567", 17);
        registrationService.register(user2);
    }
}
