package co.com.blossom.configs.utils;

public final class ErrorCode {
    //Infraestructure code
    public static final String INFRA_ENTITY_NOT_FOUND = "IE1000";
    public static final String INFRA_ENTITY_DBCONSTRAINT_FOUND = "IE1100";
    //Domain code
    public static final String DOMAIN_RESOURCE_NOT_FOUND = "DE1000";
    public static final String DOMAIN_RESOURCE_DELETED = "DE1010";
    public static final String DOMAIN_RESOURCE_DUPLICATE = "DE1050";
    public static final String DOMAIN_VALUE_FIELD_INVALID = "DE1100";
    //General code
    public static final String GLOBAL_ERROR = "GE1000";
    public static final String GLOBAL_INPUTS_VALIDATION = "GE1050";
    private ErrorCode() {}
}
