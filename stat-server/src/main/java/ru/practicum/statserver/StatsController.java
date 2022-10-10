package ru.practicum.statserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.statserver.model.*;
import ru.practicum.statserver.service.StatsService;
import ru.practicum.statserver.service.StatsServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping
public class StatsController {
    StatsService statsService;

    @Autowired
    public StatsController(StatsServiceImpl statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/hit")
    public void saveEndpointHit(@RequestBody EndpointHit endpointHit) {
        statsService.handleHit(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStats> takeStats(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                        @RequestParam(required = false) String[] uris,
                                        @RequestParam(required = false, defaultValue = "false") Boolean unique) {

        return statsService.getStats(start, end, uris, unique);
    }


}