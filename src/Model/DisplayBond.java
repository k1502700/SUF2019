package Model;

import javafx.beans.property.StringProperty;

/**
 * Created by robert on 2019. 03. 02..
 */
public class DisplayBond {

    private String name;
    private String value;
    private String irr;
    private String duration;
    private String resale;
    private String projection1;
    private String projection2;

    public DisplayBond(String name, String value, String irr, String duration, String resale, String projection1, String projection2) {
        this.name = name;
        this.value = value;
        this.irr = irr;
        this.duration = duration;
        this.resale = resale;
        this.projection1 = projection1;
        this.projection2 = projection2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
                ", value='" + value + '\'' +
                ", irr='" + irr + '\'' +
                ", duration='" + duration + '\'' +
                ", resale='" + resale + '\'' +
                ", projection1='" + projection1 + '\'' +
                ", projection2='" + projection2 + '\'' +
                '}';
    }
}
