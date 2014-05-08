package Model;
//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : CyanMagicStone.java
//  @ Date : 2014.03.20.
//  @ Author : 
//
//




/**
 * Cian varazsko.
 */
public class CyanMagicStone extends MagicStone
{
	/**
	 * Konstruktor. Peldanyositashoz ajanlott ezt hasznalni.
	 * Default parameterekkel jon letre a cian ko. 
	 */
	public CyanMagicStone() {
		this(
				"cyan",	// A varazsko neve 
				0,	// Power bonus
				0,	// Speed bonus
				1,	// Range bonus
				0.02		// Slowrate bonus
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
	public CyanMagicStone(String n, int fp, int as, int r, double sr) {
		super(n, fp, as, r, sr);
	}
}
