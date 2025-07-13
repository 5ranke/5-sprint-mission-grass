package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

public class JavaApplication {
    public static void main(String[] args) {
        UserService userService = new JCFUserService();
        User user = new User("aa");
        userService.create(user);
        userService.create(user);

        System.out.println(userService.read(user.getId()).getName());
        System.out.println(userService.read(user.getId()).getId());
        System.out.println(userService.read(user.getId()).getCreatedAt());

        userService.update(user.getId(), "bb");
        System.out.println(userService.read(user.getId()).getName());
        System.out.println(userService.read(user.getId()).getUpdatedAt());

        userService.delete(user.getId());
        System.out.println(userService.read(user.getId()));
    }
}
