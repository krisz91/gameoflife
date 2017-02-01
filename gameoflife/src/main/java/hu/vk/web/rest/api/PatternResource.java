package hu.vk.web.rest.api;

import hu.vk.model.Game;
import hu.vk.model.Point;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
public interface PatternResource {

    ResponseEntity<?> getPatterns();

    ResponseEntity<?> getPattern(String patternName);

    ResponseEntity<?> nextGeneration(Game game);
}
