package security;

import java.nio.charset.StandardCharsets;

public final class SharedSecret {

    private static final byte[] SECRET;

    static {
        // Hardcoded dev key (32+ chars for HS256)
        SECRET = "DEV_JWT_SECRET_123456789012345678901234"
                .getBytes(StandardCharsets.UTF_8);
    }

    private SharedSecret() {
    }

    public static byte[] getSharedKey() {
        return SECRET;
    }
}


