package com.mLastovsky.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PathIdExtractor {

    public static Long getIdFromPath(HttpServletRequest req){
        var pathInfo = req.getPathInfo();
        var parts = pathInfo.split("/");
        var id = parts[1];

        return Long.parseLong(id);
    }
}
