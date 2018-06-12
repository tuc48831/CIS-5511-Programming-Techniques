# Maximum Subsequence Sum and Product

This is my code for finding a maximum subsequence sum and product for a given sequence of integers.

# Description of Problem

The maximum subsequence sum (MSS) for a given sequence of numbers describes the largest amount producable by selecting the continuous subsequence that sums to the largest amount. For an example sequence of: [2, 4, -3, 5, -11, 3, 2, 1], the subsequence that adds up to the largest amount is the first 4 items [2, 4, -3, 5] for a total of 8.

The maximum subsequence product (MSP) is a variation of the problem, that instead looks for the largest product among the possible subsequences. This version of the problem is slightly more complications when negative numbers and fractional numbers are in the sequence.

Finding important subsequences of information can have valuable implications. A few modern fields where it is used include genomic sequence, computer vision, and data mining.

# Description of Solution

There are many valid ways to solve the problem. My solution to the MSS works on a 1 dimensional array in linear time. This solution is both efficient and straightforward. One only needs to scan the array from left to right while keeping 2 sums, the maximum seen and the current sequence. One adds the next number in the sequence to the current sum, if the current sum is larger than the maximum, then we remember the next maximum. If the sequence ever drops below 0, we can restart on the next positive number we see. This is intuitive as if there was a large sequence after a negative number, the sequence would be large if we did not include the negative.

The maximum subsequence product requires more overhead to solve. My solution is based on a divide and conquer algorithm similar to merge sort. The array is broken into left and right halves and then the algorithm is called recursively on the smaller halves (until the base case of 1 element where it returns itself.) There are a lot of conditional statements to catch negatives and small subsequences of negatives.

# Results

The solutions correctly solve the problem. This can be verified by running the code on the text examples and checking the output against the answers commented into the code.
