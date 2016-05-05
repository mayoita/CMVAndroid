package it.casinovenezia.casinodivenezia;

/**
 * Created by massimomoro on 29/04/16.
 */

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "Events")

public class EventsDO {
    private String _Book;
    private String _Description;
    private String _DescriptionDE;
    private String _DescriptionES;
    private String _DescriptionFR;
    private String _DescriptionIT;
    private String _DescriptionZH;
    private String _EndDate;
    private String _eventType;
    private String _ImageEvent1;
    private String _ImageEvent2;
    private String _ImageEvent3;
    private String _ImageName;
    private String _isSlotEvents;
    private String _memo;
    private String _memoDE;
    private String _memoES;
    private String _memoFR;
    private String _memoIT;
    private String _memoRU;
    private String _memoZH;
    private String _name;
    private String _NameDE;
    private String _NameES;
    private String _NameFR;
    private String _NameIT;
    private String _NameRU;
    private String _NameZH;
    private String _office;
    private String _startDate;
    private String _URL;
    private String _URLBook;


    @DynamoDBHashKey(attributeName = "Name")
    @DynamoDBAttribute(attributeName = "Name")
    public String getName() {
        return _name;
    }
    public void setName(final String _name) {
        this._name = _name;
    }

    @DynamoDBRangeKey(attributeName = "StartDate")
    @DynamoDBAttribute(attributeName = "StartDate")
    public String getStartDate() {
        return _startDate;
    }
    public void setStartDate(final String _startDate) {
        this._startDate = _startDate;
    }


}
