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

        Game newGeneration = createNewGame(game);

        calculateLivePoints(game, newGeneration);

        return newGeneration;
    }

    private void calculateLivePoints(Game game, Game newGeneration) {
        int rows = game.getRows();
        int columns = game.getColumns();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {

                calculatePoint(game, newGeneration, i, j);

            }
        }
    }

    private void calculatePoint(Game game, Game newGeneration, int i, int j) {
        long neighbourCount = getNeighbourCount(game, i, j);

        boolean isLive = game.isLive(i, j);

        boolean willBeLiving = willLive(neighbourCount, isLive);

        if (willBeLiving) {
            newGeneration.addLivePoint(i, j);
        }
    }

    private Game createNewGame(Game game) {
        Game newGeneration = new Game();

        newGeneration.setColumns(game.getColumns());
        newGeneration.setRows(game.getRows());
        newGeneration.setGeneration(game.getGeneration() + 1);
        return newGeneration;
    }

    public boolean willLive(long neighbourCount, boolean isLiveNow) {

        if ((neighbourCount == 3) && !isLiveNow) {
            return true;
        }

        if ((neighbourCount == 2 || neighbourCount == 3) && isLiveNow) {
            return true;
        }

        return false;
    }


    public long getNeighbourCount(Game game, int x, int y) {

        return game.getLivePoints()
                .stream()
                .filter(livePoint -> livePoint.getX() < x + 2 && livePoint.getX() > x - 2 && livePoint.getY() > y - 2 && livePoint.getY() < y + 2).count() - 1L;
    }

}
