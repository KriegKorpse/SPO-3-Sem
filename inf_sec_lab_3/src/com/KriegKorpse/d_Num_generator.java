package com.KriegKorpse;

public class d_Num_generator {

    public static int GCD(int a, int b) {
        while(a != 0 && b != 0) {
            if( a > b )
                a %= b;
            else
                b %= a;
        }

        if(a != 0)
            return a;
        else
            return b;
    }

    public static GCDExResult GCDEx(int a, int b) {
        GCDExResult res = new GCDExResult();
        if (a == 0) {
            res.b = b;
            res.x = 0;
            res.y = 1;
        }
        else {
            GCDExResult temp = GCDEx(b % a, a);
            res.b = temp.b;
            res.x = temp.y - (b / a) * temp.x;
            res.y = temp.x;
        }
        return res;
    }

    public static int Generate_d(int e, int fi){
        int r = fi + 1;
        while(r >= 0){
            if((r % e) == 0) {
                //System.out.println("e*d % fi = " + (e*r/e)%fi);
                return r / e;
            }
            r += fi;
        }
        System.out.println("limit breach");
        return 0;
    }

    public static int Generate_d_evklid(int e, int fi){
        int NOD = GCD(e, fi);
        System.out.println("NOD btw e = " + e + " fi =" + fi+ " : " + NOD);
        //if(1 == NOD){
        GCDExResult res = GCDEx(e, fi);
        if(res.x < 0)
            res.x += fi;
        return res.x;
        //}
        //else {
        //   System.out.println("Error_evklid");
        //}
        //return 0;
    }
}

class GCDExResult {
    public int b;
    public int x;
    public int y;
}
