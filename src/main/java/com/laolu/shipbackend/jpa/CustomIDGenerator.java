package com.laolu.shipbackend.jpa;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

/**
 * @author wanyi.lu
 * @date Created in 2023/1/9 14:08
 */
public class CustomIDGenerator extends IdentityGenerator {
    @Override
    public Serializable generate(SharedSessionContractImplementor s, Object obj) {
        Serializable id = s.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, s);
        if (id != null && Integer.parseInt(id.toString()) > 0) {
            return id;
        } else {
            return super.generate(s, obj);
        }
    }
}
