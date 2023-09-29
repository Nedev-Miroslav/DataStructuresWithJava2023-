package solutions;

import java.util.PriorityQueue;
import java.util.Queue;

public class CookiesProblem {
    public Integer solve(int requiredSweetness, int[] cookiesSweetness) {

        Queue<Integer> cookies = new PriorityQueue<>();

        for (int sweetness : cookiesSweetness) {
             cookies.offer(sweetness);
        }

        int steps = 0;
        int currentMinSweetness = cookies.peek();

            while (currentMinSweetness < requiredSweetness && cookies.size() > 1){
                int leastSweetCookie = cookies.poll();
                int secondSweetCookie = cookies.poll();

                int combinedSweetness = leastSweetCookie + 2 * secondSweetCookie;

                cookies.add(combinedSweetness);

                currentMinSweetness = cookies.peek();
                steps++;
            }



        return currentMinSweetness > requiredSweetness ? steps : -1;
    }
}
