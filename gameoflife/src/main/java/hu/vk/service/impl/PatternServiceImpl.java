package hu.vk.service.impl;

import hu.vk.model.Point;
import hu.vk.service.api.LIFParser;
import hu.vk.service.api.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by vargakrisztian on 2017. 02. 01..
 */
@Service
public class PatternServiceImpl implements PatternService {

    @Value("${lif.source}")
    private String patternSourceDirectory;

    private final LIFParser lifParser;

    @Autowired
    public PatternServiceImpl(LIFParser lifParser) {
        this.lifParser = lifParser;
    }

    @Override
    public List<String> getPatternNames() {

        File folder = new File(patternSourceDirectory);

        File[] fileList = folder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String[] fileNameParts = StringUtils.split(name, ".");

                String lastPart = fileNameParts[fileNameParts.length - 1];

                boolean isLifFile = org.apache.commons.lang3.StringUtils.equals(lastPart, "lif");

                return isLifFile;
            }
        });

        List<String> fileNames = Arrays.stream(fileList).map(File::getName).map(fileName -> {

            String[] fileNameParts = StringUtils.split(fileName, ".");

            String[] fileNamePartsWithoutExtension = Arrays.copyOfRange(fileNameParts, 0, fileNameParts.length - 1);

            return StringUtils.join(fileNamePartsWithoutExtension, ".");
        }).collect(Collectors.toList());

        return fileNames;

    }

    @Override
    public List<Point> getPattern(String patternName) {

        try {
            return lifParser.parse(patternSourceDirectory + File.separator + patternName + ".lif");
        } catch (IOException e) {
            throw new RuntimeException("Not found");
        }
    }
}
