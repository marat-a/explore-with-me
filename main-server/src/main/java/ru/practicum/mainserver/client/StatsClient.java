package ru.practicum.mainserver.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.practicum.mainserver.client.model.EndpointHit;
import ru.practicum.mainserver.client.model.ViewStats;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class StatsClient {

    private final RestTemplate rest;
    private final String serverUrl;

    @Autowired
    public StatsClient(@Value("${statistic-server.url}") String serverUrl) {
        this.rest = new RestTemplate();
        this.serverUrl = serverUrl;
    }

    public void sendHit(String app, String uri, String ip) {
        EndpointHit endpointHit = EndpointHit.builder()
                .app(app)
                .uri(uri)
                .ip(ip)
                .timestamp(LocalDateTime.now())
                .build();
        HttpEntity<EndpointHit> hit = new HttpEntity<>(endpointHit);
        rest.postForObject(serverUrl + "/hit", hit, EndpointHit.class);
    }

    public List<ViewStats> getStatsResponse(String start, String end, List<String> uris, boolean unique) {
        String requestUri = serverUrl + "/stats?start={start}&end={end}&uris={uris}&unique={unique}";

        Map<String, String> urlParam = new HashMap<>();
        urlParam.put("start", start);
        urlParam.put("end", end);
        urlParam.put("uris", String.join(",", uris));
        urlParam.put("unique", Boolean.toString(unique));

        ResponseEntity<ViewStats[]> entity = rest.getForEntity(requestUri, ViewStats[].class, urlParam);

        return entity.getBody() != null ? Arrays.asList(entity.getBody()) : Collections.emptyList();
    }


    public Map<Long, ViewStats> getViewStats(String start, String end, List<String> uri, boolean unique) {
        List<ViewStats> response = getStatsResponse(start, end, uri, false);
        Map<Long, ViewStats> viewStats = new HashMap<>();
        if (response.size() != 0) {
            response.forEach(element -> viewStats.put(extractIdFromUriString(element.getUri()), element));
        }

        return viewStats;
    }

    public Long extractIdFromUriString(String uri) {
        return Long.valueOf(uri.replace("/events/", "").trim());
    }
}