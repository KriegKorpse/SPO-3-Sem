package com.KriegKorpse;

import java.math.BigInteger;
import java.util.ArrayList;

public class RSA {

	public static void main(String[] args) {

		User A = new User("Alice");
		A.Generate_User_Parms(8);
		User B = new User("Bob");
		B.Generate_User_Parms(10);

		// A - передатчик; B - приемник
		String text = "Ныне, присно и вовеки веков.";
		System.out.println("Оригинал = " + text);
		ArrayList<Integer> crypt = A.Crypt(B.GetOpenKey(), text);
		System.out.println("Crypted_text = " + crypt);
		String decrypted = B.DeCrypt(crypt);
		System.out.println("decrypted: " + decrypted);
    }


}
