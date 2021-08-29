package products;
public class Book extends Product{

    public Book(String name, int price) {
        super(name, price);
    }

    @Override
    public int getExpire(){
        return -1;
    }

    @Override
    public String category() {
        return "книги";
    }
}
