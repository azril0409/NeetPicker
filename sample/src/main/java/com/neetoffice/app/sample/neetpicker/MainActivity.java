package com.neetoffice.app.sample.neetpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.neetoffice.library.neetpicker.OnPickerSelectListener;
import com.neetoffice.library.neetpicker.PickerView;

import java.util.Arrays;

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu1:
                pickerView.setTexts(Arrays.asList(getResources().getStringArray(R.array.list1)));
                return true;
            case R.id.menu2:
                pickerView.setTexts(Arrays.asList(getResources().getStringArray(R.array.list2)));
                return true;
            case R.id.menu3:
                pickerView.setTexts(Arrays.asList(getResources().getStringArray(R.array.dates)));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
