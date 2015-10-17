package com.mstt.qa.servicevirtualization.uicomponent.panels;

import java.util.List;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternType;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils;
import com.mstt.qa.servicevirtualization.uicomponent.utils.CommonDataUtils;
import com.mstt.qa.servicevirtualization.uicontrols.ConstructPatternListTable;
import com.mstt.qa.servicevirtualization.uicontrols.MsstButton;
import com.mstt.qa.servicevirtualization.uicontrols.MsstLabel;
import com.mstt.qa.servicevirtualization.uicontrols.MsstTextField;

public abstract class VirtualDetailsPatternListPanel extends Tab {
  public MsstTextField getKeyTextField() {
    return keyTextField;
  }

  public MsstTextField getValueTextField() {
    return valueTextField;
  }

  public MsstButton getAddProp() {
    return addProp;
  }


  public ComboBox<String> getPatternComboBox() {
    return patternComboBox;
  }

  public TableView<PatternMatcherDto> getPropTable() {
    return propTable;
  }

  final static Logger logger = CommonMethodsUtils.getLogger(VirtualDetailsPatternListPanel.class
      .getName());
  protected static final int KEY_COLOUMN_INDEX = 0;
  protected static final int PATTERN_COLOUMN_INDEX = 1;
  protected static final int VALUE_COLOUMN_INDEX = 2;
  private ScrollPane propPane;
  private ConstructPatternListTable propTable;
  private MsstTextField keyTextField;
  private MsstTextField valueTextField;
  private MsstButton addProp;
  private ComboBox<String> patternComboBox;
  TableColumn<PatternMatcherDto, String> keyColoumn;
  TableColumn<PatternMatcherDto, String> valueColoumn;
  TableColumn<PatternMatcherDto, String> patternColoumn;

  abstract public void addbuttonactionListener();

  abstract public void deletebuttonactionListener();

  abstract public void updatebuttonactionListener();

  public VirtualDetailsPatternListPanel(final List<PatternMatcherDto> patternList,
      final boolean shouldBuildPanel, final String tabName) {
    super(tabName);
    buidlPanel(shouldBuildPanel);
    propTable.setItems(FXCollections.observableArrayList(patternList));
  }


  private void buidlPanel(final boolean shouldBuildPanel) {
    propTable = new ConstructPatternListTable();
    propPane = new ScrollPane();
    VBox jc = new VBox();
    if (shouldBuildPanel) {
      HBox northPanel = constructNorthPanel();
      jc.getChildren().add(northPanel);
    }
    propPane.setContent(propTable);
    propPane.setFitToHeight(true);
    propPane.setFitToWidth(true);
    jc.getChildren().add(propPane);
    setContent(jc);
  }

  private HBox constructNorthPanel() {
    HBox northPanel = new HBox();
    northPanel.setPadding(new Insets(5, 2, 2, 2));
    northPanel.setSpacing(5.0);
    addProp = new MsstButton("Add");
    keyTextField = new MsstTextField();
    valueTextField = new MsstTextField();
    MsstLabel lblField1 = new MsstLabel("Key  ");
    MsstLabel lblField2 = new MsstLabel("Value");
    northPanel.getChildren().addAll(lblField1, keyTextField);
    MsstLabel patternLabel = new MsstLabel("Patterns");
    patternComboBox = new ComboBox<String>();
    patternComboBox.getItems().addAll(PatternType.equatePatterns());
    northPanel.getChildren().addAll(patternLabel, patternComboBox);
    northPanel.getChildren().addAll(lblField2, valueTextField, addProp);
    return northPanel;
  }

  protected void addbuttionActionListner(final List<PatternMatcherDto> patternLst) {
    getAddProp().setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(final ActionEvent paramT) {
        if (!(CommonDataUtils.validate(getKeyTextField(), getValueTextField(), patternLst))) {
          return;
        }
        PatternMatcherDto pattern = new PatternMatcherDto();
        pattern.setKey(getKeyTextField().getText());
        pattern.setValue(getValueTextField().getText());
        pattern.setPattern(patternComboBox.getSelectionModel().getSelectedItem());
        propTable.getItems().add(pattern);
        nullifyTheTextFields();
      }
    });

  };

  // protected void deletebuttonactionListener(final List<PatternMatcherDto> patternLst) {
  // getDelProp().addActionListener(new ActionListener() {
  // @Override
  // public void actionPerformed(final ActionEvent e) {
  // List<Integer> selectedRows = Ints.asList(getPropTable().getSelectedRows());
  // Collections.sort(selectedRows, Collections.reverseOrder());
  // propTableModel.removeRow(selectedRows, patternLst);
  // CommonUiActions.projectChanged();
  // }
  // });
  // }

  // public void updatebuttonactionListener(final List<PatternMatcherDto> patternLst) {
  // getPropTableModel().addTableModelListener(new TableModelListener() {
  // @Override
  // public void tableChanged(final TableModelEvent e) {
  // if (TableModelEvent.UPDATE == e.getType()) {
  // String value = (String) getPropTable().getValueAt(e.getFirstRow(), VALUE_COLOUMN_INDEX);
  // String key = (String) getPropTable().getValueAt(e.getFirstRow(), KEY_COLOUMN_INDEX);
  // String patternvalue =
  // (String) getPropTable().getValueAt(e.getFirstRow(), PATTERN_COLOUMN_INDEX);
  // Iterator<PatternMatcherDto> iterator = patternLst.iterator();
  // while (iterator.hasNext()) {
  // PatternMatcherDto patternMatchern = iterator.next();
  // if (patternMatchern.getKey().equals(key)) {
  // patternMatchern.setValue(value);
  // patternMatchern.setPattern(patternvalue);
  // }
  // }
  // CommonUiActions.projectChanged();
  // }
  // }
  //
  // });
  // }

  protected void nullifyTheTextFields() {
    keyTextField.setText(null);
    valueTextField.setText(null);
  }
}
