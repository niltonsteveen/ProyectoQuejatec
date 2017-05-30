package co.edu.udea.quejatec.utils;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import co.edu.udea.quejatec.R;

public class PreFragConf extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferent_fragment_configuracion);

    }
}