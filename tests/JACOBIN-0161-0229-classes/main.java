public class main {

    static class Insider extends Outsider {
        byte with_teeth;
        
        public Insider() {
            this.with_teeth = (byte) 41;
        }
        
        public String teething() {
            this.with_teeth = (byte) 42;
            return "done";
        }
    }
    
    public static void printer(String label, String value) {
        System.out.print(label);
        System.out.print(" : ");
        System.out.println(value);
    }
    
    public static void main(String args[]) {
        String wstr;
        
        System.out.println("Insider class will be instantiated .....");
        Insider insider = new Insider();
        System.out.println("Insider class was instantiated");
        wstr = String.valueOf(insider.with_teeth);
        printer("insider.with_teeth before calling teething", wstr);
        assert insider.with_teeth == (byte) 41;
        wstr = insider.teething();
        assert wstr == "done";
        wstr = String.valueOf(insider.with_teeth);
        printer("insider.with_teeth after calling teething", wstr);
        assert insider.with_teeth == (byte) 42;
        
        System.out.println("Outsider class will be instantiated .....");
        Outsider outsider = new Outsider();
        System.out.println("Outsider class was instantiated");
        
        String gimme_in = insider.gimmeString();
        printer("gimme_in", gimme_in);
        String gimme_out = outsider.gimmeString();
        printer("gimme_out", gimme_out);
        assert gimme_in == gimme_out;
       
        insider.iota += 1;
        assert insider.iota != outsider.iota;

        outsider.iota += 1;
        assert insider.iota == outsider.iota;
    }
    
}
