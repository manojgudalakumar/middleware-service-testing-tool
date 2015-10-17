package com.mstt.qa.servicevirtualization.commoncomponents;

import static com.mstt.qa.servicevirtualization.commoncomponents.DataPanel.CREATED_TIMESTAMP;
import static com.mstt.qa.servicevirtualization.commoncomponents.DataPanel.LAST_UPDATED_TIMESTAMP;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;
import com.mstt.qa.servicevirtualization.uicomponent.utils.CommonUiActions;

public class DataTableModel extends DefaultTableModel {

  private static final long serialVersionUID = -543449632107218963L;

  @Override
  public boolean isCellEditable(final int row, final int column) {
    if (column == 0) {
      return false;
    }
    if (column == 1) {
      String key = (String) getValueAt(row, 0);
      if (CREATED_TIMESTAMP.equals(key) || LAST_UPDATED_TIMESTAMP.equals(key)) {
        return false;
      }
    }
    return super.isCellEditable(row, column);
  }

  public void removeEntries() {
    int rows = getRowCount();
    for (int i = rows - 1; i >= 0; i--) {
      removeRow(i);
    }
  }

  public void removeActionListeners() {
    TableModelListener[] act = getTableModelListeners();
    for (int i = 0; i < act.length; i++) {
      removeTableModelListener(act[i]);
    }

  }

  public void addRow(final String keytext, final String propvalue,
      final Map<String, String> mapWhereKeyNeedToAdded) {
    Vector<String> vc = new Vector<String>();
    vc.addElement(keytext);
    vc.addElement(propvalue);
    mapWhereKeyNeedToAdded.put(keytext, propvalue);
    addRow(vc);
    CommonUiActions.projectChanged();
  }

  public void removeRow(final List<Integer> selectedRows, final Map<String, String> mapEntryDeleted) {
    if (isNotEmpty(selectedRows) && getRowCount() != 0) {
      for (int selectedRow : selectedRows) {
        mapEntryDeleted.remove(getValueAt(selectedRow, PropertyPanel.KEY_COLOUMN_INDEX));
        removeRow(selectedRow);
      }
      CommonUiActions.projectChanged();
    }
  }

  public void addRow(final String keytext, final String patternValue, final String propvalue,
      final List<PatternMatcherDto> patternList) {
    Vector<String> vector = new Vector<String>(3);
    vector.add(keytext);
    vector.add(patternValue);
    vector.add(propvalue);
    addRow(vector);
    PatternMatcherDto pttrn = new PatternMatcherDto();
    pttrn.setKey(keytext);
    pttrn.setValue(propvalue);
    pttrn.setPattern(patternValue);
    patternList.add(pttrn);
    CommonUiActions.projectChanged();
  }

  public void removeRow(final List<Integer> selectedRows, final List<PatternMatcherDto> patternList) {
    Iterator<PatternMatcherDto> iterator = patternList.iterator();
    if (isNotEmpty(selectedRows) && getRowCount() != 0) {
      for (int selectedRow : selectedRows) {
        while (iterator.hasNext()) {
          PatternMatcherDto patternMatchern = iterator.next();
          if (patternMatchern.getKey().equals(
              getValueAt(selectedRow, PropertyPanel.KEY_COLOUMN_INDEX))) {
            iterator.remove();
          }
        }
        removeRow(selectedRow);
      }
    }
    CommonUiActions.projectChanged();
  }
}
