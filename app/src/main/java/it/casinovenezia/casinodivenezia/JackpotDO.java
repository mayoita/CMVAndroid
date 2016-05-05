package it.casinovenezia.casinodivenezia;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

/**
 * Created by massimomoro on 03/05/16.
 */
@DynamoDBTable(tableName = "Jackpot")
public class JackpotDO {
    private String _id;
    private String _jackpot;
    private String _ourJackpot;
    private String _ourJackpotDE;
    private String _ourJackpotES;
    private String _ourJackpotFR;
    private String _ourJackpotIT;
    private String _ourJackpotRU;
    private String _ourJackpotZH;

    @DynamoDBHashKey(attributeName = "id")

    public String getId() {
        return _id;
    }
    public void setId(final String _id) {
        this._id = _id;
    }

    @DynamoDBAttribute(attributeName = "jackpot")
    public String getJackpot() {
        return _jackpot;
    }
    public void setJackpot(final String _jackpot) {
        this._jackpot = _jackpot;
    }
}
