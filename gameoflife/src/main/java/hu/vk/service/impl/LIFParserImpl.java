package hu.vk.service.impl;

import com.google.common.collect.Lists;
import hu.vk.model.Point;
import hu.vk.service.api.LIFParser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Aim of this class is to parse a lif file
 */
@Service
public class LIFParserImpl implements LIFParser {

    public List<Point> parse(String fileName) throws IOException {

        List<Point> livePoints = Lists.newLinkedList();

        List<String> lines = getLines(fileName);

        Iterator it = lines.iterator();

        String line = null;

        boolean hasStartWidthP = false;

        while (it.hasNext()) {

            line = skipHeaders(it, line, hasStartWidthP);

            hasStartWidthP = false;

            if (StringUtils.startsWith(line, "#P")) {
                String[] number = StringUtils.split(line, " ");

                int x = Integer.parseInt(number[1]);

                int y = Integer.parseInt(number[2]);

                List<String> pattern = new LinkedList<>();

                /**
                 * Running the end of file or the next line starting with #P
                 * and collect these lines into a list
                 */
                while (it.hasNext()) {
                    String patternLine = ((String) it.next());

                    if (patternLine.startsWith("#P")) {

                        hasStartWidthP = true;
                        line = patternLine;
                        break;
                    } else {
                        pattern.add(patternLine);
                    }
                }
                calculatePart(x, y, pattern, livePoints);
            }


        }

        int minRow = getMinRow(livePoints);
        int minColumn = getMinColumn(livePoints);

        List<Point> newPoints = modifyPoints(livePoints, minRow, minColumn);

        return newPoints;
    }

    private List<Point> modifyPoints(List<Point> livePoints, int minRow, int minColumn) {
        return livePoints.stream().map(point -> {
            Point newPoint = new Point();
            newPoint.setX(point.getX() - minRow);
            newPoint.setY(point.getY() - minColumn);

            return newPoint;
        }).collect(Collectors.toList());
    }

    private String skipHeaders(Iterator it, String line, boolean hasStartWidthP) {
        if (!hasStartWidthP) {
            line = (String) it.next();
        }
        return line;
    }

    private List<String> getLines(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }

    private int getMinColumn(List<Point> livePoints) {
        return livePoints.stream().map(Point::getY).min(Integer::compare).get();
    }

    private int getMinRow(List<Point> livePoints) {
        return livePoints.stream().map(Point::getX).min(Integer::compare).get();
    }

    /**
     * Calculating a #P block, which means that this function adds the point where the symbol is a star
     *
     * @param x
     * @param y
     * @param lines
     */
    public void calculatePart(int x, int y, List<String> lines, List<hu.vk.model.Point> livePoints) {

        Iterator it = lines.iterator();
        int i = 0;
        while (it.hasNext()) {
            String line = (String) it.next();
            for (int j = 0; j < line.length(); ++j) {
                if (line.charAt(j) == '*') {
                    livePoints.add(new Point(x + j, y - i));
                }
            }
            i++;
        }
    }


}
