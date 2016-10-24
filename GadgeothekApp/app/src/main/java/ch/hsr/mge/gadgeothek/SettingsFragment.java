package ch.hsr.mge.gadgeothek;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

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
                
            }
        });


        return rootView;
    }


}
