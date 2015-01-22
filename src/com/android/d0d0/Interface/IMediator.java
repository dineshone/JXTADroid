package com.android.d0d0.Interface;

import com.android.d0d0.FileObject.FileObject;

import net.jxta.content.ContentService;
import net.jxta.discovery.DiscoveryService;
import net.jxta.document.Advertisement;
import net.jxta.document.FileDocument;
import net.jxta.document.MimeMediaType;
import net.jxta.protocol.ContentShareAdvertisement;

import java.io.File;
import java.util.Enumeration;

/**
 * Created by d0d0 on 1/7/15.
 */
public interface IMediator {


    IMediator getMediator();

    void startJXTA();

    void startVoiceDector();

    public void stopVoiceDector();

    public void startRecording();

    public void stopRecording();

    public ContentShareAdvertisement getShareAdvForAudioFile(FileDocument fileDoc);

    public FileDocument createFileDocument(File file, MimeMediaType type );

    public ContentService getContentService();

    public DiscoveryService getDiscoveryService();

    public Enumeration<Advertisement> getLocalContentShareAdvs();

    public FileObject getFileObjectFromFilePool();

    public void putFileObjectIntoFileServer(FileObject fileObject);


}
