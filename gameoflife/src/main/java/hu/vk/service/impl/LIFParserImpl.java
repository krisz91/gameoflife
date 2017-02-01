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

/**
 * Aim of this class is to parse a lif file
 */
@Service
public class LIFParserImpl implements LIFParser {

    public List<Point> parse(String fileName) throws IOException {

        List<Point> livePoints = Lists.newLinkedList();
        Path path = Paths.get(fileName);

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        Iterator it = lines.iterator();

        String line = null;

        boolean hasStartWidthP = false;

        while (it.hasNext()) {

            if (!hasStartWidthP) {
                line = (String) it.next();
            }

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

        return livePoints;
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
