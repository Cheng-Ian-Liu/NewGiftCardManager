package com.cjl.newgiftcardmanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CardRecordActivity extends AppCompatActivity {

    TextView cardRecordsText;
    MyDBHandler dbHandler;
    Button reviewRecordAgainButton;
    Button updateCardTransferButton;
    Button deleteCardTransferButton;
    Button returnToHomeButton;
    String dbString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_record);
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

        cardRecordsText = (TextView) findViewById(R.id.textView_GiftcardRecords);
        reviewRecordAgainButton = (Button) findViewById(R.id.button_viewCardRecordAgain);
        updateCardTransferButton = (Button) findViewById(R.id.button_updateCardTransfer);
        deleteCardTransferButton = (Button) findViewById(R.id.button_deleteCardsTransfer);
        returnToHomeButton = (Button) findViewById(R.id.button_returnToHome);

        dbHandler = MyDBHandler.getInstance(this);
        dbString = dbHandler.databaseToString();
        showMessage ("Card Records", dbString);

        reviewRecordAgain();
        updateCardTransfer();
        deleteCardTransfer();
        returnToHome();

    }

    public void reviewRecordAgain() {
        reviewRecordAgainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showMessage("Card Records", dbString);
            }
        });
    }

    public void updateCardTransfer() {
        updateCardTransferButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(CardRecordActivity.this, UpdateCardsActivity.class));
            }
        });
    }

    public void deleteCardTransfer() {
        deleteCardTransferButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(CardRecordActivity.this, DeleteCardsActivity.class));
            }
        });
    }

    public void returnToHome() {
        returnToHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(CardRecordActivity.this,MainActivity.class));
            }
        });
    }


    public void showMessage(String Title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.show();
    }

}
