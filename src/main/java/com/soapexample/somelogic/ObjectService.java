package com.soapexample.somelogic;

import java.io.File;
import java.util.List;

/**
 * Created by Ivan on 28.01.2018.
 */
public interface ObjectService {

    File getFileByID(int id);

    File getObjectsInCSVFile();
}
