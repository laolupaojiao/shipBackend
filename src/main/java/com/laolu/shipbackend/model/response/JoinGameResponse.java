package com.laolu.shipbackend.model.response;

import com.laolu.shipbackend.model.Star;
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
    List<Star> stars;
    List<UserResponse> players;
    private String token;
}
