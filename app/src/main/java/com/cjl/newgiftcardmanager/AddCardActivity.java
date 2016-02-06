package com.cjl.newgiftcardmanager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    EditText cardNumber;
    EditText cardCompany;
    EditText cardPin;
    EditText cardExpData;
    EditText cardBalance;
    EditText cardNotes;
    TextView dbText;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
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

        cardCompany = (EditText) findViewById(R.id.editText_company);
        cardNumber = (EditText) findViewById(R.id.editText_cardNumber);
        cardPin = (EditText) findViewById(R.id.editText_pin);
        cardExpData = (EditText) findViewById(R.id.editText_expiration);
        cardBalance = (EditText) findViewById(R.id.editText_balance);
        cardNotes = (EditText) findViewById(R.id.editText_notes);

        dbText = (TextView) findViewById(R.id.textView_database);
    }

    public void addButtonClicked(View view){
        AlertDialog.Builder a_builder = new AlertDialog.Builder(AddCardActivity.this);
        a_builder.setMessage("Do you want to add this card?")
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
                        Cards card = new Cards( cardCompany.getText().toString(),
                                cardNumber.getText().toString(),
                                cardPin.getText().toString(),
                                cardExpData.getText().toString(),
                                cardBalance.getText().toString(),
                                cardNotes.getText().toString());

                        boolean isInserted = dbHandler.addCard(card);
                        clearInput();
                        if (isInserted == true)
                            Toast.makeText(AddCardActivity.this, "A New Card Has Been Successfully Added", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(AddCardActivity.this, "Adding Card Failed", Toast.LENGTH_SHORT).show();
                        //printDatabase();
                        dialog.cancel();
                    }
                });
        AlertDialog alert = a_builder.create();
        alert.setTitle("Confirmation");
        alert.show();
    }

    //created method to print database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        dbText.setText(dbString);

    }

    //create method to clear all input display
    public void clearInput(){
        cardCompany.setText("");
        cardNumber.setText("");
        cardPin.setText("");
        cardExpData.setText("");
        cardBalance.setText("");
        cardNotes.setText("");
    }

}
