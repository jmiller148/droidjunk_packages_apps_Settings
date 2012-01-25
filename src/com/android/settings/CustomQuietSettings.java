/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.widget.TimePicker;
import com.android.settings.R;





public class CustomQuietSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    
	private final String Tranq_Settings = "TRANQ_SETTINGS";
	private final String QUIET_TIME = "quiet_time_on";
	private final String START_HOUR = "qt_start_hour";
	private final String START_MIN = "qt_start_min";
	private final String STOP_HOUR = "qt_stop_hour";
	private final String STOP_MIN = "qt_stop_min";
	private final String NOTIF_LED_ON = "qt_led_on";
	private final String NOTIF_SOUND_ON = "qt_sound_on";
	private final String NOTIF_VIBRATE_ON = "qt_vibrate_on";
    
	private PreferenceManager prefMgr;
	private SharedPreferences sharedPref;
	private CheckBoxPreference mQuietTimeOn;
    private Preference mQtStartHour;
    private Preference mQtStartMin;
    private Preference mQtStopHour;
    private Preference mQtStopMin;
    private CheckBoxPreference mQtNotifLedOn;
    private CheckBoxPreference mQtNotifSoundOn;
    private CheckBoxPreference mQtNotifVibrateOn;
  
    public int QtStartTime = 21;
    public int QtStartHour = 0;
    public int QtStartMin = 0;
    public int QtStopTime = 7;
    public int QtStopHour = 0;
    public int QtStopMin = 0;
    
    
    
    /** If there is no setting in the provider, use this. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefMgr = getPreferenceManager();
        prefMgr.setSharedPreferencesName("Tranquility_Settings");
        prefMgr.setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
        prefMgr.getSharedPreferences();
        
       
        
        addPreferencesFromResource(R.xml.custom_quiet_time_settings);
        
        mQuietTimeOn = (CheckBoxPreference) findPreference(QUIET_TIME);
        mQuietTimeOn.setOnPreferenceChangeListener(this);
        mQtStartHour = (Preference) findPreference(START_HOUR);
        mQtStartHour.setOnPreferenceChangeListener(this);
        mQtStartMin = (Preference) findPreference(START_MIN);

        mQtStopHour = (Preference) findPreference(STOP_HOUR);
        mQtStopHour.setOnPreferenceChangeListener(this);
        mQtStopMin = (Preference) findPreference(STOP_MIN);
		
        
        mQtNotifLedOn = (CheckBoxPreference) findPreference(NOTIF_LED_ON);
		mQtNotifLedOn.setOnPreferenceChangeListener(this);
		mQtNotifSoundOn = (CheckBoxPreference) findPreference(NOTIF_SOUND_ON);
		mQtNotifSoundOn.setOnPreferenceChangeListener(this);
		mQtNotifVibrateOn = (CheckBoxPreference) findPreference(NOTIF_VIBRATE_ON);
		mQtNotifVibrateOn.setOnPreferenceChangeListener(this);
		QtStartHour = prefMgr.getSharedPreferences().getInt(START_HOUR, 21);
		QtStartMin = prefMgr.getSharedPreferences().getInt(START_MIN, 0);
		QtStopHour = prefMgr.getSharedPreferences().getInt(STOP_HOUR, 7);
		QtStopMin = prefMgr.getSharedPreferences().getInt(STOP_HOUR, 0);
    
    }

    
    @Override
    public void onResume() {
        super.onResume();
          
    }

    
    @Override
    public void onPause() {
        super.onPause();

    }

 
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

    	if (preference == mQtStartHour) {
			new TimePickerDialog(preferenceScreen.getContext(),
                    startTimeListener,
            		QtStartHour,
                    QtStartMin,
                    false).show();
        } else if (preference == mQtStopHour) {
        	new TimePickerDialog(preferenceScreen.getContext(),
                    stopTimeListener,
            		QtStopHour,
                    QtStopMin,
                    false).show();
        }
    	
    	
    	
    	
        return super.onPreferenceTreeClick(preferenceScreen, preference);
    }
    

    public boolean onPreferenceChange(Preference preference, Object objValue) {
  
     	final String key = preference.getKey();
        if (START_HOUR.equals(key)) {
        	sharedPref = prefMgr.getSharedPreferences();
        	SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(START_HOUR, QtStartHour);
            editor.putInt(START_MIN, QtStartMin);
            editor.commit();

        } else if 
        	(STOP_HOUR.equals(key)) {
            	sharedPref = prefMgr.getSharedPreferences();
            	SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(STOP_HOUR, QtStopHour);
                editor.putInt(STOP_MIN, QtStopMin);
                editor.commit();
        	
        }
        
        return true;
    }
    
    
    TimePickerDialog.OnTimeSetListener startTimeListener =
            new TimePickerDialog.OnTimeSetListener() {
				
				@Override
				public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				
					QtStartHour = hourOfDay;
					QtStartMin = minute;
		    		mQtStartHour.getOnPreferenceChangeListener().onPreferenceChange(mQtStartHour, hourOfDay);
		    		
				}
			}; 
   
    TimePickerDialog.OnTimeSetListener stopTimeListener =
            new TimePickerDialog.OnTimeSetListener() {
						
			@Override
			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							
				QtStopHour = hourOfDay;
				QtStopMin = minute;
				mQtStopHour.getOnPreferenceChangeListener().onPreferenceChange(mQtStopHour, hourOfDay);
			}
		}; 
    
    
    
}
