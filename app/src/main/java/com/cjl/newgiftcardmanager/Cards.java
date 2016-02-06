package com.cjl.newgiftcardmanager;


public class Cards {
    //private int _id;
    private String _cardNumber;
    private String _cardCompany;
    private String _cardPin;
    private String _cardExpDate;
    private String _cardBalance;
    private String _cardNotes;

    //empty constructor
    public Cards() {
    }

    //another constructor that takes cardnumber as default parameter
    public Cards(String cardCompany, String cardNumber, String cardPin, String cardExpDate, String cardBalance, String cardNotes) {
        this._cardNumber = cardNumber;
        this._cardCompany = cardCompany;
        this._cardPin = cardPin;
        this._cardExpDate = cardExpDate;
        this._cardBalance = cardBalance;
        this._cardNotes = cardNotes;
    }

    /*public void set_id(int _id) {
        this._id = _id;
    }*/

    public void set_cardNumber(String _cardNumber) {
        this._cardNumber = _cardNumber;
    }

    public void set_cardCompany(String _cardCompany) {
        this._cardCompany = _cardCompany;
    }

    public void set_cardNotes(String _cardNotes) {
        this._cardNotes = _cardNotes;
    }

    public void set_cardBalance(String _cardBalance) {
        this._cardBalance = _cardBalance;
    }

    public void set_cardExpDate(String _cardExpDate) {
        this._cardExpDate = _cardExpDate;
    }

    public void set_cardPin(String _cardPin) {
        this._cardPin = _cardPin;
    }

    /*public int get_id() {
        return _id;
    }*/

    public String get_cardNumber() {
        return _cardNumber;
    }

    public String get_cardCompany() {
        return _cardCompany;
    }

    public String get_cardPin() {
        return _cardPin;
    }

    public String get_cardExpDate() {
        return _cardExpDate;
    }

    public String get_cardBalance() {
        return _cardBalance;
    }

    public String get_cardNotes() {
        return _cardNotes;
    }
}