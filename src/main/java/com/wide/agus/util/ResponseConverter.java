package com.wide.agus.util;

import com.wide.agus.dto.CommonResponse;
import org.springframework.http.ResponseEntity;

public class ResponseConverter {
    public static <T> ResponseEntity<CommonResponse<T>> toResponseEntity(T data) {
        return ResponseEntity.ok(new CommonResponse<>(200, "success", data));
    }
}
