package sample;

public class Invoice {


    float price;
    String des;
    int qty;


    public Invoice() {
    }

    public Invoice(int qty, String des, float price) {
        this.price = price;
        this.des = des;
        this.qty = qty;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public float getPrice(){
        return  price;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        des = des;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

}
