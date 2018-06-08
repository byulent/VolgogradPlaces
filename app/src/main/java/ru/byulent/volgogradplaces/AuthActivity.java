package ru.byulent.volgogradplaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import ru.byulent.volgogradplaces.entities.User;
import ru.byulent.volgogradplaces.tasks.AuthTask;

import static android.view.View.*;

public class AuthActivity extends AppCompatActivity implements OnClickListener, AuthTask.AuthListener {

    private AuthTask task;
    private EditText etEmail;
    private EditText etPass;
    private Button bAuth;
    private Button bReg;
    private TextView tvPerform;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        task = new AuthTask(this);
        etEmail = findViewById(R.id.editText);
        etPass = findViewById(R.id.editText2);
        bAuth = findViewById(R.id.button_auth);
        bAuth.setOnClickListener(this);
        bReg = findViewById(R.id.button_reg);
        bReg.setOnClickListener(this);
        tvPerform = findViewById(R.id.textView8);
        progressBar = findViewById(R.id.progressBar11);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_auth:
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();
//                Toast.makeText(this,email+ " " +pass, Toast.LENGTH_SHORT).show();
                task.execute(email, pass);
                break;
            case R.id.button_reg:
                Intent intent = new Intent(AuthActivity.this, RegActivity.class);
                startActivity(intent);
        }
    }

    @Override
    public void onAuth(Boolean success) {
        bReg.setEnabled(true);
        bAuth.setEnabled(true);
        if(success) {
            Toast.makeText(this, "Auth is successful", Toast.LENGTH_LONG).show();
            User.authorize();
            finish();
        }
        else Toast.makeText(this, "Auth failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPreAuth() {
        tvPerform.setVisibility(TextView.VISIBLE);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        bAuth.setEnabled(false);
        bReg.setEnabled(false);
    }
}
