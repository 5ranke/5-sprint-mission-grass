package run;

import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.jcf.*;

import java.util.*;

public class JavaApplication {
    private final Scanner sc = new Scanner(System.in);
    private final UserService us;
    private final ChannelService cs;
    private final MessageService ms;

    public JavaApplication(UserService us, ChannelService cs, MessageService ms) {
        this.us = us;
        this.cs = cs;
        this.ms = ms;
    }
    public static void main(String[] args) {
        UserService us = new JCFUserService();
        ChannelService cs = new JCFChannelService();
        MessageService ms = new JCFMessageService();

        System.out.println("========== Test View ==========");
        new JavaApplication(us, cs, ms).startMenu();
    }

    private void startMenu() {
        while (true) {
            System.out.println("\n=====***** Start Menu *****=====");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 회원 조회");
            System.out.println("9. 종료");
            System.out.print("메뉴 번호 입력 : ");

            int menuNum = Integer.parseInt(sc.nextLine());
            switch (menuNum) {
                case 1: doSignUp();break;
                case 2: doLogin();break;
                case 3: findUser();break;
                case 9: System.out.println("프로그램 종료.");return;
                default: System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }

    private void mainMenu() {
        while (true) {
            System.out.println("\n=====***** Main Menu *****=====");
            System.out.println("1. 마이페이지");
            System.out.println("2. 채널 입장");
            System.out.println("3. 채널 생성");
            System.out.println("4. 전체 채널 조회");
            System.out.println("9. 로그아웃");
            System.out.print("메뉴 번호 입력 : ");

            int menuNum = Integer.parseInt(sc.nextLine());
            switch (menuNum) {
                case 1: userMenu();break;
                case 2: channelMenu();break;
                case 3: createChannel();break;
                case 4: listAllChannels();break;
                case 9: loginUser = null; System.out.println("로그아웃되었습니다.");return;
                default: System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }

    private void userMenu() {
        // 현재 User 정보 출력
        while (true) {
            System.out.println("\n*** UserMenu ***");
            System.out.println("1. 이름 변경");
            System.out.println("2. 비밀번호 변경");
            System.out.println("9. 뒤로 가기");
            System.out.print("메뉴 번호 선택 : ");

            int menuNum = Integer.parseInt(sc.nextLine());
            switch (menuNum) {
                case 1: updateUserName();break;
                case 2: updateUserPassword();break;
                case 9: return;
                default: System.out.println("잘못 입력하셨습니다. 다시 입력해주세요.");
            }
        }
    }


    private Channel nowChannel;
    private void channelMenu() {
        while(true){
            System.out.println("[채널 선택]");
            listAllChannels();
            System.out.println("채널 이름 입력 : ");
            String selectChannel = sc.nextLine();
            if(!cs.checkName(selectChannel)){
                System.out.println("존재하지 않는 채널입니다. 다시 입력해주세요.");
                continue;
            } else {
                nowChannel = cs.read(selectChannel);
                break;
            }
        }

        while (true) {
            System.out.println("\n*** ["+nowChannel.getName()+"] ChannelMenu ***");
            System.out.println("1. 메시지 작성");
            System.out.println("2. 메시지 삭제");
            System.out.println("3. 메시지 검색");
            System.out.println("4. 전체 메시지 조회");
            System.out.println("5. 멤버 추가하기");
            System.out.println("6. 멤버 삭제하기");
            System.out.println("7. 멤버 검색");
            System.out.println("8. 채널 멤버 조회");
            System.out.println("9. 뒤로가기");
            System.out.print("메뉴 번호 선택 : ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": writeMessage();break;
                case "2": deleteMessage();break;
                case "3": searchMessage();break;
                case "4": listAllMessages();break;
                case "5": addChannelMember();break;
                case "6": removeChannelMember();break;
                case "7": searchChannelMember();break;
                case "8": listAllChannelMembers();break;
                case "9": nowChannel = null; return;
                default: System.out.println("잘못된 선택입니다.");
            }
        }
    }

    // startMenu

    private void doSignUp() {
        System.out.println("[회원가입]");

        String id = "";
        while (true) {
            System.out.print("ID : ");
            id = sc.nextLine();

            if (us.checkId(id)) {
                System.out.println("중복된 ID가 있습니다. 다시 입력해주세요.");
                continue;
            } else {
                break;
            }
        }

        System.out.print("비밀번호 : ");
        String pw = sc.nextLine();

        System.out.print("이름 : ");
        String name = sc.nextLine();

        User newUser = new User(id, pw, name);
        us.create(newUser);

        System.out.println((us.read(newUser.getUuid()).toString()));
        System.out.println("회원가입 성공!");
    }

    private User loginUser;
    private void doLogin() {
        System.out.println("[로그인]");

        String id = "";
        while (true) {
            System.out.print("ID : ");
            id = sc.nextLine();

            if (!us.checkId(id)) {
                System.out.println("존재하지 않는 ID입니다. 다시 입력해주세요.");
                continue;
            } else {
                break;
            }
        }

        String pw = "";
        while (true) {
            System.out.print("PW : ");
            pw = sc.nextLine();

            if (!us.read(id).getPw().equals(pw)) {
                System.out.println("비밀번호가 틀렸습니다. 다시 입력해주세요.");
                continue;
            } else {
                break;
            }
        }

        loginUser = us.read(id);
        System.out.println("로그인 성공!");
        System.out.println(loginUser.toString() + "환영합니다");

        mainMenu();
    }

    private void findUser() {
        System.out.println("[회원 조회]");
        System.out.println("키워드 입력 : ");
        String keyword = sc.nextLine();

        List<User> userList = us.readByKeyword(keyword);

        if(userList.isEmpty()){
            System.out.println("검색 결과가 없습니다.");
        } else {
            for (User user : userList) {
                System.out.println(user.toString());
            };
        }
    }
    // mainMenu

    private void createChannel() {
        System.out.println("[채널 생성]");

        String name = "";
        while (true) {
            System.out.print("채널명 : ");
            name = sc.nextLine();

            if (cs.checkName(name)) {
                System.out.println("중복된 이름이 있습니다. 다시 입력해주세요.");
                continue;
            } else {
                break;
            }
        }

        Channel newChannel = new Channel(name);
        cs.create(newChannel);
        cs.addMember(newChannel.getUuid(), loginUser);

        System.out.println(cs.read(newChannel.getUuid()).toString());
        System.out.println("채널 생성 성공!");
    }

    private void listAllChannels() {
        List<Channel> channels = cs.readAll();

        if(channels.isEmpty()){
            System.out.println("채널이 없습니다.");
        } else {
            for (Channel channel : channels) {
                System.out.println(channel.toString());
            }
        }
    }
    // === USER MENU HANDLERS ===

    private void updateUserName() {
        System.out.println("[이름 변경]");
        System.out.print("새 이름 : ");
        String newName = sc.nextLine();
        loginUser.updateName(newName);
        System.out.println("이름 변경 성공!");
    }

    private void updateUserPassword() {
        System.out.println("[비밀번호 변경]");
        System.out.print("새 비밀번호 : ");
        String newPw = sc.nextLine();
        loginUser.updatePw(newPw);
        System.out.println("비밀번호 변경 성공!");
    }
    // === CHANNEL MENU HANDLERS ===

    private void writeMessage() {
        System.out.println("[메시지 작성]");
        System.out.println("내용을 입력하세요 : ");
        String content = sc.nextLine();

        Message message = new Message(loginUser.getUuid(), nowChannel.getUuid(), content);
        ms.create(message);
        nowChannel.addMessage(message);

        System.out.println("메시지 등록 완료");
        System.out.println(ms.read(message.getId()).toString());
        System.out.println("(작성자: "+us.read(message.getAuthorId()).getName()+", 작성 시간 : "+message.getCreatedAt()+")");
    }

    private void deleteMessage() {
        System.out.println("[메시지 삭제]");
        List<Message> messages = ms.readAll();

        for (int i = 0; i < messages.size(); i++) {
            System.out.println("(" + i + ") " + messages.get(i).toString()+"작성자: "+us.read(messages.get(i).getAuthorId()).getName());
        }

        System.out.print("삭제할 메시지 번호 입력: ");
        int num = Integer.parseInt(sc.nextLine());

        if (num < 0 || num >= messages.size()) {
            System.out.println("번호가 잘못 입력되었습니다.");
            return;
        }

        Message selected = messages.get(num);

        if (!selected.getAuthorId().equals(loginUser.getUuid())) {
            System.out.println("메시지 삭제가 불가능합니다.");
            return;
        }

        ms.delete(selected.getId());
        nowChannel.removeMessage(selected.getId());
        System.out.println("메시지 삭제 완료");
    }

    private void searchMessage() {
        System.out.println("[메시지 검색]");
        System.out.println("검색할 메시지 내용 입력 : ");
        String token = sc.nextLine();

        List<Message> messages = ms.searchMessage(token);

        if(messages.isEmpty()){
            System.out.println("메시지가 없습니다.");
        } else {
            for (Message message : messages) {
                System.out.println(message.toString() + "작성자: " + us.read(message.getAuthorId()).getName());
            }
        }
    }

    private void listAllMessages() {
        System.out.println("[전체 메시지 조회]");
        List<Message> messages = ms.readAll();

        if(messages.isEmpty()){
            System.out.println("메시지가 없습니다.");
        } else {
            for (Message message : messages) {
                System.out.println(message.toString() + "작성자: " + us.read(message.getAuthorId()).getName());
            }
        }
    }

    private void addChannelMember() {
        System.out.println("[멤버 추가]");

        List<User> addMembers = us.readAll();

        for (int i = 0; i < addMembers.size(); i++) {
            System.out.println("(" + i + ") " + addMembers.get(i).toString());
        }

        System.out.print("추가할 멤버 번호 입력 : ");
        int num = Integer.parseInt(sc.nextLine());

        if (num < 0 || num >= addMembers.size()) {
            System.out.println("번호가 잘못 입력되었습니다.");
            return;
        }

        User selected = addMembers.get(num);

        List<User> channelMembers = nowChannel.getMembers();
        for (User channelMember : channelMembers) {
            if(channelMember.getUuid().equals(selected.getUuid())){
                System.out.println("이미 추가된 멤버입니다.");
                return;
            }
        }

        nowChannel.addMember(selected);
        System.out.println("멤버 추가 완료");
    }

    private void removeChannelMember() {
        System.out.println("[멤버 삭제]");

        List<User> channelMembers = nowChannel.getMembers();

        for (int i = 0; i < channelMembers.size(); i++) {
            System.out.println("(" + i + ") " + channelMembers.get(i).toString());
        }

        System.out.println("삭제할 멤버 번호 입력 : ");
        int num = Integer.parseInt(sc.nextLine());

        User selected = channelMembers.get(num);

        if(selected.getUuid().equals(loginUser.getUuid())){
            System.out.println("본인은 삭제할 수 없습니다.");
            return;
        }

        nowChannel.removeMember(selected.getUuid());
        System.out.println("멤버 삭제 완료");
    }

    private void searchChannelMember() {
        System.out.println("[멤버 검색]");
        System.out.println("키워드 입력 : ");
        String keyword = sc.nextLine();

        List<User> searchList = new ArrayList<>();
        for (User member : nowChannel.getMembers()) {
            if(member.getId().contains(keyword) || member.getName().contains(keyword)){
                searchList.add(member);
            }
        }

        if(searchList.isEmpty()){
            System.out.println("검색 결과가 없습니다.");
        } else {
            for(User user : searchList){
                System.out.println(user.toString());
            }
        }
    }

    private void listAllChannelMembers() {
        System.out.println("[채널 멤버 조회]");

        List<User> channelMembers = nowChannel.getMembers();

        for (int i = 0; i < channelMembers.size(); i++) {
            System.out.println("(" + i + ") " + channelMembers.get(i).toString());
        }
        System.out.println("총 "+channelMembers.size()+"명");
    }
}