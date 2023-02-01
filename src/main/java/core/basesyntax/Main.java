package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.MyRegistrationException;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;

public class Main {
    public static void main(String[] args) throws MyRegistrationException {
        User jeremy = new User();
        jeremy.setAge(62);
        jeremy.setId(1234235446568L);
        jeremy.setLogin(null);
        jeremy.setPassword("ham0ndDeer#MayGrandma");
        RegistrationService registrationService = new RegistrationServiceImpl();
        registrationService.register(jeremy);
        System.out.println(Storage.people);
    }
}
