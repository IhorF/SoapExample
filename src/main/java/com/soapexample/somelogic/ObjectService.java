package com.soapexample.somelogic;

import java.io.File;
import java.util.List;

/**
 * Created by Ivan on 28.01.2018.
 *
 * Used to return file in CSV format.
 * Deprecated since 1.1.0
 *
 * @version 1.0.0
 * @since 1.0.0
 */
@Deprecated
public interface ObjectService {

    File getFileByID(int id);

    File getObjectsInCSVFile();
}
