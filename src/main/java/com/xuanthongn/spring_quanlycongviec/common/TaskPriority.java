package com.xuanthongn.spring_quanlycongviec.common;

public enum TaskPriority {
    LOW("Thấp"),
    MEDIUM("Trung bình"),
    HIGH("Cao");

    private final String displayName;

    TaskPriority(String s) {
        displayName = s;
    }

    public String getDisplayName() {
        return displayName;
    }
    }
