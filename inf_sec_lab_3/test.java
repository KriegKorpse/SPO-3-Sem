class User {
    private int p, q, m, fi, e, d;

    public User() {
       p =  
       q =  
       m =  
       fi =  
       e =  
       d =
    }

    ArrayList<Integer> Crypt(OpenKey otherKey, String text) {
       // ���������� ����� openKey
    };

    String DeCrypt(ArrayList<Integer> crypted) {
       // ���������� ���� p, q, m, fi, e, d
    };

    OpenKey GetOpenKey() {
       // ���������� ���� openKey
       return new OpenKey(e, n);
    }
};                                           

class OpenKey {
   int e;
   int n;
}

void main() {
   User A = new User(); 
   User B = new User(); 

   // A - ����������; B - �������� 
   String text;
   ArrayList<Integer> crypted = A.Crypt(B.GetOpenKey(), text);
   String decrypted = B.Decrypt(crypted);

}
