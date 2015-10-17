package com.mstt.qa.servicevirtualization.uicomponent.actions;

import static com.github.tomakehurst.wiremock.WireMockServer.FILES_ROOT;
import static com.github.tomakehurst.wiremock.WireMockServer.MAPPINGS_ROOT;
import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;

import java.awt.Frame;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.mstt.qa.servicevirtualization.commoncomponents.CenterTabbedPane;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;
import com.mstt.qa.servicevirtualization.uicomponent.utils.CommonDataUtils;
import com.mstt.qa.servicevirtualization.uicomponent.utils.CommonUiActions;

public class MenuActions {
  final static Logger logger = CommonMethodsUtils.getLogger(MenuActions.class.getName());

  public void open() throws JAXBException, IOException, PropertyAlreadyExistsException {
    CommonUiActions.alertProjectChanged();
    CommonUiActions.openProject();
  }

  public void close() {
    System.out.println("In the close method");
  }

  public void exit() {
    if (MsstGuiPckg.getInstance().getProjectChanged()) {
      try {
        CommonUiActions.alertProjectChanged();
      } catch (IOException | JAXBException e) {
        e.printStackTrace();
      }
    }
    System.exit(0); // Terminate the program
  }

  public void save() throws IOException, JAXBException {
    CommonUiActions.saveProject();
    MsstGuiPckg.getInstance().getMsttMenuBar().setFileSaveAsDisabled(true);
  }

  public void addSoapSV() {
    SoapServiceVirtualizeDto soapDto = new SoapServiceVirtualizeDto();
    MsstGuiPckg pckg = MsstGuiPckg.getInstance();
    CenterTabbedPane cntpd = pckg.getCenterTabPane();
    cntpd.addNewSoapPanel(soapDto);
    pckg.getPropPanel().populatePropertyPanel(soapDto);
    pckg.setProjectChanged(true);
  }

  public void addRestSV() {
    RestServiceVirtualizeDto rst = new RestServiceVirtualizeDto();
    MsstGuiPckg pckg = MsstGuiPckg.getInstance();
    CenterTabbedPane cntpd = pckg.getCenterTabPane();
    cntpd.addNewRestPanel(rst);
    pckg.getPropPanel().populatePropertyPanel(rst);
    pckg.setProjectChanged(true);
  }

  public void reloadTree() {
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    pck.getLstTree().reload();
  }

  public void addServiceTest() {
    System.out.println("in the service testF");
  }

  public void saveas() throws IOException, JAXBException {
    String previousProjectPath = MsstGuiPckg.getInstance().getProjectFilePath();
    CommonUiActions.saveProjectAs();
    String currentProjectPath = MsstGuiPckg.getInstance().getProjectFilePath();
    if (!(currentProjectPath.equals(previousProjectPath))) {
      copyProjectFile(previousProjectPath, currentProjectPath);
    }
  }

  private void copyProjectFile(final String previousProjectPath, final String currentProjectPath)
      throws IOException {
    FileUtils.copyDirectory(getFileRoot(previousProjectPath), getFileRoot(currentProjectPath),
        acceptOnlyJsonFile());
    FileUtils.copyDirectory(getFileMapping(previousProjectPath),
        getFileMapping(currentProjectPath), acceptOnlyJsonFile());

  }

  private File getFileRoot(final String absolutePath) {
    return new File(FilenameUtils.getFullPath(absolutePath) + FILES_ROOT);
  }

  private File getFileMapping(final String absolutePath) {
    return new File(FilenameUtils.getFullPath(absolutePath) + MAPPINGS_ROOT);
  }

  private FileFilter acceptOnlyJsonFile() {
    return new FileFilter() {
      @Override
      public boolean accept(final File pathname) {
        return FilenameUtils.getExtension(pathname.getAbsolutePath()).equalsIgnoreCase("JSON");
      }
    };
  }

  public void newProject() throws IOException, JAXBException {
    CommonUiActions.alertProjectChanged();
    UserDefinedProjectDetailsDto usr = new UserDefinedProjectDetailsDto();
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    pck.setUsrDto(usr);
    pck.getLstTree().regenerateTree();
    pck.resetDetails();
  }

  public void startVirtualServer() {
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    if (isNotNull(pck.getProjectFilePath())) {
      CommonDataUtils.startWireMockServer();
    } else {
      int dialogResult =
          JOptionPane.showConfirmDialog(new Frame(),
              "You need to save the project before starting the server", "Save the project",
              JOptionPane.YES_NO_OPTION);
      if (dialogResult == 0) {
        try {
          CommonUiActions.saveProject();
          CommonDataUtils.startWireMockServer();
        } catch (IOException | JAXBException e) {
          logger.log(Level.SEVERE,
              "Something is going wrong please fix the error shown" + e.toString());
        }
      } else {
        logger.info("it seems like you don't want to save the porject");
      }
    }
  }

  public void stopVirtualServer() {
    CommonDataUtils.stopVirtualServer();
  }

  public void restartVirtualServer() {
    stopVirtualServer();
    startVirtualServer();
  }
}
