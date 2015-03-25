import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


class ID3 {
    

	/** Each node of the tree contains either the attribute number (for non-leaf
	 *  nodes) or class number (for leaf nodes) in <b>value</b>, and an array of
	 *  tree nodes in <b>children</b> containing each of the children of the
	 *  node (for non-leaf nodes).
	 *  The attribute number corresponds to the column number in the training
	 *  and test files. The children are ordered in the same order as the
	 *  Strings in strings[][]. E.g., if value == 3, then the array of
	 *  children correspond to the branches for attribute 3 (named data[0][3]):
	 *      children[0] is the branch for attribute 3 == strings[3][0]
	 *      children[1] is the branch for attribute 3 == strings[3][1]
	 *      children[2] is the branch for attribute 3 == strings[3][2]
	 *      etc.
	 *  The class number (leaf nodes) also corresponds to the order of classes
	 *  in strings[][]. For example, a leaf with value == 3 corresponds
	 *  to the class label strings[attributes-1][3].
	 **/
	class Tree {

		Tree[] children;
		String value;

		public Tree(String val, Tree[] ch) {
			value = val;
			children = ch;
		} // constructor


		
//		public String toString(String indent) {
//			if (children != null) {
//				String s = "";
//				for (int i = 0; i < children.length; i++)
//					s += indent + data[0][value] + "=" +
//							strings[value][i] + "\n" +
//							children[i].toString(indent + '\t');
//				return s;
//			} else
//				return indent + "Class: " + strings[attributes-1][value] + "\n";
//		} // toString(String)

	} // inner class Tree
        
        class _Tree {

		_Tree[] children;
		int value;

		public _Tree(int val, _Tree[] ch) {
			value = val;
			children = ch;
		} // constructor


		
		public String toString(String indent) {
			if (children != null) {
				String s = "";
				for (int i = 0; i < children.length; i++)
					s += indent + data[0][value] + "=" +
							strings[value][i] + "\n" +
							children[i].toString(indent + '\t');
				return s;
			} else
				return indent + "Class: " + strings[attributes-1][value] + "\n";
		} // toString(String)

	} // inner class Tree

	private int attributes; 	// Number of attributes (including the class)
	private int examples;		// Number of training examples
	private Tree decisionTree;	// Tree learnt in training, used for classifying
	private String[][] data;	// Training data indexed by example, attribute
	private String[][] strings; // Unique strings for each attribute
	private int[] stringCount;  // Number of unique strings for each attribute
        private String class_value;
        private int class_index;

	public ID3() {
		attributes = 0;
		examples = 0;
		decisionTree = null;
		data = null;
		strings = null;
		stringCount = null;
	} // constructor
	
	public void printTree() {
		if (decisionTree == null)
			error("Attempted to print null Tree");
		else
			System.out.println(decisionTree);
	} // printTree()

	/** Print error message and exit. **/
	static void error(String msg) {
		System.err.println("Error: " + msg);
		System.exit(1);
	} // error()

	static final double LOG2 = Math.log(2.0);
	
	static double xlogx(double x) {
		return x == 0? 0: x * Math.log(x) / LOG2;
	} // xlogx()

	/** Execute the decision tree on the given examples in testData, and print
	 *  the resulting class names, one to a line, for each example in testData.
	 **/
	public void classify(String[][] testData) {
		if (decisionTree == null)
			error("Please run training phase before classification");
		// PUT  YOUR CODE HERE FOR CLASSIFICATION
	} // classify()
        
        public double getClassProbabilityOfAttribute(String [][] _data, String attrib_value, int attrib_index)
        {
            int count_true = 0;
            int count_false = 0;
            
            for(int i = 0; i < _data.length; i++)
            {
                if(attrib_value.equals(_data[i][attrib_index]))
                {
                    if(this.class_value.equals(_data[i][this.class_index]))
                    {
                        count_true++;
                    }
                    else
                    {
                        count_false++;
                    }
                }
               
            }
            double total_count = count_true + count_false;
            
            return (double)count_true  /  total_count;
        }
        
        public double getProbabilityOfAttribute(String [][] _data, String attrib_value, int attrib_index)
        {
            int count_true = 0;
            int count_false = 0;
            
            for(int i = 0; i < _data.length; i++)
            {
                if(attrib_value.equals(_data[i][attrib_index]))
                {
                    count_true++;
                }
                else
                {
                    count_false++;
                }
               
            }
            
            double total_count = count_true + count_false;
            
            return (double)count_true  /  total_count;
        }
        
        
        public double calculateHSAttrib(String [][] _data, String attrib_value,  int attrib_index)
        {
            double prob = getClassProbabilityOfAttribute(_data, attrib_value, attrib_index);

            return -xlogx(prob) + 
                   -xlogx(1.0 - prob);
            
        }
        
        public double calculateHSClass(String [][] _data)
        {
            double prob = getProbabilityOfAttribute(_data, this.class_value,this.class_index);
            System.out.println("prob "+ prob);
            return -xlogx(prob) + 
                   -xlogx(1.0 - prob);
            
        }
        
        
        
        
        public int getBestAttribute(String [][] _data, ArrayList<Integer> used_attrib_indices)
        {
                double best_hs = 0;
                int    best_attrb_index = -1;
                double hs = calculateHSClass(_data);
                System.out.println(hs);
                double temp_hs = 0;
                for(int i = 0; i < this.class_index; i++)
                {   //Doesnt doesn't contain!
                   
                    //If the attribute (column) has not been used
                    //then use it.
                    if(used_attrib_indices.indexOf(i) == -1)
                    {
                        temp_hs = hs;
                        for(int j = 0; j < this.stringCount[i]; j++)
                        {
                            String attrb_value = this.strings[i][j];
                            double prob_of_attrb = 
                                  getProbabilityOfAttribute(_data, attrb_value,  i);
                            temp_hs -= prob_of_attrb * calculateHSAttrib(_data, attrb_value, i );
                        }

                        if(temp_hs > best_hs)
                        {
                            best_hs = temp_hs;
                            best_attrb_index = i;
                        }
                    }
                    
                }
                
                return best_attrb_index;
        }
        
        public String [][] splitDataBy( String [][] _data, String value, int col_ind)
        {
            ArrayList<ArrayList<String>> _sub_data = new ArrayList<>();
            
            int new_ind = 0;
            
            for( int i = 0; i < _data.length; i++)
            {
                if(_data[i][col_ind].equals(value) )
                {
                    _sub_data.add(new ArrayList<String>());
                    
                    for(int j = 0; j < strings.length; j++)
                    {
                        _sub_data.get(new_ind).add(_data[i][j]);
                    }
                    
                    new_ind++;
                }
            }
            
            System.out.println(_sub_data);
            
            String[][] array = new String[_sub_data.size()][];
            for (int i = 0; i < _sub_data.size(); i++) {
                ArrayList<String> row = _sub_data.get(i);
                array[i] = row.toArray(new String[row.size()]);
            }
            
            return array;
        }
        public Tree [] buildTreeRec(String [][]  _data, ArrayList<Integer> used_attribs_ind, int depth)
        {   
            int best_ind = getBestAttribute(_data, used_attribs_ind);
           
            //This means weve reached a state where all
            //the class values are the same value.
            //Construct graph with the last ind
            if(best_ind == -1 || depth == 0)
            {
                Tree [] children = new Tree[_data.length];
                
                for(int i = 0; i < _data.length; i++)
                {
                    children[i].value = _data[i][this.class_index];
                    children[i].children = null;
                }
                
                return children;
            }
            
            used_attribs_ind.add(best_ind);
            
            for(int i = 0; i < this.stringCount[best_ind]; i++)
            {
                String [][] _sub_data = splitDataBy(_data, this.strings[best_ind][i], best_ind);
                
                Tree[] children= buildTreeRec(_sub_data,(ArrayList<Integer>) used_attribs_ind.clone(), depth - 1 );
                
                
                
            }
            
            return null;
        }
        
        public String [][] copy2DArrayRange(String [][] _arr, int start_ind, int new_size)
        {
            String [][] _sub_array = new String[new_size - start_ind][];
            
            //The one off errors could be avoided.
            for(int i = start_ind; i < new_size ; i++)
            {
                _sub_array[i - start_ind] = new String[_arr[i].length];
                for( int j = 0; j < _arr[i].length; j++)
                {
                    _sub_array[i - start_ind][j] = _arr[i][j];
                }
            }
            
            return _sub_array;
            
        }
        
        public void print2DArray(String[][] _arr)
        {
            for(int i = 0; i < _arr.length; i++)
            {
                for(int j = 0; j < _arr[i].length; j++)
                {
                    System.out.print(_arr[i][j]);
                }
                System.out.print("\n");
            }
        }
	public void train(String[][] trainingData) {
		indexStrings(trainingData);
                
                //Setup
                //One off as usual
              
                this.class_index = this.strings.length - 1;
                this.class_value = this.strings[class_index][0];
                
                                                 //attrb ind  //cls ind
                ArrayList<Integer> used_attribs_ind = new ArrayList<>();
                
                String [][] _arr = copy2DArrayRange(data, 1, data.length);
                
                print2DArray(_arr);
                System.out.println("****");
                buildTreeRec(_arr,(ArrayList<Integer>) used_attribs_ind.clone(), 2);
                
           
		// PUT  YOUR CODE HERE FOR TRAINING
                
                //Create a method that calculates H(S) of a specific col
	} // train()

	/** Given a 2-dimensional array containing the training data, numbers each
	 *  unique value that each attribute has, and stores these Strings in
	 *  instance variables; for example, for attribute 2, its first value
	 *  would be stored in strings[2][0], its second value in strings[2][1],
	 *  and so on; and the number of different values in stringCount[2].
	 **/
	void indexStrings(String[][] inputData) {
		data = inputData;
		examples = data.length;
		attributes = data[0].length;
		stringCount = new int[attributes];
		strings = new String[attributes][examples];// might not need all columns
		int index = 0;
		for (int attr = 0; attr < attributes; attr++) {
			stringCount[attr] = 0;
			for (int ex = 1; ex < examples; ex++) {
				for (index = 0; index < stringCount[attr]; index++)
					if (data[ex][attr].equals(strings[attr][index]))
						break;	// we've seen this String before
				if (index == stringCount[attr])		// if new String found
					strings[attr][stringCount[attr]++] = data[ex][attr];
			} // for each example
		} // for each attribute
	} // indexStrings()

	/** For debugging: prints the list of attribute values for each attribute
	 *  and their index values.
	 **/
	void printStrings() {
		for (int attr = 0; attr < attributes; attr++)
			for (int index = 0; index < stringCount[attr]; index++)
				System.out.println(data[0][attr] + " value " + index +
									" = " + strings[attr][index]);
	} // printStrings()
		
	/** Reads a text file containing a fixed number of comma-separated values
	 *  on each line, and returns a two dimensional array of these values,
	 *  indexed by line number and position in line.
	 **/
	static String[][] parseCSV(String fileName)
								throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String s = br.readLine();
		int fields = 1;
		int index = 0;
		while ((index = s.indexOf(',', index) + 1) > 0)
			fields++;
		int lines = 1;
		while (br.readLine() != null)
			lines++;
		br.close();
		String[][] data = new String[lines][fields];
		Scanner sc = new Scanner(new File(fileName));
		sc.useDelimiter("[,\n]");
		for (int l = 0; l < lines; l++)
			for (int f = 0; f < fields; f++)
				if (sc.hasNext())
					data[l][f] = sc.next();
				else
					error("Scan error in " + fileName + " at " + l + ":" + f);
		sc.close();
		return data;
	} // parseCSV()

	public static void main(String[] args) throws FileNotFoundException,
												  IOException {
		if (args.length != 2)
			error("Expected 2 arguments: file names of training and test data");
		String[][] trainingData = parseCSV(args[0]);
		String[][] testData = parseCSV(args[1]);
		ID3 classifier = new ID3();
		classifier.train(trainingData);
                classifier.printStrings();
		//classifier.printTree();
		//classifier.classify(testData);
                
                
	} // main()

} // class ID3
