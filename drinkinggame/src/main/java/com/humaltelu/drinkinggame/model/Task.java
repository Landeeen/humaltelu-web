package com.humaltelu.drinkinggame.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Task {
    
    @DynamoDBAttribute
    private String task_id;
    @DynamoDBAttribute
    private String task_text;
   
    
}