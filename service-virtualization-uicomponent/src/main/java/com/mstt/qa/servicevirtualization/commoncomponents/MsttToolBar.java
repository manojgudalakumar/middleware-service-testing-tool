package com.mstt.qa.servicevirtualization.commoncomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;
import com.mstt.qa.servicevirtualization.uicomponent.actions.ActionRouter;
import com.mstt.qa.servicevirtualization.uicomponent.actions.ListOfActions;
import com.mstt.qa.servicevirtualization.uicomponent.utils.ConstantsUtils;
import com.mstt.qa.servicevirtualization.uicontrols.MsstButton;

public class MsttToolBar extends HBox {
  private MsstButton start;
  private MsstButton pause;
  private MsstButton stop;
  private MsstButton save;
  private MsstButton delete;
  MsstGuiPckg pck;

  public MsttToolBar() {
    // setPadding(new Insets(20));
    setSpacing(5);
    addBasicActions();
  }

  /**
   * This method will call add method to only add the basics buttons to the Tool bar
   */
  public void addBasicActions() {
    try {
      addTool(start, ConstantsUtils.TOOLBAR_START_IMG_ICON_PATH, ListOfActions.START_TEST);
      addTool(pause, ConstantsUtils.TOOLBAR_PAUSE_IMG_ICON_PATH, ListOfActions.PAUSE_TEST);
      addTool(stop, ConstantsUtils.TOOLBAR_STOP_IMG_ICON_PATH, ListOfActions.STOP_TEST);
      addTool(save, ConstantsUtils.TOOLBAR_SAVE_IMG_ICON_PATH, ListOfActions.SAVE);
      addTool(delete, ConstantsUtils.TOOLBAR_REMOVE_IMG_ICON_PATH, ListOfActions.REMOVE_TEST);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }

  /**
   * This method is used to add button to the ToolBar
   * 
   * @param button
   * @param image
   * @param startTest
   * @throws FileNotFoundException
   */
  public void addTool(MsstButton button, final String image, final String actionCommand)
      throws FileNotFoundException {
    button = new MsstButton();
    // button.setActionCommand(actionCommand);
    button.setGraphic(new ImageView(new Image(new FileInputStream(new File(image)))));
    getChildren().add(button);
    button.setOnAction(ActionRouter.getInstance());

  }

  public void addAdditionalTool(Button button, final Image img, final String actionCommand) {
    MsttToolBar toolbar = MsstGuiPckg.getInstance().getMsttToolBar();
    button = new Button();
    toolbar.getChildren().add(button);
    button.setOnAction(ActionRouter.getInstance());
  }

}
