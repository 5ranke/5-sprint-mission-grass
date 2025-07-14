package run;

import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.jcf.*;

import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/*
이메일/비밀번호로 로그인 (이메일은 final)
mainMenu
5. 다이렉트 메시지 (1vs1) 채팅방 -> 일단 제외

startMenu
1. 회원가입
2. 로그인
3. 회원조회 (이메일)
9. 종료

mainMenu
1. 마이페이지 - userMenu
2. 채널 입장 - channelMenu
3. 채널 만들기
4. 전체 채널 조회
9. 로그아웃

userMenu (유저 정보 보여줌)
1. 이름 변경
2. 비밀번호 변경
9. 뒤로 가기

channelMenu
1. 메시지 작성
2. 메시지 삭제
3. 메시지 검색
4. 전체 메시지 조회
5. 멤버 추가하기
6. 멤버 삭제하기
7. 멤버 검색
8. 전체 멤버 조회
9. 뒤로가기
 */

public class JavaApplication {
    private final Scanner sc = new Scanner(System.in);
    private final UserService userService;
    private final ChannelService channelService;
    private final MessageService messageService;

    public JavaApplication(UserService us, ChannelService cs, MessageService ms) {
        this.userService = us;
        this.channelService = cs;
        this.messageService = ms;
    }

    public static void main(String[] args) {
        UserService us = new JCFUserService();
        ChannelService cs = new JCFChannelService();
        MessageService ms = new JCFMessageService();

        new JavaApplication(us, cs, ms).startMenu();
    }

    private void startMenu() {
        System.out.println("========== Test View ==========");

        while (true) {
            System.out.println("");
            System.out.println("=====***** Start Menu *****=====");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 회원 조회 (이메일)");
            System.out.println("9. 종료");
            System.out.print("메뉴 번호 입력 : ");

            int menuNum = Integer.parseInt(sc.nextLine());
            switch (menuNum) {
                case 1: doSignUp(); break;
                case 2: doLogin(); break;
                case 3: findUserByEmail(); break;
                case 9:
                    System.out.println("프로그램 종료.");
                    return;
                default:
                    System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }

    private void mainMenu(){
        while (true) {
            System.out.println("");
            System.out.println("=====***** Main Menu *****=====");
            System.out.println("1. 마이페이지");
            System.out.println("2. 채널 입장");
            System.out.println("3. 채널 만들기");
            System.out.println("4. 전체 채널 조회");
            System.out.println("9. 로그아웃");
            System.out.print("메뉴 번호 입력 : ");

            int menuNum = Integer.parseInt(sc.nextLine());
            switch (menuNum) {
                case 1: userMenu(); break;
                case 2: channelMenu(); break;
                case 3: createChannel(); break;
                case 4: listAllChannels(); break;
                case 9:
                    System.out.println("로그아웃되었습니다.");
                    return;
                default:
                    System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }

    private void userMenu() {
        // 현재 User 정보 출력
        while (true) {
            System.out.println("*** UserMenu ***");
            System.out.println("1. 이름 변경");
            System.out.println("2. 비밀번호 변경");
            System.out.println("9. 뒤로 가기");
            System.out.print("메뉴 번호 선택 : ");

            int menuNum = Integer.parseInt(sc.nextLine());
            switch (menuNum) {
                case 1: updateUserName(); break;
                case 2: updateUserPassword(); break;
                case 9: return;
                default:
                    System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }

    private void channelMenu() {
        // 현재 Channel 정보 출력
        while (true) {
            System.out.println("*** ChannelMenu ***");
            System.out.println("1. 메시지 작성");
            System.out.println("2. 메시지 삭제");
            System.out.println("3. 메시지 검색");
            System.out.println("4. 전체 메시지 조회");
            System.out.println("5. 멤버 추가하기");
            System.out.println("6. 멤버 삭제하기");
            System.out.println("7. 멤버 검색");
            System.out.println("8. 전체 멤버 조회");
            System.out.println("9. 뒤로가기");
            System.out.print("메뉴 번호 선택 : ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": writeMessage(); break;
                case "2": deleteMessage(); break;
                case "3": searchMessage(); break;
                case "4": listAllMessages(); break;
                case "5": addChannelMember(); break;
                case "6": removeChannelMember(); break;
                case "7": searchChannelMember(); break;
                case "8": listAllChannelMembers(); break;
                case "9": return;
                default:
                    System.out.println("잘못된 선택입니다.");
            }
        }
    }

    // === START MENU HANDLERS ===
    private void doSignUp() {
        // TODO: 회원가입 로직
    }
    private void doLogin() {
        // TODO: 로그인 로직
        // 로그인 성공 시 mainMenu() 호출
    }
    private void findUserByEmail() {
        // TODO: 이메일로 회원 조회
    }

    // === MAIN MENU HANDLERS ===
    private void createChannel() {
        // TODO: 채널 생성
    }
    private void listAllChannels() {
        // TODO: 전체 채널 조회
    }

    // === USER MENU HANDLERS ===
    private void updateUserName() {
        // TODO: 이름 변경
    }
    private void updateUserPassword() {
        // TODO: 비밀번호 변경
    }

    // === CHANNEL MENU HANDLERS ===
    private void writeMessage() {
        // TODO: 메시지 작성
    }
    private void deleteMessage() {
        // TODO: 메시지 삭제
    }
    private void searchMessage() {
        // TODO: 메시지 검색
    }
    private void listAllMessages() {
        // TODO: 전체 메시지 조회
    }
    private void addChannelMember() {
        // TODO: 멤버 추가
    }
    private void removeChannelMember() {
        // TODO: 멤버 삭제
    }
    private void searchChannelMember() {
        // TODO: 멤버 검색
    }
    private void listAllChannelMembers() {
        // TODO: 전체 멤버 조회
    }
}