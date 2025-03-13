package core.basesyntax.service;

public class RegistrationError extends Exception {
        public RegistrationError(Errors message) {
            super(message.getMessage());
        }
        
}
