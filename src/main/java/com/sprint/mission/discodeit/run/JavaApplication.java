package com.sprint.mission.discodeit.run;

import com.sprint.mission.discodeit.entity.*;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.file.FileChannelRepository;
import com.sprint.mission.discodeit.repository.file.FileMessageRepository;
import com.sprint.mission.discodeit.repository.file.FileUserRepository;
import com.sprint.mission.discodeit.service.*;
import com.sprint.mission.discodeit.service.basic.BasicChannelService;
import com.sprint.mission.discodeit.service.basic.BasicMessageService;
import com.sprint.mission.discodeit.service.basic.BasicUserService;


public class JavaApplication {
    private static User setupUser(UserService userService) {
        return userService.create("userTest", "1234", "test");
    }

    private static Channel setupChannel(ChannelService channelService, User user) {
        return channelService.create(user.getId(), "[공지 채널]");
    }

    private static Message setupMessage(MessageService messageService, User user, Channel channel) {
        return messageService.create(user.getId(), channel.getId(), "공지사항");
    }

    public static void main(String[] args) {
        System.out.println("⚙️ ️setUp 시작...");
        UserRepository userRepository = new FileUserRepository();
        ChannelRepository channelRepository = new FileChannelRepository();
        MessageRepository messageRepository = new FileMessageRepository();

        UserService userService = new BasicUserService(userRepository);
        ChannelService channelService = new BasicChannelService(channelRepository);
        MessageService messageService = new BasicMessageService(messageRepository);

        User user = setupUser(userService);
        Channel channel = setupChannel(channelService, user);
        Message message = setupMessage(messageService, user, channel);
        System.out.println("️⚙️ ️setUp 완료...");

        System.out.println("\n✅ userTest 결과");
        userCreateTest(userService);
        userFindTest(userService, user);
        userUpdateTest(userService, user);
        userDeleteTest(userService, user);

        System.out.println("\n✅ channelTest 결과");
        channelCreateTest(channelService, user);
        channelFindTest(channelService, channel, user);
        channelUpdateTest(channelService, userService, channel, user);
        channelMemberTest(channelService, userService, channel, user);
        channelDeleteTest(channelService, userService, user);

        System.out.println("\n✅ messageTest 결과");
        messageCreateTest(messageService, channel, user);
        messageFindTest(messageService, channelService, channel, user);
        messageUpdateTest(messageService, userService, message, user);
        messageDeleteTest(messageService, userService, message, user, channel);

        System.out.println("\n⚙️ ️delete 시작...");
        userService.delete(user.getId(), user.getId());
        channelService.delete(channel.getId(), user.getId());
        messageService.delete(message.getId(), user.getId());
        System.out.println("⚙️ ️delete 완료...");

        System.out.println("\n✅ 전체 Test 결과\n- 사용자 수 : " + userService.findAll().size() +
                "명\n- 채널 수 : " + channelService.findAll().size() +
                "개\n- 메시지 수 : " + messageService.findAll().size() + "개");
    }

    private static void messageCreateTest(MessageService messageService, Channel channel, User user) {
        Message message1 = messageService.create(user.getId(), channel.getId(), "메시지1");
        System.out.println("메시지 생성 결과 : 내용 " + message1.getContent());

        messageService.delete(message1.getId(), user.getId());
    }

    private static void messageFindTest(MessageService messageService, ChannelService channelService, Channel channel, User user) {
        Message message2 = messageService.create(user.getId(), channel.getId(), "메시지2");

        Channel channel1 = channelService.create(user.getId(), "[채널1]");
        Message message3 = messageService.create(user.getId(), channel1.getId(), "메시지3");

        System.out.println("메시지 조회 결과 : " + messageService.find(message2.getId()).equals(message2));
        System.out.println("전체 메시지 조회 결과 : " + messageService.findAll().size() + "개");
        System.out.println("'2' 검색 결과 : " + messageService.findByContent("2").size() + "개");
        System.out.println("[채널1] 메시지 조회 : " + messageService.findChannelMessage(channel1.getId()).size() + "개");

        channelService.delete(channel1.getId(), user.getId());
        messageService.delete(message2.getId(), user.getId());
        messageService.delete(message3.getId(), user.getId());
    }

    private static void messageUpdateTest(MessageService messageService, UserService userService, Message message, User user) {
        messageService.updateContent(message.getId(), user.getId(), "updatedContent");
        System.out.println("메시지 내용 수정 결과 : " + messageService.find(message.getId()).getContent().equals("updatedContent"));

        User user1 = userService.create("userTest1", "1234", "test1");

        try {
            messageService.updateContent(message.getId(), user1.getId(), "updatedContent");
        } catch (Exception e) {
            System.out.println("다른 사용자가 메시지 내용 수정 접근 : " + e.getMessage());
        }

        userService.delete(user1.getId(), user1.getId());
    }

    private static void messageDeleteTest(MessageService messageService, UserService userService, Message message,
                                          User user, Channel channel) {
        User user2 = userService.create("userTest2", "1234", "test2");
        Message message4 = messageService.create(user.getId(), channel.getId(), "메시지4");

        try {
            messageService.delete(message4.getId(), user2.getId());
        } catch (Exception e) {
            System.out.println("다른 멤버가 메시지 삭제 접근 : " + e.getMessage());
        }

        messageService.delete(message4.getId(), user.getId());

        try {
            messageService.delete(message4.getId(), user.getId());
        } catch (Exception e) {
            System.out.println("채널 삭제 후 반복 삭제 시도: " + e.getMessage());
        }

        userService.delete(user2.getId(), user2.getId());
    }


    private static void channelCreateTest(ChannelService channelService, User user) {
        Channel channel1 = channelService.create(user.getId(), "[채널1]");
        System.out.println("채널 생성 결과 : 이름 " + channel1.getName());

        try {
            channelService.create(user.getId(), "[채널1]");
        } catch (Exception e) {
            System.out.println("중복 채널명 생성 결과 : " + e.getMessage());
        }
        channelService.delete(channel1.getId(), user.getId());
    }

    private static void channelFindTest(ChannelService channelService, Channel channel, User user) {
        Channel channel2 = channelService.create(user.getId(), "[채널2]");

        System.out.println("채널 조회 결과 : " + channelService.find(channel.getId()).equals(channel));
        System.out.println("전체 채널 조회 결과 : " + channelService.findAll().size() + "개");
        System.out.println("'공지' 검색 결과 : " + channelService.findChannel("공지").size() + "개");

        channelService.delete(channel2.getId(), user.getId());
    }

    private static void channelUpdateTest(ChannelService channelService, UserService userService, Channel channel, User user) {
        channelService.update(channel.getId(), user.getId(), "updatedName");
        System.out.println("채널명 수정 결과 : " + channelService.find(channel.getId()).getName().equals("updatedName"));

        User user1 = userService.create("userTest1", "1234", "test1");

        try {
            channelService.update(channel.getId(), user1.getId(), "updatedName");
        } catch (Exception e) {
            System.out.println("다른 사용자가 채널명 수정 접근 : " + e.getMessage());
        }

        userService.delete(user1.getId(), user1.getId());
    }

    private static void channelMemberTest(ChannelService channelService, UserService userService, Channel channel, User user) {
        User user2 = userService.create("userTest2", "1234", "test2");
        User user3 = userService.create("userTest3", "1234", "test3");

        channelService.addMember(channel.getId(), user2.getId(), user.getId());

        System.out.println("채널에 멤버 추가 : " + channelService.findMembers(channel.getId()).size() + "명");

        try {
            channelService.addMember(channel.getId(), user3.getId(), user2.getId());
        } catch (Exception e) {
            System.out.println("권한 없는 멤버가 다른 멤버 추가 시도 : " + e.getMessage());
        }

        try {
            channelService.addMember(channel.getId(), user2.getId(), user.getId());
        } catch (Exception e) {
            System.out.println("이미 있는 멤버 추가 시도 : " + e.getMessage());
        }

        channelService.addMember(channel.getId(), user3.getId(), user.getId());

        try {
            channelService.removeMember(channel.getId(), user3.getId(), user2.getId());
        } catch (Exception e) {
            System.out.println("권한 없는 멤버가 다른 멤버 삭제 시도 : " + e.getMessage());
        }

        channelService.removeMember(channel.getId(), user2.getId(), user.getId());
        channelService.removeMember(channel.getId(), user3.getId(), user.getId());

        System.out.println("채널 멤버 삭제 후 : " + channelService.findMembers(channel.getId()).size() + "명");

        userService.delete(user2.getId(), user2.getId());
        userService.delete(user3.getId(), user3.getId());
    }

    private static void channelDeleteTest(ChannelService channelService, UserService userService, User user) {
        User user4 = userService.create("userTest4", "1234", "test4");

        Channel channel3 = channelService.create(user.getId(), "[채널3]");
        channelService.addMember(channel3.getId(), user4.getId(), user.getId());

        try {
            channelService.delete(channel3.getId(), user4.getId());
        } catch (Exception e) {
            System.out.println("다른 멤버가 채널 삭제 접근 : " + e.getMessage());
        }

        channelService.delete(channel3.getId(), user.getId());

        try {
            channelService.delete(channel3.getId(), user.getId());
        } catch (Exception e) {
            System.out.println("채널 삭제 후 반복 삭제 시도: " + e.getMessage());
        }

        userService.delete(user4.getId(), user4.getId());
    }


    static void userCreateTest(UserService userService) {
        User user1 = userService.create("userTest1", "1234", "test1");
        System.out.println("사용자 생성 결과 : 이름 " + user1.getName() +
                ", id " + user1.getUserid() + ", pw " + user1.getPw());

        try {
            userService.create("userTest1", "1234", "test2");
        } catch (Exception e) {
            System.out.println("중복 id 생성 결과 : " + e.getMessage());
        }
        userService.delete(user1.getId(), user1.getId());
    }

    static void userFindTest(UserService userService, User user) {
        User user2 = userService.create("userTest2", "1234", "test2");

        System.out.println("사용자 조회 결과 : " + userService.find(user.getId()).equals(user));
        System.out.println("전체 사용자 조회 결과 : " + userService.findAll().size() + "명");

        userService.delete(user2.getId(), user2.getId());
    }

    static void userUpdateTest(UserService userService, User user) {
        userService.updateName(user.getId(), user.getId(), "updatedName");
        System.out.println("사용자 이름 수정 결과 : " + userService.find(user.getId()).getName().equals("updatedName"));

        userService.updatePw(user.getId(), user.getId(), "updatedPw");
        System.out.println("사용자 비번 수정 결과 : " + userService.find(user.getId()).getPw().equals("updatedPw"));

        User user3 = userService.create("userTest3", "1234", "test3");

        try {
            userService.updateName(user.getId(), user3.getId(), "updatedName");
        } catch (Exception e) {
            System.out.println("다른 사용자가 이름 수정 접근 : " + e.getMessage());
        }

        try {
            userService.updatePw(user.getId(), user3.getId(), "updatedPw");
        } catch (Exception e) {
            System.out.println("다른 사용자가 비번 수정 접근 : " + e.getMessage());
        }

        userService.delete(user3.getId(), user3.getId());
    }

    static void userDeleteTest(UserService userService, User user) {
        User user4 = userService.create("userTest4", "1234", "test4");
        try {
            userService.delete(user4.getId(), user.getId());
        } catch (Exception e) {
            System.out.println("다른 사용자가 계정 삭제 접근 : " + e.getMessage());
        }

        userService.delete(user4.getId(), user4.getId());
        try {
            userService.delete(user4.getId(), user4.getId());
        } catch (Exception e) {
            System.out.println("계정 삭제 후 반복 삭제 시도 : " + e.getMessage());
        }
    }

}