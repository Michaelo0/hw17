import products.Book;
import products.Cake;
import products.Product;
import products.Soap;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Store implements BookStore, CakeStore, SoapStore, Serializable {

//    private static Store INSTANCE;

    private static final long serialVersionUID = 1L;

    private int maxSize;

    private List<Product> products = new LinkedList<>();

    private SearchIndex searchIndex = SearchIndex.getInstance();

    public SearchIndex getSearchIndex() {
        return this.searchIndex;
    }

    public Store(int maxSize) {
        this.maxSize = maxSize;
    }

//    public static Store getInstance(int maxSize) {
//        if (INSTANCE != null) return INSTANCE;
//        synchronized (Store.class){
//            if (INSTANCE == null) {
//                INSTANCE = new Store(maxSize);
//            }
//        }
//
//        return INSTANCE;
//    }

    public int getNumberOfProducts() {
        return products.size();
    }


    public List<Product> getProductsByPrice(int price){
        List<Product> listOfProducts = new LinkedList<>();

        for (Product p : this.products) {
            if (p.getPrice() < price) {
                listOfProducts.add(p);
            }
        }
        return listOfProducts;
    }

    public List<Product> findProduct(String word) throws StoreException{

        if (this.searchIndex.getIndex().get(word).isEmpty()) {
            throw new StoreException("нет такого продукта " + word);
        }
        return this.searchIndex.getIndex().get(word);
    }


    /**
     * @param product
     * @throws StoreException
     * Добавление продукта
     */
    public void add(Product product) throws StoreException{
        if (products.size() + 1 > this.maxSize)
            throw new StoreException("Превышен максимальный размер магазина");

        this.getSearchIndex().add(product);
        this.products.add(product);
    }


    public List<Product> compareExpireDate(int date) {
        List<Product> listOfProducts = new LinkedList<>();
        for (Product p : this.products) {
            if (p.getExpire() >= date || p.getExpire() == -1) {
                listOfProducts.add(p);
            }
        }

        return listOfProducts;
    }

    /**
     * @param word
     * @throws StoreException
     * Удаление продукта по ключевому слову
     */
    public void deleteProduct(String word) throws StoreException {
        int k = 0;
        Iterator<Product> itr = this.products.iterator();

        while (itr.hasNext()) {
            Product p = itr.next();
            if (p.getName().contains(word)) {
                itr.remove();
                k = 1;
            }
        }

        if (k == 0)
            throw new StoreException("не найдено совпадений: " + word);

        this.searchIndex = this.searchIndex.deleteFromIndex(word, this.products);
    }

    /**
     * @param product
     * @throws StoreException
     * Удаление продукта по объекту типа Product
     */
    public void deleteProduct(Product product) throws StoreException {
        int k = 0;
        Iterator<Product> itr = this.products.iterator();

        while (itr.hasNext()) {
            if (itr.next().equals(product)) {
                itr.remove();
                k = 1;
            }
        }

        if (k == 0)
            throw new StoreException("не найден продукт: " + product.getName());

        this.getSearchIndex().deleteFromIndex(product);
    }

    @Override
    public List<Book> booksArray(){
        List<Book> books = new LinkedList<>();

        for (Product p : this.products) {
            if (p.category().equals("книги")) {
                books.add((Book)p);
            }
        }
        return books;
    }

    @Override
    public List<Cake> cakeArray(){
        List<Cake> cakes = new LinkedList<>();

        for (Product p : this.products) {
            if (p.category().equals("торты")) {
                cakes.add((Cake)p);
            }
        }
        return cakes;
    }

    @Override
    public List<Soap> soapArray(){
        List<Soap> soap = new LinkedList<>();

        for (Product p : this.products) {
            if (p.category().equals("мыло")) {
                soap.add((Soap)p);
            }
        }
        return soap;
    }

    public void save() throws FileNotFoundException, IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.ser"));
        oos.writeObject(this);
        oos.close();
    }

    public static Store loadFromFile() throws ClassNotFoundException, IOException {
        Store store = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.ser"))){
            store = (Store)ois.readObject();
        }
        return store;
    }

    public String toString() {
        return products.toString();
    }
}

