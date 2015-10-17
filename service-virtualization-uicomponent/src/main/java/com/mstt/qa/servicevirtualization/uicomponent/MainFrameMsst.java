package com.mstt.qa.servicevirtualization.uicomponent;

import static com.github.tomakehurst.wiremock.WireMockServer.FILES_ROOT;
import static com.github.tomakehurst.wiremock.WireMockServer.MAPPINGS_ROOT;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;

import javax.swing.JTabbedPane;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.stubbing.StubMappingJsonRecorder;
import com.mstt.qa.servicevirtualization.commoncomponents.CenterDataPanel;
import com.mstt.qa.servicevirtualization.commoncomponents.CenterTabbedPane;
import com.mstt.qa.servicevirtualization.commoncomponents.MsstMenuBar;
import com.mstt.qa.servicevirtualization.commoncomponents.MsttToolBar;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils;
import com.mstt.qa.servicevirtualization.uicomponent.panels.DataPropertyPanel;
import com.mstt.qa.servicevirtualization.uicomponent.treegui.LoadServiceTree;
import com.mstt.qa.servicevirtualization.uicomponent.utils.GuiPreferredSize;
import com.mstt.qa.servicevirtualization.uicomponent.utils.MsttStubHandler;
import com.mstt.qa.servicevirtualization.uicomponent.utils.VirtualizationUiOptions;

public class MainFrameMsst {
  private final UserDefinedProjectDetailsDto usrDto;
  private final MsstMenuBar msttMenuBar;
  private final CenterDataPanel centrDataPanel;
  private final LoadServiceTree loadServiceTree;
  private final DataPropertyPanel propPanel;
  private final LoggerPanel logPanel;
  private final MsttToolBar msttToolBar;
  private final VirtualizationUiOptions options;
  private final TreeItem<String> rootNode;
  private final StubMappingJsonRecorder createStub;
  private final FileSource filesFileSource;
  private final FileSource mappingsFileSource;
  private SplitPane centerSplitPane;
  private final CenterTabbedPane centralTabbedPane;
  private final MsttStubHandler saveStubFromUi;

  public MainFrameMsst(final UserDefinedProjectDetailsDto usrDto,
      final VirtualizationUiOptions wireMockOptions, final GuiPreferredSize gui) {
    logPanel = CommonMethodsUtils.getloggerPanel();
    MsstGuiPckg.getInstance(wireMockOptions, gui).setLoggedPanel(logPanel);
    this.usrDto = usrDto;
    rootNode = new TreeItem<String>(usrDto.getProjectDetails().getProjectName());
    loadServiceTree = new LoadServiceTree(usrDto, rootNode);
    options = wireMockOptions;
    msttMenuBar = new MsstMenuBar();
    msttToolBar = new MsttToolBar();
    propPanel = new DataPropertyPanel();
    centrDataPanel = new CenterDataPanel();
    FileSource fileSource = options.filesRoot();
    fileSource.createIfNecessary();
    filesFileSource = fileSource.child(FILES_ROOT);
    filesFileSource.createIfNecessary();
    mappingsFileSource = fileSource.child(MAPPINGS_ROOT);
    mappingsFileSource.createIfNecessary();
    createStub = new StubMappingJsonRecorder(mappingsFileSource, filesFileSource, usrDto, options);
    centralTabbedPane = new CenterTabbedPane(JTabbedPane.TOP);
    saveStubFromUi = new MsttStubHandler(mappingsFileSource, filesFileSource);
    constructCenterDataPane();
    setGuiPackageDetail();
    initilize();
  }

  private void initilize() {
    msttMenuBar.setFileSaveDisabled(false);
  }

  private void constructCenterDataPane() {
    centerSplitPane = new SplitPane();
    SplitPane.setResizableWithParent(loadServiceTree, Boolean.FALSE);
    SplitPane.setResizableWithParent(logPanel, Boolean.FALSE);


    centerSplitPane.setPrefHeight(MsstGuiPckg.getInstance().getGuiPreferredSize()
        .treePanelWithHeight());
    centerSplitPane.setPrefWidth(MsstGuiPckg.getInstance().getGuiPreferredSize()
        .treePanelWithWidth());
    centerSplitPane.getItems().add(loadServiceTree);

    SplitPane splitPane_ContentNProp = new SplitPane();
    splitPane_ContentNProp.setOrientation(Orientation.HORIZONTAL);
    splitPane_ContentNProp.prefHeightProperty().bind(centralTabbedPane.heightProperty());
    splitPane_ContentNProp.prefWidthProperty().bind(centralTabbedPane.widthProperty());
    centralTabbedPane.setPrefHeight(MsstGuiPckg.getInstance().getGuiPreferredSize()
        .propertyDataPanelWithHeight());
    centralTabbedPane.setPrefWidth(MsstGuiPckg.getInstance().getGuiPreferredSize()
        .propertyDataPanelWithWidth());
    SplitPane.setResizableWithParent(centralTabbedPane, Boolean.FALSE);
    splitPane_ContentNProp.getItems().add(centralTabbedPane);
    propPanel.setPrefHeight(MsstGuiPckg.getInstance().getGuiPreferredSize()
        .propertyDataPanelWithHeight());
    propPanel.setPrefWidth(MsstGuiPckg.getInstance().getGuiPreferredSize()
        .propertyDataPanelWithWidth());
    SplitPane.setResizableWithParent(propPanel, Boolean.FALSE);
    splitPane_ContentNProp.getItems().add(propPanel);
    ObservableList<SplitPane.Divider> dividers = splitPane_ContentNProp.getDividers();
    System.out.println(dividers.size());
    dividers.get(0).setPosition(0.75);

    SplitPane splitPane_ConsoleNContent = new SplitPane();
    splitPane_ConsoleNContent.setPrefHeight(MsstGuiPckg.getInstance().getGuiPreferredSize()
        .centerPanelWithHeight());
    splitPane_ConsoleNContent.setPrefWidth(MsstGuiPckg.getInstance().getGuiPreferredSize()
        .centerPanelWithWidth());
    splitPane_ConsoleNContent.setOrientation(Orientation.VERTICAL);
    splitPane_ConsoleNContent.getItems().add(splitPane_ContentNProp);
    splitPane_ConsoleNContent.getItems().add(logPanel);
    ObservableList<SplitPane.Divider> dividers1 = splitPane_ConsoleNContent.getDividers();
    System.out.println(dividers.size());
    dividers1.get(0).setPosition(0.80);


    propPanel.addDataPanel();
    propPanel.addPropertyPanel();
    centerSplitPane.getItems().add(splitPane_ConsoleNContent);
    propPanel.LoadDefaultContent(usrDto);
  }

  private void setGuiPackageDetail() {
    MsstGuiPckg guipck = MsstGuiPckg.getInstance();
    guipck.setCenterDataPanel(centrDataPanel);
    guipck.setMsttMenuBar(msttMenuBar);
    guipck.setMsttToolBar(msttToolBar);
    guipck.setLoggedPanel(logPanel);
    guipck.setPropPanel(propPanel);
    guipck.setUsrDto(usrDto);
    guipck.setWireMockOptions(options);
    guipck.setRootNode(rootNode);
    guipck.setFilesFileSource(filesFileSource);
    guipck.setMappingsFileSource(mappingsFileSource);
    guipck.setCreateStub(createStub);
    guipck.setLstTree(loadServiceTree);
    guipck.setCenterSplitPane(centerSplitPane);
    guipck.setCenterTabPane(centralTabbedPane);
    guipck.setmsttStubHandler(saveStubFromUi);

  }

}
