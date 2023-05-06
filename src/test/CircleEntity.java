package test;

/**
 * @author Zhai Jinpei
 */
public class CircleEntity{
    private Integer N;
    private Double dr;
    private Double area;
    private Double mis;

    public CircleEntity(Integer n,Double dr,Double area,Double mis){
        N = n;
        this.dr = dr;
        this.area = area;
        this.mis = mis;
    }

    public Integer getN(){
        return N;
    }

    public void setN(Integer n){
        N = n;
    }

    public Double getDr(){
        return dr;
    }

    public void setDr(Double dr){
        this.dr = dr;
    }

    public Double getArea(){
        return area;
    }

    public void setArea(Double area){
        this.area = area;
    }

    public Double getMis(){
        return mis;
    }

    public void setMis(Double mis){
        this.mis = mis;
    }
}
