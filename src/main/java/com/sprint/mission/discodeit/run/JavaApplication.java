package com.sprint.mission.discodeit.run;

import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.jcf.*;

import java.util.*;

public class JavaApplication {
    public static void main(String[] args) {
        UserService userService = new JCFUserService();
        MessageService messageService = new JCFMessageService();
        ChannelService channelService = new JCFChannelService();

        // 테스트
        userTest(userService);
        messageTest(messageService);
        channelService(channelService);
    }

    public static void userTest(UserService us) {
        System.out.println("\n┌─────────────────────────┐");
        System.out.println("│       User Test         │");
        System.out.println("└─────────────────────────┘");


        System.out.println("\n======** 생성 **======");
        User user1 = us.create("aaa", "1234", "홍길동");
        User user2 = us.create("bbb", "1234", "김길동");

        System.out.println("\n======** 단건 조회 **======");
        User readId = us.read(user1.getUuid());
        System.out.println(user1.equals(readId));

        System.out.println("\n======** 전체 조회 **======");
        System.out.println(us.readAll());
        System.out.println(us.readAll().size() == 2);

        System.out.println("\n======** 검색 **======");
        List<User> userList = us.searchByNameOrEmail("길동");
        for (User user : userList) {
            System.out.println(user.toString());
        }

        System.out.println("\n======** 수정 **======");
        User updated1 = us.updateName(user1.getUuid(), user1.getUuid(), "정길동");
        User updated2 = us.updatePw(user2.getUuid(), user2.getUuid(), "1111");
//        User updated3 = us.updatePw(user2.getUuid(), user1.getUuid(), "1225"); //실패(수정권한x)

        System.out.println(updated1);
        System.out.println(updated2);

        System.out.println("\n======** 삭제 **======");
        System.out.println(us.delete(user1.getUuid(), user1.getUuid()));
//        System.out.println(us.delete(user1.getUuid(), user2.getUuid())); //실패(삭제권한X)
        System.out.println(us.readAll());
        System.out.println(us.readAll().size() == 1);
    }


    public static void messageTest(MessageService ms) {
        System.out.println("\n┌─────────────────────────┐");
        System.out.println("│      Message Test       │");
        System.out.println("└─────────────────────────┘");


        User user1 = new User("aaa", "1234", "홍길동");
        Channel channel1 = new Channel(user1.getUuid(), "channel");
        Channel channel2 = new Channel(user1.getUuid(), "channel");

        System.out.println("\n======** 생성 **======");
        Message msg1 = ms.create(user1.getUuid(), channel1.getUuid(), "첫 번째 메시지");
        Message msg2 = ms.create(user1.getUuid(), channel1.getUuid(), "두 번째 메시지");
        Message msg3 = ms.create(user1.getUuid(), channel2.getUuid(), "세 번째 메시지");

        System.out.println("\n======** 단건 조회 **======");
        Message readMsg = ms.read(msg1.getUuid());
        System.out.println(msg1.equals(readMsg));

        System.out.println("\n======** 전체 조회 **======");
        System.out.println(ms.readAll());
        System.out.println(ms.readAll().size() == 2);

        System.out.println("\n======** 채널 메시지 조회 **======");
        System.out.println(ms.readChannelMessage(channel1.getUuid()));
        System.out.println(ms.readChannelMessage(channel2.getUuid()));

        System.out.println("\n======** 검색 **======");
        List<Message> messageList = ms.searchMessage("두");
        for (Message message : messageList) {
            System.out.println(message.toString());
        }

        System.out.println("\n======** 수정 **======");
        Message updated1 = ms.updateContent(msg1.getUuid(), user1.getUuid(), "첫 번째 메시지 수정");
        Message updated2 = ms.updateContent(msg2.getUuid(), user1.getUuid(), "두 번째 메시지 수정");
//        Message updated3 = ms.updateContent(msg1.getUuid(), user2.getUuid(),"두 번째 메시지 수정"); //실패(수정권한X)

        System.out.println(updated1);
        System.out.println(updated2);

        System.out.println("\n======** 삭제 **======");
        System.out.println(ms.delete(msg1.getUuid(), user1.getUuid()));
//        System.out.println(ms.delete(msg1.getUuid(), user2.getUuid())); //실패(삭제권한X)
        System.out.println(ms.readAll());
        System.out.println(ms.readAll().size() == 1);
    }

    public static void channelService(ChannelService cs) {
        System.out.println("\n┌─────────────────────────┐");
        System.out.println("│      Channel Test       │");
        System.out.println("└─────────────────────────┘");


        User user1 = new User("aaa", "1234", "홍길동");
        User user2 = new User("bbb", "1234", "김길동");
        User user3 = new User("ccc", "1234", "정길동");

        System.out.println("\n======** 생성 **======");
        Channel ch1 = cs.create(user1.getUuid(), "user1의 채널 ab");
        Channel ch2 = cs.create(user2.getUuid(), "user2의 채널 bc");
        Channel ch3 = cs.create(user3.getUuid(), "user2의 채널 ca");

        System.out.println("\n======** 검색 **======");
        List<Channel> channelList = cs.searchChannel("a");
        for (Channel channel : channelList) {
            System.out.println(channel.toString());
        }

        System.out.println("\n======** 단건 조회 **======");
        Channel readCh = cs.read(ch1.getUuid());
        System.out.println(ch1.equals(readCh));

        System.out.println("\n======** 전체 조회 **======");
        System.out.println(cs.readAll());
        System.out.println(cs.readAll().size() == 3);

        System.out.println("\n======** 수정 **======");
        Channel updated1 = cs.update(ch1.getUuid(), user1.getUuid(), "user1의 채널 수정");
//        Channel updated2 = cs.update(ch1.getUuid(), user2.getUuid(), "user1의 채널 수정"); //실패(수정권한X)
        System.out.println(updated1);

        System.out.println("\n======** 삭제 **======");
        System.out.println(cs.delete(ch1.getUuid(), user1.getUuid()));
//        System.out.println(channelService.delete(ch3.getUuid(), user1.getUuid())); //실패(식제권한X)

        System.out.println(cs.readAll());
        System.out.println(cs.readAll().size() == 2);


        // 채널 2,3만 있음
        System.out.println("\n======** 채널 - 멤버 조회 **======");
        System.out.println(cs.readMembers(ch2.getUuid()));
        System.out.println(cs.readMembers(ch3.getUuid()));

        System.out.println("\n======** 채널 - 멤버 추가 **======");
        cs.addMember(ch2.getUuid(), user1.getUuid(), user2.getUuid());
        cs.addMember(ch2.getUuid(), user3.getUuid(), user2.getUuid());
//        cs.addMember(ch2.getUuid(), user3.getUuid(),user1.getUuid()); //실패(추가권한X)
        System.out.println(cs.readMembers(ch2.getUuid()));
        System.out.println(cs.readMembers(ch2.getUuid()).size() == 3);

        System.out.println("\n======** 채널 - 멤버 삭제 **======");
        cs.removeMember(ch2.getUuid(), user1.getUuid(), user2.getUuid());
//        cs.removeMember(ch2.getUuid(), user1.getUuid(), user3.getUuid()); //실패(삭제권한X)
        System.out.println(cs.readMembers(ch2.getUuid()));
        System.out.println(cs.readMembers(ch2.getUuid()).size() == 2);
    }
}