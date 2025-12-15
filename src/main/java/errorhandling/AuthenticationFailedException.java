package errorhandling;

import static org.eclipse.persistence.config.CacheUsageIndirectionPolicy.Exception;

public class AuthenticationFailedException extends Exception {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
