package com.xuanthongn.spring_quanlycongviec.common;

public enum TaskState {
    TODO("Cần làm"),
    IN_PROGRESS("Đang làm"),
    DONE("Hoàn thành"),
    REVIEW("Review"),
    SAVED("Lưu trữ");

    private final String displayName;

    TaskState(String s) {
        displayName = s;
    }

    public String getDisplayName() {
        return displayName;
    }
}
