package com.jangyujin.myRoad.domain.festival.dto;

import lombok.Data;
import java.util.List;

@Data
public class FestivalDTO {
    private Response response;

    @Data
    public static class Response {
        private Body body;

        @Data
        public static class Body {
            private Items items;

            @Data
            public static class Items {
                private List<Item> item;

                @Data
                public static class Item {
                    private String title;
                    private String eventstartdate;
                    private String eventenddate;
                    private String addr1;
                    private String contentid;
                    private String firstimage;
                    private String areacode;
                    // 필요한 필드가 있다면 여기에 계속 추가
                }
            }
        }
    }
}
