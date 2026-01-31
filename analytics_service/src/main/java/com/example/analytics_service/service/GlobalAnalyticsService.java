package com.example.analytics_service.service;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.analytics_service.dto.GlobalMetricResponse;
import com.example.analytics_service.model.GlobalMetrics;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class GlobalAnalyticsService {

    private final MongoTemplate mongoTemplate;

    public GlobalMetricResponse getGlobalMetrics(){
        Query query = Query.query(
            Criteria.where("_id").is("global")
        );
        
        GlobalMetrics globalMetrics = mongoTemplate.findOne(query, GlobalMetrics.class);

        if(globalMetrics == null){
            return new GlobalMetricResponse(0,0,0,0);
        }
        return new GlobalMetricResponse(
            globalMetrics.getTotalUsers(),
            globalMetrics.getTotalPosts(),
            globalMetrics.getTotalLikes(),
            globalMetrics.getTotalFollows()
        );
    }


    public void incrementUserCreatedCount(){
        Query query = Query.query(
            Criteria.where("_id").is("global")
        );

        Update update = new Update().inc("totalUsers" ,1);

        mongoTemplate.upsert(query, update, GlobalMetrics.class);
    }

    public void incrementFollowCount(){
        Query query = Query.query(
            Criteria.where("_id").is("global")
        );

        Update update = new Update().inc("totalFollows" ,1);

        mongoTemplate.upsert(query, update, GlobalMetrics.class);
    }

    public void incrementLikeCount(){
        Query query = Query.query(
            Criteria.where("_id").is("global")
        );

        Update update = new Update().inc("totalLikes" ,1);

        mongoTemplate.upsert(query, update, GlobalMetrics.class);
    }

    public void incrementPostsCount(){
        Query query = Query.query(
            Criteria.where("_id").is("global")
        );

        Update update = new Update().inc("totalPosts" ,1);

        mongoTemplate.upsert(query, update, GlobalMetrics.class);
    }

}
