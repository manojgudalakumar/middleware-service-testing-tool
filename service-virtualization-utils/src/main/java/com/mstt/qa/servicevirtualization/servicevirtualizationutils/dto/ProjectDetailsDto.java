package com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto;

import java.util.Calendar;

import com.mstt.qa.servicevirtualization.ProjectDetails;

public class ProjectDetailsDto {
  public String getProjectName() {
    return projectName;
  }

  public void setProjectName(final String projectName) {
    this.projectName = projectName;
  }

  public String getProjectShortName() {
    return projectShortName;
  }

  public void setProjectShortName(final String projectShortName) {
    this.projectShortName = projectShortName;
  }

  public String getProjectDescription() {
    return projectDescription;
  }

  public void setProjectDescription(final String projectDescription) {
    this.projectDescription = projectDescription;
  }

  public Calendar getLastUpdatedTimeStamp() {
    return lastUpdatedTimeStamp;
  }

  public void setLastUpdatedTimeStamp(final Calendar lastUpdatedTimeStamp) {
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
  }

  public Calendar getCreationTimeStamp() {
    return creationTimeStamp;
  }

  public void setCreationTimeStamp(final Calendar creationTimeStamp) {
    this.creationTimeStamp = creationTimeStamp;
  }

  private String projectName;
  private String projectShortName;
  private String projectDescription;
  private Calendar lastUpdatedTimeStamp;
  private Calendar creationTimeStamp;

  public ProjectDetailsDto() {
    projectName = "Sample project";
  }

  public ProjectDetailsDto(final String projectName, final String projectShortName,
      final String projectDescription, final Calendar lastUpdatedTimeStamp,
      final Calendar creationTimeStamp) {
    this.projectName = projectName;
    this.projectShortName = projectShortName;
    this.projectDescription = projectDescription;
    this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
    this.creationTimeStamp = creationTimeStamp;
  }

  public static ProjectDetailsDto getProjectDetailsDtoFromJaxb(final ProjectDetails prjctDtl) {
    ProjectDetailsDto projDetlsDto = new ProjectDetailsDto();
    projDetlsDto.setCreationTimeStamp(prjctDtl.getCreatedTimestamp());
    projDetlsDto.setLastUpdatedTimeStamp(prjctDtl.getLastUpdatedTimestamp());
    projDetlsDto.setProjectDescription(prjctDtl.getProjectDescription());
    projDetlsDto.setProjectName(prjctDtl.getProjectName());
    projDetlsDto.setProjectShortName(prjctDtl.getProjectShortName());
    return projDetlsDto;
  }

  public static ProjectDetails getProjectDetailsDto(final ProjectDetailsDto prjctDtl) {
    ProjectDetails projDetlsDto = new ProjectDetails();
    projDetlsDto.setProjectDescription(prjctDtl.getProjectDescription());
    projDetlsDto.setProjectName(prjctDtl.getProjectName());
    projDetlsDto.setProjectShortName(prjctDtl.getProjectShortName());
    projDetlsDto.setCreatedTimestamp(prjctDtl.getCreationTimeStamp());
    projDetlsDto.setLastUpdatedTimestamp(prjctDtl.getLastUpdatedTimeStamp());
    return projDetlsDto;

  }
}
