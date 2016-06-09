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
    private String _DescriptionRU;
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

    @DynamoDBAttribute(attributeName = "Book")
    public String get_Book() {
        return _Book;
    }
    @DynamoDBAttribute(attributeName = "Description")
    public String get_Description() {
        return _Description;
    }

    public void set_Description(String _Description) {
        this._Description = _Description;
    }

    @DynamoDBAttribute(attributeName = "DescriptionDE")
    public String get_DescriptionDE() {
        return _DescriptionDE;
    }

    public void set_DescriptionDE(String _DescriptionDE) {
        this._DescriptionDE = _DescriptionDE;
    }

    @DynamoDBAttribute(attributeName = "DescriptionES")
    public String get_DescriptionES() {
        return _DescriptionES;
    }

    public void set_DescriptionES(String _DescriptionES) {
        this._DescriptionES = _DescriptionES;
    }

    @DynamoDBAttribute(attributeName = "DescriptionFR")
    public String get_DescriptionFR() {
        return _DescriptionFR;
    }

    public void set_DescriptionFR(String _DescriptionFR) {
        this._DescriptionFR = _DescriptionFR;
    }

    @DynamoDBAttribute(attributeName = "DescriptionIT")
    public String get_DescriptionIT() {
        return _DescriptionIT;
    }

    public void set_DescriptionIT(String _DescriptionIT) {
        this._DescriptionIT = _DescriptionIT;
    }

    @DynamoDBAttribute(attributeName = "DescriptionRU")
    public String get_DescriptionRU() {
        return _DescriptionRU;
    }

    public void set_DescriptionRU(String _DescriptionRU) {
        this._DescriptionRU = _DescriptionRU;
    }

    @DynamoDBAttribute(attributeName = "DescriptionZH")
    public String get_DescriptionZH() {
        return _DescriptionZH;
    }

    public void set_DescriptionZH(String _DescriptionZH) {
        this._DescriptionZH = _DescriptionZH;
    }

    @DynamoDBAttribute(attributeName = "EndDate")
    public String get_EndDate() {
        return _EndDate;
    }

    public void set_EndDate(String _EndDate) {
        this._EndDate = _EndDate;
    }

    @DynamoDBAttribute(attributeName = "eventType")
    public String get_eventType() {
        return _eventType;
    }

    public void set_eventType(String _eventType) {
        this._eventType = _eventType;
    }

    @DynamoDBAttribute(attributeName = "ImageEvent1")
    public String get_ImageEvent1() {
        return _ImageEvent1;
    }

    public void set_ImageEvent1(String _ImageEvent1) {
        this._ImageEvent1 = _ImageEvent1;
    }

    @DynamoDBAttribute(attributeName = "ImageEvent2")
    public String get_ImageEvent2() {
        return _ImageEvent2;
    }

    public void set_ImageEvent2(String _ImageEvent2) {
        this._ImageEvent2 = _ImageEvent2;
    }

    @DynamoDBAttribute(attributeName = "ImageEvent3")
    public String get_ImageEvent3() {
        return _ImageEvent3;
    }

    public void set_ImageEvent3(String _ImageEvent3) {
        this._ImageEvent3 = _ImageEvent3;
    }

    @DynamoDBAttribute(attributeName = "ImageName")
    public String get_ImageName() {
        return _ImageName;
    }

    public void set_ImageName(String _ImageName) {
        this._ImageName = _ImageName;
    }

    @DynamoDBAttribute(attributeName = "isSlotEvents")
    public String get_isSlotEvents() {
        return _isSlotEvents;
    }

    public void set_isSlotEvents(String _isSlotEvents) {
        this._isSlotEvents = _isSlotEvents;
    }

    @DynamoDBAttribute(attributeName = "memo")
    public String get_memo() {
        return _memo;
    }

    public void set_memo(String _memo) {
        this._memo = _memo;
    }

    @DynamoDBAttribute(attributeName = "memoDE")
    public String get_memoDE() {
        return _memoDE;
    }

    public void set_memoDE(String _memoDE) {
        this._memoDE = _memoDE;
    }

    @DynamoDBAttribute(attributeName = "memoES")
    public String get_memoES() {
        return _memoES;
    }

    public void set_memoES(String _memoES) {
        this._memoES = _memoES;
    }

    @DynamoDBAttribute(attributeName = "memoFR")
    public String get_memoFR() {
        return _memoFR;
    }

    public void set_memoFR(String _memoFR) {
        this._memoFR = _memoFR;
    }

    @DynamoDBAttribute(attributeName = "memoIT")
    public String get_memoIT() {
        return _memoIT;
    }

    public void set_memoIT(String _memoIT) {
        this._memoIT = _memoIT;
    }

    @DynamoDBAttribute(attributeName = "memoRU")
    public String get_memoRU() {
        return _memoRU;
    }

    public void set_memoRU(String _memoRU) {
        this._memoRU = _memoRU;
    }

    @DynamoDBAttribute(attributeName = "memoZH")
    public String get_memoZH() {
        return _memoZH;
    }

    public void set_memoZH(String _memoZH) {
        this._memoZH = _memoZH;
    }

    public void set_Book(String _Book) {
        this._Book = _Book;
    }

    @DynamoDBAttribute(attributeName = "NameDE")
    public String get_NameDE() {
        return _NameDE;
    }

    public void set_NameDE(String _NameDE) {
        this._NameDE = _NameDE;
    }

    @DynamoDBAttribute(attributeName = "NameES")
    public String get_NameES() {
        return _NameES;
    }

    public void set_NameES(String _NameES) {
        this._NameES = _NameES;
    }

    @DynamoDBAttribute(attributeName = "NameFR")
    public String get_NameFR() {
        return _NameFR;
    }

    public void set_NameFR(String _NameFR) {
        this._NameFR = _NameFR;
    }

    @DynamoDBAttribute(attributeName = "NameIT")
    public String get_NameIT() {
        return _NameIT;
    }

    public void set_NameIT(String _NameIT) {
        this._NameIT = _NameIT;
    }

    @DynamoDBAttribute(attributeName = "NameRU")
    public String get_NameRU() {
        return _NameRU;
    }

    public void set_NameRU(String _NameRU) {
        this._NameRU = _NameRU;
    }

    @DynamoDBAttribute(attributeName = "NameZH")
    public String get_NameZH() {
        return _NameZH;
    }

    public void set_NameZH(String _NameZH) {
        this._NameZH = _NameZH;
    }

    @DynamoDBAttribute(attributeName = "office")
    public String get_office() {
        return _office;
    }

    public void set_office(String _office) {
        this._office = _office;
    }

    @DynamoDBAttribute(attributeName = "URL")
    public String get_URL() {
        return _URL;
    }

    public void set_URL(String _URL) {
        this._URL = _URL;
    }

    @DynamoDBAttribute(attributeName = "URLBook")
    public String get_URLBook() {
        return _URLBook;
    }

    public void set_URLBook(String _URLBook) {
        this._URLBook = _URLBook;
    }
}
