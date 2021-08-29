import products.Product;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchIndex {

    private static SearchIndex INSTANCE;

    private SearchIndex(){}

    public static SearchIndex getInstance() {
        if (INSTANCE != null) return INSTANCE;
        synchronized (Store.class){
            if (INSTANCE == null) {
                INSTANCE = new SearchIndex();
            }
        }

        return INSTANCE;
    }

    private Map<String, LinkedList<Product>> index = new HashMap<>();

    public Map<String, LinkedList<Product>> getIndex() {
        return index;
    }

    public void putInIndex(String s, Product product) {
        LinkedList<Product> l = this.index.get(s);

        if (l == null) {
            l = new LinkedList<>();
        }
        l.add(product);
        this.index.put(s, l);
    }

    public void add(Product product) {
        String[] strArr = product.getName().split(" ");
        for (String s : strArr) {
            this.putInIndex(s, product);
        }
    }

    /**
     * @param pr Удаление по предикату товара
     */
    public void deleteFromIndex(Predicate<Product> pr) {

        for (LinkedList<Product> list : this.index.values()) {
            Iterator<Product> itr = list.iterator();
            while (itr.hasNext()) {
                Product p = itr.next();
                if (pr.test(p)) {
                    itr.remove();
                }
            }
        }

        Iterator<Map.Entry<String, LinkedList<Product>>> iter = this.index.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<String, LinkedList<Product>> entry = iter.next();
            if (entry.getValue().isEmpty())
                iter.remove();
        }
    }

    public SearchIndex deleteFromIndex(String word, List<Product> products) {
        SearchIndex si = new SearchIndex();

        for (Product p : products) {
            String[] strArr = p.getName().split(" ");
            for (String s : strArr) {
                si.putInIndex(s, p);
            }
        }
        return si;
    }

    public void deleteFromIndex(Product product) {
        String strArr[] = product.getName().split(" ");

        for (String s : strArr) {
            List<Product> list = this.getIndex().get(s);
            Iterator<Product> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().equals(product)) {
                    it.remove();
                }
            }

            if (this.getIndex().get(s).isEmpty())
                this.getIndex().remove(s);
        }

    }

    /**
     * @param pr
     * @return Поиск по предикату слова
     * @throws StoreException
     */
    public Map<String, LinkedList<Product>> findProducts(Predicate<String> pr) throws StoreException {
        Map<String, LinkedList<Product>> map = new HashMap<>();
        Iterator<Map.Entry<String, LinkedList<Product>>> iter = this.index.entrySet().iterator();
        boolean flag = false;

        while (iter.hasNext()) {
            Map.Entry<String, LinkedList<Product>> entry = iter.next();
            if (pr.test(entry.getKey())) {
                map.put(entry.getKey(), entry.getValue());
                flag = true;
            }
        }

        if (flag)
            return map;

        throw new StoreException("По данному шаблону не нашлось продуктов");
    }

    /**
     * @param template
     * @return
     * @throws StoreException Поиск по шаблону строки
     */
    public Map<String, LinkedList<Product>> findTemplate(String template) throws StoreException {
        Map<String, LinkedList<Product>> map = new HashMap<>();

        if (template.contains("*")) {

            if (template.length() == 1)
                return this.index;

            if (template.indexOf('*') == 0) {

                map = findProducts(s -> s.contains(template.substring(1)));

            } else if (template.indexOf('*') == template.length() - 1) {

                map = findProducts(s -> s.contains(template.substring(0, template.length() - 1)));

            } else {

                Map<String, LinkedList<Product>> map1 = findProducts(s -> s.contains(template.substring(0, template.indexOf('*'))));
                Map<String, LinkedList<Product>> map2 = findProducts(s -> s.contains(template.substring(template.indexOf('*') + 1)));

                Set<String> set1 = new HashSet<>(map1.keySet());
                Set<String> set2 = new HashSet<>(map2.keySet());

                set1.retainAll(set2);
                Predicate<String> p = s -> set1.contains(s);
                map = findProducts(p);
            }
        } else {
            for (String s : this.index.keySet()) {
                if (template.equals(s)) {
                    map.put(s, this.index.get(s));
                }
            }
        }

        return map;
    }

    public Stream<Product> getStream(Product product) {
        return Stream.of(product)
                .flatMap(x -> Arrays.asList(x.getName().split(" ")).stream())
                .flatMap(y -> index.get(y).stream())
                .collect(Collectors.toMap(Function.identity(), count -> 1, (o, n) -> o + 1))
                .entrySet()
                .stream()
                .sorted((first, second) -> second.getValue() - first.getValue())
                .map(x -> x.getKey())
                .filter(x -> !x.equals(product));
    }


    public String toString() {
        return index.toString();
    }

}
