package comp26120;

public class hashset implements set<String> {
    int verbose;
    HashingModes mode;

    speller_config config;

    cell[] cells;
    int size;
    int num_entries; // number of cells in_use
    int collision;
    int q=0;


    // TODO add any other fields that you need

    // This is  a cell structure assuming Open Addressing
    // You wil need alternative data-structures for separate chaining
    public class cell { // hash-table entry
	String element; // only data is the key itself
	state state;
    }

    public static enum state {
	empty,
	in_use,
	deleted;
    }

    public hashset(speller_config config) {
	verbose = config.verbose;
	mode = HashingModes.getHashingMode(config.mode);
    collision = 0;
	// TODO: create initial hash table
    size=nextPrime(config.init_size);
    num_entries=0;
    cell[] abcd = new cell[size];
    cells = abcd;
    for (int i=0; i < size; i++ ){
        cells[i]= new cell();
        cells[i].element=null;
        cells[i].state=state.empty;
        }
    }
	
    // Helper functions for finding prime numbers 
    public boolean isPrime (int n)
    {
	for (int i = 2; i*i <= n; i++)
	    if (n % i == 0)
		return false;
	return true;
    }

    public int nextPrime(int n)
    {
	int i = n;
	while (!isPrime(i)) {
	    i++;
	}
	return i;
    }

    public int hash1(String s){
        int ch_ascii;
        int val =0;
        for(int i=0, j=s.length();i<s.length();i++, j--)
        {
            ch_ascii = s.charAt(i);
            val += ((ch_ascii)*nextPrime(i*i))-j; 
        }
        int a=val%size;
        if (val%size>0){return val%size;}
        return (a+size);
    }

    
    public int hash2(String s){
        int n=0;
        int val=s.charAt(0);
        return (val);
    }

     public void rehash(){
        num_entries=0;
        cell[] tem=cells;
        this.size=size*2;
        this.cells = new cell[size];
        for (int i=0;i<size;i++){
            cells[i]= new cell();
            cells[i].element=null;
            cells[i].state=state.empty;
        }
        for (int i=0;i<tem.length;i++){
            if (tem[i].state==state.in_use){
                insert (tem[i].element);
            }
        }
    }

    public void insert (String value) 
    {
	// TODO code for inserting into hash table
    if (!find(value)){
        int hashval=0;
        num_entries+=1;
        double size_dict=size;
        double ne=num_entries;
        double load_counter=ne/size_dict;
        if (load_counter>0.5){rehash();}
    if (mode==HashingModes.HASH_1_LINEAR_PROBING || mode==HashingModes.HASH_1_QUADRATIC_PROBING || mode== HashingModes.HASH_1_DOUBLE_HASHING){
        hashval=hash1(value);}
    else if (mode==HashingModes.HASH_2_LINEAR_PROBING || mode==HashingModes.HASH_2_QUADRATIC_PROBING || mode==HashingModes.HASH_2_DOUBLE_HASHING){
        hashval=hash2(value);}
    while (cells[hashval%size].state==state.in_use){hashval=collision_resolution(hashval,value);}
    cells[hashval%size].element=value;
    cells[hashval%size].state=state.in_use;
}
}
    public int collision_resolution(int hashval, String value){
        collision++;
        if (mode==HashingModes.HASH_1_LINEAR_PROBING||mode==HashingModes.HASH_2_LINEAR_PROBING){
            hashval+=29;
            return hashval;
        }
        else if (mode==HashingModes.HASH_1_QUADRATIC_PROBING){
            q++;
            hashval=hash1(value);
            hashval+=q*q;
            return hashval;
        }
        else if (mode==HashingModes.HASH_2_QUADRATIC_PROBING){
            q++;
            hashval=hash2(value);
            hashval+=q*q;
            return hashval;
        }
        else if (mode==HashingModes.HASH_1_DOUBLE_HASHING){
            q++;
            hashval=hash1(value)+q*hash2(value);
            return hashval;
        }
        else if (mode==HashingModes.HASH_2_DOUBLE_HASHING){
            q++;
            hashval=hash1(value)*q+hash2(value);
            return hashval;
        }
        else{return 0;}
    }


    public boolean find (String value)
    {
	// TODO code for looking up in hash table
    for (int i=0; i<size;i++){
        if(cells[i].state == state.in_use){if (cells[i].element.equals(value)){
            return true;
        }}
    }
	return false;
    }

    public void print_set ()
    {
	// TODO code for printing hash table
    for (int i=0; i<size;i++){
        if (cells[i].state==state.in_use){
            System.out.println(cells[i].element);
        }
    }
    }

    public void print_stats ()
    {
	// TODO code for printing statistic

    System.out.println("Number of collision:"+collision);
    double size_dict=size;
    double ne=num_entries;
    double load_counter=ne/size_dict;
    System.out.println("Load Factor:"+load_counter);


    }

    // Hashing Modes

    public enum HashingModes {
	HASH_1_LINEAR_PROBING, // =0 in mode flag
        HASH_1_QUADRATIC_PROBING, // =1, 
        HASH_1_DOUBLE_HASHING, //=2, 
        HASH_1_SEPARATE_CHAINING, // =3,
        HASH_2_LINEAR_PROBING, // =4, 
        HASH_2_QUADRATIC_PROBING, // =5, 
        HASH_2_DOUBLE_HASHING, // =6, 
        HASH_2_SEPARATE_CHAINING; // =7

	public static HashingModes getHashingMode(int i) {
	    switch (i) {
	    case 1:
		return HASH_1_QUADRATIC_PROBING;
	    case 2:
		return HASH_1_DOUBLE_HASHING;
	    case 3:
		return HASH_1_SEPARATE_CHAINING;
	    case 4:
		return HASH_2_LINEAR_PROBING;
	    case 5:
		return HASH_2_QUADRATIC_PROBING;
	    case 6:
		return HASH_2_DOUBLE_HASHING;
	    case 7:
		return HASH_2_SEPARATE_CHAINING;
	    default:
		return HASH_1_LINEAR_PROBING;
	    }
	}
    }

    // Your code

   


}