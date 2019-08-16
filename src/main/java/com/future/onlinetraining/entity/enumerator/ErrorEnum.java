package com.future.onlinetraining.entity.enumerator;

public enum ErrorEnum {

    USER_NOT_FOUND("User tidak ditemukan."),
    TRAINER_NOT_FOUND("Trainer tidak ditemukan."),
    MODULE_CATEGORY_NOT_FOUND("Kategori modul tidak ditemukan."),
    MODULE_REQUEST_NOT_FOUND("Module request tidak ditemukan."),
    NOT_LOGGED_IN("Anda belum login."),
    MATERIAL_NOT_FOUND("Materi tidak ditemukan."),
    CLASSROOM_NOT_FOUND("Kelas tidak ditemukan."),
    FAILED_UPLOAD_FILE("Gagal mengupload file."),
    CLASSROOM_SESSION_VALIDATION_ERROR("Jumlah sesi pada kelas harus sama dengan jumlah sesi pada modul."),
    EXAM_VALIDATION_ERROR("Opsi ujian harus sama dengan modul."),
    HAS_JOINED_CLASSROOM("Anda sudah mengikuti kelas."),
    MODULE_NOT_FOUND("Module tidak ditemukan.");

    private String message;

    ErrorEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

}
