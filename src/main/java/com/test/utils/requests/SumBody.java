package com.test.utils.requests;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SumBody {
    private Double sum;

    @JsonCreator
    public SumBody(@JsonProperty("sum") Double sum) {
        this.sum = sum;
    }

}
