package com.example.romeandvikings.structures;

public class UnionFind {

    private int[] parent;

    public UnionFind(int size) {
        parent = new int[size];
        for(int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if(parent[x] == x) {
            return x;
        }
        return find(parent[x]);
    }

    public void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);
        if(xRoot != yRoot) {
            parent[xRoot] = yRoot;
        }
    }


}
