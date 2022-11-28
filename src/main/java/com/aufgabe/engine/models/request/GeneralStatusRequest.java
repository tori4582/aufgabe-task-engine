package com.aufgabe.engine.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralStatusRequest {
    private String statusId;
    private String statusName;
    private String description;
    private String color;
    private Integer placementOrder;
}
