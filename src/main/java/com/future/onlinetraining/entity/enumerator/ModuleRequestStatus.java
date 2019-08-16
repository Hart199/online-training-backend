package com.future.onlinetraining.entity.enumerator;

public enum ModuleRequestStatus {

    WAITING("waiting"),
    REJECTED("rejected"),
    ACCEPTED("accepted");

    private String status;

    ModuleRequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
