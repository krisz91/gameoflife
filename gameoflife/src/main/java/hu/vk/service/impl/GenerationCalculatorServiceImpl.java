package hu.vk.service.impl;

import hu.vk.model.Game;
import hu.vk.model.LivePoint;
import hu.vk.service.api.GenerationCalculatorService;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@Service
public class GenerationCalculatorServiceImpl implements GenerationCalculatorService {
    @Override
    public Game calculateNextGeneration(Game game) {

        Game newGeneration = new Game();

        game.setGeneration(game.getGeneration() + 1);

        int rows = game.getRows();
        int columns = game.getColumns();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                int neighbourCount = getNeighbourCount(game, i, j);
                if ((neighbourCount == 3) && (!game.isLive(i, j))) {
                    newGeneration.addLivePoint(i, j);
                }

            }
        }

        return newGeneration;
    }


    /**
     * Return the number of counts a given x,y point
     *
     * @param x
     * @param y
     * @return
     */
    public int getNeighbourCount(Game game, int x, int y) {

        int count = 0;

        int left = ((y == 0) ? 0 : -1);
        int right = ((y == (game.getColumns() - 1)) ? 0 : 1);
        int top = ((x == 0) ? 0 : -1);
        int bottom = ((x == (game.getRows() - 1)) ? 0 : 1);
        for (int i = x + top; i <= x + bottom; i++) {
            for (int j = y + left; j <= y + right; j++) {
                if (i == x && j == y) {
                    continue;
                }
                if (game.isLive(x, y)) {
                    count++;
                }
            }
        }
        return count;
    }
}
