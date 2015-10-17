package com.mstt.qa.servicevirtualization.uicomponent.utils;

import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

import org.apache.commons.io.FilenameUtils;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.LoadAndSaveProjectXml;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;
import com.mstt.qa.servicevirtualization.uicomponent.panels.DataPropertyPanel;
import com.mstt.qa.servicevirtualization.uicontrols.MsstLabel;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Popup;
import javafx.util.Duration;

public class CommonUiActions {
  final static Logger logger = CommonMethodsUtils.getLogger(CommonUiActions.class.getName());

  public static void alertProjectChanged() throws IOException, JAXBException {
    if (MsstGuiPckg.getInstance().getProjectChanged()) {
      Alert alert = new Alert(AlertType.CONFIRMATION);
      alert.setTitle("Project Save");
      alert.setHeaderText("There are some changes in the project");
      alert.setContentText("please click on OK button to save");
      Optional<ButtonType> result = alert.showAndWait();
      if (result.get() == ButtonType.OK) {
        CommonUiActions.saveProject();
      } else {
        System.out.println("No Option");

      }
    }
  }

  public static void openProject()
      throws JAXBException, IOException, PropertyAlreadyExistsException {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("Middleware Service Testing(*.mstt)", "*.mstt");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showOpenDialog(MsstGuiPckg.getInstance().getPrimaryStage());
    if (isNotNull(file)) {
      String directorypath = file.getParent();
      String fileName = file.getName();
      if (isNotNull(fileName)) {
        if (!(fileName.endsWith(".mstt"))) {
          fileName = fileName + ".mstt";
        }
        UserDefinedProjectDetailsDto usrDto = LoadAndSaveProjectXml.getMsttObject(file);
        MsstGuiPckg pck = MsstGuiPckg.getInstance();
        pck.setUsrDto(usrDto);
        pck.getLstTree().reload();
        pck.setProjectFilePath(directorypath + fileName);
        MsstGuiPckg.getInstance().getMsttMenuBar().setFileSaveAsDisabled(true);
        DataPropertyPanel prop = pck.getPropPanel();
        prop.LoadDefaultContent(pck.getUsrDto());
        CommonDataUtils.resetFileSources();
      }
      logger.log(Level.INFO, "Project opened sucessfully");
    }
  }

  public static void saveProject() throws IOException, JAXBException {
    String projectFilePath = MsstGuiPckg.getInstance().getProjectFilePath();
    if (isNotNull(projectFilePath)) {
      LoadAndSaveProjectXml.saveProjectXmlWithFullPath(projectFilePath,
          MsstGuiPckg.getInstance().getUsrDto());
    } else {
      saveProjectAs();
    }
  }

  public static void saveProjectAs() throws IOException, JAXBException {
    FileChooser fileChooser = new FileChooser();
    FileChooser.ExtensionFilter extFilter =
        new FileChooser.ExtensionFilter("Middleware Service Testing(*.mstt)", "*.mstt");
    fileChooser.getExtensionFilters().add(extFilter);
    File file = fileChooser.showSaveDialog(MsstGuiPckg.getInstance().getPrimaryStage());
    if (isNotNull(file)) {
      String directorypath = file.getParent();
      String fileName = file.getName();
      System.out.println(FilenameUtils.getPath(file.getAbsolutePath()) + "\t" + file.getName());
      if (isNotNull(fileName)) {
        if (!(fileName.endsWith(".mstt"))) {
          fileName = fileName + ".mstt";
        }
        MsstGuiPckg pckInstance = MsstGuiPckg.getInstance();
        pckInstance.setProjectFilePath(directorypath + fileName);
        pckInstance.getWireMockOptions().setRootDir(directorypath);
        CommonDataUtils.resetFileSources();
        UserDefinedProjectDetailsDto usrDto = pckInstance.getUsrDto();
        CommonDataUtils.saveRestStubs(usrDto.getRestServiceVirtualizeDtolist());
        CommonDataUtils.saveSoapStubs(usrDto.getSoapServiceVirtualizeDtolist());
        LoadAndSaveProjectXml.saveProjectXmlWithFullPath(directorypath + "/" + fileName, usrDto);
      }
    }
  }

  public static void projectChanged() {
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    pck.setProjectChanged(true);
  }

  public static void projectSaved() {
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    pck.setProjectChanged(false);
  }

  public static void showErrorPopUp(final double xcordinate, final double ycordinate,
      final String message, final Node invoker) {
    Popup jp = new Popup();
    jp.setX(xcordinate);
    jp.setY(ycordinate);
    MsstLabel label = new MsstLabel(message);
    label.setTextFill(Color.web("#ff0000"));
    jp.getContent().add(label);
    jp.show(invoker, xcordinate, ycordinate);
    FadeTransition transition = new FadeTransition(Duration.millis(5000), label);
    transition.setFromValue(1);
    transition.setToValue(0);
    transition.play();
  }
}
