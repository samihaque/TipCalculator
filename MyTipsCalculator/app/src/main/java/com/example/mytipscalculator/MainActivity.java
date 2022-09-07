package com.example.mytipscalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;



// public class MainActivity extends AppCompatActivity {
public class MainActivity extends AppCompatActivity implements TextWatcher, SeekBar.OnSeekBarChangeListener {
    private static final String TAG = "MainActivity";
    //    declare your variables for the widgets
    private EditText editTextBillAmount;
    private SeekBar SeekBarTip;
    private TextView tipPercentView;
    private TextView textViewTipAmount;
    private TextView textViewBillAmount;



    // private lateinit var etBaseAmount: EditText;
   // declare the variables for the calculations
    private double billAmount = 0;
    private double percent = 0.15;
    private double total = 0;
   // set the number formats to be used for the $ amounts , and % amounts
    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         editTextBillAmount = (EditText) findViewById(R.id.enterValue);
         SeekBarTip = (SeekBar) findViewById(R.id.tipSeekBar);
         tipPercentView = (TextView) findViewById(R.id.tipPercent);
         textViewTipAmount = (TextView) findViewById(R.id.FinalTipValue);
         textViewBillAmount = (TextView) findViewById(R.id.TotalAmount);
         editTextBillAmount.addTextChangedListener((TextWatcher) this);
         SeekBarTip.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        Log.d("MainActivity", "inside onTextChanged method: charSequence= "+charSequence);
//        //surround risky calculations with try catch (what if billAmount is 0 ?
//       //charSequence is converted to a String and parsed to a double for you
        if (charSequence != null && charSequence.length() > 0) {
            try {
                billAmount = Double.parseDouble(charSequence.toString()) ;
            } catch(Exception e) {
                billAmount =0;   // or some value to mark this field is wrong. or make a function validates field first ...
            }
        }
      //  billAmount = Double.parseDouble(charSequence.toString()) ;

        Log.d("MainActivity", "Bill Amount = "+billAmount);
//        //setText on the textView
        textViewBillAmount.setText(currencyFormat.format(billAmount));
//        //perform tip and total calculation and update UI by calling calculate
         calculate();//uncomment this line
    }

    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
        Log.i(TAG, "onProgressChanged " +progress);
        percent = seekBar.getProgress();
        Log.i(TAG, "percentChange " +percent);
       // percent = progress / 100; //calculate percent based on seeker value
        calculate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    private void calculate() {
        Log.d("MainActivity", "inside calculate method");


        // format percent and display in percentTextView
        percent = percent/100;
        tipPercentView.setText(percentFormat.format(percent));

        // percent = percent/100;

        // calculate the tip and total
       double tip = billAmount * percent;
      //use the tip example to do the same for the Total

       // display tip and total formatted as currency
       //user currencyFormat instead of percentFormat to set the textViewTip
        textViewTipAmount.setText(currencyFormat.format(tip));

        total = billAmount + tip;
        textViewBillAmount.setText(currencyFormat.format(total));
       //use the tip example to do the same for the Total

    }


}