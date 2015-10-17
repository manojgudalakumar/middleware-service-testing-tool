package com.mstt.qa.servicevirtualization.commoncomponents;

import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;
import com.mstt.qa.servicevirtualization.uicomponent.actions.ActionRouter;
import com.mstt.qa.servicevirtualization.uicomponent.actions.KeyStrokes;
import com.mstt.qa.servicevirtualization.uicomponent.actions.ListOfActions;
import com.mstt.qa.servicevirtualization.uicomponent.utils.CommonDataUtils;
import com.mstt.qa.servicevirtualization.uicontrols.MsstMenu;
import com.mstt.qa.servicevirtualization.uicontrols.MsstMenuItem;

public class MsstMenuBar extends MenuBar {

  private MsstMenu fileMsstMenu;
  private MsstMenu editMsstMenu;
  private MsstMenu helpMsstMenu;
  private MsstMenu wireMockOptions;
  private MsstMenuItem file_new_project;
  private MsstMenuItem file_save_as;
  private MsstMenuItem file_save;
  private MsstMenuItem file_load;
  private MsstMenuItem file_exit;
  private MsstMenuItem file_close;
  private MsstMenuItem add_Soap_SV;
  private MsstMenuItem add_Rest_SV;
  private MsstMenuItem add_Service_Test;
  private MsstMenuItem help_about;
  private CheckMenuItem start;
  private MsstMenuItem stop;
  private MsstMenuItem restart;
  MsstGuiPckg pck;

  public MsstMenuBar() {
    setUseSystemMenuBar(true);
    createMenuBar();
  }

  public void setFileSaveAsDisabled(final boolean enabled) {
    if (file_save_as != null) {
      file_save_as.setDisable(enabled);
    }
  }

  public void setWireMockServerStart(final boolean enabled) {
    if (start != null) {
      start.setDisable(!enabled);
      start.setSelected(!enabled);
    }
  }

  public void setFileSaveDisabled(final boolean enabled) {
    if (file_save != null) {
      file_save.setDisable(enabled);
    }
  }

  public void setFileLoadDisabled(final boolean enabled) {
    if (file_load != null) {
      file_load.setDisable(enabled);
    }
  }

  public void setEditDisabled(final boolean enabled) {
    if (editMsstMenu != null) {
      editMsstMenu.setDisable(enabled);
    }
  }


  public void createMenuBar() {
    makeFileMenu();
    makeEditMenu();
    makeHelpMenu();
    makeWireMockMenu();
    getMenus().addAll(fileMsstMenu, editMsstMenu, wireMockOptions, helpMsstMenu);
  }

  private void makeWireMockMenu() {
    wireMockOptions = makeMenuRes("virtualServer", 'v', KeyCode.V);
    start = makeCheckBoxMenuItemRes("startServer", 's', ListOfActions.START_VIRTUAL_SERVER);
    stop = makeMenuItemRes("stopServer", 't', ListOfActions.STOP_VIRTUAL_SERVER);
    restart = makeMenuItemRes("restartServer", 'r', ListOfActions.RESTART_VIRTUAL_SERVER);
    wireMockOptions.getItems().addAll(start, stop, restart);
    wireMockOptions.setDisable(false);
  }

  private void makeHelpMenu() {
    // HELP MENU
    helpMsstMenu = makeMenuRes("help", 'h', KeyCode.H);

    MsstMenuItem contextHelp = makeMenuItemRes("help", 'h', ListOfActions.HELP, KeyStrokes.HELP);

    help_about = makeMenuItemRes("about", 'a', ListOfActions.ABOUT);

    helpMsstMenu.getItems().add(contextHelp);
    helpMsstMenu.addSeparator();
    helpMsstMenu.getItems().add(help_about);
  }

  private void makeEditMenu() {
    // EDIT MENU
    editMsstMenu = makeMenuRes("edit", 'e', KeyCode.E);
    add_Soap_SV =
        makeMenuItemRes("addSoapSV", 'S', ListOfActions.ADD_SOAP_SV, KeyStrokes.ADDSOAPSV);
    add_Rest_SV =
        makeMenuItemRes("addRestSV", 'R', ListOfActions.ADD_REST_SV, KeyStrokes.ADDRESTSV);
    add_Service_Test =
        makeMenuItemRes("addServiceTest", 'T', ListOfActions.ADD_SERVICE_TEST,
            KeyStrokes.ADDSERVICETEST);
    editMsstMenu.getItems().addAll(add_Soap_SV, add_Rest_SV, add_Service_Test);
    editMsstMenu.setDisable(false);
  }

  private void makeFileMenu() {
    // FILE MENU
    fileMsstMenu = makeMenuRes("file", 'f', KeyCode.F);
    file_new_project =
        makeMenuItemRes("new project", 'n', ListOfActions.NEW_PROJECT, KeyStrokes.NEWPROJECT);
    file_save = makeMenuItemRes("save", 's', ListOfActions.SAVE, KeyStrokes.SAVE);
    file_save.setDisable(true);

    file_save_as = makeMenuItemRes("save as", 'a', ListOfActions.SAVE_AS, KeyStrokes.SAVE_AS);
    file_save_as.setDisable(true);

    file_load = makeMenuItemRes("open", 'o', ListOfActions.OPEN, KeyStrokes.OPEN);

    file_close = makeMenuItemRes("close", 'c', ListOfActions.CLOSE, KeyStrokes.CLOSE);

    file_exit = makeMenuItemRes("exit", 'x', ListOfActions.EXIT, KeyStrokes.EXIT);
    fileMsstMenu.getItems().addAll(file_new_project, file_close, file_load);
    fileMsstMenu.addSeparator();
    fileMsstMenu.getItems().addAll(file_save, file_save_as);
    fileMsstMenu.addSeparator();
    fileMsstMenu.getItems().add(file_exit);
  }

  private MsstMenu makeMenuRes(final String resource) {
    MsstMenu menu = new MsstMenu(resource);
    menu.setText(resource);
    return menu;
  }

  private MsstMenu makeMenuRes(final String resource, final char mnemonic, final KeyCode keyCode) {
    MsstMenu menu = makeMenuRes(CommonDataUtils.getResString(resource, mnemonic));
    menu.setAccelerator(new KeyCodeCombination(keyCode, KeyCombination.ALT_DOWN));
    return menu;
  }

  private MsstMenuItem makeMenuItemRes(final String resource, final char mnemonic,
      final String actionCommand) {
    return makeMenuItemRes(resource, mnemonic, actionCommand, null);
  }

  private MsstMenuItem makeMenuItemRes(final String resource, final char mnemonic,
      final String actionCommand, final KeyCombination keyStroke) {
    MsstMenuItem menuItem = new MsstMenuItem(CommonDataUtils.getResString(resource, mnemonic));
    menuItem.setAccelerator(keyStroke);
    menuItem.setOnAction(ActionRouter.getInstance());
    menuItem.setId(actionCommand);
    return menuItem;
  }

  private CheckMenuItem makeCheckBoxMenuItemRes(final String string, final char mnemonic,
      final String startVirtualServer) {
    CheckMenuItem jck = new CheckMenuItem(CommonDataUtils.getResString(string, mnemonic));
    jck.setText(string);
    jck.setId(startVirtualServer);
    jck.setOnAction(ActionRouter.getInstance());
    return jck;
  }
}
