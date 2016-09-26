package capstone;
import java.util.*;
import java.io.*;

public class Fields {
	/** 
	 * Each GF(2^k) contains an irreducible polynomial mod which all computations are carried out. 
	 * I have stored these irreducible polynomials up to 10 in the constructor itself. 
	 * You can add more values of irreducible polynomials for k=11 onwards.
	 * The code will work flawlessly. 
	 * small_irreducible is basically (x^k)mod irredible, to aid in finding modulus of polynomials
	 * later. 
	 * 
	 * Representaion of polynomials is in arraylists. 
	 * Eg: 1+x^2+x^3+x^6+x^9 is represented as [1,0,1,1,0,0,1,0,0,1]
	 * The power being represented by the position in the arraylist, while coefficient of (x^i) represented
	 * by ith element of the arraylist. 
	   
	 * @author      Shivankar Ojha
	 * 
	 
	 */
	
	static ArrayList<Integer> irreducible;
	static ArrayList<Integer> small_irreducible;
	
	
	
	public Fields(int k) {
		//constructor to initalize the irreducible polynomial and small_irreducible of GF(2^k)
		irreducible=new ArrayList<Integer>();
		String x=new String();
		switch (k)
		{
			case 1:x="";
				break;
			case 2:x="111"; 
				break;
			case 3:x="1011";
				break;
			case 4:x="10011";
				break;
			case 5:x="100101";
				break;
			case 6:x="1000011";
				break;
			case 7:x="10000011";
				break;
			case 8:x="101100011";
				break;
			case 9:x="10000100001";
				break;
			case 10:x="10000001001";
				break;
			case 11:x="100000000101";
				break;
			default: 
				System.out.println("Too high! Enter a number up to 11");
				System.exit(0);
				}
		
		//initializing the small_irreducible
		for (int i=x.length()-1;i>=0;i--)
			irreducible.add(Integer.parseInt(""+x.charAt(i)));
		small_irreducible=new ArrayList<Integer>();
		for (int i=irreducible.size()-2;i>=0;i--)
		{
			if (irreducible.get(i)==0)
				continue;
			else
				{
				for (int j=0;j<i;j++)
					small_irreducible.add(irreducible.get(j));
				}
			small_irreducible.add(1);
			break;
		}
	}


	public static ArrayList<Integer> Multiply(ArrayList<Integer> poly1, ArrayList<Integer> poly2)
	{
		/**
		 * This method takes two polynomials in ArrayListform and returns their multiplication
		 * in the form of an arrayList, without the mod irreducible. 
		 * 
		 * @param       poly1    Array list representing first polynomial
		 * @param       poly2    Array list representing second polynomial
		
		 
		 */
		ArrayList<Integer> poly3=new ArrayList<Integer>();
		
		for (int i=0;i<poly1.size()+poly2.size()-1;i++)
		{
		poly3.add(0);
		}
	
		for (int i=0;i<poly1.size();i++)
		{
			for (int j=0;j<poly2.size();j++)
			{
				if (poly1.get(i)==0 || poly2.get(j)==0)
					continue;
				else
				{
					int n=poly3.get(i+j);
					n=n+1;
					poly3.set((i+j),n%2);
				}
			}
		}
		return (poly3);
	}
	
	
	public static ArrayList<Integer> Add(ArrayList<Integer> poly1, ArrayList<Integer> poly2)
	{
		/**
		 * This method takes two polynomials in ArrayListform and returns their sum
		 * in the form of an arrayList, without the mod irreducible. 
		 * 
		 * @param       poly1    Array list representing first polynomial
		 * @param       poly2    Array list representing second polynomial
		
		 
		 */
		int x,y,num=0;
		ArrayList<Integer> result=new ArrayList<Integer>();
		ArrayList<Integer> big_poly=new ArrayList<Integer>();
		ArrayList<Integer> small_poly=new ArrayList<Integer>();
		if (poly1.size()>poly2.size())
		{
			big_poly.addAll(poly1);
			small_poly.addAll(poly2);
		}
		else 
		{
			big_poly.addAll(poly2);
			small_poly.addAll(poly1);
		}
		for (int i=0;i<big_poly.size();i++)
		{
			if (i<small_poly.size())
			{
				num=(big_poly.get(i)+small_poly.get(i))%2;
				
			}
			else 
				num=(big_poly.get(i))%2;
			if (num<0)
				num=num+2;
				result.add(num);
		}
		
		for (int j=result.size()-1;j>=0;j--)
		{
			if (result.get(j)==0 && j==result.size()-1)
			result.remove(j);
		}
		return result;
		
	}
	
	public static ArrayList<Integer> Subtract(ArrayList<Integer> poly1, ArrayList<Integer> poly2)
	{
		
		/**
		 * This method takes two polynomials in ArrayListform and returns their difference
		 * in the form of an arrayList, without the mod irreducible. 
		 * 
		 * @param       poly1    Array list representing first polynomial
		 * @param       poly2    Array list representing second polynomial
		
		 
		 */
		int x,y;
		ArrayList<Integer> result=new ArrayList<Integer>();
		ArrayList<Integer> big_poly=new ArrayList<Integer>();
		ArrayList<Integer> small_poly=new ArrayList<Integer>();
		if (poly1.size()>poly2.size())
		{
			big_poly.addAll(poly1);
			small_poly.addAll(poly2);
		}
		else 
		{
			big_poly.addAll(poly2);
			small_poly.addAll(poly1);
		}
		int x1=0;
		for (int i=0;i<big_poly.size();i++)
		{
			if (i<poly1.size() && i<poly2.size())
				x1=poly1.get(i)-poly2.get(i);
			if (i>=poly1.size())
				x1=0-poly2.get(i);
			if (i>=poly2.size())
				x1=poly1.get(i)-0;
			x1=x1%2;
			if (x1<0)
				x1=x1+2;
			result.add(x1);
		}
		for (int j=result.size()-1;j>=0;j--)
		{
			if (result.get(j)==0 && j==result.size()-1)
			result.remove(j);
		}

		return result;
	}
	
	
	
	public static ArrayList<Integer> helper_mod(ArrayList<Integer> poly)
	{
		/**
		 * This method takes a polynomial in ArrayListform and takes each individual term of it
		 * and finds it mod irreducible, sending it to the mod function. 
		 * It then adds all these mod values to get the final mod irreducible of the polynomial 
		 * 
		 * @param       poly    Array list representing polynomial
		 
		 
		 */
		ArrayList<Integer> positions=new ArrayList<Integer>();
		ArrayList<Integer> result=new ArrayList<Integer>();
		for (int i=0;i<poly.size();i++)
		{
		if (poly.get(i)==1)
			positions.add(i);
		}
		for (int i=0;i<positions.size();i++)
		{
			ArrayList<Integer> element=new ArrayList<Integer>();
			for (int j=0;j<positions.get(i);j++)
			{
				element.add(0);
			}
			element.add(1);
			result=Add(result,mod(element));
		}
		return result;
	}
	
	
	
	public static ArrayList<Integer> mod(ArrayList<Integer> poly)
	{
		/**
		 * This method takes find the mod of polynomials of the form (x^k)
		 * and returns the result. 
		 * 
		 * @param       poly1    Array list representing polynomial
		 
		 
		 */
		int irreducible_power=irreducible.size()-1;
		int power=poly.size()-1;
		
		
		
		ArrayList<Integer> remainder_poly=new ArrayList<Integer>();
		ArrayList<Integer> new_poly=new ArrayList<Integer>();
		
		if (power==irreducible_power)
			return small_irreducible;
		if (power<irreducible_power)
			return poly;
		
			int a=power-irreducible_power;
			remainder_poly=new ArrayList<Integer>();
			new_poly=new ArrayList<Integer>();
			for (int i=0;i<=a;i++)
			{
				if (i==a)
					remainder_poly.add(1);
				else
					remainder_poly.add(0);
			}
			new_poly.addAll(Multiply(small_irreducible, remainder_poly));
			
			int count=0;ArrayList<Integer> positions=new ArrayList<Integer>();
			for (int i=0;i<new_poly.size();i++)
			{
			if (new_poly.get(i)==1)
				positions.add(i);
			}
			ArrayList<Integer> sub_new_poly1=new ArrayList<Integer>();
			ArrayList<Integer> sub_new_poly2=new ArrayList<Integer>();
			ArrayList<Integer> result=new ArrayList<Integer>();
			//result.add(0);
			
			for (int i=0;i<positions.size();i++)
			{
				sub_new_poly1=new ArrayList<Integer>();
				for (int j=0;j<positions.get(i);j++)
					sub_new_poly1.add(0);
				sub_new_poly1.add(1);
				result=(Add((result),mod(sub_new_poly1)));
			}
			
	
			return result;

	}
	
	public static ArrayList<Integer> divide(ArrayList<Integer> irreducible,ArrayList<Integer> poly)
	{
		/**
		 * I wrote this function later realizing I wont need it! 
		 * This method takes two polynomials in ArrayListform and returns irreducible/poly
		 * in the form of an arrayList
		 * 
		 * @param       irreducible    Array list representing first polynomial
		 * @param       poly    Array list representing second polynomial
		
		 
		 */
		ArrayList<Integer> remainder_poly=new ArrayList<Integer>();
		ArrayList<Integer> result=new ArrayList<Integer>();
		ArrayList<Integer> irreducible_copy=new ArrayList<Integer>();
		irreducible_copy.addAll(irreducible);
		int irreducible_power=irreducible.size()-1;
		int power=poly.size()-1;
		int a=irreducible_power-power;
		
		while (irreducible_power>=power)
		{
			remainder_poly=new ArrayList<Integer>();
			for (int i=0;i<=a;i++)
			{
				if (i==a)
					remainder_poly.add(1);
				else
					remainder_poly.add(0);
			}
			irreducible_copy=Subtract(irreducible_copy, Multiply(remainder_poly, poly));
			irreducible_power=irreducible_copy.size()-1;
			result=Add(result,remainder_poly);
			a=irreducible_power-power;
		}
		return result;
	}
	
	public static ArrayList<Integer> inverse(ArrayList<Integer> poly)
	{
		/**
		 * This method takes a polynomial in ArrayListform and returns its inverse
		 * in the form of an arrayList, with respect to the irreducible. 
		 * 
		 * @param       poly    Array list representing  polynomial
		 * 
		
		
		 
		 */
		ArrayList<Integer> inverse=new ArrayList<Integer>();
		int x=(int)(Math.pow(2, irreducible.size()-1))-2;
		inverse=SquareAndMult(poly, x);
		
		return inverse;
	}
	public static ArrayList<Integer> SquareAndMult(ArrayList<Integer> poly,int x)
	{
		/**
		 * This method takes two polynomials in ArrayListform and returns 
		 * (poly^x)mod irreducible
		 * 
		 * @param       poly    Array list representing  polynomial
		 * @param       x    power to which polynomial is to be raised. 
		
		 
		 */
		ArrayList<Integer> answer=new ArrayList<Integer>();
		answer.add(1);
		for (int j=1;j<=x;j++)
		{
		answer=Multiply(answer,poly);
		answer=helper_mod(answer);
		}
		return answer;
	}

	public static ArrayList<Integer> StringToList(String x1)
	{
		ArrayList<Integer> poly1=new ArrayList<Integer> ();
		for (int i=x1.length()-1;i>=0;i--)
			poly1.add(Integer.parseInt(""+x1.charAt(i)));
		return poly1;
	}

	public static ArrayList<Integer> ReverseList(ArrayList<Integer> poly)
	{
		ArrayList<Integer> poly1=new ArrayList<Integer> ();
		for (int i=poly.size()-1;i>=0;i--)
			poly1.add(poly.get(i));
		return poly1;
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader inp=new BufferedReader(new InputStreamReader(System.in));
		ArrayList<Integer> poly1=new ArrayList<Integer>();
		ArrayList<Integer> poly2=new ArrayList<Integer>();
		ArrayList<Integer> poly3=new ArrayList<Integer>();
		System.out.println("Enter the value of k for GF(2^k):" );
		int k=Integer.parseInt(inp.readLine());
		Fields gf=new Fields(k);
		System.out.println("Irreducible polynomialGF(2^"+k+"): "+gf.irreducible);
		System.out.println("Enter the numbers as follows: ");
		System.out.println("Eg: 1+x^2+x^3+x^6+x^9 is represented as [1,0,1,1,0,0,1,0,0,1]");
		System.out.println(" The power being represented by the position in the arraylist,");
		System.out.println("while coefficient of (x^i) representedby ith element of the arraylist.");
		
		String x1,x2;
		int ch=0;
		do {
		System.out.println("Enter your choice: ");
		System.out.println("Press 1 to generate all elements in GF(2^"+k+")");
		System.out.println("Press 2 to add two elements in GF(2^"+k+")");
		System.out.println("Press 3 to subtract two elements in GF(2^"+k+")");
		System.out.println("Press 4 to find product of two elements in GF(2^"+k+")");
		System.out.println("Press 5 to find element^k in GF(2^"+k+")");
		System.out.println("Press 6 to find  element mod irreducible in GF(2^"+k+")");
		System.out.println("Press 7 to find  inverse of element mod irreducible in GF(2^"+k+")");
		System.out.println("Presss 8 to exit");
		ch=Integer.parseInt(inp.readLine());
		
		switch (ch)
		{
			case 1:
				ArrayList<Integer> alpha=new ArrayList<Integer>();
				System.out.println("All elements in GF(2^"+k+"):");
				for (int i=1;i<Math.pow(2,k)-1;i++)
				{
					alpha=gf.StringToList(Integer.toBinaryString(i));
					System.out.println(alpha);
				}
				
			case 2:
				System.out.println("Enter Number 1: ");
				x1=(inp.readLine());
				poly1=gf.StringToList(x1);
				poly1=gf.ReverseList(poly1);
				
				System.out.println("Enter Number 2: ");
				x2=(inp.readLine());
				poly2=gf.StringToList(x1);
				poly2=gf.ReverseList(poly1);
				System.out.println("Sum: "+gf.Add(poly1, poly2));
				
			
			case 3:
			System.out.println("Enter Number 1: ");
			x1=(inp.readLine());
			poly1=gf.StringToList(x1);
			poly1=gf.ReverseList(poly1);
			
			System.out.println("Enter Number 2: ");
			x2=(inp.readLine());
			poly2=gf.StringToList(x1);
			poly2=gf.ReverseList(poly1);
			System.out.println("difference i.e first-second polynomial : "+gf.Subtract(poly1, poly2));
			
			case 4:
				System.out.println("Enter Number 1: ");
				x1=(inp.readLine());
				poly1=gf.StringToList(x1);
				poly1=gf.ReverseList(poly1);
				
				System.out.println("Enter Number 2: ");
				x2=(inp.readLine());
				poly2=gf.StringToList(x1);
				poly2=gf.ReverseList(poly1);
				System.out.println("Product: "+gf.helper_mod(gf.Multiply(poly1, poly2)));
				
			
			case 5:
				System.out.println("Enter Number 1: ");
				x1=(inp.readLine());
				poly1=gf.StringToList(x1);
				poly1=gf.ReverseList(poly1);
				System.out.println("Enter the power: ");
				int x=Integer.parseInt(inp.readLine());
				System.out.println("Element^k mod irreducible: "+gf.SquareAndMult(poly1, x));		
				
			case 6:
				System.out.println("Enter Number 1: ");
				x1=(inp.readLine());
				poly1=gf.StringToList(x1);
				poly1=gf.ReverseList(poly1);
				System.out.println("element mod irreducible: "+gf.helper_mod(poly1));
				
			case 7:
				System.out.println("Enter Number 1: ");
				x1=(inp.readLine());
				poly1=gf.StringToList(x1);
				poly1=gf.ReverseList(poly1);
				System.out.println("element inverse mod irreducible: "+gf.inverse(poly1));
				
			case 8:
				System.exit(0);
			
			default:
				System.out.println("wrong choice! Try again!");
				
		}
		}while (ch!=8);
		
	}
	

}
