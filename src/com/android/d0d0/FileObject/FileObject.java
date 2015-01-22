package com.android.d0d0.FileObject;

import net.jxta.document.Advertisement;
import net.jxta.protocol.ContentShareAdvertisement;

import java.io.File;

/**
 * Created by d0d0 on 1/5/15.
 * This is a wrapper around a physical file
 * This wrapper contains additional properties that are added to the physical file
 * this is done so as to enable additional functionality
 * advertisements, contenShareAdvertisements
 *
 *
 */
public class FileObject {

    public String fileName = null;
    public String filePath = null;
    public String fileType = null;
    public File FileObject = null;

    public Advertisement FileAdvertisement = null;
    public ContentShareAdvertisement FileContentShareAdvertisement = null;
    public String AdvertisedfileName = null;




    public FileObject(String fileName, String filePath, String fileType) {
        // TODO Auto-generated constructor stub
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileType = fileType;
//        this.fileState = State.created;
        this.FileObject = new File(filePath, fileName + fileType);

    }


}
