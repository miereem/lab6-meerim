package com.meerim.common.util;

public class CollectionInfo {
    private final CollectionManager collectionManager;

    public CollectionInfo(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }
    public String info() {
        if (!collectionManager.isEmpty()) {
            return  "Collection type: " + collectionManager.getMainData().getClass().toString() + "\n"
                    + "Number of elements: " + collectionManager.getMainData().size() + "\n"
                    + "Creation date: " + collectionManager.getCreationDate() + "\n"
                    + "The youngest dragon in collection is " + collectionManager.getMinAge().getAge();
        } else {
            return "Collection type: " + collectionManager.getMainData().getClass().toString() + "\n"
                    + "Number of elements: " + collectionManager.getMainData().size() + "\n"
                    + "Creation date: " + collectionManager.getCreationDate();
        }
    }

}
