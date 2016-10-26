package ch.hsr.mge.gadgeothek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("New Registration");

        Button btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Check that fields contain valid input
                final String etEmail = ((EditText)findViewById(R.id.etEmail)).getText().toString();
                String etName = ((EditText)findViewById(R.id.etName)).getText().toString();
                String etPassword = ((EditText)findViewById(R.id.etPassword)).getText().toString();
                String etStudentID = ((EditText)findViewById(R.id.etStudentID)).getText().toString();

                LibraryService.register(etEmail, etPassword, etName, etStudentID, new Callback<Boolean>() {
                    @Override
                    public void onCompletion(Boolean input) {
                        if (input){
                            Intent intent = new Intent();
                            intent.putExtra("email",etEmail);
                            setResult(RESULT_OK, intent);

                            Toast.makeText(getApplicationContext(), "Register completed", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Register not successful", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), "Error on register: " + message, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
