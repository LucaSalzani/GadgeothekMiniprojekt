package ch.hsr.mge.gadgeothek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

public class LogInActivity extends AppCompatActivity {

    /*
    Logins: test1@test.ch nr: 12341 pw: test1
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String mail = data.getStringExtra("email");
            ((EditText)findViewById(R.id.etEmail)).setText(mail);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log In");


        SharedPreferences preferences = getSharedPreferences("address", Context.MODE_PRIVATE);
        String address = preferences.getString("address","http://mge3.dev.ifs.hsr.ch/public");
        LibraryService.setServerAddress(address);



        Button btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, 1);
            }
        });

        Button btnLogIn = (Button)findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)findViewById(R.id.etEmail)).getText().toString();
                String password = ((EditText)findViewById(R.id.etPassword)).getText().toString();

                //LibraryService.login(email, password, new Callback<Boolean>() { //TODO: Uncomment at the end
                LibraryService.login("test1", "test1", new Callback<Boolean>() {
                    @Override
                    public void onCompletion(Boolean input) {
                        if (input) {
                            Intent intent = new Intent(LogInActivity.this, MainTabActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong user or password", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), "Login failed: " + message, Toast.LENGTH_LONG).show();
                    }
                });

            }
        });


        ImageButton ibtnSettings = (ImageButton)findViewById(R.id.ibtnSettings);
        ibtnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LogInActivity.this, SettingsActivity.class);
                startActivity(intent);

            }
        });

    }
}
