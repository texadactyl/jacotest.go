public class Outsider {
    int iota;
    long lucretia;
    char charisma;
    float frankly;
    double dapper;
    String sammy;
    String foursome[] = new String[] {"The", "rain", "in", "Spain"};
    
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
        String str = "iota: ";
        str += String.valueOf(this.iota);
        str += ", lucretia: ";
        str += String.valueOf(this.lucretia);
        str += ", charisma: ";
        str += String.valueOf(this.charisma);
        str += ", frankly: ";
        str += String.valueOf(this.frankly);
        str += ", dapper: ";
        str += String.valueOf(this.dapper);
        str += ", sammy: ";
        str += this.sammy;
        str += ", foursome: { ";
        str += this.foursome[0];
        str += ", ";
        str += this.foursome[1];
        str += ", ";
        str += this.foursome[2];
        str += ", ";
        str += this.foursome[3];
        str += " }";
        return str;
    }    

}
