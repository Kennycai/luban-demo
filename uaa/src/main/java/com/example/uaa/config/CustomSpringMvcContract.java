package com.example.uaa.config;

import feign.Headers;
import feign.MethodMetadata;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * resolve the headers annotation
 */
@Component
public class CustomSpringMvcContract extends SpringMvcContract {

    @Override
    protected void processAnnotationOnMethod(MethodMetadata data, Annotation methodAnnotation, Method method) {
        super.processAnnotationOnMethod(data, methodAnnotation, method);

        if (methodAnnotation instanceof Headers headers) {
            data.template().headers(toMap(headers.value()));
        }
    }

    private static Map<String, Collection<String>> toMap(String[] input) {
        Map<String, Collection<String>> result = new LinkedHashMap<>(input.length);
        for (String header : input) {
            int colon = header.indexOf(':');
            String name = header.substring(0, colon);
            if (!result.containsKey(name)) {
                result.put(name, new ArrayList<>(1));
            }
            result.get(name).add(header.substring(colon + 1).trim());
        }
        return result;
    }
}
