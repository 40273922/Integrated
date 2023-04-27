package test;

/**
 * @author Zhai Jinpei
 */
public class Circle{
    public static final double PI = Math.PI;
    public static final double R = 1;
    public static final double C = 2*PI*R;
    public double N = 1;
    public double dR = R/N;
    public double dS = 2*PI*dR*dR;
    public double dC= 2*PI*dR;
    public double AREAR = dS*(1+N)*N/2;
    public Circle setN(double n){
        N=n;
        dR = R/N;
        dC = 2*PI*dR;
        dS = 2*PI*dR*dR;
        AREAR = dS*(1+N)*N/2;
        return this;
    }

}
