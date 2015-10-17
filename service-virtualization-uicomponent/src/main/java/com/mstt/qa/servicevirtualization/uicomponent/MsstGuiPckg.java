package com.mstt.qa.servicevirtualization.uicomponent;

import static com.github.tomakehurst.wiremock.WireMockServer.FILES_ROOT;
import static com.github.tomakehurst.wiremock.WireMockServer.MAPPINGS_ROOT;

import java.awt.Container;

import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import org.apache.commons.io.FilenameUtils;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;
import com.github.tomakehurst.wiremock.stubbing.StubMappingJsonRecorder;
import com.mstt.qa.servicevirtualization.commoncomponents.CenterDataPanel;
import com.mstt.qa.servicevirtualization.commoncomponents.CenterTabbedPane;
import com.mstt.qa.servicevirtualization.commoncomponents.MsstMenuBar;
import com.mstt.qa.servicevirtualization.commoncomponents.MsttToolBar;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.uicomponent.panels.DataPropertyPanel;
import com.mstt.qa.servicevirtualization.uicomponent.treegui.LoadServiceTree;
import com.mstt.qa.servicevirtualization.uicomponent.treegui.TreeMenuListener;
import com.mstt.qa.servicevirtualization.uicomponent.utils.GuiPreferredSize;
import com.mstt.qa.servicevirtualization.uicomponent.utils.MsttStubHandler;
import com.mstt.qa.servicevirtualization.uicomponent.utils.VirtualizationUiOptions;

public class MsstGuiPckg {

  public TreeView<String> getTree() {
    return tree;
  }

  public void setTree(final TreeView<String> tree) {
    this.tree = tree;
  }

  public UserDefinedProjectDetailsDto getUsrDto() {
    return usrDto;
  }

  public void setUsrDto(final UserDefinedProjectDetailsDto usrDtoTest) {
    usrDto = usrDtoTest;
  }

  public VirtualizationUiOptions getWireMockOptions() {
    return wireMockOptions;
  }

  public void setWireMockOptions(final VirtualizationUiOptions wireMockOptions) {
    this.wireMockOptions = wireMockOptions;
  }

  public TreeMenuListener getTreeMenuListner() {
    return treeMenuListner;
  }

  public void setTreeMenuListner(final TreeMenuListener treeMenuListner) {
    this.treeMenuListner = treeMenuListner;
  }

  public DataPropertyPanel getPropPanel() {
    return propPanel;
  }

  public void setPropPanel(final DataPropertyPanel propPanel) {
    this.propPanel = propPanel;
  }

  public LoggerPanel getLoggedPanel() {
    return loggedPanel;
  }

  public void setLoggedPanel(final LoggerPanel loggedPanel) {
    this.loggedPanel = loggedPanel;
  }

  public CenterDataPanel getCenterDataPanel() {
    return centerDataPanel;
  }

  public void setCenterDataPanel(final CenterDataPanel centerDataPanel) {
    this.centerDataPanel = centerDataPanel;
  }

  public TreeItem<String> getRootNode() {
    return rootNode;
  }

  public void setRootNode(final TreeItem<String> rootNode) {
    this.rootNode = rootNode;
  }

  public String getProjectFilePath() {
    return projectFilePath;
  }

  public void setProjectFilePath(final String projectFilePath) {
    this.projectFilePath = projectFilePath;
    updateWireMockDetail();
  }

  private void updateWireMockDetail() {
    wireMockOptions.setRootDir(FilenameUtils.getFullPath(projectFilePath));
    FileSource fileSource = wireMockOptions.filesRoot();
    fileSource.createIfNecessary();
    filesFileSource = fileSource.child(FILES_ROOT);
    filesFileSource.createIfNecessary();
    mappingsFileSource = fileSource.child(MAPPINGS_ROOT);
    mappingsFileSource.createIfNecessary();
    createStub =
        new StubMappingJsonRecorder(mappingsFileSource, filesFileSource, usrDto, wireMockOptions);
  }

  public MsstMenuBar getMsttMenuBar() {
    return msstMenuBar;
  }

  public void setMsttMenuBar(final MsstMenuBar msstMenuBar) {
    this.msstMenuBar = msstMenuBar;
  }

  public MsttToolBar getMsttToolBar() {
    return msttToolBar;
  }

  public void setMsttToolBar(final MsttToolBar msttToolBar) {
    this.msttToolBar = msttToolBar;
  }

  public StubMappingJsonRecorder getCreateStub() {
    return createStub;
  }

  public void setCreateStub(final StubMappingJsonRecorder createStub) {
    this.createStub = createStub;
  }

  public FileSource getFilesFileSource() {
    return filesFileSource;
  }

  public void setFilesFileSource(final FileSource filesFileSource) {
    this.filesFileSource = filesFileSource;
  }

  public FileSource getMappingsFileSource() {
    return mappingsFileSource;
  }

  public void setMappingsFileSource(final FileSource mappingsFileSource) {
    this.mappingsFileSource = mappingsFileSource;
  }

  public LoadServiceTree getLstTree() {
    return lstTree;
  }

  public void setLstTree(final LoadServiceTree lstTree) {
    this.lstTree = lstTree;
  }

  public void setMainContainer(final Container mainContiner) {
    mainContainer = mainContiner;
  }

  public Container getMainContiner() {
    return mainContainer;
  }

  public void setCenterTabPane(final CenterTabbedPane centrTabbedPane) {
    this.centrTabbedPane = centrTabbedPane;
  }

  public CenterTabbedPane getCenterTabPane() {
    return centrTabbedPane;
  }

  public SplitPane getCenterSplitPane() {
    return centerSplitPane;
  }

  public void setCenterSplitPane(final SplitPane centerSplitPane) {
    this.centerSplitPane = centerSplitPane;
  }

  public boolean getProjectChanged() {
    return projectChanged;
  }

  public void setProjectChanged(final boolean projectChanged) {
    this.projectChanged = projectChanged;
  }

  public WireMockServerRunner getWireMockerServer() {
    return wireMockerServer;
  }

  public void setWireMockerServer(final WireMockServerRunner wireMockerServer) {
    this.wireMockerServer = wireMockerServer;
  }

  public MsttStubHandler getmsttStubHandler() {
    return msttStubHandler;
  }

  public void setmsttStubHandler(final MsttStubHandler msttStubHandler) {
    this.msttStubHandler = msttStubHandler;
  }

  public GuiPreferredSize getGuiPreferredSize() {
    return guiPreferredSize;
  }

  public void setGuiPreferredSize(final GuiPreferredSize guiPreferredSize) {
    this.guiPreferredSize = guiPreferredSize;
  }

  public static MsstGuiPckg mstt;
  private UserDefinedProjectDetailsDto usrDto;
  private MsstMenuBar msstMenuBar;
  private MsttToolBar msttToolBar;
  private VirtualizationUiOptions wireMockOptions;
  private TreeMenuListener treeMenuListner;
  private DataPropertyPanel propPanel;
  private LoggerPanel loggedPanel;
  private CenterDataPanel centerDataPanel;
  private TreeItem<String> rootNode;
  private String projectFilePath;
  private StubMappingJsonRecorder createStub;
  private FileSource filesFileSource;
  private FileSource mappingsFileSource;
  private LoadServiceTree lstTree;
  private Container mainContainer;
  private TreeView<String> tree;
  private CenterTabbedPane centrTabbedPane;
  private SplitPane centerSplitPane;
  private boolean projectChanged;
  private WireMockServerRunner wireMockerServer;
  private MsttStubHandler msttStubHandler;
  private GuiPreferredSize guiPreferredSize;
  private Stage primaryStage;

  private MsstGuiPckg() {

  }

  private MsstGuiPckg(final VirtualizationUiOptions options, final GuiPreferredSize gui) {
    wireMockOptions = options;
    guiPreferredSize = gui;
  }

  public static MsstGuiPckg getInstance() {
    return mstt;
  }

  public static MsstGuiPckg getInstance(final VirtualizationUiOptions options,
      final GuiPreferredSize gui) {
    if (mstt == null) {
      mstt = new MsstGuiPckg(options, gui);
    }
    return mstt;
  }

  public void resetDetails() {
    projectChanged = false;
    projectFilePath = null;
    propPanel.resetPanel();
    loggedPanel.nullify();
    centrTabbedPane.resetPanel();
  }

  public Stage getPrimaryStage() {
    return primaryStage;
  }

  public void setPrimaryStage(final Stage primaryStage) {
    this.primaryStage = primaryStage;
  }


}
