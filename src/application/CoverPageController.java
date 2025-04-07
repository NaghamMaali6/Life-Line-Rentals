/*Controller class for the CoverPage.fxml file*/

package application ;

//necessary classes and libraries:
import javafx.fxml.FXML ;  //For loading FXML files
import javafx.scene.image.* ;  //JavaFX component for using and displaying images
import java.io.File ;  //Represents file and directory paths

public class CoverPageController 
{
	@FXML
    private ImageView coverImage ;  //Declare an ImageView element from the FXML file

    @FXML
    public void initialize()  //Initialize method is called automatically when the FXML is loaded
    {
        String imagePath = "C:\\Users\\User\\Desktop\\DataBase\\Images\\CoverPage.jpg" ;  //Define the file path to the cover page image
        
        //Load the image and set it to the ImageView:
        File file = new File(imagePath) ;  //Create a File object for the image
        
        if (file.exists())  //Check if the image file exists at the given path
        {
        	//If the file exists: 
        	Image image = new Image(file.toURI().toString()) ;  //create an Image object from the file and set it to the ImageView  
            coverImage.setImage(image) ;  //Display the image in the ImageView
        } 
        else 
        {
            System.err.println("Image not found: " + imagePath) ;  //If the file does not exist, print an error message to the console
        }
    }
}

