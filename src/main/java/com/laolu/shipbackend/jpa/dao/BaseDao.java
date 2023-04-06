package com.laolu.shipbackend.jpa.dao;

import com.laolu.shipbackend.jpa.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/6 14:38
 */
@NoRepositoryBean
public interface BaseDao<T extends BaseEntity, I extends Serializable> extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
}
