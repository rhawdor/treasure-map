package fr.schouvey.william.treasuremap.web;

import fr.schouvey.william.treasuremap.service.TreasureMapService;
import fr.schouvey.william.treasuremap.web.mapper.FileWebMapper;
import fr.schouvey.william.treasuremap.web.mapper.TreasureMapWebMapper;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@Validated
public class TreasureMapController {

    @PostMapping("/json")
    public List<String> processTreasureMapFromJSON(@RequestBody List<String> treasureMapLines) {
        var game = TreasureMapService.processTreasureMap(TreasureMapWebMapper.mapToWorldMap(treasureMapLines),
                TreasureMapWebMapper.mapToAdventurers(treasureMapLines));
        return TreasureMapWebMapper.mapToString(game.worldMap(), game.adventurers());
    }

    @PostMapping("/file")
    @ResponseBody
    public ResponseEntity<InputStreamResource> processTreasureMapFromFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        var treasureMapLines = FileWebMapper.mapFileToListString(multipartFile);
        var game = TreasureMapService.processTreasureMap(TreasureMapWebMapper.mapToWorldMap(treasureMapLines),
                TreasureMapWebMapper.mapToAdventurers(treasureMapLines));
        var file = FileWebMapper.mapListStringToFile(TreasureMapWebMapper.mapToString(game.worldMap(), game.adventurers()));
        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(new InputStreamResource(inputStream));
    }

}
