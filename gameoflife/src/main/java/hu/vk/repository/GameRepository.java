package hu.vk.repository;

import hu.vk.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
public interface GameRepository extends MongoRepository<Game, String> {
    List<Game> findByName(String name);
}
