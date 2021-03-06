package view.admin;

import controller.common.MainController;
import org.junit.Assert;
import org.junit.Test;
import view.common.TripPlannerMain;

/**
 * Created by dieterbiedermann on 06.01.17.
 */
public class AdminViewTest {

    /**
     * adminView Konstruktor erstellt alle Komponenten
     */
    @Test
    public void adminViewCreatesAllComponentsInConstructor() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);

        Assert.assertNotNull(adminView.fileName);
        Assert.assertNotNull(adminView.fileTypeGroup);
        Assert.assertNotNull(adminView.fileDelimiterGroup);
        Assert.assertNotNull(adminView.fileHasHeader);
    }

    /**
     * setFileName setzt das fileName Label korrekt
     */
    @Test
    public void setFileNameSetsFileNameCorrect() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);

        adminView.setFileName("");

        String result = adminView.fileName.getText();
        Assert.assertEquals("", result);

        adminView.setFileName("TestName");

        result = adminView.fileName.getText();
        Assert.assertEquals("TestName", result);
    }

    /**
     * getFileType gibt für den RadioButton PoiCategory "category" zurück
     */
    @Test
    public void getFileTypeReturnsCategoryWhenCategoryRadioButtonIsSelected() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);

        adminView.fileTypeCategory.setSelected(true);

        String result = adminView.getFileType();
        Assert.assertEquals("category", result);
    }

    /**
     * getFileType gibt für den RadioButton Point of interest "poi" zurück
     */
    @Test
    public void getFileTypeReturnsPoiWhenPoiRadioButtonIsSelected() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);

        adminView.fileTypePoi.setSelected(true);

        String result = adminView.getFileType();
        Assert.assertEquals("poi", result);
    }

    /**
     * getFileDelimiter gibt für den RadioButton Pipe "|" zurück
     */
    @Test
    public void getFileDelimiterReturnsPipeWhenPipeRadioButtonIsSelected() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);

        adminView.fileDelimiterPipe.setSelected(true);

        String result = adminView.getFileDelimiter();
        Assert.assertEquals("|", result);
    }

    /**
     * getFileDelimiter gibt für den RadioButton Comma "," zurück
     */
    @Test
    public void getFileDelimiterReturnsCommaWhenCommaRadioButtonIsSelected() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);

        adminView.fileDelimiterComma.setSelected(true);

        String result = adminView.getFileDelimiter();
        Assert.assertEquals(",", result);
    }

    /**
     * getFileDelimiter gibt für den RadioButton Semicolon ";" zurück
     */
    @Test
    public void getFileDelimiterReturnsSemicolonWhenSemicolonRadioButtonIsSelected() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);

        adminView.fileDelimiterSemicolon.setSelected(true);

        String result = adminView.getFileDelimiter();
        Assert.assertEquals(";", result);
    }

    /**
     * getFileHasHeader gibt für die selektierte Checkbox fileHasHeader "true" zurück
     */
    @Test
    public void getFileHasHeaderReturnsTrueWhenFileHasHeaderCheckboxIsSelected() {
        TripPlannerMain tripPlannerMain = new TripPlannerMain(1,1);
        MainController mainController = new MainController(tripPlannerMain);
        AdminView adminView = new AdminView(mainController);

        adminView.fileHasHeader.setSelected(true);

        Boolean result = adminView.getFileHasHeader();
        Assert.assertEquals(true, result);

        adminView.fileHasHeader.setSelected(false);

        result = adminView.getFileHasHeader();
        Assert.assertEquals(false, result);
    }

}