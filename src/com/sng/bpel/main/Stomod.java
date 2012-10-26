package com.sng.bpel.main;

/**
 * User: mikola
 * Date: 26.10.12
 * Time: 17:10
 */
public class Stomod {
    public static void main(String[] args){
        StoModeller m = new StoModeller();
        StoModellerParams params = new StoModellerParams();
        m.run(params);
    }
}
