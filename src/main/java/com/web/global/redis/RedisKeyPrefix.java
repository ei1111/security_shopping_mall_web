package com.web.global.redis;

public enum RedisKeyPrefix {

    BOARD_DETAIL("board_detail:v1:"),
    ITEM_DETAIL("item_detail:v1:");
    //PRODUCT_DETAIL("product_detail:v1:");

    private final String prefix;

    RedisKeyPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
