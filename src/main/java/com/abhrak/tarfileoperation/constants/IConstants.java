package com.abhrak.tarfileoperation.constants;

public interface IConstants {

    String EXCEPTION_OCCURRED_WHILE_PROCESSING = "Exception occurred while processing!";
    String IMPORT_API = "/api-service/tfo/v1/import";
    String GET_TAR_FILE_API = "/api-service/tfo/v1/files/download/{fileName:.+}";
    String GET_ALL_TAR_FILES_API = "/api-service/tfo/v1/files/download/all";
    String API_VALUE = "This API is created for Tar file operation API Specs!";
    String HTTP_METHOD_POST = "POST";
    String HTTP_METHOD_GET = "GET";
    String IMPORT_API_VALUE = "This method is used for importing a tar file!";
    String GET_TAR_FILE_API_VALUE = "This method is used for retrieving a tar file!";
    String GET_ALL_TAR_FILES_API_VALUE = "This method is used for retrieving all tar files!";
    String TAR_FILE_EXTENSION = ".tar.gz";
    String NOT_A_TAR_FILE_EXCEPTION = "Uploaded file is not a tar file!";
    String MALFORMED_URL_EXCEPTION = "Malformed URL has occurred!";
    String INVALID_PATH_EXCEPTION = "The path string cannot be converted to a path!";
    String EXCEPTION_OCCURRED_WHILE_GETTING_TAR_FILE = "Exception occurred while getting the tar file!";
    String SUCCESSFULLY_IMPORTED = " successfully imported!";
    String EXCEPTION_OCCURRED_WHILE_SAVING_TAR_FILE = "Exception occurred while trying to save file to local file system";
    String TAR_FILE_DOWNLOAD_API = "/api-service/tfo/v1/files/download/";

}
