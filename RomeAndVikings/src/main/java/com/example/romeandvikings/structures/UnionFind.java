package com.example.romeandvikings.structures;

public class UnionFind {

    private int[] dad;

    public UnionFind(int size) {
        dad = new int[size];
        int l=0;
        do{
            dad[l]=l;
            l++;
        }while(l<dad.length);
    }


    public int find(int x) {
        int result=0;
        if(dad[x] == x) {
            result= x;
        }else {
            result= find(dad[x]);
        }
        return result;
    }

    public void union(int value1, int value2) {
        int ARoot = find(value1);
        int BRoot = find(value2);
        if(ARoot != BRoot) {
            dad[ARoot] = BRoot;
        }
    }


}
