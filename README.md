# GaloisFieldArithmatic
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
