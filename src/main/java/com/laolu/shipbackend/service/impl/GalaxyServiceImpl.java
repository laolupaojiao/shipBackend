package com.laolu.shipbackend.service.impl;

import com.laolu.shipbackend.config.consts.CommonCode;
import com.laolu.shipbackend.jpa.entity.QGalaxyEntity;
import com.laolu.shipbackend.jpa.entity.QUserEntity;
import com.laolu.shipbackend.model.response.GalaxyResponse;
import com.laolu.shipbackend.service.GalaxyService;
import com.laolu.shipbackend.utils.CommonResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/20 10:40
 */

@Service
public class GalaxyServiceImpl implements GalaxyService {

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    public CommonResponse<List<GalaxyResponse>> getGalaxyList() {
        QGalaxyEntity galaxyEntity = QGalaxyEntity.galaxyEntity;
        List<GalaxyResponse> fetch = jpaQueryFactory.select(Projections.bean(GalaxyResponse.class,
                galaxyEntity.id,
                galaxyEntity.name,
                galaxyEntity.description,
                galaxyEntity.pic,
                galaxyEntity.posX,
                galaxyEntity.posY
        )).from(galaxyEntity).where(galaxyEntity.status.eq(CommonCode.SUCCESS)).fetch();
        return CommonResponse.success(fetch);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void move(Integer userId, Integer galaxyId) {
        QUserEntity qUserEntity = QUserEntity.userEntity;
        jpaQueryFactory.update(qUserEntity).set(qUserEntity.galaxyId, galaxyId).where(qUserEntity.id.eq(userId)).execute();
    }
}
