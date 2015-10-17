package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import com.mstt.qa.servicevirtualization.FolderDetails;

public class FolderDto {
  public String getFolderName() {
    return folderName;
  }

  public void setFolderName(final String folderName) {
    this.folderName = folderName;
  }

  public String getFolderType() {
    return folderType;
  }

  public void setFolderType(final String folderType) {
    this.folderType = folderType;
  }

  public String getFolderInfo() {
    return folderInfo;
  }

  public void setFolderInfo(final String folderInfo) {
    this.folderInfo = folderInfo;
  }

  private String folderName;
  private String folderType;
  private String folderInfo;

  public static FolderDto getFolderDtoFromJaxb(final FolderDetails fldr) {
    FolderDto fldrDto = new FolderDto();
    fldrDto.setFolderInfo(fldr.getExtraInformation());
    fldrDto.setFolderName(fldr.getFolderName());
    fldrDto.setFolderType(fldr.getFolderType());
    return fldrDto;
  }

  public static FolderDetails getFolderDtoFrom(final FolderDto fldrdto) {
    FolderDetails fldr = new FolderDetails();
    fldr.setFolderName(fldrdto.getFolderName());
    fldr.setExtraInformation(fldrdto.getFolderInfo());
    fldr.setFolderType(fldrdto.getFolderType());
    return fldr;
  }
}
