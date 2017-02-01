package hu.vk.web.rest.api;

import hu.vk.model.Game;
import hu.vk.service.api.GetGameDto;
import org.springframework.http.ResponseEntity;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
public interface GameResource {

    ResponseEntity<?> create(Game game);

    ResponseEntity<?> getGameNames();

    ResponseEntity<?> getGame(String id);
}
