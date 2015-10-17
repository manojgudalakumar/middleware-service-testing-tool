package com.mstt.qa.servicevirtualization.uicomponent.panels;

import java.util.Map;

public class ResponseHeaderPanel extends VirtualDetailsMappedPanel {

  Map<String, String> responseHeaderDetailsMap;
  protected static final int VALUE_COLOUMN_INDEX = 1;

  public ResponseHeaderPanel(final String tabName, final Map<String, String> map) {
    super(map, true, tabName);
    responseHeaderDetailsMap = map;
    addbuttonactionListener();
    deletebuttonactionListener();
    updatebuttonactionListener();

  }

  @Override
  public void deletebuttonactionListener() {
    // getDelProp().addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(final ActionEvent e) {
    // List<Integer> selectedRows = Ints.asList(getPropTable().getSelectedRows());
    // Collections.sort(selectedRows, Collections.reverseOrder());
    // getPropTableModel().removeRow(selectedRows, responseHeaderDetailsMap);
    // reloadpanel();
    // }
    // });

  }

  @Override
  public void addbuttonactionListener() {
    // getAddProp().addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(final ActionEvent e) {
    // String keytext = getKeyTextField().getText();
    // String propvalue = getValueTextField().getText();
    // if (!(CommonDataUtils.validate(getKeyTextField(), getValueTextField(),
    // responseHeaderDetailsMap))) {
    // return;
    // }
    // getPropTableModel().addRow(keytext, propvalue, responseHeaderDetailsMap);
    // nullifyTheTextFields();
    // }
    // });
  }

  // private void updateActionSteps(final int row, final Map<String, String>
  // mapWhereKeyNeedToUpdated,
  // final int actionType) {
  // if (TableModelEvent.UPDATE == actionType) {
  // mapWhereKeyNeedToUpdated.put((String) getPropTableModel().getValueAt(row, KEY_COLOUMN_INDEX),
  // (String) getPropTableModel().getValueAt(row, VALUE_COLOUMN_INDEX));
  // CommonUiActions.projectChanged();
  // reloadpanel();
  // }
  // }

  @Override
  public void updatebuttonactionListener() {
    // getPropTableModel().addTableModelListener(new TableModelListener() {
    // @Override
    // public void tableChanged(final TableModelEvent paramTableModelEvent) {
    // updateActionSteps(paramTableModelEvent.getFirstRow(), responseHeaderDetailsMap,
    // paramTableModelEvent.getType());
    // System.out.println("updated details :  " + responseHeaderDetailsMap);
    // }
    // });
    //
  }


}
