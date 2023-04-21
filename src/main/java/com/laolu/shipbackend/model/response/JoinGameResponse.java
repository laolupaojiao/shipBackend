package com.laolu.shipbackend.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2022/9/11 23:00
 */
@Getter
@Setter
@ToString
public class JoinGameResponse {
    List<StarResponse> stars;
    List<UserResponse> players;
    private String token;
}
