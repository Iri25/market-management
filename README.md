# market-management
Mobile client application developed in Kotlin with server developed in Node.js.

To run the server, execute:
```
npm install
npm start
```

Mobile client application for a market management. A market is providing services using a mobile app. The clients are able to view the available products and purchase one or more of them. The clerk is able to manage the stocks.

### The following details are stored on the server side:
- Id - the internal product id. Integer value greater than zero.
- Name - the product name. A string of characters representing the product name.
- Description - a short product description. A string of characters.
- Quantity - the products available. An integer value greater or equal to zero.
- Price - the product price. Integer value greater than zero.
- Status - the product status. Eg. “new”, “sold”, ”popular”, “discounted”. A string type.
  
### The application offers the following features:

● Client Section (separate activity - also available offline)
1. View the products in a list. Using GET /products call, the client will retrieve the list of product available in the system. If offline, the app will display an offline message and a way to retry the connection and the call. For each product the name, quantity and the price are displayed.
2.  Buy a product. The client will buy a product, if available, using a POST/buyProduct call, by specifying the product id and the quantity. Available online only.
3. Once the client purchased a product, all the product details are presented to the user.
4. View the list of his purchased products. The list is persisted on the device, on the local storage, available offline.
   
● Clerk's Section (separate activity - available online only)
1. The list of products ascending by quantity. The list will be retrieved using the same GET /all call, in this list along with the name, quantity and price, the app will display the status also. Note that from the server you are retrieving an unsorted list.
2.  Add a product. Using a POST /product call, by sending the product object a new item will be added to the store list, on success the server will return the product object with the id field set.
3. Delete a product. Using DELETE /product call, by sending a valid product id, the server will remove the product. On success 200 OK status will be returned.
