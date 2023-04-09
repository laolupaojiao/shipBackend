package com.laolu.shipbackend.service.impl;

import com.laolu.shipbackend.dao.StoreMapper;
import com.laolu.shipbackend.dao.UserMapper;
import com.laolu.shipbackend.jpa.entity.QSellEntity;
import com.laolu.shipbackend.jpa.entity.QStoreEntity;
import com.laolu.shipbackend.model.Goods;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.request.store.BuyRequest;
import com.laolu.shipbackend.model.request.store.SellRequest;
import com.laolu.shipbackend.model.response.StarStoreListResponse;
import com.laolu.shipbackend.service.StarStoreService;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/4 10:39
 */

@Service
public class StarStoreServiceImpl implements StarStoreService {

    @Autowired
    StoreMapper storeMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean buy(BuyRequest buyRequest) {
        try {
            Goods goods = storeMapper.getStarGoodsById(buyRequest);
            if (ObjectUtils.isEmpty(goods)) {
                return false;
            }
            int price = buyRequest.getAmount() * goods.getPrice();
            User user = userMapper.getUserByIdWithMoneyCondition(buyRequest.getUserId(), price);
            if (ObjectUtils.isEmpty(user)) {
                return false;
            }
            if (user.getMoney() > goods.getPrice()) {
                User update = new User();
                update.setId(user.getId());
                update.setMoney(user.getMoney() - price);
                userMapper.update(update);
            }
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean sell(SellRequest sellRequest) {
        return false;
    }

    @Override
    public List<StarStoreListResponse> getList(Integer starId) {
        QStoreEntity qStoreEntity = QStoreEntity.storeEntity;
        QSellEntity qSellEntity = QSellEntity.sellEntity;
        return jpaQueryFactory.
                select(Projections.bean(StarStoreListResponse.class,
                        qStoreEntity.name,
                        qStoreEntity.description,
                        qStoreEntity.pic,
                        qSellEntity.leftAmount,
                        qSellEntity.originAmount,
                        qSellEntity.price,
                        qSellEntity.type
                ))
                .from(qSellEntity)
                .leftJoin(qStoreEntity)
                .on(qSellEntity.storeId.eq(qStoreEntity.id))
                .where(qSellEntity.leftAmount.gt(0).and(qSellEntity.starId.eq(starId))).fetch();
    }
}
