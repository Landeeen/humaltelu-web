package com.humaltelu.drinkinggame.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.datamodeling.QueryResultPage;
import com.amazonaws.services.dynamodbv2.document.KeyConditions;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.QueryRequest;
import com.humaltelu.drinkinggame.model.Pack;
// import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.humaltelu.drinkinggame.model.User;

@Repository
public class GameRepository {

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    public User saveU(User user) {
        dynamoDBMapper.save(user);
        String string = String.format("Creating user %s", user.getUser_id());
        System.out.print(string);
        return user;
    }

    public Pack saveP(Pack pack) {
        dynamoDBMapper.save(pack);
        String string = String.format("Creating pack %s", pack.getPack_id());
        System.out.print(string);
        return pack;
    }

    public User getUserById(String user_id) {
        String string = String.format("Getting user %s", user_id);
        System.out.print(string);
        User res = dynamoDBMapper.load(User.class, user_id);
        return res;
    }

    public Pack getPackById(String user_id, String pack_id) {
        String string = String.format("Getting pack %s", pack_id);
        System.out.print(string);
        Pack res = dynamoDBMapper.load(Pack.class, user_id, pack_id);
        return res;
    }

    // public Pack getPackByNew(String user_id, String pack_id) {
    //     DynamoDBQueryExpression<Pack> queryExpression = new DynamoDBQueryExpression<Pack>()
    //         .withIndexName("dayStampIndex") // Specify the GSI name
    //         .withHashKeyValues(new Pack())
    //         .withRangeKeyCondition("pack_id", new Condition()
    //             .withComparisonOperator(ComparisonOperator.EQ)
    //             .withAttributeValueList(new AttributeValue().withS(pack_id)))
    //         .withScanIndexForward(true); // Sort in ascending order of day_stamp
    
    //     PaginatedQueryList<Pack> packs = dynamoDBMapper.query(Pack.class, queryExpression);
        
    //     if (!packs.isEmpty()) {
    //         return packs.get(0); // Return the first pack (assuming pack_id is unique)
    //     } else {
    //         return null; // Pack not found
    //     }
    // }

    // public List<Pack> getUserPacksNew() {
    //     DynamoDBQueryExpression<Pack> queryExpression = new DynamoDBQueryExpression<Pack>()
    //         .withIndexName("pack_id-day_stamp-index") // Specify the GSI name
    //         .withHashKeyValues(new Pack())
    //         .withScanIndexForward(true); // Sort in ascending order of day_stamp
    
    //     PaginatedQueryList<Pack> packs = dynamoDBMapper.query(Pack.class, queryExpression);
        
    //     return packs;
    // }

    public List<Pack> getUserPacksNew() {
        DynamoDBQueryExpression<Pack> queryExpression = new DynamoDBQueryExpression<Pack>()
            .withIndexName("pack_id-day_stamp-index") // Specify the GSI name
            .withKeyConditionExpression("day_stamp = :val") // Hash key condition
            .withExpressionAttributeValues(Collections.singletonMap(":val", new AttributeValue().withN("20230701"))) // Replace with the desired day_stamp value
            .withScanIndexForward(false) // Sort in descending order of created_at or pack_id
            .withLimit(50); // Retrieve only the top 50 packs
    
        PaginatedQueryList<Pack> packs = dynamoDBMapper.query(Pack.class, queryExpression);
    
        return packs;
    }

    public List<Pack> getUserPacks(String user_id) {

        
        // System.out.print("still going");
        Pack pack = new Pack();
        pack.setUser_id(user_id);

        DynamoDBQueryExpression<Pack> queryExpression =
                new DynamoDBQueryExpression<Pack>()
                .withHashKeyValues(pack);

        // List<Workout> res = dynamoDBMapper.scan(Workout.class, new DynamoDBScanExpression());
        List<Pack> res =dynamoDBMapper.query(Pack.class, queryExpression);
        return res;
    }

    // public List<Pack> getAllPacks() {
    //     DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
    //     List<Pack> res = dynamoDBMapper.scan(Pack.class, scanExpression);
    //     return res;
    // }

    public List<Pack> getAllPacks() {

        String partitionKey = "private_tier";
        String sortKey = "day_stamp";
        String keyConditionExpression = String.format("%s = :val1 AND begins_with(%s, :val2)", partitionKey, sortKey);

        Pack pack = new Pack();
        pack.setPrivate_tier(2);
        


        DynamoDBQueryExpression<Pack> queryExpression =
                new DynamoDBQueryExpression<Pack>()
                .withIndexName("private_tier-day_stamp-index")
                .withKeyConditionExpression("private_tier = :val1")
                .withConsistentRead(false)
                .withScanIndexForward(false)
                .withLimit(4);
                
                

                Map<String, AttributeValue> attributeValues = new HashMap<>();
                attributeValues.put(":val1", new AttributeValue().withN(Integer.toString(pack.getPrivate_tier())));
                queryExpression.setExpressionAttributeValues(attributeValues);

               
            

       

        QueryResultPage<Pack> queryResultPage = dynamoDBMapper.queryPage(Pack.class, queryExpression);
        List<Pack> res = queryResultPage.getResults().subList(0, Math.min(2, queryResultPage.getResults().size())); // Cap the results at 50
        
        return res;
    }

    // public List<Pack> getAllPacks() {
        // System.out.print("still going");
        // User user = new User();
        // user.setUser_id(user_id);;
        

        // DynamoDBQueryExpression<User> queryExpression =
        //         new DynamoDBQueryExpression<Pack>()
        //         .withExpressionAttributeValues(Map<String, AttributeValue>"attribute_exists(packs)");
       
        

        // DynamoDBQueryExpression<Pack> queryExpression =
        //         new DynamoDBQueryExpression<Pack>()
        //          .withExpressionAttributeNames(Map<String, String>"pack");

        // Map<String, AttributeValue> expressionValues = new HashMap<>();
        // expressionValues.put(":val", AttributeValue.builder().bool(true).build());

        

        // List<Workout> res = dynamoDBMapper.scan(Workout.class, new DynamoDBScanExpression());
    //     List<Pack> res =dynamoDBMapper.query(Pack.class, queryExpression);
    //     return res;
    // }

    public String delete(String workoutid){
        User wor = dynamoDBMapper.load(User.class, workoutid);
        dynamoDBMapper.delete(wor);
        return "User deleted!";
    }

    public String update(String user_id, User workout){
        dynamoDBMapper.save(workout,
            new DynamoDBSaveExpression()
        .withExpectedEntry(user_id, 
            new ExpectedAttributeValue(
                new AttributeValue().withS(user_id)
            )));
        return user_id;
    }
    
}
