/*
 * the lifeRental Project 
 * Medical Equipment Rental Application
*/
/*
 * Nagham Maali-1212312
 * Leen Al-Deek-1212391
 * Sami Fawwaqa-1181747
*/
/*
 * Dr. Yousef Hassouneh
 * Section 3
*/

/*The Main class is the entry point of the JavaFX application*/

package application ;

//necessary classes and libraries:
import javafx.application.Application ;  //Base class for JavaFX applications
import javafx.fxml.FXMLLoader ;  //For loading FXML files
import javafx.scene.Scene ;  //Represents the JavaFX scene
import javafx.scene.layout.AnchorPane ;  //A layout pane for anchoring child nodes
import javafx.stage.Stage ;  //Represents the JavaFX window or stage
import javafx.animation.PauseTransition ;  //For creating a pause or delay
import javafx.util.Duration ;  //Represents time durations

public class Main extends Application 
{
    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("CoverPage.fxml")) ;  //Load the cover page layout from the FXML file
            
            Scene scene = new Scene(root , 600 , 600) ;  //Create a scene with the root layout and set dimensions
            
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm()) ;  //Apply a CSS stylesheet to the scene
            
            primaryStage.setScene(scene) ;  //Set the scene to the primary stage
            primaryStage.setTitle("LifeRental") ;  //Set the title of the primary stage
            
            PauseTransition pause = new PauseTransition(Duration.seconds(3)) ;  //Create a PauseTransition to delay for 3 seconds
            pause.setOnFinished(_ -> 
            {
                try 
                {
                	//Define the action to perform after the pause ends...
                	//Load the Sign-in/Sign-up page layout from the FXML file:
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("S.fxml")) ;
                    AnchorPane loginRoot = loader.load();

                    Scene loginScene = new Scene(loginRoot , 560 , 500) ;  //Create a new scene for the Sign-in/Sign-up page
                    primaryStage.setScene(loginScene) ;  //Set the new scene to the primary stage

                    SController controller = loader.getController() ;  //Retrieve the controller associated with the Sign-in/Sign-up page
                    controller.setStage(primaryStage) ;  //Pass the primary stage to the controller

                } 
                catch (Exception e) 
                {
                    e.printStackTrace() ;  //Print any exceptions that occur
                }
            }) ;
            
            pause.play() ;  //Start the pause timer

            primaryStage.show() ;  //Display the primary stage
        } 
        catch (Exception e) 
        {
            e.printStackTrace() ;  //Print any exceptions that occur
        }
    }

    public static void main(String[] args) 
    {
        launch(args) ;  //Launch the JavaFX application
    }
}