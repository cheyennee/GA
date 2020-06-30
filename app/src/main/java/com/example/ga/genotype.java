package com.example.ga;

public class genotype {
    double[] gene = new double[Constant.NVARS];
    double fitness;
    int[] upper = new int[Constant.NVARS];
    int[] lower = new int[Constant.NVARS];
    double rfitness;
    double cfitness ;
}
