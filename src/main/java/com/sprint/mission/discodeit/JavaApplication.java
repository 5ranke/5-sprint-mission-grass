package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.jcf.JCFChannelService;

public class JavaApplication {
    public static void main(String[] args) {
        UserService userService = new JCFUserService();
        User user1 = new User("정수진");
        userService.create(user1); // 이름으로 등록
        System.out.println(userService.read(user1.getId()).toString()); //uuid로 검색


        userService.update(user1.getId(), "김수지"); // 이름 수정
        System.out.println(userService.read(user1.getId()).toString());

        userService.delete(user1.getId()); //uuid로 삭제
        System.out.println(userService.readAll().size());


        ChannelService channelService = new JCFChannelService();

        Channel channel = new Channel("메인 채널");
        channelService.create(channel);

        User user2 = new User("김순자");
        channelService.addMember(channel.getId(), user2);
        System.out.println(channelService.read(channel.getId()).toString());

        channelService.update(channel.getId(), "서브 채널");
        System.out.println(channelService.read(channel.getId()).toString());


        System.out.println("멤버 수: " + channelService.read(channel.getId()).getMembers().size()); // 1

        channelService.removeMember(channel.getId(), user2.getId());
        System.out.println("멤버 수 (삭제 후): " + channelService.read(channel.getId()).getMembers().size()); // 0



    }
}
