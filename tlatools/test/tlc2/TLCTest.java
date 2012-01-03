package tlc2;

import junit.framework.TestCase;

public class TLCTest extends TestCase {

	public void testHandleParametersAbsoluteInvalid() {
		final TLC tlc = new TLC();
		assertFalse(tlc.handleParameters(new String[] {"-fpmem", "-1", "MC"}));
	}
	
	public void testHandleParametersAbsoluteValid() {
		final TLC tlc = new TLC();
		assertTrue(tlc.handleParameters(new String[] {"-fpmem", "101", "MC"}));
	}
	
	public void testHandleParametersFractionInvalid() {
		final TLC tlc = new TLC();
		assertFalse(tlc.handleParameters(new String[] {"-fpmem", "-0.5", "MC"}));
	}
	
	/**
	 * Allocating to little should result in min default
	 */
	public void testHandleParametersAllocateLowerBound() {
		final TLC tlc = new TLC();
		assertTrue(tlc.handleParameters(new String[] {"-fpmem", "0", "MC"}));
		assertEquals("Allocating to little should result in min default", TLC.MinFpMemSize, tlc.getFpMemSize());
	}
	
	/**
	 * Overallocating should result in max default
	 */
	public void testHandleParametersAllocateUpperBound() {
		final TLC tlc = new TLC();
		assertTrue(tlc.handleParameters(new String[] {"-fpmem", Long.toString(Long.MAX_VALUE), "MC"}));
        final long maxMemory = (long) (Runtime.getRuntime().maxMemory() * 0.75d);
		assertEquals("Overallocating should result in max default (75%)", maxMemory, tlc.getFpMemSize());
	}
	
	/**
	 * .5 is valid
	 */
	public void testHandleParametersAllocateHalf() {
		final TLC tlc = new TLC();
		assertTrue(tlc.handleParameters(new String[] {"-fpmem", ".5", "MC"}));
        final long maxMemory = (long) (Runtime.getRuntime().maxMemory() * 0.50d);
		assertEquals("Overallocating should result in max default (50%)", maxMemory, tlc.getFpMemSize());
	}
	
	/**
	 * .99 is valid
	 */
	public void testHandleParametersAllocate90() {
		final TLC tlc = new TLC();
		assertTrue(tlc.handleParameters(new String[] {"-fpmem", ".99", "MC"}));
        final long maxMemory = (long) (Runtime.getRuntime().maxMemory() * 0.99d);
		assertEquals("Overallocating should result in max default (99%)", maxMemory, tlc.getFpMemSize());
	}
}
