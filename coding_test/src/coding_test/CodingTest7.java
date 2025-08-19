package coding_test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
 * 250819
문제 설명
A 회사의 물류창고에는 알파벳 대문자로 종류를 구분하는 컨테이너가 세로로 n 줄, 가로로 m줄 총 n x m개 놓여 있습니다. 
특정 종류 컨테이너의 출고 요청이 들어올 때마다 지게차로 창고에서 접근이 가능한 해당 종류의 컨테이너를 모두 꺼냅니다. 
접근이 가능한 컨테이너란 4면 중 적어도 1면이 창고 외부와 연결된 컨테이너를 말합니다.

최근 이 물류 창고에서 창고 외부와 연결되지 않은 컨테이너도 꺼낼 수 있도록 크레인을 도입했습니다. 크레인을 사용하면 요청된 종류의 모든 컨테이너를 꺼냅니다.

위 그림처럼 세로로 4줄, 가로로 5줄이 놓인 창고를 예로 들어보겠습니다. 이때 "A", "BB", "A" 순서대로 해당 종류의 컨테이너 출고 요청이 들어왔다고 가정하겠습니다.
 “A”처럼 알파벳 하나로만 출고 요청이 들어올 경우 지게차를 사용해 출고 요청이 들어온 순간 접근 가능한 컨테이너를 꺼냅니다. 
 "BB"처럼 같은 알파벳이 두 번 반복된 경우는 크레인을 사용해 요청된 종류의 모든 컨테이너를 꺼냅니다.

위 그림처럼 컨테이너가 꺼내져 3번의 출고 요청 이후 남은 컨테이너는 11개입니다. 
두 번째 요청은 크레인을 활용해 모든 B 컨테이너를 꺼냈음을 유의해 주세요. 
세 번째 요청이 들어왔을 때 2행 2열의 A 컨테이너만 접근이 가능하고 2행 3열의 A 컨테이너는 접근이 불가능했음을 유의해 주세요.

처음 물류창고에 놓인 컨테이너의 정보를 담은 1차원 문자열 배열 storage와 출고할 컨테이너의 종류와 출고방법을 요청 순서대로 담은 
1차원 문자열 배열 requests가 매개변수로 주어집니다. 
이때 모든 요청을 순서대로 완료한 후 남은 컨테이너의 수를 return 하도록 solution 함수를 완성해 주세요.

1. 빈공간 표현하는 법?
2. 지게차가 지나갈 수 있는지 없는지 어떻게 판단?
*/
public class CodingTest7 {
	public static void main(String[] args) {
        String[] storage = {"AZWQY", "CAABX", "BBDDA", "ACACA"};
        String[] requests = {"A", "BB", "A"};
        System.out.println("최종 남은 컨테이너 수: " + solution(storage, requests));
    }

    public static int solution(String[] storage, String[] requests) {
        int n = storage.length;
        int m = storage[0].length();
        char emptySlot = ' ';

        char[][] warehouse = new char[n][m];
        for (int i = 0; i < n; i++) {
            warehouse[i] = storage[i].toCharArray();
        }

        printWarehouse(warehouse, n, m);

        for (String req : requests) {
            char containerType = req.charAt(0);

            if (req.length() == 1) { // 지게차 출고
                List<int[]> targets = new ArrayList<>();
                
                // 창고의 모든 컨테이너를 순회하며 접근 가능한지 확인
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < m; j++) {
                        if (warehouse[i][j] == containerType) {
                            if (isAccessible(i, j, n, m, warehouse, emptySlot)) {
                                targets.add(new int[]{i, j});
                            }
                        }
                    }
                }
                
                for (int[] pos : targets) {
                    warehouse[pos[0]][pos[1]] = emptySlot;
                }
            } else { // 크레인 출고
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < m; j++) {
                        if (warehouse[i][j] == containerType) {
                            warehouse[i][j] = emptySlot;
                        }
                    }
                }
            }
            
            printWarehouse(warehouse, n, m);
        }

        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (warehouse[i][j] != emptySlot) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void printWarehouse(char[][] warehouse, int n, int m) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(warehouse[i][j] + " ");
            }
            System.out.println();
        }
        int currentCount = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (warehouse[i][j] != ' ') {
                    currentCount++;
                }
            }
        }
    }

    private static boolean isAccessible(int r, int c, int n, int m, char[][] warehouse, char emptySlot) {
        // 컨테이너 주변에 빈 공간이 있는지 확인
        int[] dr = {0, 0, 1, -1};
        int[] dc = {1, -1, 0, 0};
        List<int[]> emptyNeighbors = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            // 외부 경계에 인접하면 접근 가능
            if (nr < 0 || nr >= n || nc < 0 || nc >= m) {
                return true;
            }
            // 빈 공간이 주변에 있으면 저장
            if (warehouse[nr][nc] == emptySlot) {
                emptyNeighbors.add(new int[]{nr, nc});
            }
        }

        // 빈 공간이 없으면 접근 불가
        if (emptyNeighbors.isEmpty()) {
            return false;
        }

        // BFS로 빈 공간이 외부로 연결되는지 확인
        boolean[][] visited = new boolean[n][m];
        Queue<int[]> queue = new LinkedList<>();
        for (int[] neighbor : emptyNeighbors) {
            queue.add(neighbor);
            visited[neighbor[0]][neighbor[1]] = true;
        }

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int cr = curr[0];
            int cc = curr[1];

            // 외부 경계에 도달하면 접근 가능
            if (cr == 0 || cr == n - 1 || cc == 0 || cc == m - 1) {
                return true;
            }

            for (int i = 0; i < 4; i++) {
                int nr = cr + dr[i];
                int nc = cc + dc[i];

                if (nr >= 0 && nr < n && nc >= 0 && nc < m && !visited[nr][nc] && warehouse[nr][nc] == emptySlot) {
                    queue.add(new int[]{nr, nc});
                    visited[nr][nc] = true;
                }
            }
        }

        return false;
    }
}
