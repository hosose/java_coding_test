package coding_test;

import java.util.Arrays;
/*
 * 250814
문제 설명
A도둑과 B도둑이 팀을 이루어 모든 물건을 훔치려고 합니다. 단, 각 도둑이 물건을 훔칠 때 남기는 흔적이 누적되면 경찰에 붙잡히기 때문에, 두 도둑 중 누구도 경찰에 붙잡히지 않도록 흔적을 최소화해야 합니다.

물건을 훔칠 때 조건은 아래와 같습니다.

물건 i를 훔칠 때,
A도둑이 훔치면 info[i][0]개의 A에 대한 흔적을 남깁니다.
B도둑이 훔치면 info[i][1]개의 B에 대한 흔적을 남깁니다.
각 물건에 대해 A도둑과 B도둑이 남기는 흔적의 개수는 1 이상 3 이하입니다.
경찰에 붙잡히는 조건은 아래와 같습니다.

A도둑은 자신이 남긴 흔적의 누적 개수가 n개 이상이면 경찰에 붙잡힙니다.
B도둑은 자신이 남긴 흔적의 누적 개수가 m개 이상이면 경찰에 붙잡힙니다.
각 물건을 훔칠 때 생기는 흔적에 대한 정보를 담은 2차원 정수 배열 info, A도둑이 경찰에 붙잡히는 최소 흔적 개수를 나타내는 정수 n, B도둑이 경찰에 붙잡히는 최소 흔적 개수를 나타내는 정수 m이 매개변수로 주어집니다. 두 도둑 모두 경찰에 붙잡히지 않도록 모든 물건을 훔쳤을 때, A도둑이 남긴 흔적의 누적 개수의 최솟값을 return 하도록 solution 함수를 완성해 주세요. 만약 어떠한 방법으로도 두 도둑 모두 경찰에 붙잡히지 않게 할 수 없다면 -1을 return해 주세요.

*/
public class CodingTest {
	public static void main(String[] args) {
		int[][] info = { { 3, 2 }, { 2, 1 }, { 2, 1 } };
		int n = 4;
		int m = 3;
		int result = solution(info, n, m);
		System.out.println(result);
	}

	public static int solution(int[][] info, int n, int m) {
        // dp[a_trace] = b_trace: A의 흔적이 a_trace일 때, B의 최소 흔적
        int[] dp = new int[n];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0; // 초기 상태: A, B 흔적 모두 0

        for (int[] item : info) {
            int aTrace = item[0];
            int bTrace = item[1];
            
            int[] newDp = new int[n];
            Arrays.fill(newDp, Integer.MAX_VALUE);

            for (int i = 0; i < n; i++) {
                if (dp[i] == Integer.MAX_VALUE) continue;

                // 1. 현재 물건을 A에게 할당하는 경우
                if (i + aTrace < n) {
                    newDp[i + aTrace] = Math.min(newDp[i + aTrace], dp[i]);
                }

                // 2. 현재 물건을 B에게 할당하는 경우
                if (dp[i] + bTrace < m) {
                    newDp[i] = Math.min(newDp[i], dp[i] + bTrace);
                }
            }
            dp = newDp;
        }

        int minA_trace = Integer.MAX_VALUE;
        for (int a_trace = 0; a_trace < n; a_trace++) {
            if (dp[a_trace] < m) { // B도 잡히지 않는 경우
                minA_trace = Math.min(minA_trace, a_trace);
            }
        }

        return (minA_trace == Integer.MAX_VALUE) ? -1 : minA_trace;
	}
}
