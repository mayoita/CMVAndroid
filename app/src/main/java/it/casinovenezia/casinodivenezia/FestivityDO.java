package it.casinovenezia.casinodivenezia;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by massimomoro on 04/05/16.
 */

@DynamoDBTable(tableName = "Festivity")
public class FestivityDO {
    private String _id;
    private String _festivity;

    @DynamoDBHashKey(attributeName = "id")

    public String getId() {
        return _id;
    }
    public void setId(final String _id) {
        this._id = _id;
    }

    @DynamoDBAttribute(attributeName = "festivity")
    public String getFestivity() {
        return _festivity;
    }
    public void setFestivity(final String _festivity) {
        this._festivity = _festivity;
    }
}