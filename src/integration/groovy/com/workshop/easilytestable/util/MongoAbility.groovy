package com.workshop.easilytestable.util

import com.mongodb.DBObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate

trait MongoAbility {

    @Autowired
    MongoTemplate mongoTemplate

    List findAllOrders() {
        return mongoTemplate.findAll(DBObject.class, "orders")
    }
}
