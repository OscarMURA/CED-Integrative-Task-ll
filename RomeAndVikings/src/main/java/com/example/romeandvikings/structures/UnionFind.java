package com.example.romeandvikings.structures;

/**
 * The UnionFind class implements the union-find data structure, which allows for efficient union and
 * find operations on disjoint sets.
 */
public class UnionFind {

    private int[] dad;

    /** The `public UnionFind(int size)` constructor initializes a new instance of the UnionFind class
    * with a specified size. It creates an array `dad` of integers with the given size.
    */
    public UnionFind(int size) {
        dad = new int[size];
        int l=0;
        do{
            dad[l]=l;
            l++;
        }while(l<dad.length);
    }


    /**
     * The function recursively finds the root of a given element in a disjoint set data structure.
     * 
     * @param x The parameter "x" is an integer representing the element whose parent we want to find.
     * @return The method is returning the result of the find operation, which is an integer value.
     */
    public int find(int x) {
        int result=0;
        if(dad[x] == x) {
            result= x;
        }else {
            result= find(dad[x]);
        }
        return result;
    }

    /**
     * The function "union" merges two sets by updating the parent of the root of one set to the root
     * of the other set.
     * 
     * @param value1 The value of the first element to be unioned.
     * @param value2 The value2 parameter represents the second value that needs to be unioned with the
     * first value (value1).
     */
    public void union(int value1, int value2) {
        int ARoot = find(value1);
        int BRoot = find(value2);
        if(ARoot != BRoot) {
            dad[ARoot] = BRoot;

        }
    }


}
