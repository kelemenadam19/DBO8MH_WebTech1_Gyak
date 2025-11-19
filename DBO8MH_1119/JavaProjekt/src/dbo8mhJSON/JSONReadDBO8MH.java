package dbo8mhJSON;

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;





public class JSONReadDBO8MH {
	public static void main(String args [])
	{
		try(FileReader reader = new FileReader("orarendDBO8MH.json")){
		//Parse
		JSONParser jsonParser = new JSONParser();
		JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);
		
		JSONObject root = (JSONObject) jsonObject.get("orarend");
		JSONArray lessons = (JSONArray) root.get("ora");
		
		System.out.println("DBO8MH Órarend 2025 ősz:\n");
		
		for(int i = 1; i< lessons.size(); i++) {
			JSONObject lesson = (JSONObject) lessons.get(i);
			JSONObject time = (JSONObject) lesson.get("idopont");
			System.out.println("Tárgy: "+ lesson.get("targy"));
			System.out.println("Időpont: "+ time.get("nap") + " "+ time.get("kezdes") +" " + time.get("befejezes"));
			System.out.println("Helyszín: "+ lesson.get("helyszin"));
			System.out.println("Oktató: "+ lesson.get("oktato"));
			System.out.println("Szak: "+ lesson.get("szak"));

		}
		
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
