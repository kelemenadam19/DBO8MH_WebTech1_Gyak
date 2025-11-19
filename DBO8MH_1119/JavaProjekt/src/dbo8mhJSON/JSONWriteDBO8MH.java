package dbo8mhJSON;

import org.json.simple.JSONArray;

public class JSONWriteDBO8MH {
	public static void main(String args[]) {
		
			JSONParser JSONparser = new JSONParser();
			try(Reader reader = new FileReader("orarendDBO8MH.json")){
				
				JSONObject JSONObject = new JSONObject();
				JSONArray oraArray = new JSONArray();
				oraArray.add(new String[]{"Szoftvertesztelés","Dr. Hornyák Olivér","Mérnökinformatikus","hétfő","10","12","Inf 103"});
				oraArray.add(new String[]{"Szoftvertesztelés","Dr. Hornyák Olivér","Mérnökinformatikus","hétfő","12","14","Inf 103"});
				oraArray.add(new String[]{"Web technológiák","Agárdi Anita","Mérnökinformatikus","hétfő","14","16","XXX. előadó"});
				oraArray.add(new String[]{"Web technológiák","Agárdi Anita","Mérnökinformatikus","hétfő","16","18","Inf 202"});
				oraArray.add(new String[]{"Mesterséges Intelligencia","Kunné Dr amás Judit","Mérnökinformatikus","Kedd","10","12","XXXII. előadó"});
				oraArray.add(new String[]{"Adatkezelés XML-ben","Dr. Bednarik László","Mérnökinformatikus","kedd","12","14","online"});
				oraArray.add(new String[]{"Webes Alkalmazások (Java)","Selmeci Viktor","Mérnökinformatikus","kedd","14","16","online"});
				oraArray.add(new String[]{"Webes Alkalmazások (Java)","Selmeci Viktor","Mérnökinformatikus","kedd","16","18","online"});
				oraArray.add(new String[]{"Adatkezelés XML-ben","Dr. Bednarik László","Mérnökinformatikus","szerda","10","12","Inf 101"});
				oraArray.add(new String[]{"Mesterséges Intelligencia","Fazekas Levente","Mérnökinformatikus","kedd","10","12","I. előadó"});
				
				for(int i = 0; oraArray.size(); i++)
				{
					JSONObject localObject = (JSONObject) oraArray.get(i);
					JSONObject time = (JSONObject) localObject.get("idopont")
					System.out.println("Tárgy: "+ localObject.get("targy"));
					System.out.println("Időpont: "+ time.get("nap") + " "+ time.get("kezdes") +" " + time.get("befejezes"));
					System.out.println("Helyszín: "+ localObject.get("helyszin"));
					System.out.println("Oktató: "+ localObject.get("oktato"));
					System.out.println("Szak: "+ localObject.get("szak"));
					
					
				}
				JSONObject oraObject = new JSONObject();
				oraObject.put("ora",oraArray);
				FileWriter file = new FileWriter("orarendDBO8MH.json");
				 
				
			}catch(Exception e){
				
				e.printStackTrace();
				
			}
		
	}
}
