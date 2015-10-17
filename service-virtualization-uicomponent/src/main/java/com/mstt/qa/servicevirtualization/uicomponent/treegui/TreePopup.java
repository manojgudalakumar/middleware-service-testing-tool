package com.mstt.qa.servicevirtualization.uicomponent.treegui;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import com.mstt.qa.servicevirtualization.uicomponent.actions.ActionRouter;
import com.mstt.qa.servicevirtualization.uicomponent.actions.ListOfActions;
import com.mstt.qa.servicevirtualization.uicomponent.utils.CommonDataUtils;

public class TreePopup extends ContextMenu {
  MenuItem itemDelete;
  MenuItem itemAdd;
  MenuItem itemRefresh;

  public TreePopup() {
    itemDelete = makeMenuItemRes("Delete", 'D', ListOfActions.DELETE_NODE);
    itemAdd = makeMenuItemRes("Add", 'A', ListOfActions.ADD_NODE);
    itemRefresh = makeMenuItemRes("ReloadTree", 'R', ListOfActions.TREE_REFRESH);
    getItems().add(itemDelete);
    getItems().add(new SeparatorMenuItem());
    getItems().add(itemAdd);
    getItems().add(new SeparatorMenuItem());
    getItems().add(itemRefresh);
  }

  private static MenuItem makeMenuItemRes(String resource, char mnemonic, String actionCommand) {
    MenuItem menuItem = new MenuItem(CommonDataUtils.getResString(resource, mnemonic));
    // menuItem.setName(resource);
    // menuItem.setActionCommand(actionCommand);
    menuItem.setOnAction(ActionRouter.getInstance());
    return menuItem;
  }
}
