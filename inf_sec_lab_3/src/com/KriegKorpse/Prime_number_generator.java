package com.KriegKorpse;

import java.util.ArrayList;

public class Prime_number_generator { //максимальный digits = 31
    public void Generate_prime_array_of_digit(int digits, ArrayList <Integer> Prime_arr) {
        boolean[] Arr_2;
        int low = 1 << (digits - 1);
        //System.out.println("low= " + low);
        int high = 0x7FFFFFFF >> (32 - (digits + 1));
        //System.out.println("High= " + high);


        int size = high + 1;
        //int size = high;

        Arr_2 = new boolean[size];

        for(int i = 0; i < size; i++) {
            Arr_2[i] = true;
        }
        for(int i = 2; i*i < size; i++){
            if(Arr_2[i]) {
                for(int j = i*i; j < size; j+=i){
                    Arr_2[j] = false;
                }

            }
        }

        for(int i = low; i <= high; i++){
            if(Arr_2[i]) {
                Prime_arr.add(i);
            }

        }
    }

    public void Generate_prime_array_of_size(int size, ArrayList <Integer> Prime_arr) {
        boolean[] Arr_2;
        Arr_2 = new boolean[size];

        for(int i = 0; i < size; i++) {
            Arr_2[i] = true;
        }
        for(int i = 2; i*i < size; i++){
            if(Arr_2[i]) {
                for(int j = i*i; j < size; j+=i){
                    Arr_2[j] = false;
                }

            }
        }

        for(int i = 0; i < size; i++){
            if(Arr_2[i]) {
                Prime_arr.add(i);
            }

        }
    }

    public int Generate_prime_number(ArrayList <Integer> Prime_arr) {
        return Prime_arr.get((int) (Math.random() * Prime_arr.size()));
    }
}


