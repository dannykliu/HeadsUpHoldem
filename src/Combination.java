// The algorithm is from Applied Combinatorics, by Alan Tucker.
// Based on code from koders.com

public class Combination {
  private int _n, _r;
  private int[] _index;
  private boolean _hasNext = true;
    
  public Combination(int n, int r) 
  {
    _n = n;
    _r = r;
    _index = new int[r];
    for (int i = 0; i<r; i++) 
    {
    	_index[i] = i;
    }
  }

  public boolean hasNext() 
  {
	  return _hasNext; 
  }

  // Based on code from KodersCode:
  // The algorithm is from Applied Combinatorics, by Alan Tucker.
  // Move the index forward a notch. The algorithm finds the rightmost
  // index element that can be incremented, increments it, and then 
  // changes the elements to the right to each be 1 plus the element on their left. 
  //
  // For example, if an index of 5 things taken 3 at a time is at {0 3 4}, only the 0 can
  // be incremented without running out of room. The next index is {1, 1+1, 1+2) or
  // {1, 2, 3}. This will be followed by {1, 2, 4}, {1, 3, 4}, and {2, 3, 4}.
    
  private void moveIndex() {
    int i = rightmostIndexBelowMax();
    if (i >= 0) 
    {
      _index[i] = _index[i]+1; 
      for (int j = i+1; j<_r; j++)
      {
        _index[j] = _index[j-1] + 1;
      }
    }
    else 
    {
    	_hasNext = false;
    }    
  }
 
  //returns the current combination
  public int[] next() {
    if (!_hasNext)
    {
    	return null;
    }
    int[] result = new int[_r];
    
    for (int i=0; i<_r; i++) 
    {
    	result[i] = _index[i];
    }
    moveIndex();
    return result;
  }

   // return int, the index which can be bumped up.
  private int rightmostIndexBelowMax() {
    for (int i = _r-1; i>=0; i--)
    {
        if (_index[i] < _n - _r + i) 
        {
        	return i;
        }
    }
    return -1;
  }
}
