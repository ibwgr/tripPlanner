package model.admin;

import controller.admin.ImportController;
import model.common.DatabaseProxy;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class PoiConsumer extends Thread {

    private ImportController importController;
    private DatabaseImport databaseImport;
    private ArrayList<String[]> poiList;
    private String delimiter;

    public PoiConsumer(ImportController importController, DatabaseProxy databaseProxy, String delimiter) {
        this.importController = importController;
        this.databaseImport = new DatabaseImport(importController, databaseProxy);
        this.poiList = new ArrayList<>();
        this.delimiter = delimiter;
    }

    public void run() {
        while (!importController.allRowsProcessed()) {
            String row = importController.getRow();
            if (row != null) {
                String[] rowItem = row.split(Pattern.quote(delimiter));
                if (rowItem.length != 5 || rowItem[0].isEmpty()) {
                    System.out.println("Error -> wrong row");
                    /**
                     * increase error count
                     */
                    importController.increaseErrorCount();
                } else if (!importController.poiCategoryExists(rowItem[0])) {
                    System.out.println("Error -> category does not exist");
                    importController.increaseErrorCategoryCount();
                } else {

                    System.out.println(this.getName() + " -> " + row);
                    poiList.add(rowItem);
                    importController.increaseProcessedCount();

                    if (poiList.size() >= 2000) {
                        databaseImport.insertMultiValuePois(poiList);
                        poiList.clear();
                    }
                }

            }
        }

        if (poiList.size() > 0) {
            databaseImport.insertMultiValuePois(poiList);
            poiList.clear();
        }

    }

}