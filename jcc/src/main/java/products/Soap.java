package products;
public class Soap extends Product{

    public Soap(String name, int price) {
        super(name, price);
    }

    @Override
    public int getExpire(){
        return -1;
    }

    @Override
    public String category() {
        return "мыло";
    }
}
