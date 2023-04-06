package com.laolu.shipbackend.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 21:45
 */

@Getter
@Setter
public class ChatMessageResponse {
    private String name;
    private String content;
    private String level;
    private String avatar;
}
