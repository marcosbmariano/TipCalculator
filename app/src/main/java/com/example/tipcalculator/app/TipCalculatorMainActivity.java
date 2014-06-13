package com.example.tipcalculator.app;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.SharedPreferences.Editor;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.NumberFormat;


public class TipCalculatorMainActivity extends ActionBarActivity implements
        View.OnClickListener {

    private EditText mBillAmount, mTipDivision;
    private Button mTipMinus, mTipPlus, mTip10, mTip15, mTip20;
    private TextView mTipAmount, mTipPercentage, mTotalAmount;
    private int mTipRate; //this variable holds the tip percentage
    private SharedPreferences mSharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator_main);

        setupWidgets();

    }


    //setup all the widgets used in the activity
    private void setupWidgets(){

        mSharedPreferences = getSharedPreferences("SavedPreferences", MODE_PRIVATE);

        //instantiate widgets
        mTipAmount = (TextView) findViewById(R.id.tvTipAmount);
        mTipPercentage = (TextView) findViewById(R.id.tVCurrentTipPerc);
        mTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
        mTipDivision = (EditText) findViewById(R.id.edTDivideTip); //how many people to split the tip
        mBillAmount = (EditText)findViewById(R.id.edTBill);
        mTip10 = (Button) findViewById(R.id.btn10);
        mTip15 = (Button) findViewById(R.id.btn15);
        mTip20 = (Button) findViewById(R.id.btn20);
        mTipMinus = (Button) findViewById(R.id.btnMinus);
        mTipPlus = (Button) findViewById(R.id.btnPlus);

        //setup listeners
        mTipMinus.setOnClickListener(this);
        mTipPlus.setOnClickListener(this);
        mTip10.setOnClickListener(this);
        mTip15.setOnClickListener(this);
        mTip20.setOnClickListener(this);

        //add change listeners to mBillAmount
        addChangeListenerToBillAmount();
        //add change listeners to mTipDivision
        addChangeListenerToTipDivision();

    }//eof setupWidgets()


    //this method enables the billAmount to handle changes in its input
    private void addChangeListenerToBillAmount(){

        mBillAmount.addTextChangedListener(new TextWatcher() {
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

         });

    }//eof addChangeListenerToBillAmount()



    //this method enables the tipDivision to handle changes in its input
    private void addChangeListenerToTipDivision(){
        mTipDivision.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3){
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3){
            }

            @Override
            public void afterTextChanged(Editable editable){
                calculateTipAndDisplay();
            }
        });
    }//eof addChangeListenerToTipDivision()



    @Override
    protected void onPause() {
        Editor editor = mSharedPreferences.edit();
        editor.putInt("TipRate", mTipRate);
        editor.commit();
        super.onPause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mTipRate = mSharedPreferences.getInt("TipRate", 10);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn10:
                mTipRate = 10;
                break;

            case R.id.btn15:
                mTipRate = 15;
                break;

            case R.id.btn20:
                mTipRate = 20;
                break;

            case R.id.btnMinus:
                decreaseTip();
                break;

            case R.id.btnPlus:
            default:
                increaseTip();

        }
        calculateTipAndDisplay();

    }//eof onClick


    private void decreaseTip(){
        mTipRate -= 1;
    }

    private void increaseTip(){
        mTipRate += 1;
    }


    private void displayTipPercentage(){
        mTipPercentage.setText("" + mTipRate);
    }

    //calculates how much each user will pay
    //amount reflect how many user
    private void setTotalAmount(){

        double tip = calculateTip();

        double finalAmountByUser = getBill() / getHowManyPeopleToDivideTip();

        mTotalAmount.setText(getInCurrencyFormat(tip + finalAmountByUser));

    }

    //this method is responsible to calculate and display
    //the tip
    private void calculateTipAndDisplay( ){

        //calculate tip
        double tip = calculateTip();

        displayTipPercentage();

        //set the total amount by user
        setTotalAmount();

        //display tip amount on widget
        mTipAmount.setText("Tip is: " + getInCurrencyFormat( tip ));
    }


    private String getInCurrencyFormat(double value){
        NumberFormat currency = NumberFormat.getCurrencyInstance();
        return currency.format(value);
    }


    private double calculateTip(){
        return ( getBill() * getTipPercentage() ) / getHowManyPeopleToDivideTip();
    }


    private double getTipPercentage(){
        return  mTipRate /100.0;
    }


    private int getHowManyPeopleToDivideTip(){
        int howManyPeople = 1;

        try{
            howManyPeople = Integer.parseInt(mTipDivision.getText().toString());
        }catch (NumberFormatException e){
            //Method is documented to just ignore invalid user input
            //howManyPeople will just be unchanged
        }
        return howManyPeople;
    }


    private double getBill(){
        double bill = 0.0 ;

        try { //try to parse the value from the bill
            bill = Double.parseDouble(mBillAmount.getText().toString());

        } catch(NumberFormatException e){
            //Method is documented to just ignore invalid user input
            //bill will just be unchanged
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
