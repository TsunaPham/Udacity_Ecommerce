package com.example.demo.controllers;

import com.example.demo.model.persistence.Item;

import java.math.BigDecimal;

public class Constants {

    public static final Item ITEM_0;

    public static final Item ITEM_1;

    public static final String USER_NAME = "ViHuynh";

    public static final String RAW_PASSWORD = "password";

    public static final String HASHED_PASSWORD = "$2a$12$B3XSMoAzBm6yjr3zFxLmGetJ5CxtfxmhP8clH0RJg19O2iOrJ4emy";
    static {
        ITEM_0 = new Item();
        ITEM_0.setId(0L);
        ITEM_0.setName("ITEM_0");
        ITEM_0.setPrice(new BigDecimal("3.00"));
        ITEM_0.setDescription("This is description of item 0");

        ITEM_1 = new Item();
        ITEM_1.setId(1L);
        ITEM_1.setName("ITEM_1 Widget");
        ITEM_1.setPrice(new BigDecimal("1.99"));
        ITEM_1.setDescription("This is description of item 1");
    }
}
