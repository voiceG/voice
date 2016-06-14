package com.voice.study;


import java.util.HashMap;

import com.voice.business.OperationOfBooks;
import com.voice.model.R;
import com.voice.widget.timePreference;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;

public class Preference extends PreferenceActivity implements OnPreferenceChangeListener {
    ListPreference listpre;
	timePreference timepre;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.preference);  
		timepre = (timePreference) this.findPreference("time");
		timepre.setOnPreferenceChangeListener(this);
		SharedPreferences settings = getSharedPreferences("wordroid.model_preferences", MODE_PRIVATE);
		timepre.setSummary(settings.getString("time", "18:00 ����"));
		CharSequence[] list={"Ӣ��","����"}; 	
		listpre=(ListPreference) this.findPreference("category");
		listpre.setEntries(list);
		CharSequence[] list2={"1","2"}; 
		listpre.setEntryValues(list2);
		listpre.setSummary(listpre.getEntry());
		this.getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener(){

			@Override
			public void onSharedPreferenceChanged(
					SharedPreferences sharedPreferences, String key) {
				// TODO Auto-generated method stub
				listpre.setSummary(listpre.getEntry());
				
			}
			
		});
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean onPreferenceChange(android.preference.Preference preference,
			Object newValue) {
		// TODO Auto-generated method stub
		if(preference.getKey().equals("time")){
			HashMap<String,Integer> map =(HashMap<String, Integer>) newValue;
			String ifAm="����";
			if(map.get("hour")>11)ifAm=" ����";
			int minute=map.get("minute");
			String mi=String.valueOf(minute);
			if (minute<10)mi="0"+minute;
			timepre.setSummary(""+map.get("hour")+":"+mi+" "+ifAm);
			OperationOfBooks OOB = new OperationOfBooks();
			SharedPreferences settings = getSharedPreferences("wordroid.model_preferences", MODE_PRIVATE);
			OOB.setNotify(settings.getString("time", "18:00 ����"),this);
		}
		
		
		return false;
	}
	

}
