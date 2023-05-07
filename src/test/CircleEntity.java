package test;

/**
 * @author Zhai Jinpei
 */
public class CircleEntity{
    private final Integer N;
    private final Double dr;
    private final Double area;
    private final Double mis;

    public CircleEntity(Integer n,Double dr,Double area,Double mis){
        N = n;
        this.dr = dr;
        this.area = area;
        this.mis = mis;
    }

    public Integer getN(){
        return N;
    }

    public Double getDr(){
        return dr;
    }

    public Double getArea(){
        return area;
    }

    public Double getMis(){
        return mis;
    }
}
