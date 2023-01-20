package com.example.pz2_4;


import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Objects;

public class MainActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    private EditText editTextNumberOfCups;//
    private CheckBox checkBoxCream;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchBoxSugar;
    private Spinner spinnerTypeOfCoffee;//
    private TextView textViewPrice;//
    private LinearLayout linearLayoutMenu;
    private String typeCoffee;
    int nCups = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkBoxCream = findViewById(R.id.checkBoxCream);
        switchBoxSugar = findViewById(R.id.swSugar);
        editTextNumberOfCups = findViewById(R.id.etNumberOfCups);
        spinnerTypeOfCoffee = findViewById(R.id.spTypeOfCoffe);
        linearLayoutMenu = findViewById(R.id.llMenu);
        textViewPrice = findViewById(R.id.tvPrice);
        ImageView imageMenu = findViewById(R.id.imMenu);
        imageMenu.setOnClickListener(v -> linearLayoutMenu.setVisibility(VISIBLE));
        Button buttonCalculatePrice = findViewById(R.id.btCalculatePrice);
        buttonCalculatePrice.setOnClickListener(v -> updateSettings());
        editTextNumberOfCups.setOnClickListener(v -> Toast.makeText(this, "Событие", Toast.LENGTH_SHORT).show());

    }


    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }
        }

        return super.dispatchTouchEvent(event);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "Отслеживание переключения: " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();
    }

    private int calculatePrice() {
        int price = 0;
        final int priceSugar = 10;
        final int priceCream = 5;
        try {
            nCups = Integer.parseInt(editTextNumberOfCups.getText().toString());
            typeCoffee = spinnerTypeOfCoffee.getSelectedItem().toString();
        } catch (Exception ex) {
            Toast.makeText(this, "Значение указано неверно", Toast.LENGTH_SHORT).show();
            System.out.println("Catch");
        }

        if (checkBoxCream.isChecked() & switchBoxSugar.isChecked()) {
            price = nCups * (calculateTypeCoffee() + priceCream + priceSugar);
        } else if (checkBoxCream.isChecked() | switchBoxSugar.isChecked()) {
            if (checkBoxCream.isChecked()) {
                price = nCups * (calculateTypeCoffee() + priceCream);
            }
            if (switchBoxSugar.isChecked()) {
                price = nCups * (calculateTypeCoffee() + priceSugar);
            }
        } else {
            price = nCups * calculateTypeCoffee();
        }
        return price;
    }

    private void updateSettings() {
        textViewPrice.setText(String.valueOf(calculatePrice()));
    }

    private int calculateTypeCoffee() {
        int priceCoffee = 0;
        if (Objects.equals(typeCoffee, "americano") | Objects.equals(typeCoffee, "espresso")) {
            priceCoffee = 300;
        } else if (Objects.equals(typeCoffee, "mocha") | Objects.equals(typeCoffee, "latte")) {
            priceCoffee = 400;
        }
        return priceCoffee;
    }


}