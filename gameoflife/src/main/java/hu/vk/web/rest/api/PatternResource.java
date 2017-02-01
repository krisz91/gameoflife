package hu.vk.web.rest.api;

import org.springframework.http.ResponseEntity;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
public interface PatternResource {

    ResponseEntity<?> getPatterns();

    ResponseEntity<?> getPattern(String patternName);
}
