import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HangMan {

    public static void main(String[] args) {

        System.out.println("--------- HangMan Game ---------");

        List<String> words = new ArrayList<String>(); // 영어 단어를 저장할 리스트

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("words.txt"))); // 파일 스트림 열기
            String line = null;
            while ((line = br.readLine()) != null) { // 파일 한줄 읽기
                line = line.trim(); // 문자열 앞뒤의 공백을 제거한다.
                if (line.length() > 1) { // 2글자 이상인 단어를
                    words.add(line); // 영어 단어 리스트에 추가한다.
                }
            }
        } catch (IOException e) {
            System.out.println("파일을 읽을 수 없습니다 : " + new File("words.txt").getAbsolutePath());
            return; // 프로그램 종료
        } finally {
            if (br != null) {
                try {
                    br.close(); // 파일 스트림 닫기
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (words.size() == 0) { // 읽어들인 단어가 0개인 경우
            System.out.println("words.txt파일에 단어가 없습니다.");
            return;
        }

        int correctCount = 0; // 정답 개수
        int wrongCount = 0; // 오답 개수

        Random random = new Random(); // 랜덤 객체 생성
        Scanner in = new Scanner(System.in); // 키보드 입력 객체 생성
        try {

            while (correctCount < 10 && wrongCount < 3) { // 정답이 10개 미만이고 오답이 3개 미만일 때 까지 반복
                String word = words.get(random.nextInt(words.size())); // 단어중 한개를 뽑음

                StringBuffer hiddenWord = new StringBuffer(word); // 문자열 버퍼로 변경

                int hideLength = 1; // 숨길 문자 개수. 단어 개수의 30% 미만이면서 최소 1개이상.
                // 단어 길이가 6글자 이하이면 1글자만 숨긴다. (6글자의 30%이면 1.8이므로) 7글자 이상이면 랜덤으로 30% 이하 최소 1개이상 숨긴다.
                int randomBound = (int) (word.length() * 0.3);
                if (randomBound >= 1) {
                    hideLength = random.nextInt(randomBound) + 1;
                }

                int hiddenLength = 0; // 숨긴 문자 개수
                while (hiddenLength != hideLength) { // 숨길 문자 개수와 숨긴 문자 개수와 같아질 때 까지 반복한다.
                    int index = random.nextInt(word.length()); // 숨길 문자의 인덱스를 랜덤으로 뽑는다.
                    if (hiddenWord.charAt(index) != '-') { // 아직 숨겨지지 않은 문자만 찾는다.
                        hiddenWord.replace(index, index + 1, "-"); // 해당 인덱스의 문자를 숨김으로 변경한다.
                        hiddenLength++; // 숨긴 문자 개수 카운트를 증가시킨다.
                    }
                }

                // 숨긴 단어를 출력한다.
                System.out.println();
                System.out.println("단어를 맞춰보세요 --> " + hiddenWord.toString());

                boolean correct = false; // 정오답 판별 변수
                int tryCount = 0; // 한 단어에 답한 횟수
                while (tryCount < 5) {
                    tryCount++;
                    System.out.print("입력: ");
                    String input = in.nextLine();
                    if (word.equals(input)) {
                        System.out.println("정답입니다.");
                        correct = true;
                        break;
                    } else {
                        System.out.println("틀렸습니다.");
                    }
                }

                if (correct) {
                    correctCount++;
                } else {
                    System.out.println("5번을 틀렸기 때문에 오답처리합니다.");
                    System.out.println("정답은 " + word + " 이었습니다.");
                    wrongCount++;
                }
            }
        } finally {
            if (in != null) {
                in.close(); // 키보드 입력 객체를 닫는다.
            }
        }

        System.out.println();
        if (correctCount >= 10) {
            System.out.println("승리하였습니다.");
        } else {
            System.out.println("패배하였습니다.");
        }
        System.out.println("정답 개수 : " + correctCount);
        System.out.println("오답 개수 : " + wrongCount);
        System.out.println("--------- 게임을 종료합니다 ---------");

    }

}
