package com.example.genie.domain.pot.model;

import lombok.Builder;

public class PotObject {

    private final Long potId;
    private final String potName;
    private final String ottType;
    private final Integer recruit;
    private final Integer term;
    private final String masterName;
    private final Long masterId;

    @Builder
    public PotObject(Long potId, String potName, String ottType, Integer recruit, Integer term, String masterName, Long masterId) {
        this.potId = potId;
        this.potName = potName;
        this.ottType = ottType;
        this.recruit = recruit;
        this.term = term;
        this.masterName = masterName;
        this.masterId = masterId;
    }
}