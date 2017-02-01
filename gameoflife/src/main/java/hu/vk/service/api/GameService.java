package hu.vk.service.api;

import hu.vk.model.Game;
import hu.vk.service.api.dto.GetGameDto;

import java.util.List;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */

public interface GameService {

    public void storeGame(Game game);

    List<GetGameDto> getGameNames();

    Game getGame(String id);
}
