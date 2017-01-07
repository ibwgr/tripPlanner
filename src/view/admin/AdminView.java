package view.admin;

import controller.admin.AdminController;
import controller.admin.ImportController;
import view.TripPlannerMain;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by dieterbiedermann on 04.01.17.
 */
public class AdminView extends TripPlannerMain {

    AdminController adminController;

    JButton chooseFileButton, startImportButton;
    JLabel fileName, fileNameLabel, fileTypeLabel, fileDelimiterLabel, fileHasHaederLabel, startImportLabel;
    ButtonGroup fileTypeGroup;
    JRadioButton fileTypeCategory, fileTypePoi;
    ButtonGroup fileDelimiterGroup;
    JRadioButton fileDelimiterComma, fileDelimiterSemicolon, fileDelimiterPipe;
    JCheckBox fileHasHeader;
    JPanel inputPanel, mainPanel, progressPanel;
    JLabel statusLabel;
    JButton buttonNewUpload;

    public AdminView() {

        adminController = new AdminController(this);

        Dimension labelDimension = new Dimension(140,20);

        /**
         * Layout Panels
         */
        //this.setLayout(new BorderLayout());
//        JPanel borderPanel = new JPanel();
        mainPanel = new JPanel();
        inputPanel = new JPanel(new GridLayout(6,1));
//        borderPanel.add(inputPanel, BorderLayout.WEST);

        /**
         * Anzeige des Filenamens und File Öffnen Button
         */
        fileNameLabel = new JLabel("File:");
        fileNameLabel.setPreferredSize(labelDimension);
        fileName = new JLabel("Please select file");
        chooseFileButton = new JButton("Open file");
        chooseFileButton.setActionCommand("open_file");
        chooseFileButton.addActionListener(adminController);
        JPanel jpanelFileName = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanelFileName.add(fileNameLabel);
        jpanelFileName.add(fileName);
        jpanelFileName.add(chooseFileButton);
        inputPanel.add(jpanelFileName);

        /**
         * File Type: Category oder Point of interest
         */
        fileTypeLabel = new JLabel("Type:");
        fileTypeLabel.setPreferredSize(labelDimension);
        fileTypeCategory = new JRadioButton("Category", true);
        fileTypePoi = new JRadioButton("Point of interest");
        fileTypeGroup = new ButtonGroup();
        fileTypeGroup.add(fileTypeCategory);
        fileTypeGroup.add(fileTypePoi);
        JPanel jpanelFileType = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanelFileType.add(fileTypeLabel);
        jpanelFileType.add(fileTypeCategory);
        jpanelFileType.add(fileTypePoi);
        inputPanel.add(jpanelFileType);

        /**
         * File Delimiter Optionen
         */
        fileDelimiterLabel = new JLabel("Delimiter:");
        fileDelimiterLabel.setPreferredSize(labelDimension);
        fileDelimiterPipe = new JRadioButton("|", true);
        fileDelimiterComma = new JRadioButton(",");
        fileDelimiterSemicolon = new JRadioButton(";");
        fileDelimiterGroup = new ButtonGroup();
        fileDelimiterGroup.add(fileDelimiterPipe);
        fileDelimiterGroup.add(fileDelimiterComma);
        fileDelimiterGroup.add(fileDelimiterSemicolon);
        JPanel jpanelDelimiter = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanelDelimiter.add(fileDelimiterLabel);
        jpanelDelimiter.add(fileDelimiterPipe);
        jpanelDelimiter.add(fileDelimiterComma);
        jpanelDelimiter.add(fileDelimiterSemicolon);
        inputPanel.add(jpanelDelimiter);

        /**
         * Checkbox für erste Zeile als Header
         */
        fileHasHaederLabel = new JLabel("First row is header:");
        fileHasHaederLabel.setPreferredSize(labelDimension);
        fileHasHeader = new JCheckBox();
        JPanel jpanelHasHeader = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanelHasHeader.add(fileHasHaederLabel);
        jpanelHasHeader.add(fileHasHeader);
        inputPanel.add(jpanelHasHeader);

        /**
         * Start des Imports Button
         */
        startImportLabel = new JLabel();
        startImportLabel.setPreferredSize(labelDimension);
        startImportButton = new JButton("Import file");
        startImportButton.setActionCommand("import_file");
        startImportButton.addActionListener(adminController);
        JPanel jpanelStartImport = new JPanel(new FlowLayout(FlowLayout.LEADING));
        jpanelStartImport.add(startImportLabel);
        jpanelStartImport.add(startImportButton);
        inputPanel.add(jpanelStartImport);

        /**
         * zweite Row des Main GridLayout abfüllen und Parameter für das Fenster
         */
        mainPanel.add(inputPanel);
        this.setViewTitle("Administration - File Upload");
        this.addView(mainPanel);
        this.setSize(new Dimension(500,300));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        AdminView adminView = new AdminView();
        adminView.setVisible(true);
    }

    public void setFileName(String fileName) {
        this.fileName.setText(fileName);
    }

    public String getFileType() {
        return fileTypeCategory.isSelected() ? "category" : "poi";
    }

    public String getFileDelimiter() {
        String str = null;
        if (fileDelimiterPipe.isSelected()) {
            str = "\\|";
        } else if (fileDelimiterComma.isSelected()) {
            str = ",";
        } else if (fileDelimiterSemicolon.isSelected()) {
            str = ";";
        }
        return str;
    }

    public Boolean getFileHasHeader() {
        return fileHasHeader.isSelected();
    }

    public void enableProgressForm() {
        progressPanel = new JPanel(new GridLayout(3,1));

        statusLabel = new JLabel();
        progressPanel.add(statusLabel);

        buttonNewUpload = new JButton("import new file");
        buttonNewUpload.setActionCommand("start_new");
        buttonNewUpload.addActionListener(adminController);
        buttonNewUpload.setEnabled(false);
        progressPanel.add(buttonNewUpload);

        progressPanel.setVisible(true);
        mainPanel.add(progressPanel);
    }

    public void disableProgressForm() {
        progressPanel.setVisible(false);
        mainPanel.remove(progressPanel);
    }

    public void disableInputForm() {
        inputPanel.setVisible(false);
        mainPanel.remove(inputPanel);
        enableProgressForm();
        mainPanel.updateUI();
    }

    public void enableInputForm() {
        disableProgressForm();
        inputPanel.setVisible(true);
        mainPanel.add(inputPanel);
        mainPanel.updateUI();
    }

    public void setStatusText(String str) {
        statusLabel.setText(str);
    }

    public void importIsFinished() {
        buttonNewUpload.setEnabled(true);
    }

}
