package com.mLastovsky.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PathExtractor {

    private static final int ID_INDEX = 1;
    private static final String PATH_SEPARATOR = "/";

    public static Long getIdFromPath(HttpServletRequest req){
        var params = req.getPathInfo().split(PATH_SEPARATOR);
        var id = params[ID_INDEX];

        return Long.parseLong(id);
    }
}
