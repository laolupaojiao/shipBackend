package com.laolu.shipbackend.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/19 13:15
 */

@Data
public class RegCode implements Serializable {
    private String token;
    private long time;
    private String code;
}
