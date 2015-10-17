package com.mstt.qa.servicevirtualization.uicomponent.listeners;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.exceptions.PropertyAlreadyExistsException;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.userproject.LoadAndSaveProjectXml;
import com.mstt.qa.servicevirtualization.uicomponent.MsstGuiPckg;
import com.mstt.qa.servicevirtualization.uicomponent.utils.CommonUiActions;

public class WindowsListernsImpl implements WindowListener, DropTargetListener {

  @Override
  public void windowOpened(final WindowEvent paramWindowEvent) {
    // TODO Auto-generated method stub

  }

  @Override
  public void windowClosing(final WindowEvent paramWindowEvent) {
    if (MsstGuiPckg.getInstance().getProjectChanged()) {
      try {
        CommonUiActions.alertProjectChanged();
      } catch (IOException | JAXBException e) {
        e.printStackTrace();
      }
    }
    System.exit(0); // Terminate the program
  }

  @Override
  public void windowClosed(final WindowEvent paramWindowEvent) {
    // TODO Auto-generated method stub

  }

  @Override
  public void windowIconified(final WindowEvent paramWindowEvent) {
    // TODO Auto-generated method stub

  }

  @Override
  public void windowDeiconified(final WindowEvent paramWindowEvent) {
    // TODO Auto-generated method stub

  }

  @Override
  public void windowActivated(final WindowEvent paramWindowEvent) {
    // TODO Auto-generated method stub

  }

  @Override
  public void windowDeactivated(final WindowEvent paramWindowEvent) {
    // TODO Auto-generated method stub

  }

  @Override
  public void drop(final DropTargetDropEvent dtde) {
    try {
      CommonUiActions.alertProjectChanged();
      Transferable tr = dtde.getTransferable();
      DataFlavor[] flavors = tr.getTransferDataFlavors();
      for (DataFlavor flavor : flavors) {
        if (flavor.isFlavorJavaFileListType()) {
          dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
          try {
            openMsttFilesFromDragAndDrop(tr);
          } finally {
            dtde.dropComplete(true);
          }
          return;
        }
      }
    } catch (UnsupportedFlavorException | IOException | JAXBException
        | PropertyAlreadyExistsException e) {
      System.out.println("Exception occured while load file " + e);
    }

  }

  private boolean openMsttFilesFromDragAndDrop(final Transferable tr)
      throws UnsupportedFlavorException, IOException, JAXBException, PropertyAlreadyExistsException {
    @SuppressWarnings("unchecked")
    List<File> files = (List<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);
    if (files.isEmpty()) {
      return false;
    }
    File file = files.get(0);
    if (!file.getName().endsWith(".mstt")) {
      return false;
    }

    UserDefinedProjectDetailsDto usrDto = LoadAndSaveProjectXml.getMsttObject(file);
    MsstGuiPckg pck = MsstGuiPckg.getInstance();
    pck.setUsrDto(usrDto);
    pck.setProjectFilePath(file.getAbsolutePath());
    pck.getLstTree().reload();
    return true;

  }

  @Override
  public void dropActionChanged(final DropTargetDragEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void dragEnter(final DropTargetDragEvent arg0) {}

  @Override
  public void dragExit(final DropTargetEvent arg0) {
    // TODO Auto-generated method stub

  }

  @Override
  public void dragOver(final DropTargetDragEvent arg0) {
    // TODO Auto-generated method stub

  }
}
