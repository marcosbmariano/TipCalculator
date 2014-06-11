package com.example.tipcalculator.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;


public class TipCalculatorMainActivity extends ActionBarActivity implements View.OnClickListener{
    private EditText billAmount;
    private Button tip15;
    private Button tip10;
    private Button tip20;
    private TextView tipAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator_main);

        setupWidgets();




    }


    //setup all the widgets used in the activity
    private void setupWidgets(){
        tipAmount = (TextView) findViewById(R.id.teVTipAmount);
        billAmount = (EditText)findViewById(R.id.edTBill);
        tip10 = (Button) findViewById(R.id.button10);
        tip15 = (Button) findViewById(R.id.button15);
        tip20 = (Button) findViewById(R.id.button20);
        tip10.setOnClickListener(this);
        tip15.setOnClickListener(this);
        tip20.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button10:
                calculateTipAndDisplay(10);
                break;

            case R.id.button15:
                calculateTipAndDisplay(15);
                break;

            case R.id.button20:
                calculateTipAndDisplay(20);
                break;

            default:


        }
    }


    private void calculateTipAndDisplay( double percent){
        //get bill amount
        double bill = getBill();

        //calculate tip
        double tip = bill *(percent / 100);


        //display tip on widget
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipAmount.setText(currency.format(tip));


    }

    private double getBill(){
        double bill = 0.0 ;

        try { //try to parse the value from the bill
            bill = Double.parseDouble(billAmount.getText().toString());

        } catch(NumberFormatException e){

            Toast.makeText(this,"Bill Amount Cannot Be Empty", Toast.LENGTH_LONG).show();

        } finally {

            return bill;

        }



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
