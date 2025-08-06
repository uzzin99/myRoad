package com.jangyujin.myRoad.domain.festival.dto;

import com.jangyujin.myRoad.domain.festival.dto.FestivalDTO.Response.Body.Items.Item;
import lombok.Data;

import java.util.List;

@Data
public class FestivalPagedResult {
    private List<Item> items;
    private int totalCount;

    public FestivalPagedResult(List<Item> items, int totalCount) {
        this.items = items;
        this.totalCount = totalCount;
    }


}
