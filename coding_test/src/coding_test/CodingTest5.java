package coding_test;

/*
 * 250818
문제 설명
당신은 온라인 게임을 운영하고 있습니다. 같은 시간대에 게임을 이용하는 사람이 m명 늘어날 때마다 서버 1대가 추가로 필요합니다. 
어느 시간대의 이용자가 m명 미만이라면, 서버 증설이 필요하지 않습니다. 
어느 시간대의 이용자가 n x m명 이상 (n + 1) x m명 미만이라면 최소 n대의 증설된 서버가 운영 중이어야 합니다. 
한 번 증설한 서버는 k시간 동안 운영하고 그 이후에는 반납합니다. 예를 들어, k = 5 일 때 10시에 증설한 서버는 10 ~ 15시에만 운영됩니다.

하루 동안 모든 게임 이용자가 게임을 하기 위해 서버를 최소 몇 번 증설해야 하는지 알고 싶습니다. 
같은 시간대에 서버를 x대 증설했다면 해당 시간대의 증설 횟수는 x회입니다.

다음은 m = 3, k = 5 일 때의 시간대별 증설된 서버의 수와 증설 횟수 예시입니다.
1. 플레이어가 m보다 많을 때 서버 증설
2. 이미 증설되어있는 서버가 있으면 증설을 덜해도 됨
*/
public class CodingTest5 {
	public static void main(String[] args) {
		int[] players = { 0, 0, 0, 10, 0, 12, 0, 15, 0, 1, 0, 1, 0, 0, 0, 5, 0, 0, 11, 0, 8, 0, 0, 0 };
		int m = 5;
		int k = 1;
		System.out.println(solution(players, m, k));
	}

	public static int solution(int[] players, int m, int k) {
		int answer = 0;
		int[] servers = new int[players.length + k];

		for (int i = 0; i < players.length; i++) {

			if (players[i] >= m) {
				if (servers[i] > 0) {
					if (players[i] / m > servers[i]) {
						int addServer = players[i] / m - servers[i];
						answer += addServer;
						for (int j = i; j < i + k; j++) {
							servers[j] += addServer;
						}
					}
				} else {
					answer += players[i] / m;
					for (int j = i; j < i + k; j++) {
						servers[j] = players[i] / m;
					}
				}
			}
		}
		return answer;
	}
}
