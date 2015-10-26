package com.regularCheck;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Priority {
	private String[] keyword = {"���",
								"��ü","��ġ","�ż�","����","�ἱ","����",
								"ȸ��","����","��������","����",
								"����","����",
								"����","����","ü��","����","����",
								"����","����",
								"û��","����","����","����","����","����"};
	private String[] type = {"���",
							"��ġ/��ü","��ġ/��ü","��ġ/��ü","��ġ/��ü","��ġ/��ü","��ġ/��ü",
							"ȸ������","��������","��������","��������",
							"����","����",
							"����","����","ü��","����","����",
							"����/����","����/����",
							"û��/����","û��/����","����","����","����","����"};
	
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
