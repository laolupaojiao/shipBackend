package com.laolu.shipbackend.service;

import com.laolu.shipbackend.model.request.CreateNewsResquest;
import com.laolu.shipbackend.model.response.NewsResponse;
import com.laolu.shipbackend.utils.CommonResponse;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/24 20:16
 */
public interface NewsService {
    CommonResponse<List<NewsResponse>> getNews(Integer starId, Integer page);

    CommonResponse<String> createNews(CreateNewsResquest resquest);
}
