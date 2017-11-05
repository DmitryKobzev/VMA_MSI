package bsu;

public class MSI {
    private final int N;
    private final double Epsilon;
    private double [][]a;
    private double [][]b;
    private double [] g;
    private double [] f;
    private double [] x;
    private double [] x_i;
    private double [] nevyazki;
    private double norm_nevyazki;
    MSI(){
        N=5;
        Epsilon=0.00001;
        a=new double[N][N];
        b=new double [N][N];
        g=new double [N];
        f=new double [N];
        x=new double [N];
        x_i=new double [N];
        nevyazki=new double [N];
        norm_nevyazki=0;
    }
}
