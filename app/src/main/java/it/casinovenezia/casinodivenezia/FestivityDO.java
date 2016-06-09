package it.casinovenezia.casinodivenezia;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

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
//    public JSONArray get_festivity() {
//        JSONParser parser = new JSONParser();
//        JSONArray array4 = new JSONArray();
//        try {
//            Object obj = parser.parse(_festivity);
//            array4 = (JSONArray)obj;
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return array4;
//    }
   // public void setFestivity(final String _festivity) {
    //    this._festivity = _festivity;
  //  }

//    public void set_festivity(final String _festivity) {
//        this._festivity = _festivity;
//    }
//
//    public String get_festivity() {
//        JSONParser parser = new JSONParser();
//        JSONArray array4 = new JSONArray();
//     //   try {
//      //      if (_festivity != null) {
//           //     Object obj = parser.parse(_festivity);
//             //   array4 = (JSONArray)obj;
//      //      }
//
//
//     //   } catch (ParseException e) {
//       //     e.printStackTrace();
//      //  }
//     //   return array4;
//        return _festivity;
//    }
    public String getFestivity() {

        return _festivity;
    }
    public void setFestivity(final String _festivity) {
        this._festivity = _festivity;
    }
    public JSONArray getFestivityConv () {
        JSONParser parser = new JSONParser();
        JSONArray array4 = new JSONArray();
        try {
            if (_festivity != null) {
                Object obj = parser.parse(_festivity);
                array4 = (JSONArray)obj;
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return array4;
    }
}