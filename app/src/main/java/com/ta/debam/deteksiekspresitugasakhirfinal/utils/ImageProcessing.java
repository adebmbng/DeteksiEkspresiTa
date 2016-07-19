package com.ta.debam.deteksiekspresitugasakhirfinal.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.util.SparseArray;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.ta.debam.deteksiekspresitugasakhirfinal.object.Ekspresi;

/**
 * Created by Debam on 5/3/2016.
 */
public class ImageProcessing {

    public static Bitmap RotateBitmap(Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Ekspresi GetDataBitmap(Context context, Bitmap input, int id){
        Bitmap bibir;
        String kanan, kiri, smiley;
        int[] bibirPixel = new int[80*48];

        FaceDetector detector = new FaceDetector.Builder(context)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        Frame frame = new Frame.Builder().setBitmap(input).build();
        SparseArray<Face> faces = detector.detect(frame);
        if(faces.size()!=0) {
            Face face = faces.valueAt(0);

            if(face.getLandmarks().size()==8) {
                int y = (int) face.getLandmarks().get(5).getPosition().y;
                y = y - ((int) face.getLandmarks().get(7).getPosition().y - (int) face.getLandmarks().get(5).getPosition().y);

                double x = (int) face.getLandmarks().get(6).getPosition().x;
                x = x - ((int) face.getLandmarks().get(7).getPosition().x - (int) face.getLandmarks().get(6).getPosition().x) / 2;

                double panjang = (int) face.getLandmarks().get(5).getPosition().x;
                panjang = panjang + ((int) face.getLandmarks().get(5).getPosition().x - (int) face.getLandmarks().get(7).getPosition().x) / 2;
                panjang = panjang - x;

                int lebar = (int) face.getLandmarks().get(7).getPosition().y;
                lebar = lebar + ((int) face.getLandmarks().get(7).getPosition().y - (int) face.getLandmarks().get(5).getPosition().y);
                lebar = lebar - y;

                bibir = Bitmap.createBitmap(input, (int) x, y, (int) panjang, lebar);
                bibir = scaleDownBibir(bibir, 120, true);

                bibirPixel = PixelArray(bibir);

                kanan = "" + face.getIsRightEyeOpenProbability();
                kiri = "" + face.getIsLeftEyeOpenProbability();
                smiley = "" + face.getIsSmilingProbability();

                Ekspresi objEks = new Ekspresi();
                objEks.setBibir(smiley);
                objEks.setBibirPixel(bibirPixel);
                objEks.setKanan(kanan);
                objEks.setKiri(kiri);

                Log.w("Data5", face.getLandmarks().get(5).getPosition().x + " " + face.getLandmarks().get(5).getPosition().y);
                Log.w("Data6", face.getLandmarks().get(6).getPosition().x+" " +face.getLandmarks().get(6).getPosition().y);
                Log.w("Data7", face.getLandmarks().get(7).getPosition().x+" " +face.getLandmarks().get(7).getPosition().y);

                Log.w("Data",smiley + " " + kanan + " " + kiri);

                detector.release();
                return objEks;
            } else {
                Ekspresi nullObj = new Ekspresi();
                nullObj.setBibir("no data");
                nullObj.setBibirPixel(null);
                nullObj.setKanan("no data");
                nullObj.setKiri("no data");
                detector.release();
                return nullObj;
            }

        } else {
            Ekspresi nullObj = new Ekspresi();
            nullObj.setBibir("no data");
            nullObj.setBibirPixel(null);
            nullObj.setKanan("no data");
            nullObj.setKiri("no data");
            detector.release();
            return nullObj;
        }
    }

    public static Bitmap scaleDownBibir(Bitmap realImage, float maxImageSize,
                                        boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                32, filter);
        return newBitmap;
    }

    public static int[] PixelArray(Bitmap org){
        int[] pixels = new int[org.getWidth()*org.getHeight()];

        int i=0;
        for(int x = 0;x<org.getWidth();x++){
            for(int y=0;y<org.getHeight();y++){
                int rgb = org.getPixel(x,y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                pixels[i]=(r+g+b)/3;
                i++;
            }
        }

        return pixels;
    }
}
