package hu.vk.service.api;

import hu.vk.model.Point;

import java.util.List;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
public interface PatternService {

    List<String> getPatternNames();

    List<Point> getPattern(String patternName);
}
