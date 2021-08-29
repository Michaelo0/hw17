import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import products.Cake;

import java.util.Arrays;

public class StoreTest {

    @Test
    public void testAdd() {
        Store st = new Store(3);
        Cake cake = new Cake.CakeBuilder("яблочный пирог", 200)
                .willExpire(7)
                .build();
        try {
            st.add(cake);
        } catch (StoreException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(st.getNumberOfProducts(), 1);
    }

    @Test
    public void addThrowsException(){
        Store st = new Store(0);
        Cake cake = new Cake.CakeBuilder("яблочный пирог", 200)
                .willExpire(7)
                .build();
        try {
            st.add(cake);
        } catch (StoreException e) {
            e.printStackTrace();
        }

        Assertions.assertThrows(StoreException.class, () -> st.add(new Cake.CakeBuilder("вишневый пирог", 200)
                .willExpire(7)
                .build()));
    }

    @Test
    public void findProductTest() throws StoreException {
        Store st = new Store(1);
        Cake cake = new Cake.CakeBuilder("яблочный пирог", 200)
                .willExpire(7)
                .build();
        try {
            st.add(cake);
        } catch (StoreException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals("яблочный", st.findProduct("яблочный").get(0).getName().split(" ")[0]);
    }
}
