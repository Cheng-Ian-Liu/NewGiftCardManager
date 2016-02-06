package com.cjl.newgiftcardmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class UpdateCardsActivity extends AppCompatActivity {

    Button lookUpCardsButton;
    Button selectCardButton;
    Button submitUpdateButton;
    EditText updateCardIDText;
    EditText cardCompanyUpdateText;
    EditText cardNumUpdateText;
    EditText cardPinUpdateText;
    EditText cardExpDateUpdateText;
    EditText cardBalanceUpdateText;
    EditText cardNotesUpdateText;
    MyDBHandler dbHandler;
    String selectedCardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_cards);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dbHandler = MyDBHandler.getInstance(this);
        lookUpCardsButton = (Button) findViewById(R.id.button_LookupCards);
        selectCardButton = (Button) findViewById(R.id.button_SelectCard);
        submitUpdateButton = (Button) findViewById(R.id.button_SubmitUpdates);
        updateCardIDText = (EditText) findViewById(R.id.editText_inputID);
        cardCompanyUpdateText = (EditText) findViewById(R.id.editText_companyUpdate);
        cardNumUpdateText = (EditText) findViewById(R.id.editText_numberUpdate);
        cardPinUpdateText = (EditText) findViewById(R.id.editText_pinUpdate);
        cardExpDateUpdateText = (EditText) findViewById(R.id.editText_expDateUpdate);
        cardBalanceUpdateText = (EditText) findViewById(R.id.editText_balanceUpdate);
        cardNotesUpdateText = (EditText) findViewById(R.id.editText_noteUpdate);

        lookUpCards();
        selectCards();
        submitUpdate();
    }

    public void lookUpCards() {
        lookUpCardsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String dbString = dbHandler.databaseToString();
                showMessage("Current Card Records", dbString);
            }
        });
    }

    public void selectCards() {
        selectCardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                selectedCardID = updateCardIDText.getText().toString();
                if (selectedCardID.equals("")) // test to see if there is an entered number or not
                    Toast.makeText(UpdateCardsActivity.this, "Please enter a card ID", Toast.LENGTH_SHORT).show();
                else{
                    if (dbHandler.cardIDExist(selectedCardID)) {
                        Toast.makeText(UpdateCardsActivity.this, "Card ID " + selectedCardID + " Selected", Toast.LENGTH_SHORT).show();
                        cardCompanyUpdateText.setText(dbHandler.databaseContentString(selectedCardID, dbHandler.getColumnCardcompany()));
                        cardNumUpdateText.setText(dbHandler.databaseContentString(selectedCardID, dbHandler.getColumnCardnumber() ));
                        cardPinUpdateText.setText(dbHandler.databaseContentString(selectedCardID, dbHandler.getColumnCardpin() ));
                        cardExpDateUpdateText.setText(dbHandler.databaseContentString(selectedCardID, dbHandler.getColumnCardexpdate() ));
                        cardBalanceUpdateText.setText(dbHandler.databaseContentString(selectedCardID, dbHandler.getColumnCardbalance() ));
                        cardNotesUpdateText.setText(dbHandler.databaseContentString(selectedCardID, dbHandler.getColumnCardnotes() ));
                    }
                    else{
                        Toast.makeText(UpdateCardsActivity.this, "Not a Valid Card ID", Toast.LENGTH_SHORT).show();
                        updateCardIDText.setText("");
                        clearInput();
                    }
                }
            }
        });
    }

    public void submitUpdate() {
        submitUpdateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(UpdateCardsActivity.this);
                a_builder.setMessage("Do you want to update this card?")
                        .setCancelable(false)
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Cards card = new Cards( cardCompanyUpdateText.getText().toString(),
                                        cardNumUpdateText.getText().toString(),
                                        cardPinUpdateText.getText().toString(),
                                        cardExpDateUpdateText.getText().toString(),
                                        cardBalanceUpdateText.getText().toString(),
                                        cardNotesUpdateText.getText().toString());

                                boolean isUpdated = dbHandler.updateCard(updateCardIDText.getText().toString() ,card);
                                if (isUpdated == true) {
                                    Toast.makeText(UpdateCardsActivity.this, "The Card (ID =" + updateCardIDText.getText().toString() + ") is Updated", Toast.LENGTH_SHORT).show();
                                    updateCardIDText.setText("");
                                    clearInput();
                                }
                                else {
                                    Toast.makeText(UpdateCardsActivity.this, "Error! Invalid Input", Toast.LENGTH_SHORT).show();
                                    updateCardIDText.setText("");
                                    clearInput();
                                }
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Confirmation");
                alert.show();
            }
        });
    }


    // create a method to show current card records as a AlertDialog
    public void showMessage(String Title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }

    //create a method to clear all input display
    public void clearInput(){
        cardCompanyUpdateText.setText("");
        cardNumUpdateText.setText("");
        cardPinUpdateText.setText("");
        cardExpDateUpdateText.setText("");
        cardBalanceUpdateText.setText("");
        cardNotesUpdateText.setText("");
    }


}
