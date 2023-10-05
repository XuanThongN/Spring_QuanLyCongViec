package com.xuanthongn.spring_quanlycongviec.common;

public enum NotificationType {
    CREATE("Tạo thẻ mới"),
    UPDATE("Cập nhật thẻ"),
    ADD_USER("Thêm thành viên"),
    LEAVE("Rời đi"),
    JOIN("Tham gia");
    private final String displayName;

    NotificationType(String s) {
        displayName = s;
    }

    public String getDisplayName() {
        return displayName;
    }
}
