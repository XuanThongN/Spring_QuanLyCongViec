package com.xuanthongn.spring_quanlycongviec.common;

public enum TaskState {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    SAVED("Saved"),
    CANCELED("Canceled");

    private final String displayName;

    TaskState(String s) {
        displayName = s;
    }

    public String getDisplayName() {
        return displayName;
    }
}
