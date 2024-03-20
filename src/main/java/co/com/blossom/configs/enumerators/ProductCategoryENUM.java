package co.com.blossom.configs.enumerators;

import java.util.HashMap;
import java.util.Map;

public enum ProductCategoryENUM {
    CATEGORY_A,
    CATEGORY_B,
    CATEGORY_C,
    CATEGORY_D;
    private static final Map<String, ProductCategoryENUM> MY_ENUM_MAP = new HashMap<>();

    static {
        for (ProductCategoryENUM myEnum : values()) {
            MY_ENUM_MAP.put(myEnum.name(), myEnum);
        }
    }
    public static ProductCategoryENUM getByValue(String codigo) {
        return MY_ENUM_MAP.get(codigo);
    }

    }
