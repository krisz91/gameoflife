package hu.vk.service.impl;

import hu.vk.model.Game;
import hu.vk.model.Point;
import hu.vk.service.api.GenerationCalculatorService;
import org.springframework.stereotype.Service;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@Service
public class GenerationCalculatorServiceImpl implements GenerationCalculatorService {
    @Override
    public Game calculateNextGeneration(Game game) {

        Game newGeneration = new Game();

        newGeneration.setColumns(game.getColumns());
        newGeneration.setRows(game.getRows());
        newGeneration.setGeneration(game.getGeneration() + 1);

        int rows = game.getRows();
        int columns = game.getColumns();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                long neighbourCount = getNeighbourCount(game, i, j);
                if ((neighbourCount == 3) && (!game.isLive(i, j))) {
                    newGeneration.addLivePoint(i, j);
                }

                if ((neighbourCount == 2 || neighbourCount == 3) && game.isLive(i, j)) {
                    newGeneration.addLivePoint(i, j);
                }

            }
        }

        return newGeneration;
    }


    public long getNeighbourCount(Game game, int x, int y) {

        return game.getLivePoints()
                .stream()
                .filter(livePoint -> livePoint.getX() < x + 2 && livePoint.getX() > x - 2 && livePoint.getY() > y - 2 && livePoint.getY() < y + 2).count()-1L;
    }

}
