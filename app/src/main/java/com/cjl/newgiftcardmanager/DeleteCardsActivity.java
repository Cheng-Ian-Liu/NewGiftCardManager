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

public class DeleteCardsActivity extends AppCompatActivity {

    Button viewRecordsButton;
    Button deleteOneCardButton;
    Button deleteAllCardsButton;
    EditText deleteCardID;
    MyDBHandler dbHandler;
    String deleteCardIDString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_cards);
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
        viewRecordsButton = (Button) findViewById(R.id.button_viewCards);
        deleteOneCardButton = (Button) findViewById(R.id.button_deleteOneCard);
        deleteAllCardsButton = (Button) findViewById(R.id.button_deleteAllCards);
        deleteCardID = (EditText) findViewById(R.id.editText_deleteCardID);

        deleteOneCard ();
        deleteAllCard();
        viewCardRecords ();
    }

    public void deleteOneCard () {
        deleteOneCardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                deleteCardIDString = deleteCardID.getText().toString();
                if (deleteCardIDString.equals("")) // test if there is an entered ID number
                    Toast.makeText(DeleteCardsActivity.this, "Please enter a card ID", Toast.LENGTH_SHORT).show();
                else {
                    AlertDialog.Builder a_builder = new AlertDialog.Builder(DeleteCardsActivity.this);
                    // show a warning message before deleting
                    a_builder.setMessage("Do you want to delete this card?")
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
                                    Integer deletedRows = dbHandler.deleteCard(deleteCardIDString);
                                    if (deletedRows > 0)
                                        Toast.makeText(DeleteCardsActivity.this, "The card has been deleted", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(DeleteCardsActivity.this, "Error! Can not find this card", Toast.LENGTH_SHORT).show();

                                    deleteCardID.setText("");
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert = a_builder.create();
                    alert.setTitle("Confirmation");
                    alert.show();
                }
            }
        });
    }

    public void deleteAllCard () {
        deleteAllCardsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                AlertDialog.Builder a_builder = new AlertDialog.Builder(DeleteCardsActivity.this);
                a_builder.setMessage("Are you sure to delete ALL cards?")
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
                                dbHandler.deleteAllCards();
                                Toast.makeText(DeleteCardsActivity.this, "ALL Cards have been Deleted", Toast.LENGTH_LONG).show();
                                dialog.cancel();
                            }
                        });

                AlertDialog alert = a_builder.create();
                alert.setTitle("Caution!");
                alert.show();
            }
        });
    }

    public void viewCardRecords () {
        viewRecordsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String dbString = dbHandler.databaseToString();
                showMessage("Card Records", dbString);
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
