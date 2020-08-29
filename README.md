# Inventory Allocator

Inventory Allocator computes the best way an order can be shipped (called shipments) given inventory across a set of warehouses (called inventory distribution).

It produces the cheapest shipment and writes result into file called **ShipmentPath.json**.

# Pre - Requirement
Java 8 or above.

You can install java from oracle website : [https://www.oracle.com/java/technologies/javase-downloads.html](https://www.oracle.com/java/technologies/javase-downloads.html)

# Insturctions

 - Dowanload  InventoryAllocator.jar file from [https://github.com/HeliSjsu/InventoryAllocator/blob/master/InventoryAllocator.jar](https://github.com/HeliSjsu/InventoryAllocator/blob/master/InventoryAllocator.jar)
 - Run below command :
	 **java -jar InventoryAllocator.jar**
- The first input will be an order: a map of items that are being ordered and how many of them are ordered.
**{ apple: 5 }**
- The second input will be a list of object with warehouse name and inventory amounts (inventory distribution) for these items.
**[ { name: owd, inventory: { apple: 5, orange: 10 } } ]**
- Output will be written in **ShipmentPath.json** file as well as displayed on console.  
**[{"owd":{"apple":5}}]**

## Explanation

  Inside InventoryAllocator.java located at [https://github.com/HeliSjsu/InventoryAllocator/blob/master/src/main/java/Deliverr/InventoryAllocator.java](https://github.com/HeliSjsu/InventoryAllocator/blob/master/src/main/java/Deliverr/InventoryAllocator.java) below functions are being performed. 
 - Format the first input to create Map of order item using method **generateOrder()**.
 - Format the second input to collect names of vendor as List and inventory as map using method **generateInventory()**.
 - Check if all orders can be shipped from one vendor using  **checkIndividualStore()** as it is given that that shipping out of one warehouse is cheaper than shipping from multiple warehouses.
 - If all items can not be found in same warehouse multiple warehouses are used for shipment. **allocateInventory()** is used for this feature.
 - If order items are not present in all warehouses empty list is returned.
 - If there is error in input format or no order at all empty list is returned as output.
 - Unit test cases are written in file called **InventoryAllocatorTest** which is located at [https://github.com/HeliSjsu/InventoryAllocator/blob/master/src/test/java/Deliverr/InventoryAllocatorTest.java](https://github.com/HeliSjsu/InventoryAllocator/blob/master/src/test/java/Deliverr/InventoryAllocatorTest.java).
