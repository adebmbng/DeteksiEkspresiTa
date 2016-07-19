package com.ta.debam.deteksiekspresitugasakhirfinal.object;

/**
 * Created by Debam on 5/4/2016.
 */
public class Ekspresi{
    String nama, bibir, kanan, kiri, cluster;
    double c1,c2,c3,c4,c5;
    int id;
    int[] bibirPixel;

    public int[] getBibirPixel() {
        return bibirPixel;
    }

    public void setBibirPixel(int[] bibirPixel) {
        this.bibirPixel = bibirPixel;
    }

    public Ekspresi(){

    }

    public double getC1() {
        return c1;
    }

    public void setC1(double c1) {
        this.c1 = c1;
    }

    public double getC2() {
        return c2;
    }

    public void setC2(double c2) {
        this.c2 = c2;
    }

    public double getC3() {
        return c3;
    }

    public void setC3(double c3) {
        this.c3 = c3;
    }

    public double getC4() {
        return c4;
    }

    public void setC4(double c4) {
        this.c4 = c4;
    }

    public double getC5() {
        return c5;
    }

    public void setC5(double c5) {
        this.c5 = c5;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public String getBibir() {
        return bibir;
    }

    public void setBibir(String bibir) {
        this.bibir = bibir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKanan() {
        return kanan;
    }

    public void setKanan(String kanan) {
        this.kanan = kanan;
    }

    public String getKiri() {
        return kiri;
    }

    public void setKiri(String kiri) {
        this.kiri = kiri;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
