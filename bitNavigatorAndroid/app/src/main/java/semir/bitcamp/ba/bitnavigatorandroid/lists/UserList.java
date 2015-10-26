package semir.bitcamp.ba.bitnavigatorandroid.lists;

import java.util.ArrayList;
import java.util.List;

import semir.bitcamp.ba.bitnavigatorandroid.models.User;

/**
 * Created by semir.sahman on 22.10.15..
 */
public class UserList {

    private static UserList mUserList;

    public static UserList getInstance() {
        if (mUserList == null) {
            mUserList = new UserList();
        }
        return mUserList;
    }

    private List<User> userList;

    private UserList() {
        userList = new ArrayList<>();
    }

    public void add(User place) {
        userList.add(place);
    }

    public User get(int index) {
        return userList.get(index);
    }

    public int getSize() {
        return userList.size();
    }

    public void remove(User place) {
        userList.remove(place);
    }

    public List<User> getUserList(){
        return userList;
    }

}
