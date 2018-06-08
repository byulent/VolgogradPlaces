package ru.byulent.volgogradplaces;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import ru.byulent.volgogradplaces.entities.LocalDB;
import ru.byulent.volgogradplaces.tasks.RegTask;

public class RegActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etEmail;
    private EditText etPass;
    private EditText etName;
    private EditText etBdate;
    private EditText etCity;
    private Spinner spSex;
    private Button bReg;
//    private Drawable defBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        etEmail = findViewById(R.id.editText3);
//        defBackground = etEmail.getBackground();
        etPass = findViewById(R.id.editText4);
        etName = findViewById(R.id.editText6);
        etBdate = findViewById(R.id.editText7);
        etCity = findViewById(R.id.editText8);
        spSex = findViewById(R.id.spinner);
        bReg = findViewById(R.id.button_reg2);
        bReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        String email = etEmail.getText().toString();
        String pass = etPass.getText().toString();
        String name = etName.getText().toString();
        String bdate = etBdate.getText().toString();
        String city = etCity.getText().toString();
        String sex = spSex.getSelectedItem().toString();
        switch (v.getId()){
//            case R.id.editText3:
            case R.id.button_reg2:
                if(email.isEmpty()){
//                    etEmail.getBackground().mutate().setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);
                    etEmail.setError(getString(R.string.required_field));
                    return;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    etEmail.setError(getString(R.string.invalid_email));
                    return;
                }
                 if(pass.isEmpty()){
                    etPass.setError(getString(R.string.required_field));
                    return;
                } else if(etPass.getText().toString().length() < 6){
                    etPass.setError(getString(R.string.min_length));
                    return;
                }
                RegTask task = new RegTask();
                task.execute(email, pass, name, bdate, city, sex);
                finish();
        }
    }
}
