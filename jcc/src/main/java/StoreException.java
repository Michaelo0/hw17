/**
 * @author Администратор
 *  Исключение является проверяемым, поскольку возникает при вполне возможной
 *	ситуации(удалении несуществующего продукта или переполнении магазина)
 *	во время работы программы
 */
public class StoreException extends Exception{

    public StoreException(String s) {
        super(s);
    }

}
