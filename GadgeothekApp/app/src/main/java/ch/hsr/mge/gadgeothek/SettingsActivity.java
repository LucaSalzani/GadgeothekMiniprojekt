package ch.hsr.mge.gadgeothek;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setTitle("Change server");

        final Spinner spinner = (Spinner)findViewById(R.id.spnServer);

        String current = LibraryService.getServerAddress();
        int serverNr = Character.getNumericValue(current.charAt(10));
        if (current.charAt(11) == '0') {
            spinner.setSelection(9);
        } else if (serverNr > 0 && serverNr < 10) {
            spinner.setSelection(serverNr - 1);
        }

        Button btnSave = (Button)findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String address = "http://" + spinner.getSelectedItem().toString() + "/public";
                if (LibraryService.isLoggedIn()) {
                    LibraryService.logout(new Callback<Boolean>() {
                        @Override
                        public void onCompletion(Boolean input) {

                            SharedPreferences preferences = getSharedPreferences("address", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("address", address);
                            editor.commit();

                            LibraryService.setServerAddress(address);

                            finish();
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(getApplicationContext(), R.string.ServerChangeFailed, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    SharedPreferences preferences = getSharedPreferences("address", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("address", address);
                    editor.commit();

                    LibraryService.setServerAddress(address);

                    finish();
                }



            }
        });

    }
}
