import products.Cake;
import products.Soap;

public class Main {

    public static void main(String[] args) throws StoreException {
        Soap s1 = new Soap("Хозяйственное мыло", 20);
        Soap s2 = new Soap("Дегтярное мыло", 50);
//        Cake c5 = new Soap("торт \"Наполеон\" постный", 300, 7);
//        Cake c1 = new Cake("шоколадный торт постный", 350, 14);
//        Cake c2 = new Cake("яблочный пирог", 200, 7);
//        Cake c3 = new Cake("медовый торт", 200, 7);
//        Cake c4 = new Cake("постный пирог", 200, 7);


        Cake c1 =  new Cake.CakeBuilder("торт \"Наполеон\" постный", 300)
                .willExpire(7)
                .build();
        Cake c2 =  new Cake.CakeBuilder("шоколадный торт постный", 350)
                .willExpire(14)
                .build();
        Cake c3 =  new Cake.CakeBuilder("яблочный пирог", 200)
                .willExpire(7)
                .build();
        Cake c4 =  new Cake.CakeBuilder("медовый торт", 200)
                .willExpire(7)
                .build();
        Cake c5 =  new Cake.CakeBuilder("постный пирог", 50)
                .willExpire(7)
                .build();
//        Store st = Store.getInstance(7);
//
//        st.add(s1);
//        st.add(s2);
//        st.add(c1);
//        st.add(c2);
//        st.add(c3);
//        st.add(c4);
//        st.add(c5);


        System.out.println("Содержимое поискового индекса магазина: ");
        System.out.println();
//        System.out.println(st.getSearchIndex().toString());

        System.out.println();

        System.out.println("Поток продуктов, похожих по названию на \"шоколадный торт постный\": ");
//        st.getSearchIndex().getStream(c1).forEach(System.out::println);
        Store st = new Store(1);
        Cake cake = new Cake.CakeBuilder("яблочный пирог", 200)
                .willExpire(7)
                .build();
        st.add(cake);
        System.out.println(st.findProduct("яблочный"));
    }
}


