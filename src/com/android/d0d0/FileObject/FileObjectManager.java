package com.android.d0d0.FileObject;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by d0d0 on 1/4/15.
 * @author d0d0
 *
 * FileObject Manager is the object that manages the FileObject resource.
 * This object follows the Object Manager design pattern
 * the mediator object coordinates it's activities with this object
 * It performs the following:
 *  1) It creates a file Object
 *  2) It deletes file objects
 *  3) It puts the created file objects in a cache queue
 *  4) It maintains a certain number of fileObjects in the cache
 *  5) If the number file objects exceeds a certain number, it deletes file objects
 *
 */
public class FileObjectManager {

    private static BlockingQueue<FileObject> FileObjectPool =  new LinkedBlockingQueue<FileObject>() ;

//    public void sampleMethod() {
//        FileObjectManagerThread FOMThread = new FileObjectManagerThread(new Runnable() {
//            @Override
//            public void run() {
//                Object sampleObject = new Object();
//                // Do execution
//            }
//        });
//        FOMThread.start();
//    }
//    private static class FileObjectManagerThread extends Thread {
//        public FileObjectManagerThread(Runnable runnable) {
//            super(runnable);
//        }
//    }
//



}
