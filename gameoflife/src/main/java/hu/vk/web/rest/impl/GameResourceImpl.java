package hu.vk.web.rest.impl;

import hu.vk.model.Game;
import hu.vk.service.api.GameService;
import hu.vk.service.api.dto.GetGameDto;
import hu.vk.web.rest.api.GameResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/games")
public class GameResourceImpl implements GameResource {

    private final GameService gameService;

    @Autowired
    public GameResourceImpl(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Game game) {

        gameService.storeGame(game);
        return ResponseEntity.ok(null);
    }

    @Override
    @GetMapping("/names")
    public ResponseEntity<?> getGameNames() {

        List<GetGameDto> games = gameService.getGameNames();

        return ResponseEntity.ok(games);
    }

    @Override
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getGame(@PathVariable("id") String id) {


        Game game = gameService.getGame(id);

        if (game == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(game);
    }
}
