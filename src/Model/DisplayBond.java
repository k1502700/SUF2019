package Model;

/**
 * Created by robert on 2019. 03. 02..
 */
public class DisplayBond {


    private String id;
    private String name;
    private String disvalue;
    private String contvalue;
    private String irr;
    private String duration;
    private String resale;
    private String projection1;
    private String projection2;
    private String redemptiondate;
    private String enddate;
    private String cleanprice;
    private String interest;
    private String coupon;



    public DisplayBond(String id,String name, String disvalue, String contvalue, String irr, String duration, String resale, String projection1, String projection2) {
        this.id = id;
        this.name = name;
        this.disvalue = disvalue;
        this.contvalue = contvalue;
        this.irr = irr;
        this.duration = duration;
        this.resale = resale;
        this.projection1 = projection1;
        this.projection2 = projection2;
    }

    public DisplayBond(String id, String name, String redemptiondate, String enddate, String cleanprice, String interest, String coupon) {
        this.id = id;
        this.name = name;
        this.redemptiondate = redemptiondate;
        this.enddate = enddate;
        this.cleanprice = cleanprice;
        this.interest = interest;
        this.coupon = coupon;
    }

    public String getRedemptiondate() {
        return redemptiondate;
    }

    public String getEnddate() {
        return enddate;
    }

    public String getCleanprice() {
        return cleanprice;
    }

    public String getInterest() {
        return interest;
    }

    public String getCoupon() {
        return coupon;
    }

    public String getId(){ return id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getIrr() {
        return irr;
    }

    public void setIrr(String irr) {
        this.irr = irr;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getResale() {
        return resale;
    }

    public void setResale(String resale) {
        this.resale = resale;
    }

    public String getProjection1() {
        return projection1;
    }

    public void setProjection1(String projection1) {
        this.projection1 = projection1;
    }

    public String getProjection2() {
        return projection2;
    }

    public void setProjection2(String projection2) {
        this.projection2 = projection2;
    }

    @Override
    public String toString() {
        return "DisplayBond{" +
                "name='" + name + '\'' +
                ", irr='" + irr + '\'' +
                ", duration='" + duration + '\'' +
                ", resale='" + resale + '\'' +
                ", projection1='" + projection1 + '\'' +
                ", projection2='" + projection2 + '\'' +
                '}';
    }

    public String getDisvalue() {
        return disvalue;
    }

    public String getContvalue() {
        return contvalue;
    }
}
