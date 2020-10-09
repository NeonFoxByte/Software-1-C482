package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static model.Inventory.getPartInventory;

public class Inventory {


    /*Observable list of Parts*/
    private static final ObservableList<Part> partInventory = FXCollections.observableArrayList();

    /*Observable list of Products*/
    private static final ObservableList<Product> productInventory = FXCollections.observableArrayList();




    /*Adding a new part to inventory*/

    public static void addPart(Part newPart) {
        System.out.println("addPart called");
        partInventory.add(newPart);
    }

    /*Adding a new product to inventory*/

    public static void addProduct(Product newProduct) {
        System.out.println("addProduct called");
        productInventory.add(newProduct);
    }




    /*Looking up part with index and string

    checks to see if the search term is an integer

    if search term is not an integer then searches

    part name by setting characters to uppercase and

    comparing search term and part name*/

    public static int partLookup(String searchText) {
        System.out.println("partLookup called");
        boolean isFound = false;
        int index = 0;
        if (isInteger(searchText)) {
            System.out.println("searching via PartID");
            for (int i = 0; i < partInventory.size(); i++) {
                if (Integer.parseInt(searchText) == partInventory.get(i).getPartID()) {
                    isFound = true;
                    index = i;
                }
            }
        } else if (!isInteger(searchText)) {
            System.out.println("searching via partName");
            for (int i = 0; i < partInventory.size(); i++) {
                searchText = searchText.toUpperCase();
                if (searchText.equals(partInventory.get(i).getPartName().toUpperCase())) {
                    isFound = true;
                    index = i;
                }
            }
        }
        if (!isFound) {
            System.out.println("No parts found.");
            return -1;
        } else {
            return index;
        }
    }

    /*Looking up product with index and string

    checks to see if the search term is an integer

    if search term is not an integer then searches

    product name by setting characters to uppercase and

    comparing search term and product name*/

    public static int productLookup(String searchText) {
        System.out.println("productLookup called");
        boolean isFound = false;
        int index = 0;
        if (isInteger(searchText)) {
            System.out.println("searching via productID");
            for (int i = 0; i < productInventory.size(); i++) {
                if (Integer.parseInt(searchText) == productInventory.get(i).getProductID()) {
                    index = i;
                    isFound = true;
                }
            }
        } else if(!isInteger(searchText)) {
            System.out.println("searching via partName");
            for (int i = 0; i < productInventory.size(); i++) {
                if (searchText.equals(productInventory.get(i).getProductName())) {
                    isFound = true;
                    index = i;
                }
            }
        }

        if (!isFound) {
            System.out.println("No products found.");
            return -1;
        } else {
            return index;
        }
    }

    /*Updating part by selecting the part index*/
    public static void updatePart(int index, Part part) {
        System.out.println("updatePart called");
        partInventory.set(index, part);
    }

    /*Updates product by selecting the product index*/
    public static void updateProduct(int index, Product product) {
        System.out.println("updateProduct called");
        productInventory.set(index, product);
    }

/*    gets a list of all current parts

    @return list of all current parts
    */
    public static ObservableList<Part> getPartInventory() {
        System.out.println("getPartInventory called");
        return partInventory;
    }

    /*Deleting part*/
    public static void deletePart(Part part) {
        System.out.println("deletePart called");
        partInventory.remove(part);
    }

    /*Deleting product*/
    public static void deleteProduct(Product product) {
        System.out.println("deleteProduct called");
        productInventory.remove(product);
    }

    /*    Checks if part can be safely deleted

    * by checking to see if the product contains parts

    * if the product contains parts it cannot be deleted*/

    public static boolean canDeletePart(Part part) {
        System.out.println("canDeletePart called");
        boolean isFound = false;
        for (Product product : productInventory) {
            if (product.getProductParts().contains(part)) {
                isFound = true;
            }
        }
        return isFound;
    }

    /*    Checks if part can be safely deleted by

     * checking to see if the product contains parts

     * if the product contains parts it cannot be deleted*/

    public static boolean canDeleteProduct(Product product) {
        System.out.println("canDeleteProduct called");
        boolean isFound = false;
        int productID = product.getProductID();
        for (Product value : productInventory) {
            if (value.getProductID() == productID) {
                if (!value.getProductParts().isEmpty()) {
                    isFound = true;
                }
            }
        }
        return isFound;
    }

    /*Get all products in inventory*/
    public static ObservableList<Product> getProductInventory() {
        System.out.println("getProductInventory called");
        return productInventory;
    }

    /*Checks if number*/
    public static boolean isInteger(String input) {
        System.out.println("isInteger called");
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}