package org.silsagusi.joonggaemoa.domain.crawler.naverbudongsan.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
public class ComplexListResponseDto {
    private String result;
    private ComplexData data;

    public Collection<Object> getComplexes() {
        return Collections.singleton(data.getComplexList());
    }

    // 내부 클래스로 data 객체 정의
    @Data
    public static class ComplexData {
        @JsonProperty("result")
        private List<ComplexData> complexList;
    }
}
