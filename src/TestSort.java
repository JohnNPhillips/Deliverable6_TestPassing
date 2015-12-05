import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class TestSort
{
	Random rng;

	@Before
	public void setUp() throws Exception
	{
		rng = new Random();
	}

	@Test
	public void testArraySort()
	{
		for (int i = 0; i < 1000; i++)
		{
			// Generate random array
			int[] arr = generateRandomArray();

			// Copy and sort
			int[] sorted = arr.clone();
			Arrays.sort(sorted);

			// Output array must be same size as input array
			assertEquals(arr.length, sorted.length);

			// Same elements in both arrays
			assertTrue(haveSameElements(arr, sorted));

			// Check that values are always increasing
			assertTrue(elementsIncreasing(sorted));

			// Check that running sort again won't change the elements (idempotent)
			int[] cloned = sorted.clone();
			Arrays.sort(cloned);
			assertArrayEquals(sorted, cloned);

			// Check that sorting the original again will yield the same output (pure)
			cloned = arr.clone();
			Arrays.sort(cloned);
			assertArrayEquals(sorted, cloned);
		}
	}

	/*
	 * Check that elements are in non-decreasing order such that for each
	 * element:
	 * arr[i - 1] <= arr[i] <= arr[i + 1]
	 */
	private boolean elementsIncreasing(int[] arr)
	{
		for (int i = 1; i < arr.length; i++)
		{
			// Each element can be equal to, but not less than the previous
			if (arr[i] < arr[i - 1])
			{
				return false;
			}
		}

		// All elements more than or equal to the previous
		return true;
	}

	/*
	 * Check that two arrays contain the same elements. Both arrays
	 * must have the same number of elements.
	 */
	private boolean haveSameElements(int[] a, int[] b)
	{
		// Map from Number => number of occurrences
		Map<Integer, Integer> numChars = new HashMap<>();
		for (int num : a)
		{
			// Get current count of number, and increase by 1
			Integer count = numChars.get(num);
			if (count != null)
			{
				numChars.put(num, count + 1);
			}
			else
			{
				numChars.put(num, 1);
			}
		}

		// Subtract out numbers in the second array
		for (int num : b)
		{
			Integer count = numChars.get(num);
			if (count >= 1)
			{
				numChars.put(num, count - 1);
			}
			else
			{
				// Number doesn't exist in first array
				return false;
			}
		}

		return true;
	}
	
	@Test
	public void testSomethingElse()
	{
		assertEquals("asdf", "asdf");
	}

	/*
	 * Generate an array of random integers of random length
	 */
	private int[] generateRandomArray()
	{
		// 0 <= length < 10,000
		int length = rng.nextInt(10000);

		// Fill with random integers, MIN_INT <= val <= MAX_INT
		int[] arr = new int[length];
		for (int i = 0; i < length; i++)
		{
			arr[i] = rng.nextInt();
		}

		return arr;
	}
}
