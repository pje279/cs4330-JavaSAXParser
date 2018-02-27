/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javasaxparser;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Peter
 */
public class FXMLDocumentController implements Initializable
{
    @FXML
    private TextArea textArea;
    
    @FXML
    private void handleButtonAction(ActionEvent event)
    {
        
    } //End private void handleButtonAction(ActionEvent event)
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // TODO
    } //End public void initialize(URL url, ResourceBundle rb)
    
    
    @FXML
    private void handleOpen(Event event)
    {
        Stage primaryStage = (Stage)textArea.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null)
        {
            //Do the parsing
            System.out.println("I happened");
            try
            {
                XMLNode root = XMLStackParser.load(file);
                System.out.println("Maybe it worked?");
                
                this.textArea.clear();
                displayXML(root, 0);
                
                System.out.println("It didn't infinite loop");
            } //End try
            catch (Exception e)
            {
                displayExceptionAlert("Exception parsing XML file.", e);
                System.out.println("Something went wrong.");
            } //End catch (Exception e)
            
        } //End if (file != null)
        
    } //End private void handleOpen(Event event)
    
    private void displayXML(XMLNode currentNode, int depth)
    {
        for (int i = 0; i < depth; i++)
        {
            this.textArea.appendText("\t");
        } //End
        this.textArea.appendText("<" + currentNode.name);
        
        for (Map.Entry<String, String> attribute : currentNode.attributes.entrySet())
        {
            this.textArea.appendText(" " + attribute.getKey() + "=" + attribute.getValue());
        }
        
        this.textArea.appendText(">");
        
        if (currentNode.content.equals(""))
        {
            this.textArea.appendText("\n");
        } //End 
        else
        {
            if (currentNode.content.length() > 50)
            {
                this.textArea.appendText("\n");
                
                for (int i = 0; i < depth + 1; i++)
                {
                    this.textArea.appendText("\t");
                } //End
            } //End 
            
            this.textArea.appendText(currentNode.content);
        } //End 
        
        if (currentNode.properties.size() > 0)
        {
            for (Map.Entry<String, List<XMLNode>> property : currentNode.properties.entrySet())
            {
                for (int j = 0; j < property.getValue().size(); j++)
                {
                    displayXML(property.getValue().get(j), depth + 1);
                } //End 
            } //End 
        } //End 
        
        if (currentNode.content.equals(""))
        {
            for (int i = 0; i < depth; i++)
            {
                this.textArea.appendText("\t");
            } //End
        } //End 
        
        if (currentNode.content.length() > 50)
        {
            this.textArea.appendText("\n");
            
            for (int i = 0; i < depth; i++)
            {
                this.textArea.appendText("\t");
            } //End 
        } //End 
        
        this.textArea.appendText("</" + currentNode.name + ">\n");
        
        
    } //End 
    
    private void displayExceptionAlert(String message, Exception ex)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("Exception!");
        alert.setContentText(message);

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    } //End private void displayExceptionAlert(String message, Exception ex)
    
} //End public class FXMLDocumentController implements Initializable
