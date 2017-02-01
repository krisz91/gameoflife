package hu.vk.datasource.lif;

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
public class LIFParser {

    /**
     * File name
     */
    private String fileName;

    /**
     * The live points
     */
    private LinkedList<Point> points;

    public LIFParser(String fileName) {
        this.fileName = fileName;
        points = new LinkedList<>();
    }


    /**
     * Converting the point list to two dimensional array where the index zero is the x, and index one means the y coordinate
     * @return
     */
    private int[][] merge() {

        int[][] pointArray = new int[points.size()][2];
        int i=0;
        for (Point p : points) {
            pointArray[i][0] = p.x;
            pointArray[i++][1] = p.y;
        }

        return pointArray;
    }

    /**
     * Parsing the lif file
     * @throws IOException
     */
    public void parse() throws IOException {
        Path path = Paths.get(fileName);

        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

        Iterator it = lines.iterator();


        String line = null;
        boolean hasStartWidthP = false;
        while (it.hasNext()) {


            if(!hasStartWidthP) {
                line = (String) it.next();
            }

            hasStartWidthP = false;


            if (StringUtils.startsWith("#P")) {
                String[] number = line.split(" ");
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
                calculatePart(x,y,pattern);
            }


        }
    }

    public int[][] getPoints() {
        try {
            parse();
        } catch (IOException e) {
            System.out.println(e);
        }
        return merge();
    }

    /**
     * Calculating a #P block, which means that this function adds the point where the symbol is a star
     * @param x
     * @param y
     * @param lines
     */
    public  void calculatePart(int x, int y, List<String> lines) {

        Iterator it = lines.iterator();
        int i=0;
        while (it.hasNext()) {
            String line = (String) it.next();
            for (int j = 0; j < line.length(); ++j) {
                if (line.charAt(j) == '*') {
                    points.add(new Point(x+j, y-i));
                }
            }
            i++;
        }
    }

    /**
     * Nested class to handling the x/y coordinate easily
     */
    static class Point {
        private int x;
        private int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
