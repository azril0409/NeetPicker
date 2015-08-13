package com.neetoffice.app.sample.neetpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.neetoffice.library.neetpicker.OnPickerSelectListener;
import com.neetoffice.library.neetpicker.PickerView;

public class MainActivity extends AppCompatActivity implements OnPickerSelectListener {
    private PickerView pickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickerView = (PickerView) findViewById(R.id.pickerView);
        pickerView.setOnPickerSelectListener(this);
    }

    @Override
    public void onPickerSelected(int index) {
        Toast.makeText(this,String.format("onPickerSelected : %s",index),Toast.LENGTH_SHORT).show();
    }
}
