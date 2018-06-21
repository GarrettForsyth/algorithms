# Compute Inversions

Given a filename containing an array with each entry on its own line
and the number of entries in the file, this class will compute the
number of inversions using a divide and conquer technique. 

It piggy backs merge sort and is expected to run at O(nlgn)
This implementation of merge sort uses a copy of itself, so it takes
up extra space.

Also, the the data type used to track the number of inversions 
was BigInteger instead of int, to handle larger data sets. BigInteger
seems slower than using int, so this will add to the run time.