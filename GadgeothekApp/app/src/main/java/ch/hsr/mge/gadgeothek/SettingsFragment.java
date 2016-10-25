package ch.hsr.mge.gadgeothek;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.net.Inet4Address;

import ch.hsr.mge.gadgeothek.service.Callback;
import ch.hsr.mge.gadgeothek.service.LibraryService;


public class SettingsFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        final Spinner spinner = (Spinner)rootView.findViewById(R.id.spnServer);

        String current = LibraryService.getServerAddress();
        int serverNr = Character.getNumericValue(current.charAt(10));
        if (current.charAt(11) == '0') {
            spinner.setSelection(9);
        } else if (serverNr > 0 && serverNr < 10) {
            spinner.setSelection(serverNr - 1);
        }

        Button btnSave = (Button)rootView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Save Server and reconnect
                final String address = "http://" + spinner.getSelectedItem().toString() + "/public";
                LibraryService.logout(new Callback<Boolean>() {
                    @Override
                    public void onCompletion(Boolean input) {

                        SharedPreferences preferences = getActivity().getSharedPreferences("address", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("address", address);
                        editor.commit();

                        Intent intent = new Intent(getActivity(), LogInActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(getActivity().getApplicationContext(), "Server change failed", Toast.LENGTH_LONG).show();
                    }
                });
                
            }
        });


        return rootView;
    }


}
