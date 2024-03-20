package co.com.blossom.configs.enumerators;

import java.util.HashMap;
import java.util.Map;

public enum ProductBrandENUM {
    BRAND_A,
    BRAND_B,
    BRAND_C;
    private static final Map<String, ProductBrandENUM> MY_ENUM_MAP = new HashMap<>();

    static {
        for (ProductBrandENUM myEnum : values()) {
            MY_ENUM_MAP.put(myEnum.name(), myEnum);
        }
    }
    public static ProductBrandENUM getByValue(String name) {
        return MY_ENUM_MAP.get(name);
    }
}
