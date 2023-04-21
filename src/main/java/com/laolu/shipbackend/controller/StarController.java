package com.laolu.shipbackend.controller;

import com.laolu.shipbackend.model.SocketClient;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.response.GalaxyResponse;
import com.laolu.shipbackend.model.response.StarResponse;
import com.laolu.shipbackend.model.response.UserResponse;
import com.laolu.shipbackend.service.StarService;
import com.laolu.shipbackend.socket.SocketHandle;
import com.laolu.shipbackend.utils.CommonResponse;
import com.laolu.shipbackend.utils.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/21 19:15
 */

@RestController
@CrossOrigin
@RequestMapping("/api/star")
public class StarController {
    @Autowired
    StarService starService;

    @GetMapping("/{galaxyId}")
    public CommonResponse<List<StarResponse>> getstarListByGalaxyId(@PathVariable Integer galaxyId){
        List<StarResponse> starList = starService.getStarList(galaxyId);
        return CommonResponse.success(starList);
    }


}
