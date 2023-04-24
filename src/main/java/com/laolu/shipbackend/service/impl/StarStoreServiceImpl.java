package com.laolu.shipbackend.service.impl;

import com.laolu.shipbackend.config.consts.CommonCode;
import com.laolu.shipbackend.config.consts.ResponseCode;
import com.laolu.shipbackend.config.consts.StoreCode;
import com.laolu.shipbackend.dao.StoreMapper;
import com.laolu.shipbackend.dao.UserMapper;
import com.laolu.shipbackend.jpa.dao.StoreDao;
import com.laolu.shipbackend.jpa.dao.UserDao;
import com.laolu.shipbackend.jpa.entity.*;
import com.laolu.shipbackend.model.Goods;
import com.laolu.shipbackend.model.User;
import com.laolu.shipbackend.model.request.store.BuyRequest;
import com.laolu.shipbackend.model.request.store.SellRequest;
import com.laolu.shipbackend.model.response.StarStoreListResponse;
import com.laolu.shipbackend.service.BagService;
import com.laolu.shipbackend.service.StarStoreService;
import com.laolu.shipbackend.utils.CommonResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
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
    UserDao userDao;
    @Autowired
    StoreDao storeDao;

    @Autowired
    JPAQueryFactory jpaQueryFactory;

    @Autowired
    BagService bagService;

    @Override
    @Transactional
    public CommonResponse<String> buy(BuyRequest buyRequest) {
        try {
            QSellEntity qSellEntity = QSellEntity.sellEntity;
            SellEntity sellEntity = jpaQueryFactory.select(qSellEntity).from(qSellEntity)
                    .where(qSellEntity.id.eq(buyRequest.getTargetId())).fetchOne();

            if (ObjectUtils.isEmpty(sellEntity)) {
                return CommonResponse.error("无商品信息");
            }

            if (sellEntity.getLeftAmount() < 1 || buyRequest.getAmount() > sellEntity.getLeftAmount()) {
                return CommonResponse.error("商品没那么多库存");
            }


            int price = buyRequest.getAmount() * sellEntity.getPrice();
            QUserEntity qUserEntity = QUserEntity.userEntity;
            UserEntity userEntity = jpaQueryFactory.select(qUserEntity).from(qUserEntity)
                    .where(qUserEntity.id.eq(buyRequest.getUserId())).fetchOne();

            if (ObjectUtils.isEmpty(userEntity)) {
                return CommonResponse.error("无用户信息");
            }

            CommonResponse<String> commonResponse = CommonResponse.success("购买成功！");

            // 如果是系统出售的，玩家扣钱
            if (sellEntity.getType() == StoreCode.SELL) {
                System.out.println("玩家购买");
                if (userEntity.getMoney() >= price) {
                    userEntity.setMoney(userEntity.getMoney() - price);
                    sellEntity.setLeftAmount(sellEntity.getLeftAmount() - buyRequest.getAmount());
                    bagService.addItem(userEntity.getId(), buyRequest.getTargetId(), buyRequest.getAmount());
                } else {
                    commonResponse.setMessage("没这么多钱");
                    commonResponse.setCode(ResponseCode.ERROR);
                    return commonResponse;
                }
            } else {
                // 系统收购，玩家增加金额，背包增加物品
                if (bagService.deleteItem(userEntity.getId(), sellEntity.getStoreId(), buyRequest.getAmount()).getCode() != ResponseCode.SUCCESS) {
                    commonResponse.setMessage("没那么多物品");
                    commonResponse.setCode(ResponseCode.ERROR);
                    return commonResponse;
                }
                userEntity.setMoney(userEntity.getMoney() + price);
                commonResponse.setMessage("出售成功！");
            }

            jpaQueryFactory.update(qSellEntity)
                    .set(qSellEntity.leftAmount, sellEntity.getLeftAmount() - buyRequest.getAmount())
                    .where(qSellEntity.id.eq(buyRequest.getTargetId()))
                    .execute();

            System.out.println(userEntity.getMoney());

            long execute = jpaQueryFactory.update(qUserEntity).set(qUserEntity.money, userEntity.getMoney()).where(qUserEntity.id.eq(buyRequest.getUserId())).execute();
            if (execute == CommonCode.SUCCESS) {
                return commonResponse;
            }
            return CommonResponse.error("操作失败！");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.error("系统错误！");
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
                        qSellEntity.id,
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
