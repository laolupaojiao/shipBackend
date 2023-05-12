package com.laolu.shipbackend.model.response;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/24 20:17
 */

@Data
public class NewsResponse {
    private String nickName;
    private String content;
    private String createTime;
}
