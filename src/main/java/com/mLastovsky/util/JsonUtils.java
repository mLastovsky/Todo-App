package com.mLastovsky.util;

import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.util.stream.Collectors;

@UtilityClass
public class JsonUtils {

    private static final Gson gson = new Gson();

    public static <T> T parseJsonBody(HttpServletRequest req, Class<T> dtoClass) throws IOException {
        try (var reader = req.getReader()) {
            String jsonBody = reader.lines()
                    .collect(Collectors.joining());
            return gson.fromJson(jsonBody, dtoClass);
        }
    }

    public static <T> String toJson(T obj) {
        return gson.toJson(obj);
    }
}
