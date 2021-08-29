package products;

public class Cake extends Product implements ExpiredProduct{

//    public Cake(String name, int price, int expire) {
//        super(name, price);
//        this.expire = expire;
//    }

    public Cake(CakeBuilder cb) {
        super(cb.name, cb.price);
        this.expire = cb.expire;
    }

    @Override
    public int getExpire() {
        return expire;
    }

    @Override
    public String category() {
        return "торты";
    }

    public static class CakeBuilder {
        private String name;
        private int price;
        private int expire;

        public CakeBuilder(String name, int price){
            this.name = name;
            this.price = price;
        }

        public CakeBuilder willExpire(int expireDate){
            this.expire = expireDate;
            return this;
        }

        public Cake build() {
            return new Cake(this);
        }
    }
}
