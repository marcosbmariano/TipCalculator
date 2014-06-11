package com.example.tipcalculator.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


public class TipCalculatorMainActivity extends ActionBarActivity implements
        View.OnClickListener {
    private EditText billAmount;
    private Button tipMinus, tipPlus, tip10, tip15, tip20;
    private TextView tipAmount, tipPercentage;
    private int tipRate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator_main);

        setupWidgets();




    }


    //setup all the widgets used in the activity
    private void setupWidgets(){
        tipAmount = (TextView) findViewById(R.id.teVTipAmount);
        tipPercentage = (TextView) findViewById(R.id.teVCurrentTipPerc);
        billAmount = (EditText)findViewById(R.id.edTBill);
        tip10 = (Button) findViewById(R.id.button10);
        tip15 = (Button) findViewById(R.id.button15);
        tip20 = (Button) findViewById(R.id.button20);
        tipMinus = (Button) findViewById(R.id.btnMinus);
        tipPlus = (Button) findViewById(R.id.btnPlus);
        tipMinus.setOnClickListener(this);
        tipPlus.setOnClickListener(this);
        tip10.setOnClickListener(this);
        tip15.setOnClickListener(this);
        tip20.setOnClickListener(this);

        billAmount.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
              calculateTipAndDisplay();
            }
                                           }

        );

    }//eof setupWidgets()


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button10:
                tipRate = 10;
                break;

            case R.id.button15:
                tipRate = 15;
                break;

            case R.id.button20:
                tipRate = 20;
                break;

            case R.id.btnMinus:
                decreaseTip();
                break;

            case R.id.btnPlus:
                increaseTip();
                break;

            default:


        }
        calculateTipAndDisplay();
    }//eof onClick

    private void decreaseTip(){
        tipRate -= 1;
    }

    private void increaseTip(){
        tipRate += 1;
    }


    private void displayTipPercentage(){
        tipPercentage.setText("" + tipRate );
    }


    private void calculateTipAndDisplay( ){
        //get bill amount
        double bill = getBill();

        //calculate tip
        double tip = bill * (tipRate/100.0);
        displayTipPercentage();
        //display tip on widget
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipAmount.setText("Tip is: " + currency.format(tip));


    }

    private double getBill(){
        double bill = 0.0 ;

        try { //try to parse the value from the bill
            bill = Double.parseDouble(billAmount.getText().toString());

        } catch(NumberFormatException e){

           // Toast.makeText(this,"Bill Amount Cannot Be Empty", Toast.LENGTH_LONG).show();

        }

            return bill;

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tip_calculator_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
