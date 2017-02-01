package hu.vk.datasource;


import hu.vk.datasource.lif.LIFParser;

/**
 * A LiF source, which is a concrete datasource
 */
public class LIFSource implements DataSource {


    private String fileName;

    @Override
    public void setData(String data) {
        fileName = data;
    }

    @Override
    public int[][] getPoints() {
        LIFParser parser = new LIFParser(fileName);

        return parser.getPoints();
    }
}
