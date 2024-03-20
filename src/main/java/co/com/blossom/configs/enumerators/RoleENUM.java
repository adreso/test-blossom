package co.com.blossom.configs.enumerators;

import java.util.HashMap;
import java.util.Map;

public enum RoleENUM {
    ADMIN, GUEST, USER;
    private static final Map<String, RoleENUM> MY_ENUM_MAP = new HashMap<>();

    static {
        for (RoleENUM myEnum : values()) {
            MY_ENUM_MAP.put(myEnum.name(), myEnum);
        }
    }
    public static RoleENUM getByValue(String codigo) {
        return MY_ENUM_MAP.get(codigo);
    }
}
