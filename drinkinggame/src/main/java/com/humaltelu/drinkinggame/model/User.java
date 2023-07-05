package com.humaltelu.drinkinggame.model;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
// import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName="users")
public class User {

    
    @DynamoDBHashKey
    @DynamoDBAutoGeneratedKey
    private String user_id;

    @DynamoDBAttribute
    private String email;
    
    
    @DynamoDBAttribute
    private String username;

    @DynamoDBAttribute
    private Boolean member;

    // @DynamoDBAttribute
    // private Pack packs;

    // @DynamoDBAttribute
    // private List <Pack> packs;
  
 
    @DynamoDBAttribute
    private List <String> liked_packs;



}



