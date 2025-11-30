package com.zcs.zcssdkdemo;

import android.media.Image;

public class MyImage {
    private Image image;
    private byte[] imageData;

    public MyImage(){

    }

    public  Image getImage(){
        return image;
    }

    public void setImage(Image image ){
        this.image = image;
    }
    public void setImageData(byte[] imageData){
        this.imageData = imageData;
    }
    public void concatImageData(byte[] newImageData){

        byte[]  concatDate = new byte[imageData.length + newImageData.length];
        System.arraycopy(imageData,0,concatDate,0,imageData.length);
        System.arraycopy(newImageData,0,concatDate,imageData.length,newImageData.length);
        imageData = concatDate;
    }

}
