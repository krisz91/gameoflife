package hu.vk.service.api;

import hu.vk.model.Point;

import java.io.IOException;
import java.util.List;

/**
 * Aim of this class is to parse a lif file
 */
public interface LIFParser {

    List<Point> parse(String fileName) throws IOException;
}
