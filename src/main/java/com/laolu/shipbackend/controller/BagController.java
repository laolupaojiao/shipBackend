package com.laolu.shipbackend.controller;

import com.laolu.shipbackend.model.response.BagItemResponse;
import com.laolu.shipbackend.model.response.StarStoreListResponse;
import com.laolu.shipbackend.service.BagService;
import com.laolu.shipbackend.utils.CommonResponse;
import com.laolu.shipbackend.utils.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/19 15:11
 */

@RestController
@CrossOrigin
@RequestMapping("/api/bag")
public class BagController {

    @Autowired
    BagService bagService;

    @GetMapping("/{id}")
    public CommonResponse<List<BagItemResponse>> getStoreListByStarId(@PathVariable Integer id) {
        return bagService.getItems(id);
    }
}
