package com.laolu.shipbackend.service.impl;

import com.laolu.shipbackend.config.consts.CommonCode;
import com.laolu.shipbackend.jpa.dao.BagDao;
import com.laolu.shipbackend.jpa.entity.BagEntity;
import com.laolu.shipbackend.jpa.entity.QBagEntity;
import com.laolu.shipbackend.jpa.entity.QStoreEntity;
import com.laolu.shipbackend.jpa.entity.StoreEntity;
import com.laolu.shipbackend.model.response.BagItemResponse;
import com.laolu.shipbackend.service.BagService;
import com.laolu.shipbackend.utils.CommonResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.SubQueryExpressionImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.criterion.SubqueryExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2023/4/18 17:58
 */
@Service
public class BagServiceImpl implements BagService {

    @Autowired
    JPAQueryFactory jpaQueryFactory;
    @Autowired
    BagDao bagDao;

    @Override
    public CommonResponse<String> addItem(Integer userId, Integer storeId, Integer amount) {
        BagEntity bagEntity = new BagEntity();
        bagEntity.setUserId(userId);
        bagEntity.setStoreId(storeId);
        bagEntity.setAmount(amount);
        BagEntity save = bagDao.save(bagEntity);
        System.out.println(save.getId());
        if (save.getId() != null) {
            return CommonResponse.success("添加成功");
        }
        return CommonResponse.success("添加失败");
    }

    @Override
    public CommonResponse<String> deleteItem(Integer userId, Integer storeId, Integer amount) {
        QBagEntity entity = QBagEntity.bagEntity;
        Long all = jpaQueryFactory.select(Expressions.simpleTemplate(Long.class, "sum(amount)"))
                .from(entity).where(entity.userId.eq(userId).and(entity.storeId.eq(storeId))).fetchOne();
        if (ObjectUtils.isEmpty(all) || all < amount) {
            return CommonResponse.error("你没这么多物品！");
        }

        CommonResponse<String> addItem = addItem(userId, storeId, -amount);
        if (addItem.getCode() == CommonCode.SUCCESS) {
            return CommonResponse.success("出售成功");
        }
        return CommonResponse.success("出售失败");
    }

    @Override
    public CommonResponse<List<BagItemResponse>> getItems(Integer userId) {
        System.out.println(userId);
        QBagEntity bagEntity = QBagEntity.bagEntity;
        QStoreEntity storeEntity = QStoreEntity.storeEntity;
        JPAQuery<BagItemResponse> query = jpaQueryFactory.select(Projections.bean(BagItemResponse.class,
                        storeEntity.name.as("name"),
                        storeEntity.pic.as("pic"),
                        storeEntity.description.as("description"),
                        Expressions.simpleTemplate(Integer.class, "sum({0})", bagEntity.amount).as("amount")
                )).from(bagEntity)
                .leftJoin(storeEntity).on(storeEntity.id.eq(bagEntity.storeId))
                .where(bagEntity.userId.eq(userId)).groupBy(bagEntity.storeId);
        List<BagItemResponse> items = query.fetch();
        return CommonResponse.success(items);
    }
}
