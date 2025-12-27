package lib.response;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public record BackendResponse<T>(@NotNull Timestamp timestamp, @NotNull int status, @NotNull String message, @Nullable T data) {

    public BackendResponse(int status, String message, T data) {
        this(new Timestamp(System.currentTimeMillis()), status, message, data);
    }

    public BackendResponse(int status, String message) {
        this(new Timestamp(System.currentTimeMillis()), status, message,null);
    }

}
