package com.src;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
/**
 * 
 * @author Prasanna
 * 
-	<a>Every interim step between the first and the last word must also be a word
-	No interim step can be less than three letters
-	The first line of input will contain the “cost” of each operation in the order above
-	The second line of input will contain the starting word
-	The third line of input will contain the ending word
-   Your goal is to find the lowest possible “cost” of transforming the starting word into the ending word. 
-   You can use any word list you like -- feel free to include your word list or a link to it as part of your solution.
-	(Depending on your word list, your answer might not be exactly the same as ours below.)
-	Your solution should detect and handle invalid input, and return -1 if there is no solution.</a>
 *
 *	Reference:
 *	{@link https://en.wikipedia.org/wiki/Edit_distance}
 *
 */
public class RallyHealthDistance {
	static int addition=0 ,deletion = 0, replace=0,anagram=0, finalEdits=0;
	static List<String> modified_inputs = null; 

	/**
	 * 
-	e.g:

-	Please provide inputs: 
-	1 5 1 3
-	team
-	maet
-	(output: 3) (team - maet)

	 * @param args
	 */
	public static void main(String args[]){
		System.out.println("Please provide inputs: ");
		Scanner sc = new Scanner(System.in);
		addition = sc.nextInt();
		deletion = sc.nextInt();
		replace = sc.nextInt();
		anagram = sc.nextInt();
		String str1 =sc.next();// "team";
		String str2 = sc.next();//"maet";
		RallyHealthDistance rHDistance = new RallyHealthDistance();
		rHDistance.createDynamicArray(str1.toLowerCase().toCharArray(), str2.toLowerCase().toCharArray());
		//System.out.println("Final Edits: "+finalEdits);

		if(str1.equals(new StringBuilder(str2).reverse().toString())) {

			if(anagram<finalEdits) {
				finalEdits=anagram;

				System.out.print("(output: "+finalEdits+") ");
				System.out.print("("+str1+" - "+str2+")");


			}else {
				System.out.print("(output: "+finalEdits+") ");
				System.out.print("("+str1+" - ");
				for (String names : modified_inputs) {
					System.out.print(names+" - ");

				}
				System.out.print(str2+")");
			}
		}else {
			System.out.print("(output: "+finalEdits+") ");
			System.out.print("("+str1+" - ");
			for (String names : modified_inputs) {
				System.out.print(names+" - ");

			}
			System.out.print(str2+")");
		}

	}

	/**
	 * Method to create table which stores edit values between characters
	 */
	public void createDynamicArray(char[] str1, char[] str2){
		//creating 2D array w.r.t both string
		int temp[][] = new int[str1.length+1][str2.length+1];
		//System.out.println((temp.length-1) +" "+(temp[0].length-1) );
		//filling up first row 
		for(int i=0; i < temp[0].length; i++){
			temp[0][i] = i;

		}
		//filling up first column 
		for(int i=0; i < temp.length; i++){
			temp[i][0] = i;
		}

		for(int i=1;i <=str1.length; i++){
			for(int j=1; j <= str2.length; j++){
				if(str1[i-1] == str2[j-1]){//checking if the character values are same
					temp[i][j] = temp[i-1][j-1];
				}else{//if characters are different then check the minimum from below mentioned positions. 
					temp[i][j] = 1 + min(temp[i-1][j-1], 
							temp[i-1][j],
							temp[i][j-1]
							);
				}
			}
		}
		//System.out.println(Arrays.deepToString(temp));
		calculateEdits(temp, str1, str2);
	}


	public void calculateEdits(int T[][], char[] str1, char[] str2) {
		modified_inputs = new ArrayList<>();
		int i = T.length - 1;
		int j = T[0].length - 1;
		while(true) {
			if (i == 0 || j == 0) {
				break;
			}
			if (str1[i-1] == str2[j-1]) {
				i = i-1;
				j = j-1;
			} else if (T[i][j] == T[i-1][j-1] + 1){//replace
				str1[i-1] = str2[j-1];
				modified_inputs.add(new String(str1));
				i = i-1;
				j = j-1;
				finalEdits+=replace;
			} else if (T[i][j] == T[i-1][j] + 1) {//delete
				StringBuilder sb = new StringBuilder(new String(str1));
				sb.deleteCharAt(i-1);
				i = i-1;
				modified_inputs.add(sb.toString());
				finalEdits+=deletion;

			} else if (T[i][j] == T[i][j-1] + 1){//addition
				StringBuilder sb = new StringBuilder(new String(str2));
				sb.deleteCharAt(j-1);
				j = j -1;
				modified_inputs.add(sb.toString());
				finalEdits+=addition;

			} else {
				System.out.println("Output: -1");
			}
		}
	}
	/**
	 * Finds minimum of three
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	private int min(int x,int y, int z){
		int m = Math.min(x, y);
		return Math.min(m, z);
	}



}
