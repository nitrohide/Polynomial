package poly;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements evaluate, add and multiply for polynomials.
 * 
 * @author runb-cs112
 *
 */
public class Polynomial {
	
	/**
	 * Reads a polynomial from an input stream (file or keyboard). The storage format
	 * of the polynomial is:
	 * <pre>
	 *     <coeff> <degree>
	 *     <coeff> <degree>
	 *     ...
	 *     <coeff> <degree>
	 * </pre>
	 * with the guarantee that degrees will be in descending order. For example:
	 * <pre>
	 *      4 5
	 *     -2 3
	 *      2 1
	 *      3 0
	 * </pre>
	 * which represents the polynomial:
	 * <pre>
	 *      4*x^5 - 2*x^3 + 2*x + 3 
	 * </pre>
	 * 
	 * @param sc Scanner from which a polynomial is to be read
	 * @throws IOException If there is any input error in reading the polynomial
	 * @return The polynomial linked list (front node) constructed from coefficients and
	 *         degrees read from scanner
	 */
	public static Node read(Scanner sc) 
	throws IOException {
		Node poly = null;
		while (sc.hasNextLine()) {
			Scanner scLine = new Scanner(sc.nextLine());
			poly = new Node(scLine.nextFloat(), scLine.nextInt(), poly);
			scLine.close();
		}
		return poly;
	}
	
	/**
	 * Returns the sum of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list
	 * @return A new polynomial which is the sum of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node add(Node poly1, Node poly2) {
		Node ptr1=poly1;
		Node ptr2=poly2;
		Node polyadded=null;
		if (ptr1==null) {
			return poly2;
		}
		if (ptr2==null) {
			return poly1;
		}
		while (ptr1!=null || ptr2!=null) {
			if (ptr1!=null && ptr2==null) {
				Node temp=new Node(ptr1.term.coeff,ptr1.term.degree,polyadded);
				polyadded=temp;
				ptr1=ptr1.next;
				
			}
			else if (ptr2!=null && ptr1==null) {
				Node temp=new Node(ptr2.term.coeff,ptr2.term.degree,polyadded);
				polyadded=temp;
				ptr2=ptr2.next;
			}
			else if (ptr1.term.degree>ptr2.term.degree) {
				Node temp=new Node(ptr2.term.coeff,ptr2.term.degree,polyadded);
				polyadded=temp;
				ptr2=ptr2.next;
			}
			else if (ptr2.term.degree>ptr1.term.degree) {
				Node temp=new Node(ptr1.term.coeff,ptr1.term.degree,polyadded);
				polyadded=temp;
				ptr1=ptr1.next;
			}
			else if (ptr1.term.degree==ptr2.term.degree) {
				if (ptr1.term.coeff+ptr2.term.coeff!=0) {
				Node temp=new Node(ptr1.term.coeff+ptr2.term.coeff,ptr1.term.degree,polyadded);
				polyadded=temp;
				ptr1=ptr1.next;
				ptr2=ptr2.next;
				}
				else {
					ptr1=ptr1.next;
					ptr2=ptr2.next;
				}
			}
		}
		return polyadded;
	}
	
	/**
	 * Returns the product of two polynomials - DOES NOT change either of the input polynomials.
	 * The returned polynomial MUST have all new nodes. In other words, none of the nodes
	 * of the input polynomials can be in the result.
	 * 
	 * @param poly1 First input polynomial (front of polynomial linked list)
	 * @param poly2 Second input polynomial (front of polynomial linked list)
	 * @return A new polynomial which is the product of the input polynomials - the returned node
	 *         is the front of the result polynomial
	 */
	public static Node multiply(Node poly1, Node poly2) {
		Node polymultiplied=null;
		Node polysimplified=null;
		int i=0;
		if (poly1 ==null || poly2==null) {
			return null;
		}
		for (Node ptr1=poly1;ptr1!=null;ptr1=ptr1.next) {
			for (Node ptr2=poly2;ptr2!=null;ptr2=ptr2.next) {
				Node temp=new Node(ptr1.term.coeff*ptr2.term.coeff,ptr1.term.degree+ptr2.term.degree,polymultiplied);
				polymultiplied=temp;
				if ((ptr1.term.degree+ptr2.term.degree)>i) {
					i=ptr1.term.degree+ptr2.term.degree;
				}
			}
		}
		float coeff=0;
		for (int j=0;i>=j;j++){
			coeff=0;
			for (Node ptr3=polymultiplied;ptr3!=null;ptr3=ptr3.next) {
				if (ptr3.term.degree==j) {
					coeff+=ptr3.term.coeff;
				}
			}
			if (coeff!=0) {
			Node temp=new Node(coeff,j,polysimplified);
			polysimplified=temp;
			}
		}
		return polysimplified;
	}
		
	/**
	 * Evaluates a polynomial at a given value.
	 * 
	 * @param poly Polynomial (front of linked list) to be evaluated
	 * @param x Value at which evaluation is to be done
	 * @return Value of polynomial p at x
	 */
	public static float evaluate(Node poly, float x) {
		float result=0;
		for (Node ptr=poly;ptr!=null;ptr=ptr.next) {
			result+=((Math.pow(x, ptr.term.degree))*ptr.term.coeff);
		}
		return result;
	}
	
	/**
	 * Returns string representation of a polynomial
	 * 
	 * @param poly Polynomial (front of linked list)
	 * @return String representation, in descending order of degrees
	 */
	public static String toString(Node poly) {
		if (poly == null) {
			return "0";
		} 
		
		String retval = poly.term.toString();
		for (Node current = poly.next ; current != null ;
		current = current.next) {
			retval = current.term.toString() + " + " + retval;
		}
		return retval;
	}	
}
