package com.github.tomakehurst.wiremock.stubbing;

import static com.github.tomakehurst.wiremock.common.Json.write;
import static com.github.tomakehurst.wiremock.common.LocalNotifier.notifier;
import static java.util.Arrays.asList;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import com.github.tomakehurst.wiremock.common.FileSource;
import com.github.tomakehurst.wiremock.common.IdGenerator;
import com.github.tomakehurst.wiremock.common.UniqueFilenameGenerator;
import com.github.tomakehurst.wiremock.common.VeryShortIdGenerator;
import com.github.tomakehurst.wiremock.core.Admin;
import com.github.tomakehurst.wiremock.core.Options;
import com.github.tomakehurst.wiremock.http.CaseInsensitiveKey;
import com.github.tomakehurst.wiremock.http.HttpHeader;
import com.github.tomakehurst.wiremock.http.Request;
import com.github.tomakehurst.wiremock.http.RequestListener;
import com.github.tomakehurst.wiremock.http.Response;
import com.github.tomakehurst.wiremock.http.ResponseDefinition;
import com.github.tomakehurst.wiremock.matching.RequestPattern;
import com.github.tomakehurst.wiremock.matching.ValuePattern;
import com.github.tomakehurst.wiremock.verification.VerificationResult;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.RestServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.ServiceDataType;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.SoapServiceVirtualizeDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.dto.UserDefinedProjectDetailsDto;
import com.mstt.qa.servicevirtualization.servicevirtualizationutils.utils.CommonMethodsUtils;
import com.mstt.qa.servicevirtualization.utils.StaticValues;

public class StubMappingJsonRecorder implements RequestListener {
  final static Logger logger = CommonMethodsUtils
      .getLogger(StubMappingJsonRecorder.class.getName());
  private static final String CONTENT_TYPE = "Content-Type";
  private final FileSource mappingsFileSource;
  private final FileSource filesFileSource;
  private final Admin admin;
  private final List<CaseInsensitiveKey> headersToMatch;
  private IdGenerator idGenerator;
  private UserDefinedProjectDetailsDto usrDto;
  private Options options;

  public StubMappingJsonRecorder(final FileSource mappingsFileSource,
      final FileSource filesFileSource, final Admin admin,
      final List<CaseInsensitiveKey> headersToMatch) {
    this.mappingsFileSource = mappingsFileSource;
    this.filesFileSource = filesFileSource;
    this.admin = admin;
    this.headersToMatch = headersToMatch;
    idGenerator = new VeryShortIdGenerator();
  }

  public StubMappingJsonRecorder(final FileSource mappingsFileSource,
      final FileSource filesFileSource, final Admin admin,
      final List<CaseInsensitiveKey> headersToMatch, final UserDefinedProjectDetailsDto usrDto,
      final Options options) {
    this.options = options;
    this.mappingsFileSource = mappingsFileSource;
    this.filesFileSource = filesFileSource;
    this.admin = admin;
    this.headersToMatch = headersToMatch;
    idGenerator = new VeryShortIdGenerator();
    this.usrDto = usrDto;
  }

  public StubMappingJsonRecorder(final FileSource mappingsFileSource,
      final FileSource filesFileSource, final UserDefinedProjectDetailsDto usrDto,
      final Options options) {
    this.options = options;
    this.mappingsFileSource = mappingsFileSource;
    this.filesFileSource = filesFileSource;
    this.headersToMatch = null;
    idGenerator = new VeryShortIdGenerator();
    this.usrDto = usrDto;
    this.admin = null;
  }

  @Override
  public void requestReceived(final Request request, final Response response) {
    RequestPattern requestPattern = buildRequestPatternFrom(request);

    if (requestNotAlreadyReceived(requestPattern) && response.isFromProxy()) {
      notifier().info(String.format("Recording mappings for %s", request.getUrl()));
      writeToMappingAndBodyFile(request, response, requestPattern);
    } else {
      notifier().info(
          String.format("Not recording mapping for %s as this has already been received",
              request.getUrl()));
    }
  }

  private RequestPattern buildRequestPatternFrom(final Request request) {
    RequestPattern requestPattern = new RequestPattern(request.getMethod(), request.getUrl());
    if (!headersToMatch.isEmpty()) {
      for (HttpHeader header : request.getHeaders().all()) {
        if (headersToMatch.contains(header.caseInsensitiveKey())) {
          requestPattern.addHeader(header.key(), ValuePattern.equalTo(header.firstValue()));
        }
      }
    }

    String body = request.getBodyAsString();
    if (!body.isEmpty()) {
      ValuePattern bodyPattern = valuePatternForContentType(request);
      requestPattern.setBodyPatterns(asList(bodyPattern));
    }

    return requestPattern;
  }

  private ValuePattern valuePatternForContentType(final Request request) {
    String contentType = request.getHeader("Content-Type");
    if (contentType != null) {
      if (contentType.contains("json")) {
        return ValuePattern.equalToJson(request.getBodyAsString(), LENIENT);
      } else if (contentType.contains("xml")) {
        return ValuePattern.equalToXml(request.getBodyAsString());
      }
    }

    return ValuePattern.equalTo(request.getBodyAsString());
  }

  private void writeToMappingAndBodyFile(final Request request, final Response response,
      final RequestPattern requestPattern) {
    String fileId = idGenerator.generate();
    String mappingFileName =
        UniqueFilenameGenerator.generate(request, StaticValues.RECORD_MAPPING_FILE_PREFIX, fileId);
    String bodyFileName =
        UniqueFilenameGenerator.generate(request, StaticValues.RECORD_BODY_FILE_PREFIX, fileId);
    ResponseDefinition responseToWrite = new ResponseDefinition();
    responseToWrite.setStatus(response.getStatus());
    responseToWrite.setBodyFileName(bodyFileName);

    if (response.getHeaders().size() > 0) {
      responseToWrite.setHeaders(response.getHeaders());
    }
    try {
      if (options.addToProjectXml()) {
        if (request.portNumber() == options.soapPortNumber()) {
          usrDto.addSoapServiceVirtualize(getSoapVirtual(request, response, mappingFileName));
        } else {
          usrDto.addRestServiceVirtualize(getRestVirtual(request, response, mappingFileName));
        }
      }
    } catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
    StubMapping mapping = new StubMapping(requestPattern, responseToWrite);
    filesFileSource.writeBinaryFile(bodyFileName, response.getBody());
    mappingsFileSource.writeTextFile(mappingFileName, write(mapping));
    logger.info("Adding a new virtual component to the list please reload the tree");
  }

  private RestServiceVirtualizeDto getRestVirtual(final Request request, final Response response,
      final String bodyFileName) throws IOException {
    RestServiceVirtualizeDto rstDto = new RestServiceVirtualizeDto();
    Calendar clnd = Calendar.getInstance();
    rstDto.setCreationTimeStamp(clnd);
    rstDto.setLastUpdatedTimeStamp(clnd);
    rstDto.setRequestData(request.getBodyAsString());
    rstDto.setResponseData(response.getBodyAsString());
    rstDto.setRestServiceVirtualName(gettrimmed(bodyFileName));
    rstDto.setRestVirtualMappingFileName(bodyFileName);
    if (isNotEmpty(request.getHeaders().getHeaderValue(CONTENT_TYPE))) {
      String[] str = request.getHeaders().getContentTypeHeader().mimeTypePart().split("/");
      rstDto.setRestVirtualRequestDataType(ServiceDataType.fromString(str[1]));
    }
    if (isNotEmpty(response.getHeaders().getHeaderValue(CONTENT_TYPE))) {
      String[] str = response.getHeaders().getContentTypeHeader().mimeTypePart().split("/");
      rstDto.setRestVirtualResponseDataType(ServiceDataType.fromString(str[1]));
    }
    rstDto.setRestServiceVirtualUrl(request.getUrl());
    System.out.println(rstDto.toString());
    return rstDto;
  }

  private SoapServiceVirtualizeDto getSoapVirtual(final Request request, final Response response,
      final String bodyFileName) throws IOException {
    SoapServiceVirtualizeDto soapDto = new SoapServiceVirtualizeDto();
    Calendar clnd = Calendar.getInstance();
    soapDto.setCreationTimeStamp(clnd);
    soapDto.setLastUpdatedTimeStamp(clnd);
    soapDto.setRequestXml(request.getBodyAsString());
    soapDto.setResponseXml(response.getBodyAsString());
    soapDto.setSoapServiceVirtualizeName(bodyFileName);
    soapDto.setVirtualizeServiceMappingFileName(bodyFileName);
    soapDto.setSoapServiceVirtualizeName(gettrimmed(bodyFileName));
    soapDto.setSoapEndPointUrl(request.getUrl());
    return soapDto;
  }

  private String gettrimmed(final String bodyFileName) {
    int index = bodyFileName.indexOf(".json");
    return bodyFileName.substring(0, index);
  }

  private boolean requestNotAlreadyReceived(final RequestPattern requestPattern) {
    VerificationResult verificationResult = admin.countRequestsMatching(requestPattern);
    verificationResult.assertRequestJournalEnabled();
    return (verificationResult.getCount() <= 1);
  }

  public void setIdGenerator(final IdGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

}
