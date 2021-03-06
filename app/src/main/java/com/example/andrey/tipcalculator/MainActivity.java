// MainActivity.java
// Calculates bills using 15% and custom percentage tips.
package com.example.andrey.tipcalculator;

import java.text.NumberFormat; // for currency formatting

import android.app.Activity; // base class for activities
import android.graphics.Color;
import android.os.Bundle; // for saving state information
import android.support.v7.widget.Toolbar;
import android.text.Editable; // for EditText event handling
import android.text.TextWatcher; // EditText listener
import android.widget.EditText; // for bill amount input
import android.widget.SeekBar; // for changing custom tip percentage
import android.widget.SeekBar.OnSeekBarChangeListener; // SeekBar listener
import android.widget.TextView; // for displaying text


import butterknife.Bind;
import butterknife.ButterKnife;

// MainActivity class for the Tip Calculator app
public class MainActivity extends Activity
{   // currency and percent formatters
   private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
   private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();
   private double billAmount = 0.0; // bill amount entered by the user
   private double customPercent = 0.18; // initial custom tip percentage
   @Bind(R.id.toolBar) Toolbar appToolBar; // shows ToolBar on app
   @Bind(R.id.amountDisplayTextView) TextView amountDisplayTextView;// shows formatted bill amount
   @Bind(R.id.percentCustomTextView) TextView percentCustomTextView; // shows custom tip percentage
   @Bind(R.id.tip15TextView) TextView tip15TextView; // shows 15% tip
   @Bind(R.id.total15TextView) TextView total15TextView;  // shows custom tip amount
   @Bind(R.id.tipCustomTextView) TextView tipCustomTextView; // shows custom tip amount
   @Bind(R.id.totalCustomTextView) TextView totalCustomTextView; // shows total with custom tip
   // called when the activity is first created

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState); // call superclass's version
      setContentView(R.layout.activity_main); // inflate the GUI
      ButterKnife.bind(this);
      initToolBar();

      // update GUI based on billAmount and customPercent 
      amountDisplayTextView.setText(currencyFormat.format(billAmount));
      updateStandard(); // update the 15% tip TextViews
      updateCustom(); // update the custom tip TextViews
      // set amountEditText's TextWatcher
      EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
      amountEditText.addTextChangedListener(amountEditTextWatcher);
      // set customTipSeekBar's OnSeekBarChangeListener
      SeekBar customTipSeekBar = (SeekBar) findViewById(R.id.customTipSeekBar);
      customTipSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
   } // end method onCreate

   private void initToolBar() {
      appToolBar.setTitle("Tip Calculator");
      appToolBar.setTitleTextColor(Color.WHITE);
//      appToolBar.setBackgroundColor(Color.rgb(0,47,100));
   }

   // updates 15% tip TextViews
   private void updateStandard() {
      // calculate 15% tip and total
      double fifteenPercentTip = billAmount * 0.15;
      double fifteenPercentTotal = billAmount + fifteenPercentTip;
      // display 15% tip and total formatted as currency
      tip15TextView.setText(currencyFormat.format(fifteenPercentTip));
      total15TextView.setText(currencyFormat.format(fifteenPercentTotal));
   } // end method updateStandard

   // updates the custom tip and total TextViews
   private void updateCustom() {
      // show customPercent in percentCustomTextView formatted as %
      percentCustomTextView.setText(percentFormat.format(customPercent));
      // calculate the custom tip and total
      double customTip = billAmount * customPercent;
      double customTotal = billAmount + customTip;
      // display custom tip and total formatted as currency
      tipCustomTextView.setText(currencyFormat.format(customTip));
      totalCustomTextView.setText(currencyFormat.format(customTotal));
   } // end method updateCustom
   
   // called when the user changes the position of SeekBar
   private OnSeekBarChangeListener customSeekBarListener = new OnSeekBarChangeListener() {
      // update customPercent, then call updateCustom
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
         // sets customPercent to position of the SeekBar's thumb
         customPercent = progress / 100.0;
         updateCustom(); // update the custom tip TextViews
      } // end method onProgressChanged

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {} // end method onStartTrackingTouch

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {} // end method onStopTrackingTouch
   }; // end OnSeekBarChangeListener

   // event-handling object that responds to amountEditText's events
   private TextWatcher amountEditTextWatcher = new TextWatcher() {
      // called when the user enters a number
      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count)
      {         
         // convert amountEditText's text to a double
         try
         {
            billAmount = Double.parseDouble(s.toString()) / 100.0;
         } // end try
         catch (NumberFormatException e)
         {
            billAmount = 0.0; // default if an exception occurs
         } // end catch 

         // display currency formatted bill amount
         amountDisplayTextView.setText(currencyFormat.format(billAmount));
         updateStandard(); // update the 15% tip TextViews
         updateCustom(); // update the custom tip TextViews
      } // end method onTextChanged

      @Override
      public void afterTextChanged(Editable s) {} // end method afterTextChanged

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {} // end method beforeTextChanged
   }; // end amountEditTextWatcher
} // end class MainActivity


