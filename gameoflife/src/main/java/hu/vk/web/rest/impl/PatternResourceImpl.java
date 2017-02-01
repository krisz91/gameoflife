package hu.vk.web.rest.impl;

import hu.vk.model.Game;
import hu.vk.model.Point;
import hu.vk.service.api.GenerationCalculatorService;
import hu.vk.service.api.PatternService;
import hu.vk.web.rest.api.PatternResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import sun.jvm.hotspot.memory.Generation;

import java.util.List;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/patterns")
public class PatternResourceImpl extends BaseResource implements PatternResource {


    private final PatternService patternService;

    private final GenerationCalculatorService generationCalculatorService;

    @Autowired
    public PatternResourceImpl(final PatternService patternService, final GenerationCalculatorService generationCalculatorService) {
        Assert.notNull(patternService, "PatternService must be not null");
        Assert.notNull(patternService, "GenerationCalculatorService must be not null");
        this.patternService = patternService;
        this.generationCalculatorService = generationCalculatorService;
    }

    @Override
    @GetMapping
    public ResponseEntity<?> getPatterns() {
        List<String> patterns = patternService.getPatternNames();

        return ResponseEntity.ok(patterns);
    }

    @Override
    @GetMapping(value = "/{patternName}")
    public ResponseEntity<?> getPattern(@PathVariable("patternName") String patternName) {
        List<Point> points = patternService.getPattern(patternName);

        return ResponseEntity.ok(points);
    }

    @Override
    @PostMapping(value = "/nextGeneration")
    public ResponseEntity<?> nextGeneration(@RequestBody Game game) {

        Game newGame = generationCalculatorService.calculateNextGeneration(game);

        return ResponseEntity.ok(newGame);
    }


}
