package com.mstt.qa.servicevirtualization.uicomponent.utils;

import static com.github.tomakehurst.wiremock.WireMockServer.FILES_ROOT;
import static com.github.tomakehurst.wiremock.WireMockServer.MAPPINGS_ROOT;
import static com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils.isNotNull;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JButton;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;
import com.github.tomakehurst.wiremock.stubbing.StubMappingJsonRecorder;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.PatternMatcherDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;
import com.mstt.qa.servicevirtualization.uicontrols.MsstTextField;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CommonDataUtils {
  final static Logger logger = CommonMethodsUtils.getLogger(CommonDataUtils.class.getName());

  public static String getResString(final String resource, final char menemonic) {
    int position = resource.indexOf(menemonic);
    if (position == 0) {
      StringBuilder strbuild = new StringBuilder();
      strbuild.append("_");
      strbuild.append(resource);
      return strbuild.toString();
    }
    if (position == -1) {
      return resource;
    }
    StringBuilder strbuild = new StringBuilder();
    strbuild.append(resource.substring(0, position));
    strbuild.append("_");
    strbuild.append(resource.substring(position, resource.length()));
    return strbuild.toString();
  }

  public static void resetFileSources() {
    MsstGuiPckg pckInstance = MsstGuiPckg.getInstance();
    VirtualizationUiOptions options = pckInstance.getWireMockOptions();
    FileSource fileSource = options.filesRoot();
    fileSource.createIfNecessary();
    FileSource filesFileSource = fileSource.child(FILES_ROOT);
    filesFileSource.createIfNecessary();
    FileSource mappingsFileSource = fileSource.child(MAPPINGS_ROOT);
    mappingsFileSource.createIfNecessary();
    StubMappingJsonRecorder createStub = new StubMappingJsonRecorder(mappingsFileSource,
        filesFileSource, pckInstance.getUsrDto(), options);
    MsttStubHandler saveStubFromUi = new MsttStubHandler(mappingsFileSource, filesFileSource);
    pckInstance.setFilesFileSource(filesFileSource);
    pckInstance.setMappingsFileSource(mappingsFileSource);
    pckInstance.setCreateStub(createStub);
    pckInstance.setmsttStubHandler(saveStubFromUi);
    logger.info("Restarting the server by resetting the files path");
    // TODO : add validation before restarting the wire mock server
  }

  public static void startWireMockServer() {
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    WireMockServerRunner wiremockserverrunner = new WireMockServerRunner();
    wiremockserverrunner.runInBackground(pck.getWireMockOptions(), pck.getUsrDto());
    pck.setWireMockerServer(wiremockserverrunner);
    logger.info("sucessfully started the wiremock server");
    pck.getMsttMenuBar().setWireMockServerStart(false);
  }

  public static void stopVirtualServer() {
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    WireMockServerRunner wiremockserver = pck.getWireMockerServer();
    if (isNotNull(wiremockserver)) {
      wiremockserver.stop();
      logger.info("successfully stopped the wire mock server");
      pck.getMsttMenuBar().setWireMockServerStart(true);
    } else {
      logger.warning("wire mock server is not running");
    }
  }

  public static void removeActionListeners(final JButton jbutton) {
    ActionListener[] act = jbutton.getActionListeners();
    for (int i = 0; i < act.length; i++) {
      jbutton.removeActionListener(act[i]);
    }
  }

  public static void removeActionListeners(final Button jbutton) {}

  private static void verifyKeyExistInMap(final Map<String, String> mapForCheckingKeyAlreadyExists,
      final String keytext, final String propvalue) throws PropertyAlreadyExistsException {
    if (mapForCheckingKeyAlreadyExists.containsKey(keytext)) {
      throw new PropertyAlreadyExistsException(keytext, propvalue);

    }
  }

  private static void verifyKeyExistInPatternMathersList(final List<PatternMatcherDto> patternLst,
      final String keytext, final String propvalue) throws PropertyAlreadyExistsException {
    Iterator<PatternMatcherDto> iterator = patternLst.iterator();
    while (iterator.hasNext()) {
      PatternMatcherDto patternMatchern = iterator.next();
      if (patternMatchern.getKey().equals(keytext)) {
        throw new PropertyAlreadyExistsException(keytext, propvalue);
      }
    }
  }

  public static void saveRestStubs(final List<RestServiceVirtualizeDto> rstlst) {
    for (RestServiceVirtualizeDto rst : rstlst) {
      System.out.println("Virtual mapping file name is " + rst.getRestVirtualMappingFileName());
      saveRestStub(rst);
    }
  }

  public static void saveRestStub(final RestServiceVirtualizeDto rst) {
    MsttStubHandler msttStub = MsstGuiPckg.getInstance().getmsttStubHandler();
    if (isNotNull(rst.getRestVirtualMappingFileName())) {
      rst.setRestVirtualMappingFileName(msttStub.storeStub(rst.getRequesStub(),
          rst.getResponseStub(), "REST", rst.getRestVirtualMappingFileName()));
    } else {
      rst.setRestVirtualMappingFileName(
          msttStub.storeStub(rst.getRequesStub(), rst.getResponseStub(), "REST"));
    }
  }

  public static void saveSoapStubs(final List<SoapServiceVirtualizeDto> soaplst) {
    for (SoapServiceVirtualizeDto soap : soaplst) {
      System.out
          .println("Virtual mapping file name is " + soap.getVirtualizeServiceMappingFileName());
      saveSoapStub(soap);
    }
  }

  public static void saveSoapStub(final SoapServiceVirtualizeDto soap) {
    MsttStubHandler msttStub = MsstGuiPckg.getInstance().getmsttStubHandler();
    if (isNotNull(soap.getVirtualizeServiceMappingFileName())) {
      System.out
          .println("name of the mapping file is " + soap.getVirtualizeServiceMappingFileName());
      soap.setVirtualizeServiceMappingFileName(msttStub.storeStub(soap.getRequesStub(),
          soap.getResponseStub(), "SOAP", soap.getVirtualizeServiceMappingFileName()));
    } else {
      soap.setVirtualizeServiceMappingFileName(
          msttStub.storeStub(soap.getRequesStub(), soap.getResponseStub(), "SOAP"));
    }
  }

  public static boolean validate(final TextField keyTextField, final TextField valueTextField,
      final Map<String, String> mapForCheckingKeyAlreadyExists) {
    String keytext = keyTextField.getText();
    String propvalue = valueTextField.getText();
    if (keytext.isEmpty()) {
      CommonUiActions.showErrorPopUp(keyTextField.getLayoutX(), keyTextField.getLayoutY(),
          "Key cannot be empty", keyTextField);
      return false;
    } else if (propvalue.isEmpty()) {
      CommonUiActions.showErrorPopUp(valueTextField.getLayoutX(), valueTextField.getLayoutY(),
          "value cannot be empty", valueTextField);
      return false;
    } else {
      try {
        verifyKeyExistInMap(mapForCheckingKeyAlreadyExists, keytext, propvalue);
      } catch (PropertyAlreadyExistsException e) {
        CommonUiActions.showErrorPopUp(keyTextField.getLayoutX(), keyTextField.getLayoutY(),
            "Key already exists try to edit", keyTextField);
        logger.info(e.toString());
        return false;
      }
    }
    return true;
  }

  public static boolean validate(final MsstTextField keyTextField,
      final MsstTextField valueTextField, final List<PatternMatcherDto> patternLst) {
    String keytext = keyTextField.getText();
    String propvalue = valueTextField.getText();
    if (keytext.isEmpty()) {
      CommonUiActions.showErrorPopUp(keyTextField.getLayoutX(), keyTextField.getLayoutY(),
          "Key cannot be empty", keyTextField);
      return false;
    } else if (propvalue.isEmpty()) {
      CommonUiActions.showErrorPopUp(valueTextField.getLayoutX(), valueTextField.getLayoutY(),
          "value cannot be empty", valueTextField);
      return false;
    } else {
      try {
        verifyKeyExistInPatternMathersList(patternLst, keytext, propvalue);
      } catch (PropertyAlreadyExistsException e) {
        CommonUiActions.showErrorPopUp(keyTextField.getLayoutX(), keyTextField.getLayoutY(),
            "Key already exists try to edit", keyTextField);
        logger.info(e.toString());
        return false;
      }

    }
    return true;
  }
}
