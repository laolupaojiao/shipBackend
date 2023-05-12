package com.laolu.shipbackend.controller;

import com.laolu.shipbackend.model.request.CreateNewsResquest;
import com.laolu.shipbackend.model.response.NewsResponse;
import com.laolu.shipbackend.service.NewsService;
import com.laolu.shipbackend.utils.CommonResponse;
import com.laolu.shipbackend.utils.UserPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/24 20:30
 */

@RestController
@CrossOrigin
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    NewsService newsService;

    @GetMapping("/{starId}/{page}")
    public CommonResponse<List<NewsResponse>> getStoreListByStarId(@PathVariable Integer starId, @PathVariable Integer page){
        return newsService.getNews(starId, page);
    }

    @PostMapping("")
    public CommonResponse<String> getStoreListByStarId(@RequestBody CreateNewsResquest resquest){
        resquest.setUserId(UserPool.getUser().getId());
        return newsService.createNews(resquest);
    }
}
