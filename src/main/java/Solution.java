import java.util.*;

public class Solution {
  static class Record {
    final int size, right;
    Record(int size, int right) {
      this.size = size;
      this.right = right;
    }
  }
  public int[] minInterval(int[][] intervals, int[] queries) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    int qLen = queries.length;
    int nLen = intervals.length;
    int[] copyQueries = Arrays.copyOf(queries, qLen);
    Arrays.sort(copyQueries);
    HashMap<Integer, Integer> hash = new HashMap<>();
    PriorityQueue<Record> pq = new PriorityQueue<>((a, b) -> (a.size == b.size) ? a.right - b.right: a.size - b.size);
    int[] result = new int[qLen];
    int pos = 0;
    for (int q : copyQueries) {
      while(pos < nLen && intervals[pos][0] <= q) {
        pq.add(new Record(intervals[pos][1] - intervals[pos][0]+1, intervals[pos][1]));
        pos++;
      }
      while(pq.size()>0 && pq.peek().right < q) {
        pq.poll();
      }
      if (pq.isEmpty()) {
        hash.put(q, -1);
      } else {
        hash.put(q, pq.peek().size);
      }
    }
    for (int idx = 0; idx < qLen; idx++) {
      result[idx] = hash.get(queries[idx]);
    }
    return result;
  }
}
