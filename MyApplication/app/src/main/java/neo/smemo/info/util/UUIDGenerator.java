package neo.smemo.info.util;

import java.util.UUID;

public class UUIDGenerator {

    /**
     * 生成36位的UUID
     *
     * @return
     */
    public static String generate36UUID() {
        return UUID.randomUUID().toString().toUpperCase();
    }

}
