package ba.bitcamp.bitNavigator.models;

/**
 * Created by hajrudin.sehic on 29/10/15.
 */
public class WorkingHours {
    private int id;
    private Integer place_id;
    private Integer open1;
    private Integer close1;
    private Integer open2;
    private Integer close2;
    private Integer open3;
    private Integer close3;
    private Integer open4;
    private Integer close4;
    private Integer open5;
    private Integer close5;
    private Integer open6;
    private Integer close6;
    private Integer open7;
    private Integer close7;

    public WorkingHours(int id, Integer place_id, Integer open1, Integer close1, Integer open5, Integer close7, Integer open7, Integer close6, Integer open6, Integer close5, Integer close4, Integer open4, Integer open3, Integer close3, Integer close2, Integer open2) {
        this.id = id;
        this.place_id = place_id;
        this.open1 = open1;
        this.close1 = close1;
        this.open5 = open5;
        this.close7 = close7;
        this.open7 = open7;
        this.close6 = close6;
        this.open6 = open6;
        this.close5 = close5;
        this.close4 = close4;
        this.open4 = open4;
        this.open3 = open3;
        this.close3 = close3;
        this.close2 = close2;
        this.open2 = open2;
    }

    public Integer getPlace_id() {
        return place_id;
    }

    public void setPlace_id(Integer place_id) {
        this.place_id = place_id;
    }

    public Integer getOpen1() {
        return open1;
    }

    public void setOpen1(Integer open1) {
        this.open1 = open1;
    }

    public Integer getOpen2() {
        return open2;
    }

    public void setOpen2(Integer open2) {
        this.open2 = open2;
    }

    public Integer getClose1() {
        return close1;
    }

    public void setClose1(Integer close1) {
        this.close1 = close1;
    }

    public Integer getClose2() {
        return close2;
    }

    public void setClose2(Integer close2) {
        this.close2 = close2;
    }

    public Integer getOpen3() {
        return open3;
    }

    public void setOpen3(Integer open3) {
        this.open3 = open3;
    }

    public Integer getClose3() {
        return close3;
    }

    public void setClose3(Integer close3) {
        this.close3 = close3;
    }

    public Integer getOpen4() {
        return open4;
    }

    public void setOpen4(Integer open4) {
        this.open4 = open4;
    }

    public Integer getClose4() {
        return close4;
    }

    public void setClose4(Integer close4) {
        this.close4 = close4;
    }

    public Integer getOpen5() {
        return open5;
    }

    public void setOpen5(Integer open5) {
        this.open5 = open5;
    }

    public Integer getClose5() {
        return close5;
    }

    public void setClose5(Integer close5) {
        this.close5 = close5;
    }

    public Integer getOpen6() {
        return open6;
    }

    public void setOpen6(Integer open6) {
        this.open6 = open6;
    }

    public Integer getClose6() {
        return close6;
    }

    public void setClose6(Integer close6) {
        this.close6 = close6;
    }

    public Integer getOpen7() {
        return open7;
    }

    public void setOpen7(Integer open7) {
        this.open7 = open7;
    }

    public Integer getClose7() {
        return close7;
    }

    public void setClose7(Integer close7) {
        this.close7 = close7;
    }
}
