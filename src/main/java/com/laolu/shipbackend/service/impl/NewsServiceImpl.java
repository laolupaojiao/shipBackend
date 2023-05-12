package com.laolu.shipbackend.service.impl;

import com.laolu.shipbackend.config.consts.CommonCode;
import com.laolu.shipbackend.jpa.dao.NewsDao;
import com.laolu.shipbackend.jpa.dao.UserDao;
import com.laolu.shipbackend.jpa.entity.NewsEntity;
import com.laolu.shipbackend.jpa.entity.QNewsEntity;
import com.laolu.shipbackend.jpa.entity.QUserEntity;
import com.laolu.shipbackend.jpa.entity.UserEntity;
import com.laolu.shipbackend.model.request.CreateNewsResquest;
import com.laolu.shipbackend.model.response.NewsResponse;
import com.laolu.shipbackend.service.NewsService;
import com.laolu.shipbackend.utils.CommonResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/24 20:20
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    NewsDao newsDao;
    @Autowired
    UserDao userDao;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public CommonResponse<List<NewsResponse>> getNews(Integer starId, Integer page) {
        QNewsEntity qNewsEntity = QNewsEntity.newsEntity;
        QUserEntity qUserEntity = QUserEntity.userEntity;
        List<NewsResponse> fetch = jpaQueryFactory.select(Projections.bean(NewsResponse.class,
                        qNewsEntity.content,
                        qNewsEntity.createTime,
                        qUserEntity.nickName
                )).from(qNewsEntity)
                .leftJoin(qUserEntity).on(qNewsEntity.userId.eq(qUserEntity.id))
                .where(qNewsEntity.starId.eq(starId).and(qNewsEntity.status.eq(CommonCode.SUCCESS)))
                .offset(page > 1? (page -1) * 15L : 0)
                .limit(15)
                .fetch();
        return CommonResponse.success(fetch);
    }

    @Override
    public CommonResponse<String> createNews(CreateNewsResquest resquest) {
        Optional<UserEntity> byId = userDao.findById(resquest.getUserId());
        if (byId.isPresent()) {
            UserEntity userEntity = byId.get();
            if (userEntity.getMoney() > 5000) {
                userEntity.setMoney(userEntity.getMoney() - 5000);
                userDao.save(userEntity);

                NewsEntity newsEntity = new NewsEntity();
                BeanUtils.copyProperties(resquest, newsEntity);
                newsDao.save(newsEntity);
                return CommonResponse.success("发布成功！");
            } else {
                return CommonResponse.error("没那么多钱！");
            }
        }
        return CommonResponse.success("请求失败！");
    }
}
