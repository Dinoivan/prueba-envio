package com.incloud.hcp.pdf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResourceUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResourceUtil.class);

    public static InputStream getResourceStream(Class<?> clazz, String resourceName) throws FileNotFoundException {
        String cpResourceName;

        LOGGER.debug("Loading: %", resourceName);

        if (!resourceName.startsWith("/")) {
            cpResourceName = "/" + resourceName;
        } else {
            cpResourceName = resourceName;
        }

        InputStream is = clazz.getResourceAsStream(cpResourceName);

        if (is == null) {
            is = new FileInputStream(resourceName);
        }

        return is;
    }
}
