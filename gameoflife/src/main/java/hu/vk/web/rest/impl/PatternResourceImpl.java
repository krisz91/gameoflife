package hu.vk.web.rest.impl;

import hu.vk.model.Point;
import hu.vk.service.api.PatternService;
import hu.vk.web.rest.api.PatternResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@RestController
@RequestMapping("/patterns")
public class PatternResourceImpl extends BaseResource implements PatternResource {


    private final PatternService patternService;

    @Autowired
    public PatternResourceImpl(PatternService patternService) {
        Assert.notNull(patternService, "PatternService must be not null");
        this.patternService = patternService;
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


}
