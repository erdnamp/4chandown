/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pro;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.validator.UrlValidator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
/**
 * FXML Controller class
 *
 * @author User
 */
public class SickController implements Initializable {
    public static boolean doesURLExist(URL url) throws IOException
{
    HttpURLConnection.setFollowRedirects(false);
    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    httpURLConnection.setRequestMethod("HEAD");
    httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
    int responseCode = httpURLConnection.getResponseCode();
    return responseCode == HttpURLConnection.HTTP_OK;
}
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
     UrlValidator urlValidator = new UrlValidator();
    if(urlValidator.isValid(url.getText())){
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
    else{
        Document x = Jsoup.connect("https://www.4chan.org//").get();
        Elements boards = x.getElementsByAttributeValue("class", "boardlink");
        for(int i=0;i<boards.size()-9;i++){
            String links=boards.get(i).attr("href");
            String link = "https:"+links+"thread/"+url.getText();
            URL d=new URL(link);
            if(doesURLExist(d)){
                Document novo=Jsoup.connect(link).get();
                Elements images2=novo.getElementsByAttributeValue("class", "fileThumb");
                for(i=0;i<images2.size();i++){
                    String image=images2.get(i).attr("href");
                    String imags="https:"+image;
                    URL xd= new URL(imags);
                    File file=new File(output.getText().concat(image.substring(image.lastIndexOf("/"))));
                    if(!(file.exists())){
                        FileUtils.copyURLToFile(xd,file);
                    }
                }
                break;
            }
        }
    }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
