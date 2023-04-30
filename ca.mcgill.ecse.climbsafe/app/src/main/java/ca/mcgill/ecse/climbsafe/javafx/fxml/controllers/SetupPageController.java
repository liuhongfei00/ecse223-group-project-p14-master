package ca.mcgill.ecse.climbsafe.javafx.fxml.controllers;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.DatePicker;
import java.sql.Date;
import java.time.LocalDate;

/**
 * This page is for set up the climbsafe season.
 * @author Zihan Zhang
 * 
 */
public class SetupPageController {
    @FXML 
	private TextField nr_wks;
    @FXML
    private TextField ppw;
    @FXML
    private Button c_btn;
    @FXML
    private DatePicker start_date; 
    @FXML
	private ImageView logoImage;
	@FXML
	public void initialize() {
		logoImage.setImage(new Image(getClass().getResource("climbsafeLogo.png").toExternalForm(),66,62,false,false));
	}
/**
 * @author Zihan Zhang
 * 
 * @param event the event that the button is clicked.
 */
    @FXML
    private void setUpClicked(ActionEvent event){
        
        
        try {
            Integer.parseInt(nr_wks.getText());
            try {
                Integer.parseInt(ppw.getText());
                try {
                    Date.valueOf(start_date.getValue());
                } catch (Exception e) {
                    //TODO: handle exception
                    ViewUtils.showError("Invalid start date");
                }
            } catch (Exception e) {
                //TODO: handle exception
                ViewUtils.showError("Price of guide per week should be an integer");
            }
        } catch (Exception e) {
            //TODO: handle exception
            ViewUtils.showError("Number of week should be an integer");
        }
        
        

        int nr_week = Integer.valueOf(nr_wks.getText());
        int priceOfGuide = Integer.valueOf(ppw.getText());
        Date startD = Date.valueOf(start_date.getValue());

        if(nr_week<=0){
            ViewUtils.showError("Number of weeks can not be smaller than or equal to 0");
        }

        else if(priceOfGuide<=0.0){
            ViewUtils.showError("Price of guide per week can not be smaller than or equal to 0");
        }

        else if(startD.before(Date.valueOf(LocalDate.now()))){
            ViewUtils.showError("Start date can not be earlier than now");
        }

        else{
            if(ViewUtils.successful(() -> ClimbSafeFeatureSet1Controller.setup(startD, nr_week, priceOfGuide))){
                ViewUtils.makePopupWindow("Setup status", "Successful");
            }
        }


       
    }
}
