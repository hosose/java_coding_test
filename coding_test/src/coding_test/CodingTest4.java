package coding_test;

/*
 * 250817
문제 설명
당신은 동영상 재생기를 만들고 있습니다. 당신의 동영상 재생기는 10초 전으로 이동, 10초 후로 이동, 오프닝 건너뛰기 3가지 기능을 지원합니다. 각 기능이 수행하는 작업은 다음과 같습니다.

10초 전으로 이동: 사용자가 "prev" 명령을 입력할 경우 동영상의 재생 위치를 현재 위치에서 10초 전으로 이동합니다. 현재 위치가 10초 미만인 경우 영상의 처음 위치로 이동합니다. 영상의 처음 위치는 0분 0초입니다.
10초 후로 이동: 사용자가 "next" 명령을 입력할 경우 동영상의 재생 위치를 현재 위치에서 10초 후로 이동합니다. 동영상의 남은 시간이 10초 미만일 경우 영상의 마지막 위치로 이동합니다. 영상의 마지막 위치는 동영상의 길이와 같습니다.
오프닝 건너뛰기: 현재 재생 위치가 오프닝 구간(op_start ≤ 현재 재생 위치 ≤ op_end)인 경우 자동으로 오프닝이 끝나는 위치로 이동합니다.
동영상의 길이를 나타내는 문자열 video_len, 기능이 수행되기 직전의 재생위치를 나타내는 문자열 pos, 오프닝 시작 시각을 나타내는 문자열 op_start, 오프닝이 끝나는 시각을 나타내는 문자열 op_end, 사용자의 입력을 나타내는 1차원 문자열 배열 commands가 매개변수로 주어집니다. 이때 사용자의 입력이 모두 끝난 후 동영상의 위치를 "mm:ss" 형식으로 return 하도록 solution 함수를 완성해 주세요.

내 풀이
1.오프닝 건너 뛰기
2. 이전 다음 함수 작성 
3. 비디오 길이 확인 00:00 이거나 비디오 길이 초과 면 비디오 길이로 리턴 
*/
public class CodingTest4 {
	public static void main(String[] args) {
		String video_len = "30:00";
		String pos = "00:08";
		String op_start = "00:00";
		String op_end = "00:05";
		String[] commands = { "prev" };
		String result = solution(video_len, pos, op_start, op_end, commands);
		System.out.println(result);
	}

	public static String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
		int posSec = convertToSeconds(pos);
		int opStartSec = convertToSeconds(op_start);
		int opEndSec = convertToSeconds(op_end);
		int videoLenSec = convertToSeconds(video_len);

		// 오프닝 건너뛰기
		posSec = clampPosition(posSec, videoLenSec);
		posSec = skipOpening(posSec, opStartSec, opEndSec);

		for (int i = 0; i < commands.length; i++) {
			if ("prev".equals(commands[i])) {
				posSec -= 10;
			} else {
				posSec += 10;
			}
			posSec = clampPosition(posSec, videoLenSec);
			posSec = skipOpening(posSec, opStartSec, opEndSec);
		}

		posSec = clampPosition(posSec, videoLenSec);
		posSec = skipOpening(posSec, opStartSec, opEndSec);

		return convertToMinSec(posSec);
	}

	public static int convertToSeconds(String time) {
		String[] parts = time.split(":");
		int minutes = Integer.parseInt(parts[0]);
		int seconds = Integer.parseInt(parts[1]);
		return minutes * 60 + seconds;
	}

	public static String convertToMinSec(int time) {
		int minutes = time / 60;
		int seconds = time % 60;

		return String.format("%02d:%02d", minutes, seconds);
	}

	// 현재 위치가 오프닝 구간에 있으면 오프닝 끝으로 이동시킵니다.
	private static int skipOpening(int posSec, int opStartSec, int opEndSec) {
		if (posSec >= opStartSec && posSec <= opEndSec) {
			return opEndSec;
		}
		return posSec;
	}
	
	// 현재 위치가 0보다 작거나 비디오 길이보다 크면 0 또는 비디오 길이로 보정합니다.
	private static int clampPosition(int posSec, int videoLenSec) {
		if (posSec < 0) {
			return 0;
		}
		if (posSec > videoLenSec) {
			return videoLenSec;
		}
		return posSec;
	}
}
