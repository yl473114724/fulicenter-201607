package cn.ucai.xm_fulicenter.dao;

import android.content.Context;

import cn.ucai.xm_fulicenter.bean.User;

/**
 * Created by yanglei on 2016/10/24.
 */

public class UserDao {
    public static final String USER_TABLE_NAME = "t_superwechat_user";
    public static final String USER_COLUMN_NAME = "m_user_name";
    public static final String USER_COLUMN_NICK = "m_user_nick";
    public static final String USER_COLUMN_AVATAR_ID = "m_user_avater_id";
    public static final String USER_COLUMN_AVATAR_TYPE = "m_user_avater_type";
    public static final String USER_COLUMN_AVATAR_PATH = "m_user_avater_path";
    public static final String USER_COLUMN_AVATAR_SUFFIX = "m_user_avater_suffix";
    public static final String USER_COLUMN_AVATAR_LASTUPDATE_TIME = "m_user_avater_lastupdate_time";
    DBManager dbManager;
    public UserDao(Context context) {
        DBManager.getInstance().onInit(context);
    }
    public boolean saveUser(User user) {
        return DBManager.getInstance().saveUser(user);
    }
    public User getUser(String username) {
        return DBManager.getInstance().getUser(username);
    }
    public boolean updateUser(User user) {
        return DBManager.getInstance().updateUser(user);
    }
}
