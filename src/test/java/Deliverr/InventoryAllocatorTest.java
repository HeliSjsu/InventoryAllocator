package Deliverr;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author shahh
 *
 */
public class InventoryAllocatorTest {
	

	@Test
	public void test1() {
		String orderInput = "{ apple: 5, banana: 5, orange: 5 }";
		String  inventoryInput= "[ { name: owd, inventory: { apple: 5, orange: 10 } }, { name: dm:, inventory: { banana: 5, orange: 10 } } ]";
	
		Map<String,Integer> order = InventoryAllocator.generateOrder(orderInput); 
		assertEquals(3, order.size());
		
		List<Map<String,Integer>> inventory = new ArrayList<>();
		List<String> names = new ArrayList<>();
		InventoryAllocator.generateInventory(inventoryInput, inventory, names);		
		assertEquals(2, names.size());

		Map<String,Map<String,Integer>> ans = InventoryAllocator.allocateInventory(order, inventory, names);
		String finalans = InventoryAllocator.formulateJSON(ans).toJSONString();
		assertEquals("check final values","[{\"dm:\":{\"banana\":5}},{\"owd\":{\"orange\":5,\"apple\":5}}]" , finalans);
	}
	
	@Test
	public void test2() {
		String orderInput = "{ apple: 1 }";
		String  inventoryInput= "[ { name: owd, inventory: { apple: 1} }]";
	
		Map<String,Integer> order = InventoryAllocator.generateOrder(orderInput); 
		assertEquals(1, order.size());
		
		List<Map<String,Integer>> inventory = new ArrayList<>();
		List<String> names = new ArrayList<>();
		InventoryAllocator.generateInventory(inventoryInput, inventory, names);	
		Map<String,Map<String,Integer>> ans = InventoryAllocator.checkIndividualStore(order, inventory, names);
		assertEquals(1, ans.size());	
		
		String finalans = InventoryAllocator.formulateJSON(ans).toJSONString();
		assertEquals("check final values","[{\"owd\":{\"apple\":1}}]" , finalans);
	}
	
	@Test
	public void test3() {
		String orderInput = "{ apple: 10 }";
		String  inventoryInput= "[ { name: owd, inventory: { apple: 5} }, { name: dm:, inventory: { apple: 5 } } ]";
	
		Map<String,Integer> order = InventoryAllocator.generateOrder(orderInput); 
		assertEquals(1, order.size());
		
		List<Map<String,Integer>> inventory = new ArrayList<>();
		List<String> names = new ArrayList<>();
		InventoryAllocator.generateInventory(inventoryInput, inventory, names);
		assertEquals(2, names.size());
		
		Map<String,Map<String,Integer>> ans = InventoryAllocator.checkIndividualStore(order, inventory, names);		
		assertEquals(0, ans.size());
		
		Map<String,Map<String,Integer>> ans1 = InventoryAllocator.allocateInventory(order, inventory, names);
		String finalans = InventoryAllocator.formulateJSON(ans1).toJSONString();
		assertEquals("check final values","[{\"dm:\":{\"apple\":5}},{\"owd\":{\"apple\":5}}]" , finalans);
	}
	
	@Test
	public void test4() {
		String orderInput = "{ apple: 1 }";
		String  inventoryInput= "[ { name: owd, inventory: { apple: 0} } ]";
	
		Map<String,Integer> order = InventoryAllocator.generateOrder(orderInput); 
		assertEquals(1, order.size());
		
		List<Map<String,Integer>> inventory = new ArrayList<>();
		List<String> names = new ArrayList<>();
		InventoryAllocator.generateInventory(inventoryInput, inventory, names);
		assertEquals(1, names.size());
		
		Map<String,Map<String,Integer>> ans = InventoryAllocator.allocateInventory(order, inventory, names);
		String finalans = InventoryAllocator.formulateJSON(ans).toJSONString();
		assertEquals("check final values","[]" , finalans);
	}
	
}
