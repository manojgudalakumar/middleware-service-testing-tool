package com.mstt.qa.servicevirtualization.uicomponent;

import java.io.File;
import java.io.FileInputStream;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.uicomponent.utils.GuiPreferredSize;
import com.mstt.qa.servicevirtualization.uicomponent.utils.PopulateWireMockProperties;
import com.mstt.qa.servicevirtualization.uicomponent.utils.VirtualizationUiOptions;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainClass extends Application {
  /**
   * Launch the application.
   */

  public static void main(final String[] args) {
    try {
      UserDefinedProjectDetailsDto usrDfndDtsDto = new UserDefinedProjectDetailsDto();
      // usrDfndDtsDto = PopulateUserDefinedDetails.getPopulatedUsrDefinedObj();
      VirtualizationUiOptions wireMockOptions = new PopulateWireMockProperties().getOptions();
      Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
      GuiPreferredSize gui =
          new GuiPreferredSize(primaryScreenBounds.getHeight(), primaryScreenBounds.getWidth());
      new MainFrameMsst(usrDfndDtsDto, wireMockOptions, gui);
      if (wireMockOptions.isRunbackgrond()) {
        launch(args);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   * Initialize the contents of the frame.
   */
  @Override
  public void init() {
    System.out.println("in the init");
  }

  @Override
  public void start(final Stage primaryStage) throws Exception {
    BorderPane bp = new BorderPane();
    VBox vbox = new VBox();
    MsstGuiPckg guiPckg = MsstGuiPckg.getInstance();
    vbox.getChildren().add(guiPckg.getMsttMenuBar());
    vbox.getChildren().add(guiPckg.getMsttToolBar());
    bp.setTop(vbox);
    bp.setCenter(guiPckg.getCenterSplitPane());
    Scene mySceneGraph = new Scene(bp);
    primaryStage.getIcons().add(new Image(new FileInputStream(new File("images\\logo.png"))));
    primaryStage.setTitle("Middle Ware Service Testing Tool");
    primaryStage.setScene(mySceneGraph);
    primaryStage.setMaximized(true);
    primaryStage.setHeight(guiPckg.getGuiPreferredSize().getScreenHeight());
    primaryStage.setWidth(guiPckg.getGuiPreferredSize().getScreenWidht());
    guiPckg.setPrimaryStage(primaryStage);
    primaryStage.show();
  }
}
