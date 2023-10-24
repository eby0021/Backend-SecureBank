package select.constants;

import select.base.BaseEnum;

public enum BaseEnums implements BaseEnum<String, String> {
    // Enum constants
    SUCCESS("request.success", "request success"),
    FAILURE("request.failure", "request failure"),
    OPERATION_SUCCESS("operation.success", "operation success"),
    OPERATION_FAILURE("operation.failure", "operation failure"),
    ERROR("system.error", "system error"),
    NOT_FOUND("not_found", "request resource not found"),
    FORBIDDEN("forbidden", "No access rights");

    private String code;
    private String desc;

    BaseEnums(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String desc() {
        return desc;
    }
}
