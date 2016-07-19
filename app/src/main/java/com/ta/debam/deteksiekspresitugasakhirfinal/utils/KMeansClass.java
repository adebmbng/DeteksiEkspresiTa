package com.ta.debam.deteksiekspresitugasakhirfinal.utils;

import android.util.Log;

import com.ta.debam.deteksiekspresitugasakhirfinal.object.Ekspresi;

import java.util.ArrayList;

/**
 * Created by Debam on 5/4/2016.
 */
public class KMeansClass {

    public void calcKmeans (ArrayList<Ekspresi> ee, ArrayList<Ekspresi> cl){

        for(int i=0;i<cl.size();i++){
            double a1,a2,a3;
            int[] a4;
            a1=Double.parseDouble(cl.get(i).getBibir());
            a2=Double.parseDouble(cl.get(i).getKanan());
            a3=Double.parseDouble(cl.get(i).getKiri());
            a4=cl.get(i).getBibirPixel();
            //Log.i("A", a1 + " " + a2 + " " + a3);

            for(int j=0;j<ee.size();j++){
                double b1,b2,b3;
                int[] b4;
                //Log.i("Object B",ee.get(j).getBibir()+" "+ee.get(j).getKanan()+" "+ee.get(j).getKiri());
                b1 = Double.parseDouble(ee.get(j).getBibir());
                b2 = Double.parseDouble(ee.get(j).getKanan());
                b3 = Double.parseDouble(ee.get(j).getKiri());
                b4 = ee.get(j).getBibirPixel();
                //Log.i("B",b1+" "+b2+" "+b3);

                double a,b,c,dtot=0, kmeans;
                double[] d = new double[a4.length];

                a=(a1-b1);
                a=Math.pow(a, 2);

                b=(a2-b2);
                b=Math.pow(b,2);

                c=(a3-b3);
                c=Math.pow(c,2);

//                Log.i("AB", a4.length+" "+b4.length);

                for(int k=0;k<a4.length;k++){
                    int d0 = a4[k]-b4[k];
//                    d[k]= Math.pow(d0,2);
                    dtot=dtot+d0;
                }

                dtot = dtot/(a4.length*1000);
                dtot = Math.pow(dtot,2);

                kmeans=a+b+c+dtot;
                kmeans=Math.sqrt(kmeans);
//                Log.i("detail",a+" "+b+" "+c+" "+dtot);
//                Log.i("K-Means", "A: " + i + " B: " + j + " total: " + kmeans);

                if(j==ee.size()-1) {
                    Log.e("K-Means", "Ekspresi: " + i + " Kemiripan: " + kmeans);
                }
                if(i==0){
                    ee.get(j).setC1(kmeans);
                } else if(i==1){
                    ee.get(j).setC2(kmeans);
                } else if(i==2){
                    ee.get(j).setC3(kmeans);
                }
//                else if(i==3){
//                    ee.get(j).setC4(kmeans);
//                } else if(i==4){
//                    ee.get(j).setC5(kmeans);
//                }

            }
        }

        AssignKmeans(ee);

    }

    public void AssignKmeans(ArrayList<Ekspresi> ee){
        for(int i =0; i<ee.size();i++){
            double c1,c2,c3,c4,c5;

            ArrayList<Double> asg = new ArrayList<Double>();

            c1 = ee.get(i).getC1();
            c2 = ee.get(i).getC2();
            c3 = ee.get(i).getC3();
//            c4 = ee.get(i).c4;
//            c5 = ee.get(i).c5;

            asg.add(c1);
            asg.add(c2);
            asg.add(c3);
//            asg.add(c4);
//            asg.add(c5);

            double min = asg.get(0);

            for(Double j: asg) {
                if(j < min) min = j;
            }

//            Log.i("ekspresi",c1+" "+c2+" "+c3+" "+min);

            if(min==c1){
                ee.get(i).setCluster("C1");
            } else if(min==c2){
                ee.get(i).setCluster("C2");
            } else if(min==c3){
                ee.get(i).setCluster("C3");
            }

//            else if(min==c4){
//                ee.get(i).setCluster("C4");
//            } else {
//                ee.get(i).setCluster("C5");
//            }

            //Log.i("Cluster", ee.get(i).getCluster());
        }
        lastCluster = ee.get(ee.size()-1).getCluster();

    }

    String lastCluster;

    public String cls (){
        return lastCluster;
    }

    public void iterasi(ArrayList<Ekspresi> ee, ArrayList<Ekspresi> cl){
        int t1=0,t2=0,t3=0,t4=0,t5=0;

        //Log.d("I:","ITERASI");

        for(int i=0;i<cl.size();i++){
            cl.get(i).setBibir("0");
            cl.get(i).setKiri("0");
            cl.get(i).setKanan("0");
            int[] alis = new int[cl.get(i).getBibirPixel().length];
            for(int j=0;j<cl.size();j++){
                alis[j]=0;
            }
            cl.get(i).setBibirPixel(alis);
        }

        for(int i =0;i<ee.size();i++){
            if(ee.get(i).getCluster().equalsIgnoreCase("C1")){
                Double bibir = Double.parseDouble(cl.get(0).getBibir())+Double.parseDouble(ee.get(i).getBibir());
                cl.get(0).setBibir(bibir.toString());

                Double kanan = Double.parseDouble(cl.get(0).getKanan())+Double.parseDouble(ee.get(i).getKanan());
                cl.get(0).setKanan(kanan.toString());

                Double kiri = Double.parseDouble(cl.get(0).getKiri())+Double.parseDouble(ee.get(i).getKiri());
                cl.get(0).setKiri(kiri.toString());

                int[] alis = cl.get(0).getBibirPixel();
                int[] a1= ee.get(i).getBibirPixel();
                for (int j=0; j<ee.get(i).getBibirPixel().length; j++) {
                    alis[j] =  a1[j]+alis[j];
                }
                cl.get(0).setBibirPixel(alis);

                t1++;
            } else if(ee.get(i).getCluster().equalsIgnoreCase("C2")){
                Double bibir = Double.parseDouble(cl.get(1).getBibir())+Double.parseDouble(ee.get(i).getBibir());
                cl.get(1).setBibir(bibir.toString());

                Double kanan = Double.parseDouble(cl.get(1).getKanan())+Double.parseDouble(ee.get(i).getKanan());
                cl.get(1).setKanan(kanan.toString());

                Double kiri = Double.parseDouble(cl.get(1).getKiri())+Double.parseDouble(ee.get(i).getKiri());
                cl.get(1).setKiri(kiri.toString());

                int[] alis = cl.get(1).getBibirPixel();
                int[] a1= ee.get(i).getBibirPixel();
                for (int j=0; j<ee.get(i).getBibirPixel().length; j++) {
                    alis[j] =  a1[j]+alis[j];
                }
                cl.get(1).setBibirPixel(alis);

                t2++;

            } else if(ee.get(i).getCluster().equalsIgnoreCase("C3")){
                Double bibir = Double.parseDouble(cl.get(2).getBibir())+Double.parseDouble(ee.get(i).getBibir());
                cl.get(2).setBibir(bibir.toString());

                Double kanan = Double.parseDouble(cl.get(2).getKanan())+Double.parseDouble(ee.get(i).getKanan());
                cl.get(2).setKanan(kanan.toString());

                Double kiri = Double.parseDouble(cl.get(2).getKiri())+Double.parseDouble(ee.get(i).getKiri());
                cl.get(2).setKiri(kiri.toString());

                int[] alis = cl.get(2).getBibirPixel();
                int[] a1= ee.get(i).getBibirPixel();
                for(int j=0; j<ee.get(i).getBibirPixel().length; j++) {
                    alis[j] =  a1[j]+alis[j];
                }
                cl.get(2).setBibirPixel(alis);

                t3++;
            }
//            else if(ee.get(i).getCluster().equalsIgnoreCase("C4")){
//                Double bibir = Double.parseDouble(cl.get(3).getBibir())+Double.parseDouble(ee.get(i).getBibir());
//                cl.get(3).setBibir(bibir.toString());
//
//                Double kanan = Double.parseDouble(cl.get(3).getKanan())+Double.parseDouble(ee.get(i).getKanan());
//                cl.get(3).setKanan(kanan.toString());
//
//                Double kiri = Double.parseDouble(cl.get(3).getKiri())+Double.parseDouble(ee.get(i).getKiri());
//                cl.get(3).setKiri(kiri.toString());
//
//                int[] alis = new int[ee.get(i).getBibirPixel().length];
//                int[] a1= ee.get(i).getBibirPixel();
//                for(int j=0; j<ee.get(i).getBibirPixel().length; j++) {
//                    alis[j] =  a1[j]+alis[j];
//                }
//                cl.get(3).setBibirPixel(alis);
//
//                t4++;
//            }else if(ee.get(i).getCluster().equalsIgnoreCase("C5")){
//                Double bibir = Double.parseDouble(cl.get(4).getBibir())+Double.parseDouble(ee.get(i).getBibir());
//                cl.get(4).setBibir(bibir.toString());
//
//                Double kanan = Double.parseDouble(cl.get(4).getKanan())+Double.parseDouble(ee.get(i).getKanan());
//                cl.get(4).setKanan(kanan.toString());
//
//                Double kiri = Double.parseDouble(cl.get(4).getKiri())+Double.parseDouble(ee.get(i).getKiri());
//                cl.get(4).setKiri(kiri.toString());
//
//                int[] alis = new int[ee.get(i).getBibirPixel().length];
//                int[] a1= ee.get(i).getBibirPixel();
//                for(int j=0; j<ee.get(i).getBibirPixel().length; j++) {
//                    alis[j] =  a1[j]+alis[j];
//                }
//                cl.get(4).setBibirPixel(alis);
//
//                t5++;
//            }
        }

        int[] total = {t1,t2,t3};

        for(int i =0;i<cl.size();i++){
            Double bibir = Double.parseDouble(cl.get(i).getBibir()) / total[i];
            cl.get(i).setBibir(bibir.toString());

            Double kanan = Double.parseDouble(cl.get(i).getKanan())/total[i];
            cl.get(i).setKanan(kanan.toString());

            Double kiri = Double.parseDouble(cl.get(i).getKiri())/total[i];
            cl.get(i).setKiri(kiri.toString());

            int[] alis = new int[cl.get(i).getBibirPixel().length];
            int[] a = cl.get(i).getBibirPixel();
            for(int j=0; j<cl.get(i).getBibirPixel().length; j++) {
                if(total[i]!=0) {
                    alis[j] = a[j] / total[i];
                }
            }
            cl.get(i).setBibirPixel(alis);
            Log.i("cluster", total[i]+" "+cl.get(i).getBibir()+" "+cl.get(i).getKanan()+" "+cl.get(i).getKiri());
        }
        //AssignKmeans(ee);

        calcKmeans(ee, cl);
    }

}
