package view.admin;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by dieterbiedermann on 06.01.17.
 */
public class AdminViewTest {

    /**
     * adminView Konstruktor erstellt alle Komponenten
     */
    @Test
    public void adminViewCreatesAllComponentsInConstructor() {
        AdminView adminView = new AdminView();

        Assert.assertNotNull(adminView.chooseFileButton);
        Assert.assertNotNull(adminView.startImportButton);
        Assert.assertNotNull(adminView.fileName);
        Assert.assertNotNull(adminView.fileNameLabel);
        Assert.assertNotNull(adminView.fileTypeLabel);
        Assert.assertNotNull(adminView.fileDelimiterLabel);
        Assert.assertNotNull(adminView.fileHasHaederLabel);
        Assert.assertNotNull(adminView.startImportLabel);
        Assert.assertNotNull(adminView.fileTypeGroup);
        Assert.assertNotNull(adminView.fileTypeCategory);
        Assert.assertNotNull(adminView.fileTypePoi);
        Assert.assertNotNull(adminView.fileDelimiterGroup);
        Assert.assertNotNull(adminView.fileDelimiterComma);
        Assert.assertNotNull(adminView.fileDelimiterSemicolon);
        Assert.assertNotNull(adminView.fileDelimiterPipe);
        Assert.assertNotNull(adminView.fileHasHeader);
        Assert.assertNotNull(adminView.inputPanel);
    }

    /**
     * setFileName setzt das fileName Label korrekt
     */
    @Test
    public void setFileNameSetsFileNameCorrect() {
        AdminView adminView = new AdminView();

        adminView.setFileName("");

        String result = adminView.fileName.getText();
        Assert.assertEquals("", result);

        adminView.setFileName("TestName");

        result = adminView.fileName.getText();
        Assert.assertEquals("TestName", result);
    }

    /**
     * getFileType gibt für den RadioButton Category "category" zurück
     */
    @Test
    public void getFileTypeReturnsCategoryWhenCategoryRadioButtonIsSelected() {
        AdminView adminView = new AdminView();

        adminView.fileTypeCategory.setSelected(true);

        String result = adminView.getFileType();
        Assert.assertEquals("category", result);
    }

    /**
     * getFileType gibt für den RadioButton Point of interest "poi" zurück
     */
    @Test
    public void getFileTypeReturnsPoiWhenPoiRadioButtonIsSelected() {
        AdminView adminView = new AdminView();

        adminView.fileTypePoi.setSelected(true);

        String result = adminView.getFileType();
        Assert.assertEquals("poi", result);
    }

    /**
     * getFileDelimiter gibt für den RadioButton Pipe "|" zurück
     */
    @Test
    public void getFileDelimiterReturnsPipeWhenPipeRadioButtonIsSelected() {
        AdminView adminView = new AdminView();

        adminView.fileDelimiterPipe.setSelected(true);

        String result = adminView.getFileDelimiter();
        Assert.assertEquals("\\|", result);
    }

    /**
     * getFileDelimiter gibt für den RadioButton Comma "," zurück
     */
    @Test
    public void getFileDelimiterReturnsCommaWhenCommaRadioButtonIsSelected() {
        AdminView adminView = new AdminView();

        adminView.fileDelimiterComma.setSelected(true);

        String result = adminView.getFileDelimiter();
        Assert.assertEquals(",", result);
    }

    /**
     * getFileDelimiter gibt für den RadioButton Semicolon ";" zurück
     */
    @Test
    public void getFileDelimiterReturnsSemicolonWhenSemicolonRadioButtonIsSelected() {
        AdminView adminView = new AdminView();

        adminView.fileDelimiterSemicolon.setSelected(true);

        String result = adminView.getFileDelimiter();
        Assert.assertEquals(";", result);
    }

    /**
     * getFileHasHeader gibt für die selektierte Checkbox fileHasHeader "true" zurück
     */
    @Test
    public void getFileHasHeaderReturnsTrueWhenFileHasHeaderCheckboxIsSelected() {
        AdminView adminView = new AdminView();

        adminView.fileHasHeader.setSelected(true);

        Boolean result = adminView.getFileHasHeader();
        Assert.assertEquals(true, result);

        adminView.fileHasHeader.setSelected(false);

        result = adminView.getFileHasHeader();
        Assert.assertEquals(false, result);
    }

}