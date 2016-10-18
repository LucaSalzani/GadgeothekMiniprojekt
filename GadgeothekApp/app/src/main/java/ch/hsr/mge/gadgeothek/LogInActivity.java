package ch.hsr.mge.gadgeothek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ch.hsr.mge.gadgeothek.service.LibraryService;

public class LogInActivity extends AppCompatActivity {

    /*
    Logins: test1@test.ch nr: 12341 pw: test1
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Log In");

        LibraryService.setServerAddress("http://mge4.dev.ifs.hsr.ch/public");



        Button btnRegister = (Button)findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button btnLogIn = (Button)findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Implement login
                Intent intent = new Intent(LogInActivity.this, MainTabActivity.class);
                startActivity(intent);
            }
        });
    }
}
