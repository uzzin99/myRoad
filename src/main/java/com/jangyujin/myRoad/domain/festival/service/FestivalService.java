package com.jangyujin.myRoad.domain.festival.service;

import com.jangyujin.myRoad.domain.festival.dto.FestivalDTO;
import com.jangyujin.myRoad.domain.festival.dto.FestivalDTO.Response.Body.Items.Item;
import com.jangyujin.myRoad.domain.festival.dto.FestivalPagedResult;
import io.swagger.v3.core.util.Json;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class FestivalService {

    public ResponseEntity<List<Item>> getFestivals() {

        RestTemplate restTemplate = new RestTemplate();

        String baseUrl = "https://apis.data.go.kr/B551011/KorService2/searchFestival2";

        String rawKey = "j18T//z2WUjaqI5bkEbV0w0scqtuCQQaI1aJ3O7gg0klFbYlNU5woVOEch63Z+154AKiPLPOdGFu8oHkYuNAEQ==";
        String encodedKey = URLEncoder.encode(rawKey, StandardCharsets.UTF_8);

        URI uri = UriComponentsBuilder.fromUriString(baseUrl)
                // 실제 공공데이터 API는 페이지 단위 호출 가능하지만,
                // 전체 데이터를 한번에 받아서 서버단에서 페이징하기 위해 1페이지에 충분히 많은 데이터 요청
                .queryParam("pageNo", 1) // 1페이지 고정
                .queryParam("numOfRows", 1000)
                .queryParam("MobileOS", "web")
                .queryParam("MobileApp", "myroad")
                .queryParam("_type", "json")
                .queryParam("eventStartDate", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                .queryParam("serviceKey", encodedKey)
                .build(true)
                .toUri();

        ResponseEntity<FestivalDTO> response = restTemplate.getForEntity(uri, FestivalDTO.class);
        List<Item> allItems = response.getBody().getResponse().getBody().getItems().getItem();

        LocalDate now = LocalDate.now();
        List<Item> ongoingFestivals = new ArrayList<>();

        for (Item item : allItems) {
            try {
                String start = item.getEventstartdate();
                String end = item.getEventenddate();

                if (start != null && end != null && start.length() == 8 && end.length() == 8) {
                    LocalDate startDate = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyyMMdd"));
                    LocalDate endDate = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyyMMdd"));

                    if ((now.isEqual(startDate) || now.isAfter(startDate)) &&
                            (now.isEqual(endDate) || now.isBefore(endDate))) {
                        ongoingFestivals.add(item);
                    }
                }
            } catch (Exception e) {
                continue;
            }
        }

        int totalCount = ongoingFestivals.size();
//        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
//
//        // pageNo 범위 체크
//        if (pageNo < 1) pageNo = 1;
//        if (pageNo > totalPages) pageNo = totalPages;
//
//        int fromIndex = (pageNo - 1) * pageSize;
//        int toIndex = Math.min(fromIndex + pageSize, totalCount);
//
//        List<Item> pagedList = new ArrayList<>();
//        if (fromIndex < totalCount) {
//            pagedList = ongoingFestivals.subList(fromIndex, toIndex);
//        }

        //return new FestivalPagedResult(ongoingFestivals, totalCount);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ongoingFestivals);
    }
}
