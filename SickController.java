/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * FXML Controller class
 *
 * @author User
 */
public class SickController implements Initializable {
    @FXML
    private TextField url;
    @FXML
    private TextField output;
    @FXML
    private void browse(ActionEvent event){
    try{
    DirectoryChooser dirChooser = new DirectoryChooser();
    dirChooser.setTitle("Choose Folder");
    File file = dirChooser.showDialog(null);
    output.setText(file.getPath().concat("\\"));
    }catch(Exception error){
        
    }
    }
    @FXML
    private void download(ActionEvent event) throws IOException{
    Document doc = Jsoup.connect(url.getText()).get();
    Elements images=doc.getElementsByAttributeValue("class", "fileThumb");
    for(int i=0;i<images.size();i++){
        String image=images.get(i).attr("href");
        String imags="https:"+image;
        URL x = new URL(imags);
        File file=new File(output.getText().concat(image.substring(image.lastIndexOf("/"))));
        if(!(file.exists())){
        FileUtils.copyURLToFile(x,file);
        }
    }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
