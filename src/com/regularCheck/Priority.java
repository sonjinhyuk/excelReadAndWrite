package com.regularCheck;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Priority {
	private String[] keyword = {"취소",
								"교체","설치","신설","포설","결선","보강",
								"회로","정밀","상태점검","분해",
								"제거","변경",
								"측정","계측","체결","진단","정비",
								"도포","도장",
								"청소","정리","예방","교정","조정","교육"};
	private String[] type = {"취소",
							"설치/교체","설치/교체","설치/교체","설치/교체","설치/교체","설치/교체",
							"회로점검","정밀점검","상태점검","분해정검",
							"제거","변경",
							"측정","측정","체결","진단","진단",
							"도장/도포","도장/도포",
							"청소/정리","청소/정리","예방","교정","교정","교육"};
	
	private int[] priority = {1,
							2,2,2,2,2,2,
							3,3,3,3,
							4,4,
							5,5,5,5,5,
							6,6,
							7,7,7,7,7,7};
	
	public JSONArray calPrioString( String data ){
		JSONArray returndata = new JSONArray();
		for( int i = 0; i < keyword.length; i ++ ){
			if( data.contains(keyword[i]) ) {
				JSONObject json = new JSONObject();
				json.put("type", type[i]);
				returndata.add(json);
			}
		}
		return returndata;
	}
	
}
