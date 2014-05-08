package Model;
//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : PurpleMagicStone.java
//  @ Date : 2014.03.20.
//  @ Author : 
//
//



/**
 * Lila varazsko.
 */
public class PurpleMagicStone extends MagicStone
{
	/**
	 * Konstruktor. Peldanyositashoz ajanlott ezt hasznalni.
	 * Default parameterekkel jon letre a lila ko. 
	 */
	public PurpleMagicStone() {
		this(
				"purple",	// A varazsko neve 
				5,		// Power bonus
				0,		// Speed bonus
				0,		// Range bonus
				0.05			// Slowrate bonus
				);
	}

	/**
	 * Konstruktor.
	 * @param n  A varazsko neve.
	 * @param fp  Egy torony sebzesi erejenek novelese.
	 * @param as  Egy torony tamadasi sebessegenek novelese.
	 * @param r  Egy torony lotavolsaganak novelese.
	 * @param sr  Egy akadaly lassitasi szorzojanak novelese.
	 */
	public PurpleMagicStone(String n, int fp, int as, int r, double sr) {
		super(n, fp, as, r, sr);
	}
}
