package com.future.onlinetraining.entity.enumerator;

public enum ClassroomStatus {

    OPEN("open"),
    CLOSED("closed"),
    ONGOING("ongoing");

    private String status;

    ClassroomStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
