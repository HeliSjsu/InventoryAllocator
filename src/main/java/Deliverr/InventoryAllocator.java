package Deliverr;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * @author shahh
 *
 */
public class InventoryAllocator {
	
	public static void main(String []args) {
		String orderInput = "";
		String inventoryInput = "";
		
		Scanner sc = new Scanner(System.in);
		orderInput = sc.nextLine();
		inventoryInput = sc.nextLine();	
		
		
		Map<String,Integer> order = generateOrder(orderInput); 
		
		List<Map<String,Integer>> inventory = new ArrayList<>();
		List<String> names = new ArrayList<>();
		
		generateInventory(inventoryInput, inventory, names);
		
	    Map<String,Map<String,Integer>> ans= allocateInventory(order,inventory,names);
		
		System.out.println(formulateJSON(ans).toJSONString());
	}

	/*
	 * Populate value in List inventory & names 
	 */
	protected static void generateInventory(String inventoryInput, List<Map<String, Integer>> inventory,
			List<String> names) {
		try {
		
			String[] inventoryInputSplit = inventoryInput.substring(1, inventoryInput.length()-1).split("}");
			
			for(String str: inventoryInputSplit) {
				if(str.trim().isEmpty()) continue;
				String temp = str.substring(str.indexOf('{')+1);  
				String name = temp.substring(temp.indexOf(':')+1,temp.indexOf(',')).trim();
				String[] splitTemp = temp.substring(temp.indexOf('{')+1).split(",");
				Map<String,Integer> inv1 = new HashMap<>();
				for(String inv : splitTemp) {
					String[] inventoryAmount = inv.split(":");
					inv1.put(inventoryAmount[0].trim(), Integer.parseInt(inventoryAmount[1].trim()));
				}
				if(inv1.size() > 0) {
					inventory.add(inv1);
					names.add(name);		
				}
			}
		}catch (Exception e) {
			System.err.println("Invalid Input format");
		}
		
	}
	
	/*
	 * Populate order inpute details in map 
	 */
	protected static Map<String, Integer> generateOrder(String orderInput) {
		Map<String,Integer> order = new HashMap<>();
		
		try {
			String[] orderInputSplit = orderInput.substring(1, orderInput.length()-1).split(",");
			
			for(String str : orderInputSplit) {
				String[] splitedorder = str.split(":");
				order.put(splitedorder[0].trim(), Integer.parseInt(splitedorder[1].trim()));
			}	
		}catch(Exception e) {
			System.err.println("Invalid Input format");
		}
		return order;
	}

	/*
	 * Write output in Json format.
	 */
	protected static JSONArray formulateJSON(Map<String, Map<String, Integer>> ans) {
		 JSONArray ja = new JSONArray();
		 if(ans.size() < 1) return ja;
		 for(Map.Entry<String, Map<String, Integer>> entry : ans.entrySet()) {
			 JSONObject jo = new JSONObject(); 
			 String vendorName = entry.getKey();
			 Map<String,Integer> tempMap = entry.getValue();
			 int size = tempMap.size();
			 Map m = new LinkedHashMap(size); 
			 for(Map.Entry<String, Integer> inventory : tempMap.entrySet()) {
				 m.put(inventory.getKey(), inventory.getValue());
			 }
			 jo.put(vendorName, m);
			 ja.add(jo);
		 }
		 
		 PrintWriter pw;
		try {
			pw = new PrintWriter("JSONExample.json");
			pw.write(ja.toJSONString()); 
		    pw.flush(); 
		    pw.close(); 
		} catch (FileNotFoundException e) {
			System.out.println("Error occured while writing data into file");
			e.printStackTrace();
		} 
	       
	        
		 System.out.println(ja.toJSONString());
		 return ja;
	}

	/*
	 * Allocate Inventory to produce the cheapest shipment. 
	 */
	protected static Map<String,Map<String,Integer>> allocateInventory(Map<String, Integer> orders, List<Map<String, Integer>> inventory,
			List<String> names) {
		Map<String,Map<String,Integer>> finalans = new HashMap<>();
		try {
			for(int i=0; i<inventory.size(); i++) {
				Map<String,Integer> inventories = inventory.get(i);
				Iterator<Map.Entry<String,Integer>> iter = orders.entrySet().iterator();
				Map<String,Integer> ans = new HashMap<>();
				// check if vendor has inventory for given order.
				while (iter.hasNext()) {
				    Map.Entry<String,Integer> order = iter.next();
				    String item = order.getKey();
					System.out.println("order item "+item);
					if(inventories.containsKey(item)) {
						int quantity = order.getValue();
						int available = inventories.get(item);
						if(available >= quantity) {
							 iter.remove();
							 ans.put(item, quantity);
						}else {
							orders.put(item, quantity - available);
							 ans.put(item, available);
						}
						
					}
				}
				if(ans.size()>0)
				finalans.put(names.get(i), ans);
				if(orders.size() < 1) break;
			}
			if(orders.size() >=1) return new HashMap<>();
			
		}catch(NullPointerException nullPointerExc) {
			System.out.println("Null value found");
			nullPointerExc.printStackTrace();
		}catch(Exception e) {
			System.out.println("Exception occured");
			e.printStackTrace();
		}
		return finalans;
	}
}
