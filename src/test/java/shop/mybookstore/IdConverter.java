package shop.mybookstore;

import java.util.UUID;

public class IdConverter {

    public static Long convertUuidToLong(UUID uuid) {
        return Math.abs(uuid.getMostSignificantBits() & Long.MAX_VALUE);
    }

}
