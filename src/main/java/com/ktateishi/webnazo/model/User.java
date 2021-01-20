package com.ktateishi.webnazo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private String name;
    private String registeredDate;
    private int rank;
}
