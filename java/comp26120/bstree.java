package comp26120;

public class bstree implements set<String> {
    int verbose;

    String value;
    bstree left;
    bstree right;

    speller_config config;
    int all_insert=0;
    int com_insert=0;
    int all_find=0;
    int com_find=0;


    // TODO add fields for statistics

    public bstree(speller_config config) {
	verbose = config.verbose;
	this.config = config;
    }
    public int height_of_tree(){
        if (!tree()){return 0;}
        int lh=this.left.height_of_tree();
        int rh=this.right.height_of_tree();
        return ((int) Math.max(lh,rh)+1);
    }

	
    public int size(){
	// This presumes that if value is not null then (possibly empty) left and right trees exist.
	if(tree()){
	    return 1 + left.size() + right.size();
	}
	return 0;
    } 

    public void insert (String value) 
    {
	if(tree()){
	    // TODO if tree is not NULL then insert into the correct sub-tree
        if (!find(value)){
            com_insert++;
            int a=this.value.compareTo(value);
            if (a>0){this.left.insert(value);}
            else if (a<0){this.right.insert(value);}}
	}
	else{
	    // TODO otherwise create a new node containing the value and two sub-trees.
        this.value=value;
        all_insert++;
        this.right=new bstree(this.config);
        this.left=new bstree(this.config);
	}
    }

    public boolean find (String value)
    {
	if(tree()){
	    //TODO complete the find function
        int a=this.value.compareTo(value);
        if (a==0){
            all_find+=1;
            return true;}
        else if (a>0){
            com_find++;
            return this.left.find(value);}
        else if (a<0){
            com_find++;
            return this.right.find(value);}
	}
	// if tree is NULL then it contains no values
	return false;
    }

    private boolean tree() {
	return (value != null);
    }

    // You can update this if you want
    public void print_set_recursive(int depth)
    {
	if(tree()){
	    for(int i=0;i<depth;i++){ System.out.print(" "); }
	    System.out.format("%s\n",value);
	    left.print_set_recursive(depth+1);
	    right.print_set_recursive(depth+1);
	}
    } 

    // You can update this if you want
    public void print_set ()
    {
	System.out.print("Tree:\n");
	print_set_recursive(0);
    }

    public void print_stats ()
    {
	// TODO update code to record and print statistics
    System.out.println("Binary tree size:"+size());
    System.out.println("Binary tree height:"+height_of_tree());
    if (this.all_find>0){
        System.out.println("avg compersion per find:"+(double) (this.com_find/this.all_find));
    }
    if (this.all_insert>0){
        System.out.println("avg compersion per insert:"+(double) (this.com_insert/this.all_insert));
    }
    
}
}