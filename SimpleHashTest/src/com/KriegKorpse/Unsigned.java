package com.KriegKorpse;

class UShort {
   public static short mod(short _a, short _b) {
      int a = _a & 0x0000FFFF;
      int b = _b & 0x0000FFFF;
      int c = a % b;
      short _c = (short) (c & 0x0000FFFF);
      return _c;
   }

   public static int compare(short _a, short _b) {
      int a = _a & 0x0000FFFF;
      int b = _b & 0x0000FFFF;
      if     (a < b) return -1;
      else if(a > b) return  1;
      return 0;
   }
}

class UInt {
   public static long cast_ulong(int a) {
      return a & 0x00000000FFFFFFFFL;
   }

   public static int cast_uint(long a) {
      return (int)(a & 0x00000000FFFFFFFFL);
   }
   
   public static int mod(int a, int b) {
      long c = cast_ulong(a) % cast_ulong(b);
      return cast_uint(c);
   }

   public static int compare(int _a, int _b) {
      long a = cast_ulong(_a);
      long b = cast_ulong(_b);
      if     (a < b) return -1;
      else if(a > b) return  1;
      return 0;
   }
   
   public static int rightrotate(int val, int count) {

      while(count != 0) {      
         boolean is_one = 1 == (val & 0x00000001);
         val = val >> 1;

         if(is_one)
            val = val | 0x80000000;
         else
            val = val & 0x7FFFFFFF;
         
         count--;
      }
      
      return val;
   }
   
}
