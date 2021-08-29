package products;
import java.io.Serializable;

public abstract class Product implements Serializable{
    protected String name;
    protected int price;
    protected int weight;
    protected int expire;
    private static final long serialVersionUID = 1L;

    public int getWeight(){
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }

    public int getExpire(){
        return expire;
    }

    public void setExpire(int expire){
        this.expire = expire;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    protected Product(String name, int price){
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Наименование товара: " + name  + ", цена: " + price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;

        if (o == null || o.getClass() != this.getClass()) {
            return false;
        }

        Product prod = (Product) o;
        return name.equals(prod.getName()) && price == prod.getPrice() &&
                weight == prod.getWeight() && expire == prod.getExpire();
    }

    public String category() {
        return "";
    }
}
