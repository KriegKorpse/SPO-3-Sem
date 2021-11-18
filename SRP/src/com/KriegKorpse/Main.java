package com.KriegKorpse;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;

class LoginClientPart1Res{
    public String login;
    public BigInteger A;

    LoginClientPart1Res(String _login, BigInteger _A){
        login = _login;
        A = _A;
    }

}

class LoginServerPart1Res{
    public String salt;
    public BigInteger B;

    LoginServerPart1Res(String _salt, BigInteger _B){
        salt = _salt;
        B = _B;
    }

}

class Server {
    private CommonValues c_v = new CommonValues();
    private HashMap<String, UserBaseRec> userBase = new HashMap();
    Alphabet alph = new Alphabet();
    BigInteger M;

    boolean Register(String login, BigInteger ver, String salt){
        UserBaseRec rec = userBase.get(login);
        if(null != rec)
            return false;
        userBase.put(login, new UserBaseRec(login, ver, salt));
        return true;
    }

    LoginServerPart1Res LoginPart1(LoginClientPart1Res LCP1R) throws SRPException {
        if(LCP1R.A.equals(BigInteger.ZERO)) {
            throw new SRPException("A == 0");
        }

        UserBaseRec curr_user = userBase.get(LCP1R.login);
        if(null == curr_user){
            throw new SRPException("User " + LCP1R.login + " isn't found");
        }

        BigInteger b = c_v.Create_BI_Random();
        BigInteger B = c_v.g.modPow(b, c_v.N);
        B = B.add(c_v.k.multiply(curr_user.verificator));
        BigInteger u = new BigInteger(LCP1R.A.toString() + B.toString());
        BigInteger pow = curr_user.verificator.modPow(u, c_v.N);
        BigInteger mult = LCP1R.A.multiply(pow);
        BigInteger S_S = mult.modPow(b, c_v.N);
        String s_M = S_S.toString() + LCP1R.A.toString() + B.toString();
        M = new BigInteger(s_M);
        return new LoginServerPart1Res(curr_user.salt, B);
    }

    void loginCheck(BigInteger clnt_M){
        System.out.println("clnt M= " + clnt_M);
        System.out.println("serv M= " + M);
        if(M.equals(clnt_M)){
            System.out.println("login successful");
        }
        else {
            System.out.println("login denied");
        }
    }
}

    class Client {
        private CommonValues c_v = new CommonValues();
        private String login;
        private String password;
        private BigInteger a;
        private BigInteger A;
        private BigInteger B;
        private BigInteger S_C;
        BigInteger M;

        void RegisterToServer(String l, String p, Server srv) {
            String s = c_v.Create_salt();
            BigInteger x = c_v.Create_x(s, p);
            BigInteger v = c_v.g.modPow(x, c_v.N);
            srv.Register(l, v, s);
        }

        LoginClientPart1Res LoginPart1(String _login, String _password, Server serv) {
            this.login = _login;
            this.password = _password;
            a = c_v.Create_BI_Random();
            //System.out.println("a= " + a);
            A = c_v.g.modPow(a, c_v.N);
            //System.out.println("A= " + A);
            return new LoginClientPart1Res(login, A);
        }

        BigInteger LoginPart2(LoginServerPart1Res LSP1R) throws SRPException {
            if(LSP1R.B.equals(BigInteger.ZERO)){
                throw new SRPException("B == 0");
            }
            B = LSP1R.B;
            BigInteger x = c_v.Create_x(LSP1R.salt, password);
            BigInteger u = new BigInteger(A.toString() + B.toString());
            BigInteger tmp1 = c_v.k.multiply(c_v.g.modPow(x, c_v.N));
            BigInteger tmp2 = LSP1R.B.subtract(tmp1);
            BigInteger tmp3 = a.add(u.multiply(x));
            S_C = tmp2.modPow(tmp3, c_v.N);
            Calc_M();
            return S_C;
        }

        BigInteger Calc_M(){
            String s_M = S_C.toString() + A.toString() + B.toString();
            M = new BigInteger(s_M);
            return M;
        }
}

    public class Main {

        public static void main(String[] args) throws SRPException {

            //Registration
            Server serv = new Server();
            Client clnt_1 = new Client();
            Client clnt_2 = new Client();
            Client clnt_3 = new Client();
            Client clnt_4 = new Client();
            clnt_1.RegisterToServer("Kairos", "Fateweaver345", serv);
            clnt_2.RegisterToServer("Wulfric", "Wanderer123", serv);
            clnt_3.RegisterToServer("Dagon", "Fateweaver345", serv);
            clnt_4.RegisterToServer("Arkhan", "Black567", serv);

            //login
            System.out.println("Client 1 correct login: Kairos, Fateweaver345");
            LoginClientPart1Res LCP1R_1 = clnt_1.LoginPart1("Kairos", "Fateweaver345", serv);
            LoginServerPart1Res LSP1R_1 = serv.LoginPart1(LCP1R_1);
            BigInteger S_C_1 = clnt_1.LoginPart2(LSP1R_1);
            serv.loginCheck(clnt_1.Calc_M());

            System.out.println();
            System.out.println("Client 2 correct login: Wulfric, Wanderer123");
            LoginClientPart1Res LCP1R_2 = clnt_2.LoginPart1("Wulfric", "Wanderer123", serv);
            LoginServerPart1Res LSP1R_2 = serv.LoginPart1(LCP1R_2);
            BigInteger S_C_2 = clnt_2.LoginPart2(LSP1R_2);
            serv.loginCheck(clnt_2.Calc_M());

            System.out.println();
            System.out.println("Client 3 correct login: Dagon, Fateweaver345");
            LoginClientPart1Res LCP1R_3 = clnt_3.LoginPart1("Kairos", "Fateweaver345", serv);
            LoginServerPart1Res LSP1R_3 = serv.LoginPart1(LCP1R_3);
            BigInteger S_C_3 = clnt_3.LoginPart2(LSP1R_3);
            serv.loginCheck(clnt_3.Calc_M());


            System.out.println();
            System.out.println("Client 4 incorrect login with wrong password: Arkhan, Bluck345");
            LoginClientPart1Res LCP1R_4 = clnt_4.LoginPart1("Arkhan", "Bluck345", serv);
            LoginServerPart1Res LSP1R_4 = serv.LoginPart1(LCP1R_4);
            BigInteger S_C_4 = clnt_4.LoginPart2(LSP1R_4);
            serv.loginCheck(clnt_4.Calc_M());

            System.out.println();
            System.out.println("Client 4 incorrect login with wrong login: Dugon, Black567");
            LoginClientPart1Res LCP1R_5 = clnt_4.LoginPart1("Dagon", "Black567", serv);
            LoginServerPart1Res LSP1R_5 = serv.LoginPart1(LCP1R_5);
            BigInteger S_C_5 = clnt_4.LoginPart2(LSP1R_5);
            serv.loginCheck(clnt_4.Calc_M());
        }
    }
    class CommonValues{
        public final static Alphabet alph = new Alphabet();
        public final static SHA256_Hash_Function hash = new SHA256_Hash_Function(alph);
        public final static BigInteger N = Create_N();
        public final static BigInteger g = BigInteger.valueOf(2l);;
        public final static BigInteger k = Create_k();

        private final static BigInteger Create_N(){
            Prime_number_generator p_n_g = new Prime_number_generator();
            ArrayList<Integer> prime_arr = new ArrayList<Integer>();
            p_n_g.Generate_prime_array_of_digit(24, prime_arr);
            int q = p_n_g.Generate_prime_number(prime_arr);
            return BigInteger.valueOf(2 * q + 1);
        }

        private final static BigInteger Create_k(){
            String Str_N = N.toString(16);
            String Str_g = g.toString(16);
            BigInteger k = hash.hash_func(Str_N + Str_g);
            return k;
        }

        public BigInteger Create_BI_Random(){
            SecureRandom random = new SecureRandom();
            final int minBits = Math.max(256, N.bitLength());
            BigInteger r = BigInteger.ZERO;
            while( BigInteger.ZERO.equals(r)){
                r = (new BigInteger(minBits, random)).mod(N);
            }
            return r;
        }

        public String Create_salt() {
            String salt = new String();
            for (int i = 0; i < 10; i++) {
                int ind = (int) (2 + Math.random() * (alph.length() - 3));
                salt += alph.charOf(ind);
            }
            return salt;
        }

        public  BigInteger Create_x(String salt, String password){
            BigInteger x = hash.hash_func(salt + password);
            return x;
        }

    }

class UserBaseRec{
    public String login;
    public BigInteger verificator;
    public String salt;

    public UserBaseRec(String _login, BigInteger _ver, String _salt){
        login = _login;
        verificator = _ver;
        salt = _salt;
    }

    void UserPrintln(){
        System.out.println(login + " " + verificator + " " + salt);
    }
}

class SRPException extends Exception {
    public SRPException(String text) {
        super(text);
    }
}
