public class Outsider {
    int iota;
    long lucretia;
    char charisma;
    float frankly;
    double dapper;
    String sammy;
    String foursome[] = new String[]{"The", "rain", "in", "Spain"};

    public Outsider() {
        this.iota = 11235813;
        this.lucretia = 44;
        this.charisma = 'Z';
        this.frankly = 3.14159265359f;
        this.dapper = (double) (this.frankly * 57.2958f);
        this.sammy = "Mary had a little lamb ...";
    }

    public int is_this_a_7() {
        return 7;
    }

    public String gimmeString() {
    	//System.out.println("DEBUG gimmeString begin");
		String str = String.format("iota: %d, lucretia: %d, frankly: %f, dapper: %f, sammy: %s, foursome: %s %s %s %s",
        							iota, lucretia, frankly, dapper, sammy, foursome[0], foursome[1], foursome[2], foursome[3] );
    	//System.out.println("DEBUG gimmeString end");
        return str;
    }

}
