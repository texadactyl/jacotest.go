public class main {

    public static void main(String[] args) {
        int result = -1;
        var bc = new BronzeClass();
        result = bc.returnAnInt();
        assert (result == 42);
        result = bc.bcReturnAnInt();
        assert (result == 42);
    }

}

interface BronzeFace {
    default int returnAnInt() {
        return 42;
    }
}

class BronzeClass implements BronzeFace {
    int bcReturnAnInt() {
        return returnAnInt();
    }
}
